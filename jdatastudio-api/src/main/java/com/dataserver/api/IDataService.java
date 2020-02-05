package com.dataserver.api;

import com.dataserver.api.schema.JDataSource;

import java.util.List;
import java.util.Map;

/**
 * 数据服务
 *
 * @author gongxinyi
 * @date 2018-09-27
 */
public interface IDataService {

    /**
     * 查询数据列表
     *
     * @param jDataSource
     * @param entityName
     * @param queryParams
     * @return
     */
    List<Map<String, Object>> list(JDataSource jDataSource, String entityName, QueryParams queryParams);

    /**
     * count
     *
     * @param jDataSource
     * @param entityName
     * @param queryParams
     * @return
     */
    Long count(JDataSource jDataSource, String entityName, QueryParams queryParams);

    /**
     * 保存数据
     *
     * @param jDataSource
     * @param entityName
     * @param data
     * @return
     */
    Map<String, Object> create(JDataSource jDataSource, String entityName, Map<String, Object> data);

    /**
     * 保存数据
     *
     * @param jDataSource
     * @param entityName
     * @param datas
     * @return
     */
    List<Map<String, Object>> create(JDataSource jDataSource, String entityName, List<Map<String, Object>> datas);

    /**
     * @param jDataSource
     * @param entityName
     * @param data
     * @param filters
     * @return
     */
    Map<String, Object> update(JDataSource jDataSource, String entityName, Map<String, Object> data, List<Filter> filters);

}
