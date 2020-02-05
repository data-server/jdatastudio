package com.dataserver.admin.sys;

import com.dataserver.admin.model.Permission;
import com.dataserver.admin.model.security.AuthorityName;
import com.dataserver.admin.security.User;
import com.dataserver.api.Filter;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import io.jsonwebtoken.lang.Collections;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 */
@Component
public class SysService {

    @Autowired
    MorphiaFactory morphiaFactory;

    /**
     * all the tenant cache the mongoClient
     */
    @Autowired
    Map<String, MongoClient> uriMongoMap;

    public Datastore getSysDataStore() {
        return morphiaFactory.get();
    }

    public Datastore getTenantDataStore() {

//        setJDataContext();
        JDataContext jDataContext = JDataContext.get();
        return getTenantDataStore(jDataContext.getTenant().getConnectionStr());
    }

    public Datastore getTenantDataStore(Tenant tenant) {
        return getTenantDataStore(tenant.getConnectionStr());
    }

    public Datastore getTenantDataStore(String mongoUri) {
        MongoClientURI mongoClientURI = getMongoClientURI(mongoUri);
        initMongoClient(mongoUri);
        return new Morphia().createDatastore(uriMongoMap.get(mongoUri), mongoClientURI.getDatabase());
    }

    public MongoCollection getTenantCollection(String entity) {
//        setJDataContext();
        JDataContext jDataContext = JDataContext.get();
        MongoClientURI mongoClientURI = getMongoClientURI(jDataContext.getTenant().getConnectionStr());
        initMongoClient(jDataContext.getTenant().getConnectionStr());
        return uriMongoMap.get(jDataContext.getTenant().getConnectionStr()).getDatabase(mongoClientURI.getDatabase()).getCollection(entity);
    }

    public MongoClient initMongoClient(String uri) {
        if (!uriMongoMap.containsKey(uri)) {
            MongoClientURI mongoClientURI = getMongoClientURI(uri);
            MongoClient client = new MongoClient(mongoClientURI);
            uriMongoMap.put(uri, client);
        }
        return uriMongoMap.get(uri);
    }

    public MongoClientURI getMongoClientURI(String uri) {
        return new MongoClientURI(uri);
    }

    public List<String> getRoles() {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Collection<GrantedAuthority> grantedAuthorities = authentication.getAuthorities();
        return grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    public Set<Permission> getPermissions() {
        User user = getTenantDataStore().get(User.class, SecurityContextHolder.getContext().getAuthentication().getName());
        return user.getPermissions();
    }


    public List<Filter> getFilters(String eid) {
        for (Permission permission : getPermissions()) {
            if (eid.equals(permission.getEid()) && !Collections.isEmpty(permission.getFilters())) {
                return permission.getFilters();
            }
        }
        return new ArrayList<>();
    }

    public boolean isAdmin() {
        return getRoles().contains(AuthorityName.ROLE_ADMIN.toString());
    }

}
