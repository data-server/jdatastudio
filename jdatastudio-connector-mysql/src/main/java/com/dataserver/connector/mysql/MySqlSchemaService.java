package com.dataserver.connector.mysql;

import com.dataserver.api.schema.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import schemacrawler.schema.Catalog;
import schemacrawler.schema.Column;
import schemacrawler.schema.Table;
import schemacrawler.schemacrawler.RegularExpressionInclusionRule;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.utility.SchemaCrawlerUtility;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.JDBCType;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author gongxinyi
 * @date 2018-10-31
 */
@Slf4j
@Service
public class MySqlSchemaService implements ISchemaService {
    @Autowired
    MysqlDataSourceService mysqlDataSourceService;

    public Collection<Table> getDbSchemas(JDataSource jdataSource) throws Exception {
        DataSource dataSource = mysqlDataSourceService.dataSource2Hikari(jdataSource);
        return getDbSchemas(dataSource, dataSource.getConnection().getCatalog());
    }

    public Collection<Table> getDbSchemas(DataSource dataSource, String schema) throws Exception {
        Connection connection = dataSource.getConnection();

        final SchemaCrawlerOptions options = new SchemaCrawlerOptions();
        options.setSchemaInclusionRule(new RegularExpressionInclusionRule(schema));
        Catalog catalog = SchemaCrawlerUtility.getCatalog(connection, options);

        return catalog.getTables();
    }


    @Override
    public List<Entity> getSchemas(JDataSource jdataSource) {
        List<Entity> entities = new ArrayList<>();
        try {
            Collection<Table> tables = getDbSchemas(jdataSource);

            entities = tables.stream()
                    .map(table -> wrapEntityAndFields(table))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("sync schema error!!! datasource:{}", jdataSource, e);
            e.printStackTrace();
        }
        return entities;
    }

    /**
     * add new entity and its fields when entity is not exists
     *
     * @param table
     */
    private Entity wrapEntityAndFields(Table table) {
        Entity entity = Entity.builder()
                .name(table.getName())
                .label(StringUtils.isEmpty(table.getRemarks()) ? table.getName() : table.getRemarks())
                .build();

        List<Field> fields = table.getColumns()
                .stream()
                .map(column -> column2Field(column))
                .collect(Collectors.toList());

        entity.setFields(fields);

        return entity;
    }

    /**
     * 数据库字段转换成field对象
     *
     * @param column
     * @return
     */
    private Field column2Field(Column column) {
        log.info("column : {},{},{}", column, column.isGenerated(), column.isAutoIncremented());
        return Field.builder()
                .autoIncremented(column.isAutoIncremented())
                .partOfPrimaryKey(column.isPartOfPrimaryKey())
                .name(column.getName())
                .label(StringUtils.isEmpty(column.getRemarks()) ? column.getName() : column.getRemarks())
                .maxLength(column.getSize())
                .required(!column.isNullable())
                .defaultValue(column.getDefaultValue())
                .jdbcType(JDBCType.valueOf(column.getColumnDataType().getJavaSqlType().getJavaSqlType()))
                .dbColumnType(fieldTypeMap().get(JDBCType.valueOf(column.getColumnDataType().getJavaSqlType().getJavaSqlType())))
                .build();
    }

    @Bean
    public Map<JDBCType, DbColumnType> fieldTypeMap() {
        Map<JDBCType, DbColumnType> fieldTypeMap = new HashMap<JDBCType, DbColumnType>();
        fieldTypeMap.put(JDBCType.CHAR, DbColumnType.varchar);
        fieldTypeMap.put(JDBCType.VARCHAR, DbColumnType.varchar);
        fieldTypeMap.put(JDBCType.LONGNVARCHAR, DbColumnType.varchar);

        fieldTypeMap.put(JDBCType.NUMERIC, DbColumnType.number);
        fieldTypeMap.put(JDBCType.DECIMAL, DbColumnType.number);

        fieldTypeMap.put(JDBCType.BIT, DbColumnType.bool);

        fieldTypeMap.put(JDBCType.TINYINT, DbColumnType.number);
        fieldTypeMap.put(JDBCType.SMALLINT, DbColumnType.number);

        fieldTypeMap.put(JDBCType.INTEGER, DbColumnType.number);
        fieldTypeMap.put(JDBCType.BIGINT, DbColumnType.number);
        fieldTypeMap.put(JDBCType.REAL, DbColumnType.number);
        fieldTypeMap.put(JDBCType.FLOAT, DbColumnType.number);
        fieldTypeMap.put(JDBCType.DOUBLE, DbColumnType.number);

        fieldTypeMap.put(JDBCType.BINARY, DbColumnType.number);
        fieldTypeMap.put(JDBCType.VARBINARY, DbColumnType.number);
        fieldTypeMap.put(JDBCType.LONGVARBINARY, DbColumnType.number);
        fieldTypeMap.put(JDBCType.LONGVARCHAR, DbColumnType.number);
        fieldTypeMap.put(JDBCType.LONGNVARCHAR, DbColumnType.number);

        fieldTypeMap.put(JDBCType.DATE, DbColumnType.datetime);
        fieldTypeMap.put(JDBCType.TIME, DbColumnType.datetime);
        fieldTypeMap.put(JDBCType.TIMESTAMP, DbColumnType.datetime);
        return fieldTypeMap;
    }

}
