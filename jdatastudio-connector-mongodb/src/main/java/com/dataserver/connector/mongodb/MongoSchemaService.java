package com.dataserver.connector.mongodb;

import com.dataserver.api.schema.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author gongxinyi
 * @date 2018-10-31
 */
@Slf4j
@Service
public class MongoSchemaService implements ISchemaService {
    @Autowired
    MongoDataSourceService mongoDataSourceService;

    @Override
    public List<Entity> getSchemas(JDataSource jDataSource) {
        MongoDatabase database = mongoDataSourceService.getMongoDatabase(jDataSource);
        MongoIterable<String> collections = database.listCollectionNames();
        List<Entity> entities = new ArrayList<>();
        for (String collection : collections) {
            log.info("collection:{}", collection);
            if ("system.indexes".equals(collection)) {
                continue;
            }
            MongoCollection dbCollection = database.getCollection(collection);
            FindIterable<Document> documents = dbCollection.find();
            if (!documents.iterator().hasNext()) {
                continue;
            }
            Document doc = documents.iterator().next();

            Entity entity = Entity.builder()
                    .name(collection)
                    .label(collection)
                    .build();
            List<Field> fields = mongoDoc2Fields(doc);
            entity.setFields(fields);
            entities.add(entity);
        }
        return entities;
    }

    /**
     * mongo document 2 field
     *
     * @param doc
     * @return
     */
    private List<Field> mongoDoc2Fields(Document doc) {
        return doc.entrySet().stream()
                .map(entry -> doc2Field(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private Field doc2Field(String k, Object o) {
        String clazz = o == null ? String.class.getSimpleName() : o.getClass().getSimpleName();

        Field field = Field.builder()
                .name(k)
                .label(k)
                .dbColumnType(mongoDocTypeComponentMap.getOrDefault(clazz, DbColumnType.varchar))
                .build();
        return field;
    }

    static Map<String, DbColumnType> mongoDocTypeComponentMap = new HashMap<>();

    static {
        mongoDocTypeComponentMap.put(String.class.getSimpleName(), DbColumnType.varchar);
        mongoDocTypeComponentMap.put(Integer.class.getSimpleName(), DbColumnType.number);
        mongoDocTypeComponentMap.put(Boolean.class.getSimpleName(), DbColumnType.bool);
        mongoDocTypeComponentMap.put(Double.class.getSimpleName(), DbColumnType.number);
        mongoDocTypeComponentMap.put(Long.class.getSimpleName(), DbColumnType.number);
    }
}
