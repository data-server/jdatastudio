package com.dataserver.connector.mongodb;

import com.dataserver.api.*;
import com.dataserver.api.schema.JDataSource;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author gongxinyi
 * @date 2018-10-31
 */
@Slf4j
@Service
public class MongoDataService implements IDataService {

    @Autowired
    MongoDataSourceService mongoDataSourceService;

    @Override
    public List<Map<String, Object>> list(JDataSource jDataSource, String entityName, QueryParams queryParams) {

        DBObject query = getQuery(queryParams.getFilters());

        log.info("query :{}", query);

        MongoCursor<Document> mongoCursor = mongoDataSourceService.getMongoDatabase(jDataSource)
                .getCollection(entityName)
                .find((Bson) query)
                .sort(sort(queryParams))
                .skip(queryParams.getPagination().getOffset())
                .limit(queryParams.getPagination().getLimit())
                .iterator();

        List<Map<String, Object>> dataList = new LinkedList<>();
        try {
            while (mongoCursor.hasNext()) {
                Document doc = mongoCursor.next();
                dataList.add(doc);
            }
        } finally {
            mongoCursor.close();
        }
        return dataList;
    }

    @Override
    public Long count(JDataSource jDataSource, String entityName, QueryParams queryParams) {
        DBObject query = getQuery(queryParams.getFilters());
        return mongoDataSourceService.getMongoDatabase(jDataSource).getCollection(entityName).count((Bson) query);
    }

    @Override
    public Map<String, Object> create(JDataSource jDataSource, String entityName, Map<String, Object> data) {
        MongoCollection collection = mongoDataSourceService.getMongoDatabase(jDataSource).getCollection(entityName);

        Document document = new Document(data);

        collection.insertOne(document);

        return document;
    }

    @Override
    public List<Map<String, Object>> create(JDataSource jDataSource, String entityName, List<Map<String, Object>> datas) {
        MongoCollection collection = mongoDataSourceService.getMongoDatabase(jDataSource).getCollection(entityName);

        List<Document> documents = datas.stream()
                .map(data -> new Document(data))
                .collect(Collectors.toList());

        collection.insertMany(documents);

        return datas;
    }

    @Override
    public Map<String, Object> update(JDataSource jDataSource, String entityName, Map<String, Object> data, List<Filter> filters) {
        DBObject query = getQuery(filters);

        MongoCollection collection = mongoDataSourceService.getMongoDatabase(jDataSource).getCollection(entityName);

        BasicDBObject document = new BasicDBObject(data);

        Document update = new Document();
        update.append("$set", document);

        collection.updateOne((BasicDBObject)query, update);
        return data;
    }

    /**
     * wrap query filter
     *
     * @param filters
     * @return
     */
    private DBObject getQuery(List<Filter> filters) {

        QueryBuilder query = new QueryBuilder();


        // common filter
        filters.forEach(filter -> {
            buildQuery(query, filter);
        });


        return query.get();
    }

    /**
     * sort
     *
     * @param queryParams
     * @return
     */
    private Bson sort(QueryParams queryParams) {
        BasicDBObject sortDbObject = new BasicDBObject();
        for (Sort sort : queryParams.getSorts()) {
            sortDbObject.put(sort.getField(), sort.getOrder() == OrderEnum.desc ? -1 : 1);
        }
        return sortDbObject;
    }

    private void buildQuery(QueryBuilder query, Filter filter) {
        log.info("filter:{}", filter);
        QueryBuilder qb = QueryBuilder.start().put(filter.getField());
        switch (filter.getOperator()) {
            case eq:
                qb.is(filter.getValue());
                break;
            case gte:
                qb.greaterThanEquals(filter.getValue());
                break;
            case gt:
                qb.greaterThan(filter.getValue());
                break;
            case lte:
                qb.lessThanEquals(filter.getValue());
                break;
            case lt:
                qb.lessThan(filter.getValue());
                break;
            case like:
                qb.text(filter.getValue().toString());
                break;
            case in:
                String[] param = filter.getValue().toString().split(",");
                qb.in(param);
                break;
            default:
                qb.is(filter.getValue());
                break;
        }
        query.and(qb.get());
    }
}
