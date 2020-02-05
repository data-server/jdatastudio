package com.dataserver.admin.security.repository;


import com.dataserver.admin.security.User;

/**
 * Created by stephan on 20.03.16.
 */
public interface UserRepository {
    User findByUsername(String username);
}
