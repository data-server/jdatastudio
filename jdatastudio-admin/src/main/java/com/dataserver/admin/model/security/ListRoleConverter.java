package com.dataserver.admin.model.security;

import com.dataserver.admin.security.Role;
import com.fasterxml.jackson.databind.util.StdConverter;

import java.util.List;
import java.util.stream.Collectors;

public class ListRoleConverter extends StdConverter<List<Role>, List<String>> {
    @Override
    public List<String> convert(List<Role> authorities) {
        return authorities.stream().map(Role::getId).collect(Collectors.toList());
    }
}
