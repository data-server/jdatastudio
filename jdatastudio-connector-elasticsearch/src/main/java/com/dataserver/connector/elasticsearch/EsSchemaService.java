package com.dataserver.connector.elasticsearch;

import com.dataserver.api.schema.*;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.searchbox.client.JestClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gongxinyi
 * @date 2018-11-01
 */
@Slf4j
@Service
public class EsSchemaService implements ISchemaService {

    @Autowired
    EsDataSourceService esDataSourceService;

    @Override
    public List<Entity> getSchemas(JDataSource jDataSource) {
        JestClient jestClient = esDataSourceService.getJestClient(jDataSource.getUrl());
        Map<String, Map<String, String>> schemaMap = null;
        try {
            JsonObject mapping = esDataSourceService.getMapping(jestClient, jDataSource);
            schemaMap = mapping2Map(mapping);
        } catch (IOException e) {
            throw new RuntimeException("not find index:" + jDataSource.getIndexName());
        }
        List<Entity> entityList = new ArrayList<>();
        schemaMap.entrySet().stream().forEach(entry -> {
            entityList.add(wrapEntityAndFields(entry.getKey(), entry.getValue()));
        });
        return entityList;
    }

    private Entity wrapEntityAndFields(String type, Map<String, String> fieldMap) {
        Entity entity = Entity.builder()
                .name(type)
                .label(type)
                .build();
        List<Field> fields = new ArrayList<>();
        fieldMap.forEach((k, v) -> {
            log.info("field name:{},type:{}", k, v);
            Field field = esType2Field(k, v);
            fields.add(field);
        });
        entity.setFields(fields);
        return entity;
    }

    private Map<String, Map<String, String>> mapping2Map(JsonObject mapping) {
        Map<String, Map<String, String>> schemaMap = new HashMap<>();
        mapping.entrySet().forEach(entry -> {
            String type = entry.getKey();
            JsonElement jsonElement = entry.getValue();

            Map<String, String> fieldMap = new HashMap<>();

            JsonObject properties = jsonElement.getAsJsonObject();
            JsonElement fieldTypeMap = properties.get("properties");
            getJsonProperties(fieldMap, "", fieldTypeMap);
            schemaMap.put(type, fieldMap);
        });
        return schemaMap;
    }

    private void getJsonProperties(Map<String, String> fieldMap, String fieldNamePrefix, JsonElement element) {
        if (!element.isJsonNull()) {
            element.getAsJsonObject().entrySet().forEach(fieldEntry -> {
                String fieldName = StringUtils.isEmpty(fieldNamePrefix) ? fieldEntry.getKey() : fieldNamePrefix + "." + fieldEntry.getKey();
                JsonElement fieldTypeElement = fieldEntry.getValue().getAsJsonObject().get("type");
                if (fieldEntry.getValue().getAsJsonObject().has("properties")) {
                    JsonElement propertiesElement = fieldEntry.getValue().getAsJsonObject().get("properties");
                    getJsonProperties(fieldMap, fieldName, propertiesElement);
                }
                String fieldType = fieldTypeElement == null ? "string" : fieldTypeElement.getAsString();
                fieldMap.put(fieldName, fieldType);
            });
        }
    }

    private Field esType2Field(String k, Object v) {
        return Field.builder()
                .name(k)
                .label(k)
                .dbColumnType(jestEsFieldTypeMap.getOrDefault(v.toString(), DbColumnType.varchar))
                .build();
    }

    static Map<String, DbColumnType> jestEsFieldTypeMap = new HashMap();

    static {
        jestEsFieldTypeMap.put("string", DbColumnType.varchar);
        jestEsFieldTypeMap.put("date", DbColumnType.datetime);

        jestEsFieldTypeMap.put("double", DbColumnType.number);
        jestEsFieldTypeMap.put("long", DbColumnType.number);
        jestEsFieldTypeMap.put("integer", DbColumnType.number);

    }
}
