package com.dataserver.admin.security;

import com.dataserver.admin.model.Permission;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mongodb.morphia.annotations.*;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(value = "_role", noClassnameStored = true)
@Data
@NoArgsConstructor
public class Role {
    @Id
    private String id;
    private String name;
    @Transient
    @Reference
    private List<User> users;
    @Embedded
    private Set<Permission> permissions;

    public Set<Permission> getPermissions() {
        if (permissions == null) {
            permissions = new HashSet<>();
        }
        return permissions;
    }

    public Role(String id) {
        this.id = id;
    }
}