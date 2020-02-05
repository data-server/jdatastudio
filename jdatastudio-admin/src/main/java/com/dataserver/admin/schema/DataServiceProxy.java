package com.dataserver.admin.schema;

import com.dataserver.api.IDataService;
import com.dataserver.api.schema.DbTypeEnum;
import com.dataserver.api.schema.ISchemaService;
import com.dataserver.api.schema.JDataSource;
import com.dataserver.dataapi.data.AbstractRequestWrapper;
import com.dataserver.dataapi.data.ApiStandard;
import com.dataserver.dataapi.data.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author gongxinyi
 * @date 2018-10-31
 */
@Component
public class DataServiceProxy {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    Map<DbTypeEnum, Class<? extends ISchemaService>> schemaServiceMap;
    @Autowired
    Map<DbTypeEnum, Class<? extends IDataService>> dataServiceMap;
    @Autowired
    Map<ApiStandard, Class<? extends AbstractRequestWrapper>> apiRequestWrapperMap;
    @Autowired
    Map<ApiStandard, Class<? extends ResponseWrapper>> apiResponseWrapperMap;

    public IDataService getDataService(JDataSource jDataSource) {
        return applicationContext.getBean(dataServiceMap.get(jDataSource.getDbType()));
    }

    public AbstractRequestWrapper getRequestWrapper(ApiStandard apiStandard) {
        return applicationContext.getBean(apiRequestWrapperMap.get(apiStandard), AbstractRequestWrapper.class);
    }

    public ResponseWrapper getResponseWrapper(ApiStandard apiStandard) {
        return applicationContext.getBean(apiResponseWrapperMap.get(apiStandard), ResponseWrapper.class);
    }
    public ISchemaService getSchemaService(JDataSource jDataSource) {
        return applicationContext.getBean(schemaServiceMap.get(jDataSource.getDbType()));
    }
}
