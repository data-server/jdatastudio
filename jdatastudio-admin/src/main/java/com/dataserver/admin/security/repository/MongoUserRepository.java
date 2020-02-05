package com.dataserver.admin.security.repository;

import com.dataserver.admin.security.User;
import com.dataserver.admin.sys.JDataContext;
import com.dataserver.admin.sys.SysService;
import com.dataserver.admin.sys.Tenant;
import com.dataserver.admin.sys.TenantUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Component;

/**
 * Created by gongxinyi on 2018/12/5.
 */
@Component
@Slf4j
public class MongoUserRepository implements UserRepository {
    @Autowired
    SysService sysService;

    @Autowired
    ConfigurableEnvironment env;

    @Value("${spring.data.mongodb.uri}")
    String uri;
    @Value("${jdatastudio.cloud}")
    boolean cloud;

    @Override
    public User findByUsername(String username) {

        Tenant tenant;

        if (cloud) {
            TenantUser tenantUser = sysService.getSysDataStore().get(TenantUser.class, username);
            if (tenantUser == null) {
                log.error("user not exists or exist!!! user :{}", username);
                return null;
            }
            tenant = tenantUser.getTenant();
        } else {
            tenant = new Tenant();
            tenant.setId("jdatastudio");
            tenant.setConnectionStr(uri);
        }
        JDataContext jDataContext = new JDataContext();
        jDataContext.setTenant(tenant);
        JDataContext.set(jDataContext);

        return sysService.getTenantDataStore().get(User.class, username);
    }
}
