package com.dataserver.admin.security.service;

import com.dataserver.admin.model.security.AuthorityName;

import java.io.Serializable;

/**
 * Created by stephan on 20.03.16.
 */
public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    private final String token;
    private final AuthorityName roleName;

    public JwtAuthenticationResponse(String token, AuthorityName roleName) {
        this.token = token;
        this.roleName = roleName;
    }

    public String getToken() {
        return this.token;
    }

    public AuthorityName getRoleName() {
        return roleName;
    }
}
