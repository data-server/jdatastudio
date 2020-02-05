package com.dataserver.admin;

import com.dataserver.admin.security.Role;
import com.dataserver.admin.security.User;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by gongxinyi on 2018/12/5.
 */
@Slf4j
public class UserServiceTest extends AbstractTest {
    @Test
    public void getUsers() throws Exception {
        final String token = extractToken(login("admin", "admin").andReturn());
        log.info("token:{}", token);
        mockMvc.perform(get("/users")
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void addUser() throws Exception {
        final String token = extractToken(login("admin", "admin").andReturn());
        User user = new User();
        user.setUsername("foo");
        user.setPassword("foo");
        user.setEnabled(true);
        Role role = new Role();
        role.setId("ROLE_ADMIN");
        List<Role> roles = new ArrayList();
        roles.add(role);
        user.setRoles(roles);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        mockMvc.perform(post("/users")
                .content(ow.writeValueAsString(user))
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
