package com.dataserver.api;

import lombok.Builder;
import lombok.Data;

/**
 * 分页
 *
 * @author gongxinyi
 * @date 2018-09-27
 */
@Data
@Builder
public class Pagination {
    private int offset;
    private int limit;
}
