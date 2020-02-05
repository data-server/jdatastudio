package com.dataserver.admin.sys;

import lombok.Data;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

/**
 * Created by gongxinyi on 2018/12/5.
 */
@Entity(value = "_tenantUser", noClassnameStored = true)
@Data
public class TenantUser {
    @Id
    private String username;
    @Reference
    private Tenant tenant;
}
