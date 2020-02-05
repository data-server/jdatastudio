package com.dataserver.connector.mysql;

import com.dataserver.api.*;
import com.dataserver.api.schema.DbColumnType;
import com.dataserver.api.schema.JDataSource;
import com.healthmarketscience.sqlbuilder.*;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbColumn;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author gongxinyi
 * @date 2018-10-15
 */
@Slf4j
@Service
public class MySqlDataService implements IDataService {

    @Autowired
    MysqlDataSourceService mysqlDataSourceService;

    @Override
    public List<Map<String, Object>> list(JDataSource dataSource, String entityName, QueryParams queryParams) {
        LinkedList<Object> args = new LinkedList<>();
        String sql = sqlBuild(entityName, args, queryParams, false);
        log.info("sql {} , args:{}", sql, args);
        return mysqlDataSourceService.getJdbcTemplate(dataSource).queryForList(sql, args.toArray());
    }

    @Override
    public Long count(JDataSource dataSource, String entityName, QueryParams queryParams) {
        LinkedList<Object> args = new LinkedList<>();
        String sql = sqlBuild(entityName, args, queryParams, true);
        log.info("sql {} , args:{}", sql, args);
        return mysqlDataSourceService.getJdbcTemplate(dataSource).queryForObject(sql, args.toArray(), Long.class);
    }

    @Override
    public Map<String, Object> create(JDataSource jDataSource, String entityName, Map<String, Object> data) {
        List<Object> args = new LinkedList<>();
        DbTable table = getDbTable(entityName);
        InsertQuery insertCustomerQuery =
                new InsertQuery(table);
        data.forEach((k, v) -> {
            args.add(v);
            DbColumn dbColumn = new DbColumn(table, k, DbColumnType.varchar.name());
            insertCustomerQuery.addPreparedColumns(dbColumn);
        });

        mysqlDataSourceService.getJdbcTemplate(jDataSource).update(insertCustomerQuery.toString(), args.toArray());
        return data;
    }

    @Override
    public List<Map<String, Object>> create(JDataSource jDataSource, String entityName, List<Map<String, Object>> datas) {
        List<Object[]> argsArray = new LinkedList<>();
        DbTable table = getDbTable(entityName);
        InsertQuery insertCustomerQuery =
                new InsertQuery(table);

        datas.get(0).forEach((k, v) -> {
            DbColumn dbColumn = new DbColumn(table, k, DbColumnType.varchar.name());
            insertCustomerQuery.addPreparedColumns(dbColumn);
        });

        for (Map<String, Object> data : datas) {
            List<Object> args = new LinkedList<>();
            data.forEach((k, v) -> {
                args.add(v);
            });
            argsArray.add(args.toArray());
        }

        mysqlDataSourceService.getJdbcTemplate(jDataSource).batchUpdate(insertCustomerQuery.toString(), argsArray);
        return datas;
    }

    @Override
    public Map<String, Object> update(JDataSource jDataSource, String entityName, Map<String, Object> data, List<Filter> filters) {
        DbTable table = getDbTable(entityName);
        LinkedList<Object> args = new LinkedList<>();

        UpdateQuery updateQuery = new UpdateQuery(table);

        /**
         *  update set
         */
        data.forEach((k, v) -> {
            args.add(v);
            QueryPreparer preparer = new QueryPreparer();
            Object setValue = preparer.addStaticPlaceHolder(v);
            DbColumn dbColumn = new DbColumn(table, k, DbColumnType.varchar.name());
            updateQuery.addSetClause(dbColumn, setValue);
        });

        /**
         * update where
         */
        buildUpdateWhereClause(updateQuery, args, table, filters);

        String sql = updateQuery.toString();

        log.info("sql {} , args:{}", sql, args);
        mysqlDataSourceService.getJdbcTemplate(jDataSource).update(sql, args.toArray());
        return data;
    }

    public void buildCount(SelectQuery selectQuery) {
        selectQuery
                .addCustomColumns(FunctionCall.countAll());
    }

    public void buildSelect(SelectQuery selectQuery, DbTable table, QueryParams queryParams) {
        String[] selects = queryParams.getSelect();
        for (String field : selects) {
            if (Constants.allcolumns.equals(field)) {
                selectQuery.addAllColumns();
            } else {
                DbColumn dbColumn = new DbColumn(table, field, DbColumnType.varchar.name());
                selectQuery.addColumns(dbColumn);
            }
        }
    }

    protected String sqlBuild(String entityName, LinkedList<Object> args, QueryParams queryParams, boolean count) {

        /**
         * 构建rdb select query
         */
        SelectQuery selectQuery = new SelectQuery();

        DbTable table = getDbTable(entityName);

        if (count) {
            buildCount(selectQuery);
        } else {
            buildSelect(selectQuery, table, queryParams);
        }

        selectQuery.addFromTable(table);


        /**
         * 构建query
         */
        buildSelectWhereClause(selectQuery, args, table, queryParams.getFilters());

        /**
         * 排序
         */
        sort(selectQuery, table, queryParams);

        return count ? selectQuery.toString() : (selectQuery.toString() + pagination(queryParams, args));
    }

