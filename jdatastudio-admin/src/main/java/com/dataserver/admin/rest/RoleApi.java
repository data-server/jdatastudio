package com.dataserver.admin.rest;


import com.dataserver.admin.model.Permission;
import com.dataserver.admin.security.Role;
import com.dataserver.admin.security.User;
import com.dataserver.admin.sys.SysService;
import com.dataserver.admin.util.JsonUtil;
import com.dataserver.admin.util.ResponseUtil;
import com.dataserver.api.schema.Entity;
import com.dataserver.api.schema.Field;
import lombok.extern.slf4j.Slf4j;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author gongxinyi
 * @date 2018-11-08
 */
@Slf4j
@RestController
@RequestMapping("roles")
public class RoleApi {

    @Autowired
    SysService sysService;
    @Autowired
    RoleService roleService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Role>> getRoles(@RequestParam Map<String, Object> params) {
        FindOptions findOptions = ParamUtil.wrapFindOptions(params);
        Query<Role> query = sysService.getTenantDataStore().createQuery(Role.class);
        Long totalCount = query.count();
        List<Role> roles = query.asList(findOptions);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("X-Total-Count", totalCount + "")
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .body(roles);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Role> addRole(@RequestBody Role role) {
        roleService.saveRole(role);
        return ResponseEntity
                .ok(role);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Role> getRole(@PathVariable("id") String id) {
        return ResponseEntity
                .ok(roleService.getRole(id));
    }

    @PutMapping("/{roleId}/editPermission")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity saveRolePermission(@PathVariable("roleId") String roleId, @RequestBody Map<String, String> params) {
        log.info("params:{}", params);
        Permission permission = JsonUtil.parseJson(JsonUtil.toJsonStr(params), Permission.class);
        roleService.editRolePermission(roleId, permission);
        return ResponseEntity.ok().build();
    }
}
