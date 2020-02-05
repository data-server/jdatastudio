package com.dataserver.api.schema;

import java.util.List;

/**
 * 获取schema
 *
 * @author gongxinyi
 * @date 2018-10-31
 */
public interface ISchemaService {
    List<Entity> getSchemas(JDataSource dataSource);
}
