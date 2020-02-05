package com.dataserver.dataapi.jsonserver;

import com.dataserver.dataapi.data.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by gongxinyi on 2018-11-02.
 */
@Component
public class JsonServerResponseWrapper implements ResponseWrapper {
    @Override
    public ResponseEntity listToResponseEntity(List results, Long totalCount) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("X-Total-Count", totalCount + "")
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .body(results);
    }
}
