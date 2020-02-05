package com.dataserver.connector.mysql;

import com.dataserver.api.schema.JDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gongxinyi on 2018-11-01.
 */
@Service
public class MysqlDataSourceService {
    static Map<String, JdbcTemplate> jdbcTemplateMap = new HashMap<>();

    /**
     * 根据url缓存jdbctemplate
     *
     * @param jDataSource
     * @return
     */
    public JdbcTemplate getJdbcTemplate(JDataSource jDataSource) {
        if (!jdbcTemplateMap.containsKey(jDataSource.getUrl())) {
            jdbcTemplateMap.put(jDataSource.getUrl(), new JdbcTemplate(dataSource2Hikari(jDataSource)));
        }
        return jdbcTemplateMap.get(jDataSource.getUrl());
    }


    /**
     * jdatasource转换成HikariDataSource
     *
     * @param jDataSource
     * @return
     */
    public HikariDataSource dataSource2Hikari(JDataSource jDataSource) {
        HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
        ds.setJdbcUrl(jDataSource.getUrl());
        ds.setUsername(jDataSource.getUsername());
        ds.setPassword(jDataSource.getPassword());
        return ds;
    }
}
