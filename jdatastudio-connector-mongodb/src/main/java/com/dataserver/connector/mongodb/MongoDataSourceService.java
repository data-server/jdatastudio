package com.dataserver.connector.mongodb;

import com.dataserver.api.schema.JDataSource;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gongxinyi
 * @date 2018-11-01
 */
@Service
public class MongoDataSourceService {
    static Map<String, MongoClient> uriMongoClientMap = new HashMap<>();

    /**
     * 获取mongoclient
     *
     * @param jDataSource
     * @return
     */
    public MongoClient getMongoClient(JDataSource jDataSource) {
        if (!uriMongoClientMap.containsKey(jDataSource.getUrl())) {
            MongoClientURI mongoClientURI = new MongoClientURI(jDataSource.getUrl());
            MongoClient client = new MongoClient(mongoClientURI);
            uriMongoClientMap.put(jDataSource.getUrl(), client);
        }
        return uriMongoClientMap.get(jDataSource.getUrl());
    }

    /**
     * @param jDataSource
     * @return
     */
    public MongoDatabase getMongoDatabase(JDataSource jDataSource) {
        return getMongoClient(jDataSource).getDatabase(getDatabase(jDataSource));
    }

    /**
     * 根据url 获取 database
     *
     * @param dataSource
     * @return
     */
    private String getDatabase(JDataSource dataSource) {
        MongoClientURI mongoClientURI = new MongoClientURI(dataSource.getUrl());
        return mongoClientURI.getDatabase();
    }
}