    private String pagination(QueryParams queryParams, List<Object> args) {
        args.add(queryParams.getPagination().getLimit());
        args.add(queryParams.getPagination().getOffset());
        return " limit ? offset ?";
    }

    private void sort(SelectQuery selectQuery, DbTable table, QueryParams queryParams) {
        if (!CollectionUtils.isEmpty(queryParams.getSorts())) {
            for (Sort sort : queryParams.getSorts()) {
                DbColumn dbColumn = new DbColumn(table, sort.getField(), DbColumnType.varchar.name());
                selectQuery.addOrdering(dbColumn, sort.getOrder() == OrderEnum.asc ? OrderObject.Dir.ASCENDING : OrderObject.Dir.DESCENDING);
            }
        }
    }

    public DbTable getDbTable(String tableName) {
        DbSpec spec = new DbSpec();
        DbSchema dbSchema = new DbSchema(spec, null);
        return new DbTable(dbSchema, tableName);
    }

    private void buildSelectWhereClause(SelectQuery selectQuery, LinkedList<Object> args, DbTable table, List<Filter> filters) {
        for (Filter filter : filters) {
            buildWhereClause(selectQuery, table, filter, filter.getField());
            if(filter.getOperator()!=OperatorEnum.in){
                args.add(filter.getValue());
            }
        }
    }

    private void buildUpdateWhereClause(UpdateQuery updateQuery, LinkedList<Object> args, DbTable table, List<Filter> filters) {
        for (Filter filter : filters) {
            buildWhereClause(updateQuery, table, filter, filter.getField());
            if(filter.getOperator()!=OperatorEnum.in){
                args.add(filter.getValue());
            }
        }
    }

    /**
     * build select query
     *
     * @param selectQuery
     * @param table
     * @param filter
     * @param fieldName
     */
    private void buildWhereClause(SelectQuery selectQuery, DbTable table, Filter filter, String fieldName) {
        log.info("filter:{}", filter);
        QueryPreparer preparer = new QueryPreparer();
        Object filterValue = preparer.addStaticPlaceHolder(filter.getValue());
        DbColumn dbColumn = new DbColumn(table, fieldName, DbColumnType.varchar.name());
        switch (filter.getOperator()) {
            case eq:
                selectQuery.addCondition(BinaryCondition.equalTo(dbColumn, filterValue));
                break;
            case gte:
                selectQuery.addCondition(BinaryCondition.greaterThanOrEq(dbColumn, filterValue));
                break;
            case gt:
                selectQuery.addCondition(BinaryCondition.greaterThan(dbColumn, filterValue));
                break;
            case lte:
                selectQuery.addCondition(BinaryCondition.lessThanOrEq(dbColumn, filterValue));
                break;
            case lt:
                selectQuery.addCondition(BinaryCondition.lessThan(dbColumn, filterValue));
                break;
            case like:
                selectQuery.addCondition(BinaryCondition.like(dbColumn, filterValue));
                break;
            case in:
                String[] param = filter.getValue().toString().split(",");
                selectQuery.addCondition(new InCondition(dbColumn,param));
                break;
            default:
                selectQuery.addCondition(BinaryCondition.equalTo(dbColumn, filterValue));
                break;
        }
    }

    private void buildWhereClause(UpdateQuery updateQuery, DbTable table, Filter filter, String fieldName) {
        log.info("filter:{}", filter);
        QueryPreparer preparer = new QueryPreparer();
        Object filterValue = preparer.addStaticPlaceHolder(filter.getValue());
        DbColumn dbColumn = new DbColumn(table, fieldName, DbColumnType.varchar.name());
        switch (filter.getOperator()) {
            case eq:
                updateQuery.addCondition(BinaryCondition.equalTo(dbColumn, filterValue));
                break;
            case gte:
                updateQuery.addCondition(BinaryCondition.greaterThanOrEq(dbColumn, filterValue));
                break;
            case gt:
                updateQuery.addCondition(BinaryCondition.greaterThan(dbColumn, filterValue));
                break;
            case lte:
                updateQuery.addCondition(BinaryCondition.lessThanOrEq(dbColumn, filterValue));
                break;
            case lt:
                updateQuery.addCondition(BinaryCondition.lessThan(dbColumn, filterValue));
                break;
            case like:
                updateQuery.addCondition(BinaryCondition.like(dbColumn, filterValue));
                break;
            case in:
                String[] param = filter.getValue().toString().split(",");
                updateQuery.addCondition(new InCondition(dbColumn, param));
                break;
            default:
                updateQuery.addCondition(BinaryCondition.equalTo(dbColumn, filterValue));
                break;
        }
    }

}
