package com.dataserver.admin.model;

import com.dataserver.admin.security.service.CRUDPermission;
import com.dataserver.api.Filter;
import com.dataserver.api.schema.Field;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Transient;

import java.util.List;
import java.util.Set;

/**
 * @author gongxinyi
 * @date 2019-03-06
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embedded
public class Permission {
    private String id;
    private String name;
    private boolean r;
    private boolean u;
    private boolean c;
    private boolean d;

    /**
     * 数据权限表达式
     */
    private List<Filter> filters;

    @Transient
    private List<Field> fields;

    @Transient
    private String relatedTarget;
    @Transient
    private Set<Permission> related;

    private String label;

    public String getEid() {
        return id.substring(id.lastIndexOf("_") + 1);
    }

    public boolean allow(CRUDPermission crudPermission) {
        return (crudPermission.equals(CRUDPermission.r) && r) || (crudPermission.equals(CRUDPermission.u) && u) || (crudPermission.equals(CRUDPermission.c) && c);
    }

    public boolean own() {
        return c || r || u || d;
    }
}
