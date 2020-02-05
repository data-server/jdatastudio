package com.dataserver.admin.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.List;

/**
 * Created by gongxinyi on 2019-05-08.
 */
public class ResponseUtil {
    public static ResponseEntity listToResponseEntity(Collection results, Long totalCount) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("X-Total-Count", totalCount + "")
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .body(results);
    }

    public static ResponseEntity listToResponseEntity(Collection results) {
        return listToResponseEntity(results, new Long(results.size()));
    }
}
