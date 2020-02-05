package com.dataserver.dataapi.postgrest;

import com.dataserver.api.*;
import com.dataserver.dataapi.data.AbstractRequestWrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author gongxinyi
 * @date 2018-11-02
 */
@Component
public class PostgrestRequestWrapper extends AbstractRequestWrapper {
    @Override
    protected Pagination wrapPagination(Map<String, Object> requestParams) {
        return Pagination.builder()
                .offset(Integer.parseInt(requestParams.getOrDefault(offset, "0").toString()))
                .limit(Integer.parseInt(requestParams.getOrDefault(limit, "10").toString()))
                .build();
    }

    @Override
    protected List<Sort> wrapSort(Map<String, Object> requestParams) {
        List<Sort> sorts = new ArrayList<>();
        String orderStr = requestParams.getOrDefault(order, "").toString();
        if (!StringUtils.isEmpty(orderStr)) {
            String[] orders = orderStr.split(",");
            for (String order : orders) {
                String[] orderArray = order.split("\\.");
                sorts.add(new Sort(orderArray[0], OrderEnum.valueOf(orderArray[1])));
            }
        }
        return sorts;
    }

    @Override
    protected String[] wrapSelect(Map<String, Object> requestParams) {
        return String.valueOf(requestParams.getOrDefault(select, allcolumns)).split(",");
    }

    @Override
    protected List<Filter> wrapFilters(Map<String, Object> requestParams) {
        List<Filter> filters = new ArrayList<>();
        requestParams.forEach((k, v) -> {
            if (!sysKeys.contains(k)) {
                String[] operatorAndValue = v.toString().split("\\.");
                OperatorEnum operatorEnum = OperatorEnum.eq;
                String value;
                if (operatorAndValue.length == 2) {
                    operatorEnum = OperatorEnum.valueOf(operatorAndValue[0]);
                    value = operatorAndValue[1];
                } else {
                    value = operatorAndValue[0];
                }
                filters.add(new Filter(k, operatorEnum, value));
            }
        });
        return filters;
    }

    public static final String select = "select";
    public static final String order = "order";
    public static final String limit = "limit";
    public static final String offset = "offset";
    public static final String and = "and";
    public static final String or = "or";
    public static final String allcolumns = "*";

    static List<String> sysKeys = Arrays.asList(offset,
            select,
            and,
            limit,
            order,
            or);

}
