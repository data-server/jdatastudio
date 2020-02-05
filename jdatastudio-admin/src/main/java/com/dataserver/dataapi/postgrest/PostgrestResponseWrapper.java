package com.dataserver.dataapi.postgrest;

import com.dataserver.dataapi.data.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @author gongxinyi
 * @date 2018-11-02
 */
@Component
public class PostgrestResponseWrapper implements ResponseWrapper {
    @Override
    public ResponseEntity listToResponseEntity(List results, Long totalCount) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Content-Range", totalCount + "")
                .header("Access-Control-Expose-Headers", "Content-Range")
                .body(results);
    }
}
