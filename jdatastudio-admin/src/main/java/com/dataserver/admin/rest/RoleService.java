package com.dataserver.admin.rest;


import com.dataserver.admin.config.Constants;
import com.dataserver.admin.model.Permission;
import com.dataserver.admin.security.Role;
import com.dataserver.admin.security.User;
import com.dataserver.admin.sys.SequenceService;
import com.dataserver.admin.sys.SysService;
import com.dataserver.api.schema.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by gongxinyi on 2018-11-12.
 */
@Service
public class RoleService {
    @Autowired
    SysService sysService;
    @Autowired
    SequenceService sequenceService;
    
    public void editRolePermission(String roleId, Permission permission) {
        Role role = sysService.getTenantDataStore().get(Role.class, roleId);
        role.getPermissions().removeIf(ownerPermission -> ownerPermission.getId().equals(permission.getId()));
        role.getPermissions().add(permission);
        sysService.getTenantDataStore().save(role);
    }

    public void saveRole(Role role){
        if(StringUtils.isEmpty(role.getId())){
            role.setId(sequenceService.getNextSequence(Constants.SYS_COL_ROLE + Constants._id));
        }
        sysService.getTenantDataStore().save(role);
    }

    public Role getRole(String id){
        Role role = sysService.getTenantDataStore().get(Role.class, id);

        List<User> users = sysService.getTenantDataStore().createQuery(User.class).field("roles").hasThisOne(new Role(id)).asList();
        role.setUsers(users);

        List<Entity> entities = sysService.getTenantDataStore().createQuery(Entity.class).asList();

        Set<Permission> permissions = entities.stream()
                .filter(entity -> !role.getPermissions().stream()
                        .filter(permission -> permission.getEid().equals(entity.getId()))
                        .findAny()
                        .isPresent())
                .map(entity -> wrapPermission(entity, id))
                .collect(Collectors.toSet());

        role.getPermissions().addAll(permissions);
        return role;
    }

    private Permission wrapPermission(Entity entity, String rid) {
        return Permission.builder().id(rid + "_" + entity.getId()).name(entity.getId()).label(entity.getLabel()).build();
    }
}
