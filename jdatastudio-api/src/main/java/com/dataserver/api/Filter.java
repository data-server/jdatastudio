package com.dataserver.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 过滤器
 *
 * @author gongxinyi
 * @date 2018-09-27
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Filter {
    private String field;
    private OperatorEnum operator;
    private String value;
}
