package com.dataserver.dataapi.data;

import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 *
 * @author gongxinyi
 * @date 2018-11-02
 */
public interface ResponseWrapper {
    ResponseEntity listToResponseEntity(List results, Long totalCount);
}
