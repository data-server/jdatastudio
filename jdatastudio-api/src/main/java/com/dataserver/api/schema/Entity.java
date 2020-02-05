package com.dataserver.api.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by gongxinyi on 2018-10-15.
 */
@Data
@Builder
@org.mongodb.morphia.annotations.Entity(value = "_entity", noClassnameStored = true)
public class Entity {

    @Id
    private String id;
    private String name;
    private String label;
    @Embedded
    private List<Field> fields;
    @Reference
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private JDataSource jDataSource;
    @Tolerate
    public Entity() {
    }
    public List<Field> getPrimaryFields() {
        return fields.stream().filter(field -> field.isPartOfPrimaryKey()).collect(Collectors.toList());
    }

}
