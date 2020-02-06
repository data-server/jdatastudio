package com.dataserver.dataapi;

import com.aliyun.oss.OSSClient;
import com.dataserver.api.IDataService;
import com.dataserver.api.schema.DbTypeEnum;
import com.dataserver.connector.elasticsearch.EsDataService;
import com.dataserver.connector.mongodb.MongoDataService;
import com.dataserver.connector.mysql.MySqlDataService;
import com.dataserver.dataapi.data.AbstractRequestWrapper;
import com.dataserver.dataapi.data.ApiStandard;
import com.dataserver.dataapi.data.FileStorageApi;
import com.dataserver.dataapi.data.ResponseWrapper;
import com.dataserver.dataapi.jsonserver.JsonServerRequestWrapper;
import com.dataserver.dataapi.jsonserver.JsonServerResponseWrapper;
import com.dataserver.dataapi.postgrest.PostgrestRequestWrapper;
import com.dataserver.dataapi.postgrest.PostgrestResponseWrapper;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gongxinyi on 2018-11-01.
 */
@Component
public class SysConfig {

    @Bean
    public Map<ApiStandard, Class<? extends AbstractRequestWrapper>> apiRequestWrapperMap() {
        Map<ApiStandard, Class<? extends AbstractRequestWrapper>> apiRequestWrapperMap = new HashMap<>();
        apiRequestWrapperMap.put(ApiStandard.postgrest, PostgrestRequestWrapper.class);
        apiRequestWrapperMap.put(ApiStandard.jsonserver, JsonServerRequestWrapper.class);
        return apiRequestWrapperMap;
    }

    @Bean
    public Map<ApiStandard, Class<? extends ResponseWrapper>> apiResponseWrapperMap() {
        Map<ApiStandard, Class<? extends ResponseWrapper>> apiResponseWrapperMap = new HashMap<>();
        apiResponseWrapperMap.put(ApiStandard.postgrest, PostgrestResponseWrapper.class);
        apiResponseWrapperMap.put(ApiStandard.jsonserver, JsonServerResponseWrapper.class);
        return apiResponseWrapperMap;
    }

    @Bean
    public Map<DbTypeEnum, Class<? extends IDataService>> dataServiceMap() {
        Map<DbTypeEnum, Class<? extends IDataService>> dbTypeEnumClassMap = new HashMap<>();
        dbTypeEnumClassMap.put(DbTypeEnum.mysql, MySqlDataService.class);
        dbTypeEnumClassMap.put(DbTypeEnum.mongo, MongoDataService.class);
        dbTypeEnumClassMap.put(DbTypeEnum.elasticsearch, EsDataService.class);
        return dbTypeEnumClassMap;
    }


    @Value("${jdatastudio.storage.endpoint}")
    String endpoint;

    @Bean
    public OSSClient cosClient() {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "***";
        String accessKeySecret = "***";

        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        return ossClient;
    }

    @Autowired
    private BeanFactory beanFactory;

    @Value("${jdatastudio.storage.strategy}")
    String storageStrategy;

    @Bean
    public FileStorageApi fileStorageApi() {
        return (FileStorageApi) beanFactory.getBean(storageStrategy);
    }
}
