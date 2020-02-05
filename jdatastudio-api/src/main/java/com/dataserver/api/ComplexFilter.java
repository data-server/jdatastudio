package com.dataserver.api;

import lombok.Data;

import java.util.List;

/**
 * 复杂组合
 *
 * @author gongxinyi
 * @date 2018-09-27
 */
@Data
public class ComplexFilter {
    LogicEnum logicEnum;
    List<Filters> filtersList;
}
