package com.dataserver.api.schema;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by gongxinyi on 2018-11-01.
 */
@Data
@NoArgsConstructor
@org.mongodb.morphia.annotations.Entity(value = "_datasource", noClassnameStored = true)
public class JDataSource {
    @Id
    private String id;
    private String name;
    private String url;
    private String username;
    private String password;
    private DbTypeEnum dbType;

    // for elasticsearch
    private String indexName;
    private String clusterName;
}
