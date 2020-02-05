package com.dataserver.api;

import lombok.Data;

import java.util.List;

/**
 * 过滤组合
 *
 * @author gongxinyi
 * @date 2018-09-27
 */
@Data
public class Filters {
    LogicEnum logicEnum;
    List<Filter> filterList;
}
