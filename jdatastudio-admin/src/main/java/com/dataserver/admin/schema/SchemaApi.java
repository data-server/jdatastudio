package com.dataserver.admin.schema;

import com.dataserver.admin.model.Permission;
import com.dataserver.admin.sys.SysService;
import com.dataserver.admin.util.ResponseUtil;
import com.dataserver.api.schema.ComponentType;
import com.dataserver.api.schema.Entity;
import com.dataserver.api.schema.Field;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by gongxinyi on 2018/11/28.
 */
@Slf4j
@RestController
public class SchemaApi {

    @Autowired
    SchemaService schemaService;
    @Autowired
    SysService sysService;
    @GetMapping("/_entity")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Entity>> list(@RequestParam final Map<String, Object> params) {
        String dataSourceId = (String) params.get("jdatasource");

        List<Entity> entities = schemaService.getEntities(dataSourceId);

        return ResponseUtil.listToResponseEntity(entities);
    }

    @PutMapping("/_entity/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Entity> editEntity(@PathVariable("id") String id, @RequestBody Entity entity) {
        return ResponseEntity.ok(schemaService.saveEntity(entity));
    }

    @GetMapping("/_entity/{id}")
    public ResponseEntity<Entity> getEntity(@PathVariable("id") String id) {
        return ResponseEntity.ok(schemaService.getEntity(id));
    }

    @GetMapping("/_field")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Field>> getFields(@RequestParam("eid") String eid) {

        List<Field> fields = schemaService.getEntity(eid).getFields();

        return ResponseUtil.listToResponseEntity(fields);
    }

    @GetMapping("/_field/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Field> getField(@PathVariable("id") String id) {
        String eid = id.substring(0, id.indexOf("_"));
        Optional<Field> optionalField = schemaService.getEntity(eid).getFields().stream().filter(field -> field.getId().equals(id)).findFirst();
        return ResponseEntity.ok(optionalField.get());
    }

    @PutMapping("/_field/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity editField(@PathVariable("id") String id,@RequestBody Field field) {
        String eid = id.substring(0, id.indexOf("_"));
        Entity entity = schemaService.getEntity(eid);
        entity.getFields().removeIf(field1 -> field1.getId().equals(id));
        entity.getFields().add(field);
        schemaService.saveEntity(entity);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/schemas")
    @PreAuthorize("authenticated")
    public ResponseEntity<Set<Permission>> getSchemas() {
        Set<Permission> permissions = sysService.getPermissions();

        Set<Permission> myPermissions = permissions.stream().filter(permission -> permission.own()).map(permission -> {
            Entity entity = schemaService.getEntity(permission.getEid());
            permission.setFields(entity.getFields());
            return permission;
        }).collect(Collectors.toSet());


        Map<String,Set<Permission>> relation = new HashMap<>();

        myPermissions.forEach(permission -> permission.getFields().forEach(field -> {
            if(ComponentType.Reference.equals(field.getComponent())){
                if(!relation.containsKey(field.getReference())){
                    relation.put(field.getReference(),new HashSet<>());
                }
                Permission permission1 = new Permission();
                BeanUtils.copyProperties(permission,permission1);
                permission1.setRelatedTarget(field.getName());
                relation.get(field.getReference()).add(permission1);
            }
        }));

        myPermissions.forEach(permission -> {
            if(relation.containsKey(permission.getEid())){
                permission.setRelated(relation.get(permission.getEid()));
            }
        });

        return ResponseEntity.ok(myPermissions);
    }
}
