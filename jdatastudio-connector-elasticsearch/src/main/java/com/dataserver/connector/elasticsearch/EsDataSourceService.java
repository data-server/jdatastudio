package com.dataserver.connector.elasticsearch;

import com.dataserver.api.schema.JDataSource;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.indices.mapping.GetMapping;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by gongxinyi on 2018-11-01.
 */
@Service
public class EsDataSourceService {
    static Map<String, JestClient> jestClientMap = new HashMap<>();

    public JestClient getJestClient(JDataSource jDataSource) {
        String nodes = jDataSource.getUrl();
        if (!jestClientMap.containsKey(nodes)) {
            jestClientMap.put(nodes, getJestClient(nodes));
        }
        return jestClientMap.get(nodes);
    }


    public JestClient getJestClient(String connectionUrl) {
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder(connectionUrl)
                .gson(new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd HH:mm:ss")
                        .create())
                .multiThreaded(true)
                .discoveryEnabled(true)
                .discoveryFrequency(60, TimeUnit.SECONDS)
                .readTimeout(20000)
                .build());
        return factory.getObject();
    }

    public JsonObject getMapping(JestClient client, JDataSource jDataSource)
            throws IOException {
        String index = jDataSource.getIndexName();
        Map<String, Object> headers = new HashMap();
        final JestResult result = client.execute(
                new GetMapping.Builder().addIndex(index).setHeader(headers).build()
        );
        final JsonObject indexRoot = result.getJsonObject().getAsJsonObject(index);
        if (indexRoot == null) {
            return null;
        }
        final JsonObject mappingsJson = indexRoot.getAsJsonObject("mappings");
        if (mappingsJson == null) {
            return null;
        }
        return mappingsJson.getAsJsonObject();
    }
}
