package com.dataserver.admin.security;

import com.dataserver.admin.model.Permission;
import com.dataserver.admin.model.security.ListRoleConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity(value = "_user", noClassnameStored = true)
@Data
public class User {
    private String id;
    @Id
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String email;
    private Boolean enabled;
    private Date lastPasswordResetDate;
    @Reference
    @JsonDeserialize(contentAs = Role.class)
    @JsonSerialize(converter = ListRoleConverter.class)
    private List<Role> roles;

    public Set<Permission> getPermissions(){
        return roles.stream().map(role -> role.getPermissions()).flatMap(Set::stream).collect(Collectors.toSet());
    }
    public String getId() {
        return username;
    }
}
