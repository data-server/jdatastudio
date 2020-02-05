package com.dataserver.api;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 请求参数
 *
 * @author gongxinyi
 * @date 2018-09-27
 */
@Data
@Builder
public class QueryParams {
    private String q;
    private String[] select;
    private List<Sort> sorts;
    private ComplexFilter complexFilter;
    private List<Filter> filters;
    private Pagination pagination;
}
