package com.dataserver.admin.rest;


import com.dataserver.admin.security.User;
import com.dataserver.admin.sys.SysService;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by gongxinyi on 2018-11-08.
 */
@RestController
@RequestMapping("users")
public class UserApi {


    @Autowired
    UserService userService;
    @Autowired
    SysService sysService;
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getUsers(@RequestParam Map<String, Object> params) {
        FindOptions findOptions = ParamUtil.wrapFindOptions(params);
        String username = (String) params.get("username");
        Query<User> query = sysService.getTenantDataStore().createQuery(User.class);

        if (!StringUtils.isEmpty(username)) {
            query.field("username").contains(username);
        }
        Long totalCount = query.count();
        List<User> users = query.asList(findOptions);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("X-Total-Count", totalCount + "")
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .body(users);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> addUser(@RequestBody final User user) {
        userService.addUser(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> editUser(@PathVariable("id") String id, @RequestBody User user) {
        userService.editUser(id,user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity getUser(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

//    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity deleteUser(@PathVariable("id") Long id) {
//        userService.deleteUser(id);
//        return ResponseEntity.ok().build();
//    }
}
