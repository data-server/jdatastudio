package com.dataserver.admin.rest;

import com.dataserver.admin.security.Role;
import com.dataserver.admin.security.User;
import com.dataserver.admin.sys.JDataContext;
import com.dataserver.admin.sys.SysService;
import com.dataserver.admin.sys.Tenant;
import com.dataserver.admin.sys.TenantUser;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by gongxinyi on 2018/12/5.
 */
@Service
public class UserService {
    @Autowired
    SysService sysService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        sysService.getTenantDataStore().save(user);

        TenantUser tenantUser = new TenantUser();
        tenantUser.setTenant(JDataContext.get().getTenant());
        tenantUser.setUsername(user.getUsername());
        sysService.getSysDataStore().save(tenantUser);
    }

    public User getUser(String id) {
        User user = sysService.getTenantDataStore().get(User.class, id);
        return user;
    }

    public void editUser(String id, User user) {
        final Query<User> userQuery = sysService.getTenantDataStore().createQuery(User.class).field("username").equal(id);
        final UpdateOperations<User> updateOperations = sysService.getTenantDataStore().createUpdateOperations(User.class)
                .set("roles", user.getRoles())
                .set("enabled", user.getEnabled());

        if (!StringUtils.isEmpty(user.getPassword())) {
            updateOperations.set("password", passwordEncoder.encode(user.getPassword()));
            updateOperations.set("lastPasswordResetDate", new Date());
        }

        sysService.getTenantDataStore().update(userQuery, updateOperations);
    }

    public void addTenantUser(Tenant tenant, User user) {
        TenantUser tenantUser = new TenantUser();
        tenantUser.setTenant(tenant);
        tenantUser.setUsername(user.getUsername());
        sysService.getSysDataStore().save(tenantUser);
    }


    @Value("${jdatastudio.cloud}")
    boolean cloud;
    @Value("${spring.data.mongodb.uri}")
    String uri;
    @Value("${jdatastudio.tenant.uri}")
    String tenantUri;
    @Value("${jdatastudio.tenant.admin}")
    String tenantAdmin;

    /**
     * 初始化租户和用户
     */
    @PostConstruct
    public void initUser() {
        if (!cloud && !existsTenantUser()){
            Tenant tenant = new Tenant();
            tenant.setId("admin");
            tenant.setConnectionStr(uri);
            initUser(tenant);
        }
        if(cloud && !StringUtils.isEmpty(tenantUri) && !existsTenantUser(tenantAdmin)){
            Tenant tenant = new Tenant();
            tenant.setId(tenantAdmin);
            tenant.setConnectionStr(tenantUri);
            initUser(tenant);
        }
    }

    private boolean existsTenantUser() {
        return sysService.getSysDataStore().createQuery(TenantUser.class).count()!=0;
    }

    private boolean existsTenantUser(String tenantAdmin) {
        return sysService.getSysDataStore().createQuery(TenantUser.class).field("username").equal(tenantAdmin).count()!=0;
    }

    public void initUser(Tenant tenant) {
        sysService.getSysDataStore().save(tenant);

        User user = new User();
        user.setUsername(tenant.getId());
        user.setPassword("admin");
        addTenantUser(tenant, user);

        initUserAndRole(tenant, user);
    }

    public void initUserAndRole(Tenant tenant, User user) {
        Datastore datastore = sysService.getTenantDataStore(tenant);
        Role role = new Role();
        role.setId("ROLE_ADMIN");
        role.setName("ROLE_ADMIN");
        datastore.save(role);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        List<Role> roles = new ArrayList<Role>();
        roles.add(role);
        user.setRoles(roles);
        datastore.save(user);
    }

}
