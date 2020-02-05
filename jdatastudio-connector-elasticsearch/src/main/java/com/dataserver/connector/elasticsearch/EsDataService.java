package com.dataserver.connector.elasticsearch;

import com.dataserver.api.*;
import com.dataserver.api.schema.JDataSource;
import com.dataserver.connector.elasticsearch.util.JsonUtil;
import io.searchbox.core.Count;
import io.searchbox.core.CountResult;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.document.Document;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author gongxinyi
 * @date 2018-11-01
 */
@Slf4j
@Service
public class EsDataService implements IDataService {
    @Autowired
    EsDataSourceService esDataSourceService;

    @Override
    public List<Map<String, Object>> list(JDataSource jDataSource, String entityName, QueryParams queryParams) {

        List<Map<String, Object>> datas = new ArrayList<>();

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        QueryBuilder queryBuilder = buildQuery(queryParams);
        searchSourceBuilder.query(queryBuilder);

        /**
         * 分页
         */
        searchSourceBuilder.from(queryParams.getPagination().getOffset())
                .size(queryParams.getPagination().getLimit());

        /**
         * 排序
         */
        for (Sort sort : queryParams.getSorts()) {
            searchSourceBuilder.sort(sort.getField(), sort.getOrder() == OrderEnum.desc ? SortOrder.DESC : SortOrder.ASC);
        }

        searchSourceBuilder.fetchSource(true);

        log.info("searchSourceBuilder:{}", searchSourceBuilder.toString());

        Search search = new Search.Builder(searchSourceBuilder.toString())
                .addIndex(jDataSource.getIndexName())
                .addType(entityName)
                .build();

        try {
            SearchResult result = esDataSourceService.getJestClient(jDataSource).execute(search);
            result.getSourceAsObjectList(Document.class, false);

            List<SearchResult.Hit<Object, Void>> hits = result.getHits(Object.class);
            List<String> resultList = result.getSourceAsStringList();

            log.info("resultList:{}", resultList);

            for (int i = 0; i < resultList.size(); i++) {
                String recordStr = resultList.get(i);
                Map<String, Object> record = JsonUtil.parseJson(recordStr, Map.class);
                datas.add(record);
            }
        } catch (IOException e) {
            log.error("query es error!", e);
        }
        return datas;
    }

    @Override
    public Long count(JDataSource jDataSource, String entityName, QueryParams queryParams) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        QueryBuilder queryBuilder = buildQuery(queryParams);

        searchSourceBuilder.query(queryBuilder);

        Count count = new Count.Builder()
                .addIndex(jDataSource.getIndexName())
                .addType(entityName)
                .query(searchSourceBuilder.toString())
                .build();

        try {
            CountResult results = esDataSourceService.getJestClient(jDataSource).execute(count);
            return results.getCount().longValue();
        } catch (IOException e) {
            log.error("count error", e);
            return null;
        }
    }

    @Override
    public Map<String, Object> create(JDataSource jDataSource, String entityName, Map<String, Object> data) {
        return null;
    }

    @Override
    public List<Map<String, Object>> create(JDataSource jDataSource, String entityName, List<Map<String, Object>> datas) {
        return null;
    }

    @Override
    public Map<String, Object> update(JDataSource jDataSource, String entityName, Map<String, Object> data, List<Filter> filters) {
        return null;
    }

    protected QueryBuilder buildQuery(QueryParams queryParams) {
        BoolQueryBuilder boolqueryBuilder = QueryBuilders.boolQuery();
        queryParams.getFilters().forEach(filter -> {
            QueryBuilder queryBuilder = buildEsQuery(filter);
            boolqueryBuilder.must(queryBuilder);
        });
        return boolqueryBuilder;
    }

    protected QueryBuilder buildEsQuery(Filter filter) {
        QueryBuilder queryBuilder;
        switch (filter.getOperator()) {
            case eq:
                queryBuilder = QueryBuilders.termQuery(filter.getField(), filter.getValue());
                break;
            case gte:
                queryBuilder = QueryBuilders.rangeQuery(filter.getField()).gte(filter.getValue());
                break;
            case gt:
                queryBuilder = QueryBuilders.rangeQuery(filter.getField()).gt(filter.getValue());
                break;
            case lte:
                queryBuilder = QueryBuilders.rangeQuery(filter.getField()).lte(filter.getValue());
                break;
            case lt:
                queryBuilder = QueryBuilders.rangeQuery(filter.getField()).lt(filter.getValue());
                break;
            case like:
                queryBuilder = QueryBuilders.matchQuery(filter.getField(), String.valueOf(filter.getValue()));
                break;
            case in:
                queryBuilder = QueryBuilders.termsQuery(filter.getField(), filter.getValue().toString().split(","));
                break;
            default:
                queryBuilder = QueryBuilders.termQuery(filter.getField(), filter.getValue());
                break;
        }
        return queryBuilder;
    }
}
