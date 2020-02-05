package com.dataserver.admin.rest;

import org.mongodb.morphia.query.FindOptions;

import java.util.Map;

/**
 * Created by gongxinyi on 2018-11-08.
 */
public class ParamUtil {
    public static FindOptions wrapFindOptions(Map<String, Object> params) {
        Integer start = Integer.parseInt(params.getOrDefault(_start, "0").toString());
        Integer end = Integer.parseInt(params.getOrDefault(_end, "10").toString());
        return new FindOptions().skip(start).limit(end-start);
    }

    public static final String _start = "_start";
    public static final String _end = "_end";
    public static final String _sort = "_sort";
    public static final String _order = "_order";
}
