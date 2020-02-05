package com.dataserver.admin.schema;

import com.dataserver.admin.config.Constants;
import com.dataserver.admin.sys.SequenceService;
import com.dataserver.admin.sys.SysService;
import com.dataserver.api.schema.JDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by gongxinyi on 2018/11/27.
 */
@Service
public class DataSourceService {
    @Autowired
    SequenceService sequenceService;
    @Autowired
    SysService sysService;

    @Autowired
    SchemaService schemaService;

    public JDataSource save(JDataSource jDataSource) {
        if (StringUtils.isEmpty(jDataSource.getId())) {
            String id = sequenceService.getNextSequence(Constants.SYS_COL_DS + Constants._id);
            jDataSource.setId(id);
            schemaService.syncSchemas(jDataSource);
        }
        sysService.getTenantDataStore().save(jDataSource);
        return jDataSource;
    }

    public JDataSource get(String id) {
        return sysService.getTenantDataStore().get(JDataSource.class,id);
    }

    public List<JDataSource> getAll() {
        return sysService.getTenantDataStore().createQuery(JDataSource.class).asList();
    }
}
