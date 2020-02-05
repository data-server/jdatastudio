package com.dataserver.admin.security.service.impl;

import com.dataserver.admin.model.Permission;
import com.dataserver.admin.model.security.AuthorityName;
import com.dataserver.admin.security.service.CRUDPermission;
import com.dataserver.admin.security.service.SecurityService;
import com.dataserver.admin.sys.SysService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author gongxinyi
 * @date 2017-08-30
 */
@Service
@Slf4j
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    SysService sysService;

    // TODO 权限授权还需要完善
    @Override
    public Boolean hasProtectedAccess(String eid, CRUDPermission permission) {
        Set<Permission> permissions = sysService.getPermissions();
        Optional<Permission> result = permissions.stream()
                .filter(authPermission -> authPermission.getEid().equals(eid) && authPermission.allow(permission))
                .findAny();
        return result.isPresent();
    }

}
