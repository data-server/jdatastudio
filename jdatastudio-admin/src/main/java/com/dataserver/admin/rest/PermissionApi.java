package com.dataserver.admin.rest;

import com.dataserver.admin.model.Permission;
import com.dataserver.admin.security.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("_permission")
public class PermissionApi {
  @Autowired
  RoleService roleService;

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Permission> getPermission(@PathVariable("id") String id) {
    String rid = id.substring(0, id.lastIndexOf("_"));
    Set<Permission> permissions = roleService.getRole(rid).getPermissions();
    Optional<Permission> optionalPermission = permissions.stream().filter(permission -> permission.getId().equals(id)).findFirst();
    return ResponseEntity.ok(optionalPermission.get());
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity editPermission(@PathVariable("id") String id,@RequestBody Permission permission) {
    String rid = id.substring(0, id.lastIndexOf("_"));
    Role role = roleService.getRole(rid);
    role.getPermissions().removeIf(permission1 -> permission1.getId().equals(id));
    role.getPermissions().add(permission);
    roleService.saveRole(role);
    return ResponseEntity.ok().build();
  }
}
