package com.dataserver.admin.sys;

import lombok.Data;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by gongxinyi on 2018/11/27.
 */
@Data
@Entity(value = "_tenant", noClassnameStored = true)
public class Tenant {
    @Id
    private String id;
    private String connectionStr;
}
