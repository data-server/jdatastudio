package com.dataserver.admin.schema;

import com.dataserver.admin.config.Constants;
import com.dataserver.admin.sys.SequenceService;
import com.dataserver.admin.sys.SysService;
import com.dataserver.api.schema.Entity;
import com.dataserver.api.schema.Field;
import com.dataserver.api.schema.JDataSource;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by gongxinyi on 2018-11-19.
 */
@Component
public class SchemaService {

    @Autowired
    DataServiceProxy dataServiceProxy;
    @Autowired
    SequenceService sequenceService;
    @Autowired
    SysService sysService;

    public Entity getEntity(String eid) {
        return sysService.getTenantDataStore().get(Entity.class, eid);
    }

    public Entity saveEntity(Entity entity) {
        sysService.getTenantDataStore().merge(entity);
        return entity;
    }

    public void syncSchemas(JDataSource jDataSource) {
        List<Entity> persistentEntities = getEntities(jDataSource);
        List<Entity> entities = dataServiceProxy.getSchemaService(jDataSource).getSchemas(jDataSource);
        List<Field> newFields = new ArrayList<>();
        entities.forEach(outerEntity -> {
            Optional<Entity> optionalEntity = persistentEntities.stream()
                    .filter(persistentEntity -> persistentEntity.getName().equals(outerEntity.getName()))
                    .findAny();
            if (!optionalEntity.isPresent()) {
                String id = sequenceService.getNextSequence(Constants.SYS_COL_ENTITY + Constants._id);
                outerEntity.setId(Constants.ENTITY_NAME_PREFIX + id);
                outerEntity.getFields().forEach(field -> field.setId(outerEntity.getId() + "_" + field.getName()));
                outerEntity.setJDataSource(jDataSource);
                sysService.getTenantDataStore().save(outerEntity);
            } else {
                Entity findEntity = optionalEntity.get();
                outerEntity.getFields().forEach(outerField -> {
                    if (!CollectionUtils.isEmpty(findEntity.getFields())) {
                        Optional<Field> findField = findEntity.getFields().stream().filter(field -> field.getName().equals(outerField.getName())).findAny();
                        if (!findField.isPresent()) {
                            Field field = outerField;
                            field.setId(findEntity.getId() + "_" + field.getName());
                            newFields.add(field);
                        }
                    }
                });
                if (!CollectionUtils.isEmpty(newFields)) {
                    Query<Entity> updateQuery = sysService.getTenantDataStore().createQuery(Entity.class).field("id").equal(findEntity.getId());
                    UpdateOperations<Entity> ops = sysService.getTenantDataStore()
                            .createUpdateOperations(Entity.class)
                            .addToSet("fields", newFields);
                    sysService.getTenantDataStore().update(updateQuery, ops);
                }
            }
        });
    }

    public List<Entity> getEntities(JDataSource jDataSource) {
        if (StringUtils.isEmpty(jDataSource.getId())) {
            return sysService.getTenantDataStore().createQuery(Entity.class).asList();
        }else{
            return sysService.getTenantDataStore().createQuery(Entity.class).field("jDataSource").equal(jDataSource).asList();
        }

    }

    public List<Entity> getEntities(String jDataSourceId) {
        JDataSource jDataSource = new JDataSource();
        jDataSource.setId(jDataSourceId);
        return getEntities(jDataSource);
    }
}
