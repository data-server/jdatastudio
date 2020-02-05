package com.dataserver.api;

import lombok.Data;

/**
 * 排序
 *
 * @author gongxinyi
 * @date 2018-09-27
 */
@Data
public class Sort {
    private String field;
    private OrderEnum order;

    public Sort(String field, OrderEnum order) {
        this.field = field;
        this.order = order;
    }
}
