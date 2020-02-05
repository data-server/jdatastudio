package com.dataserver.dataapi.jsonserver;

import com.dataserver.api.*;
import com.dataserver.dataapi.data.AbstractRequestWrapper;
import io.jsonwebtoken.lang.Collections;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author gongxinyi
 * @date 2018-11-02
 */
@Component
public class JsonServerRequestWrapper extends AbstractRequestWrapper {
    @Override
    protected Pagination wrapPagination(Map<String, Object> requestParams) {
        Integer start = Integer.parseInt(requestParams.getOrDefault(_start, "0").toString());
        Integer end = Integer.parseInt(requestParams.getOrDefault(_end, "10").toString());
        return Pagination.builder()
                .offset(start)
                .limit(end - start)
                .build();
    }

    @Override
    protected List<Sort> wrapSort(Map<String, Object> requestParams) {
        List<Sort> sorts = new ArrayList<>();
        String[] sort = requestParams.getOrDefault(_sort, "").toString().split(",");
        String orderStr = requestParams.getOrDefault(_order, "").toString();
        if (!StringUtils.isEmpty(orderStr)) {
            String[] order = orderStr.split(",");
            if (sort.length > 0) {
                for (int i = 0; i < sort.length; i++) {
                    sorts.add(new Sort(sort[i], OrderEnum.valueOf(order[i].toLowerCase())));
                }
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
        List<Filter> filters = requestParams
                .entrySet()
                .stream()
                .filter(p -> !sysField(p.getKey()))
                .map(e -> {
                    String k = e.getKey();
                    Object v = e.getValue();
                    if (endsWithOperator(k)) {
                        int idx = k.lastIndexOf("_");
                        String key = k.substring(0, idx);
                        String operator = k.substring(idx + 1);
                        return new Filter(key, OperatorEnum.valueOf(operator), v.toString());
                    } else {
                        //查询字符串中包含逗号，则为多选查询，需要使用IN
                        if (v.toString().split(",").length > 1) {
                            return new Filter(k, OperatorEnum.in, v.toString());
                        } else {
                            return new Filter(k, OperatorEnum.eq, v.toString());
                        }
                    }
                }).collect(Collectors.toList());

        return Collections.isEmpty(filters) ? new ArrayList<>() : filters;
    }

    private boolean endsWithOperator(String field) {
        return field.endsWith(_in) ||
                field.endsWith(_like) ||
                field.endsWith(_gte) ||
                field.endsWith(_gt) ||
                field.endsWith(_lt) ||
                field.endsWith(_lte);
    }

    private boolean sysField(String key) {
        return key.equals(_start) ||
                key.equals(_end) ||
                key.equals(_sort) ||
                key.equals(_order);
    }

    public static final String select = "select";
    public static final String allcolumns = "*";
    public static final String _start = "_start";
    public static final String _end = "_end";
    public static final String _sort = "_sort";
    public static final String _order = "_order";
    public static final String _lte = "_lte";
    public static final String _lt = "_lt";
    public static final String _gt = "_gt";
    public static final String _gte = "_gte";
    public static final String _like = "_like";
    public static final String _in = "_in";
}
