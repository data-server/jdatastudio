package com.dataserver.dataapi.data;

import com.dataserver.admin.schema.DataServiceProxy;
import com.dataserver.admin.schema.SchemaService;
import com.dataserver.admin.sys.SysService;
import com.dataserver.api.*;
import com.dataserver.api.schema.ComponentType;
import com.dataserver.api.schema.Entity;
import com.dataserver.api.schema.Field;
import com.dataserver.api.schema.JDataSource;
import com.dataserver.dataapi.Constants;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.Collectors;

/**
 * http://localhost:8080/api/tb_jrm_role?
 * limit=3&offset=0
 * &select=role_name,id,created_time,modified_time
 * &created_time=gt.2018-05-22
 * &order=created_time.asc
 * &id=eq.5
 *
 * @author gongxinyi
 * @date 2018-10-31
 */
@Slf4j
@RestController
public class DataApi {
    @Autowired
    DataServiceProxy dataServiceProxy;
    @Autowired
    SchemaService schemaService;
    @Autowired
    SysService sysService;

    @Autowired
    FileStorageApi fileStorageApi;

    @GetMapping(value = "/api/{entity}")
    @PreAuthorize("@securityService.hasProtectedAccess(#eid,'r')")
    @SneakyThrows
    public ResponseEntity<List<Map<String, Object>>> list(@RequestParam(value = com.dataserver.dataapi.Constants.ApiStandard, defaultValue = com.dataserver.dataapi.Constants.ApiStandard_jsonserver) ApiStandard apiStandard, @PathVariable("entity") String eid, @RequestParam final Map<String, Object> params) {

        log.debug("params:{}", params);
        Entity entity = schemaService.getEntity(eid);

        /**
         * 获取数据源
         */
        JDataSource jDataSource = entity.getJDataSource();

        /**
         * 数据源类型获取数据服务
         */
        IDataService dataService = dataServiceProxy.getDataService(jDataSource);

        /**
         * api标准转换成QueryParams
         */
        QueryParams queryParams = dataServiceProxy.getRequestWrapper(apiStandard).wrapQueryParams(params);


        /**
         * 添加全局过滤器
         */
        queryParams.getFilters().addAll(sysService.getFilters(eid));

        /**
         * 关联查找时根据主字段做匹配
         */
        if (!StringUtils.isEmpty(queryParams.getQ())) {
            entity.getFields().forEach(field -> {
                if (field.isMainField()) {
                    queryParams.getFilters().add(Filter.builder()
                            .field(field.getName())
                            .operator(OperatorEnum.eq)
                            .value(queryParams.getQ())
                            .build());
                    return;
                }
            });
        }

        ForkJoinPool forkjoinPool = new ForkJoinPool(2);


        ForkJoinTask<List<Map<String, Object>>> dataListTask = forkjoinPool.submit(() -> {
            /**
             * 获取列表
             */
            List<Map<String, Object>> dataList = dataService.list(entity.getJDataSource(), entity.getName(), queryParams);

            /**
             * 根据主键增加id值
             */
            addIdValue(entity.getPrimaryFields(), dataList);


            for (Map<String, Object> data : dataList) {

                entity.getFields().forEach(field -> {
                    /**
                     * 数字类型的下拉框
                     */
                    if (field.getComponent() == ComponentType.Select) {
                        data.replace(field.getName(), String.valueOf(data.get(field.getName())));
                    }
                    /**
                     * 数据脱敏
                     */
                    dataDesensitization(field.getName(), field, data);

                    /**
                     * 图片转base64
                     */
                    if (field.getComponent() == ComponentType.Image || field.getComponent() == ComponentType.File) {
                        String fileId = (String) data.get(field.getName());
                        if (!StringUtils.isEmpty(fileId)) {
                            String[] file = fileId.split(";");
                            String base64Str = fileStorageApi.getBase64Str(file[0]);
                            Map<String, String> imageMap = new HashMap<>();
                            imageMap.put("src", base64Str);
                            imageMap.put("title", file[1]);
                            imageMap.put("id", fileId);
                            data.replace(field.getName(), imageMap);
                        }
                    }
                    /**
                     * 删掉不显示在列表页的数据
                     */
                    if (!field.isShowInList() && !Constants.id.equals(field.getName())) {
                        data.remove(field.getName());
                    }
                });
            }

            return dataList;
        });


        ForkJoinTask<Long> countTask = forkjoinPool.submit(() -> {
            /**
             * 获取记录数
             */
            Long count = dataService.count(jDataSource, entity.getName(), queryParams);

            return count;
        });


        /**
         * 获取api标准的返回结果包装类，输出
         */
        return dataServiceProxy.getResponseWrapper(apiStandard).listToResponseEntity(dataListTask.get(), countTask.get());
    }

    @GetMapping(value = "/api/{entity}/{id}")
    @PreAuthorize("@securityService.hasProtectedAccess(#eid,'r')")
    public ResponseEntity<Map<String, Object>> findOne(@RequestParam(value = com.dataserver.dataapi.Constants.ApiStandard, defaultValue = com.dataserver.dataapi.Constants.ApiStandard_jsonserver) ApiStandard apiStandard, @PathVariable("entity") String eid, @PathVariable("id") String id) {
        Entity entity = schemaService.getEntity(eid);
        /**
         * 获取数据源
         */
        JDataSource jDataSource = entity.getJDataSource();

        /**
         * 数据源类型获取数据服务
         */
        IDataService dataService = dataServiceProxy.getDataService(jDataSource);

        /**
         * 获取主键
         */
        List<Field> primaryFields = entity.getPrimaryFields();

        /**
         * 根据id获取过滤器
         */
        List<Filter> filters = getFilters(primaryFields, id);

        /**
         * 构建按id查询的queryParams
         */
        QueryParams queryParams = QueryParams.builder()
                .select(new String[]{"*"})
                .pagination(Pagination.builder().offset(0).limit(1).build())
                .filters(filters).build();

        /**
         * 添加全局过滤器
         */
        queryParams.getFilters().addAll(sysService.getFilters(eid));

        /**
         * 获取列表
         */
        List<Map<String, Object>> dataList = dataService.list(jDataSource, entity.getName(), queryParams);

        if (CollectionUtils.isEmpty(dataList) || dataList.size() > 1) {
            throw new RuntimeException("");
        }
        Map<String, Object> data = dataList.get(0);

        /**
         * 根据主键增加id值
         */
        addIdValue(primaryFields, data);

        /**
         * 数据脱敏
         */
        entity.getFields().forEach(field -> {
            dataDesensitization(field.getName(), field, data);
            /**
             * 数字类型的下拉框
             */
            if (field.getComponent() == ComponentType.Select) {
                data.replace(field.getName(), String.valueOf(data.get(field.getName())));
            }

            /**
             * 图片转base64
             */
            if (field.getComponent() == ComponentType.Image || field.getComponent() == ComponentType.File) {
                String fileId = (String) data.get(field.getName());
                if (!StringUtils.isEmpty(fileId)) {
                    String[] file = fileId.split(";");
                    String base64Str = fileStorageApi.getBase64Str(file[0]);
                    Map<String, String> imageMap = new HashMap<>();
                    imageMap.put("src", base64Str);
                    imageMap.put("title", file[1]);
                    imageMap.put("id", fileId);
                    data.replace(field.getName(), imageMap);
                }
            }
            /**
             * 删掉不显示在详情页的数据
             */
            if (!field.isShowInShow() && !field.isShowInEdit() && !Constants.id.equals(field.getName())) {
                data.remove(field.getName());
            }
        });

        /**
         * 获取api标准的返回结果包装类，输出
         */
        return ResponseEntity.ok(data);
    }

    @PostMapping("/api/{entity}")
    @PreAuthorize("@securityService.hasProtectedAccess(#eid,'u')")
    public ResponseEntity create(@RequestParam(value = com.dataserver.dataapi.Constants.ApiStandard, defaultValue = com.dataserver.dataapi.Constants.ApiStandard_jsonserver) ApiStandard apiStandard, @PathVariable("entity") String eid, @RequestBody Map<String, Object> data) {

        Entity entity = schemaService.getEntity(eid);

        /**
         * 获取数据源
         */
        JDataSource jDataSource = entity.getJDataSource();

        /**
         * 数据源类型获取数据服务
         */
        IDataService dataService = dataServiceProxy.getDataService(jDataSource);

        entity.getFields().forEach(field -> {
            if (!field.isShowInCreate()) {
                data.remove(field.getName());
            } else {
                if (field.getComponent() == ComponentType.Image || field.getComponent() == ComponentType.File) {
                    Map<String, String> fileData = (Map) data.get(field.getName());
                    if (fileData != null) {
                        String fileId = fileStorageApi.saveBase64File(fileData.get("src"));
                        fileId += ";" + fileData.get("title");
                        data.replace(field.getName(), fileId);
                    }
                }
            }
        });

        /**
         * 新建数据
         */
        dataService.create(jDataSource, entity.getName(), data);

        return ResponseEntity.ok(data);
    }

//    @GetMapping(path = "/download/{filePath}")
//    @PreAuthorize("authenticated")
//    public ResponseEntity<InputStreamResource> download(@PathVariable("filePath") String filePath) throws IOException {
//
//        FileSystemResource file = new FileSystemResource(filePath);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
//        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
//        headers.add("Pragma", "no-cache");
//        headers.add("Expires", "0");
//
//        return ResponseEntity
//                .ok()
//                .headers(headers)
//                .contentLength(file.contentLength())
//                .contentType(MediaType.parseMediaType("application/octet-stream"))
//                .body(new InputStreamResource(file.getInputStream()));
//    }

    @PutMapping(value = "/api/{entity}")
    @PreAuthorize("@securityService.hasProtectedAccess(#eid,'u')")
    public ResponseEntity update(@RequestParam(value = com.dataserver.dataapi.Constants.ApiStandard, defaultValue = com.dataserver.dataapi.Constants.ApiStandard_jsonserver) ApiStandard apiStandard, @PathVariable("entity") String eid, @RequestParam final Map<String, Object> params, @RequestBody final Map<String, Object> data) {
        Entity entity = schemaService.getEntity(eid);

        /**
         * 获取数据源
         */
        JDataSource jDataSource = entity.getJDataSource();

        /**
         * api标准转换成QueryParams
         */
        QueryParams queryParams = dataServiceProxy.getRequestWrapper(apiStandard).wrapQueryParams(params);

        /**
         * 添加全局过滤器
         */
        queryParams.getFilters().addAll(sysService.getFilters(eid));

        /**
         * 数据源类型获取数据服务
         */
        IDataService dataService = dataServiceProxy.getDataService(jDataSource);

        entity.getFields().forEach(field -> {
            if (!field.isShowInEdit() && !Constants.id.equals(field.getName())) {
                data.remove(field.getName());
            } else {
                Map<String, String> fileData = (Map) data.get(field.getName());
                if (StringUtils.isEmpty(fileData.get("id"))) {
                    String fileId = fileStorageApi.saveBase64File(fileData.get("src"));
                    fileId += ";" + fileData.get("title");
                    data.replace(field.getName(), fileId);
                }
            }
        });
        /**
         * 更新数据
         */
        dataService.update(jDataSource, entity.getName(), data, queryParams.getFilters());

        return ResponseEntity.ok(data);
    }

    @PutMapping(value = "/api/{entity}/{id}")
    @PreAuthorize("@securityService.hasProtectedAccess(#eid,'u')")
    public ResponseEntity update(@RequestParam(value = com.dataserver.dataapi.Constants.ApiStandard, defaultValue = com.dataserver.dataapi.Constants.ApiStandard_jsonserver) ApiStandard apiStandard, @PathVariable("entity") String eid, @PathVariable("id") String id, @RequestBody final Map<String, Object> data) {
        Entity entity = schemaService.getEntity(eid);

        /**
         * 获取数据源
         */
        JDataSource jDataSource = entity.getJDataSource();

        /**
         * 数据源类型获取数据服务
         */
        IDataService dataService = dataServiceProxy.getDataService(jDataSource);


        List<Field> primaryFields = entity.getPrimaryFields();

        /**
         * 根据id获取过滤器
         */
        List<Filter> filters = getFilters(primaryFields, id);

        /**
         * 存储base64文件，转换为文件id
         */
        entity.getFields().forEach(field -> {
            if (!field.isShowInEdit() && !Constants.id.equals(field.getName())) {
                data.remove(field.getName());
            } else {
                if (field.getComponent() == ComponentType.Image || field.getComponent() == ComponentType.File) {
                    Map<String, String> fileData = (Map) data.get(field.getName());
                    /**
                     * 如果是新的图片，才上传到服务端，否则不修改
                     */
                    String fileId = fileData.get("id");
                    if (StringUtils.isEmpty(fileId)) {
                        fileId = fileStorageApi.saveBase64File(fileData.get("src"));
                    }
                    fileId += ";" + fileData.get("title");
                    data.replace(field.getName(), fileId);
                }
            }
        });
        /**
         * 更新数据
         */
        dataService.update(jDataSource, entity.getName(), data, filters);

        return ResponseEntity.ok(data);
    }

    /**
     * 获取过滤器
     * 1、根据实体名读取schema
     * 2、根据主键分割id的值，构建filter
     *
     * @param primaryFields
     * @param id
     * @return
     */
    private List<Filter> getFilters(List<Field> primaryFields, String id) {
        List<Filter> filters = new ArrayList<>();
        if (primaryFields.size() > 1) {
            String[] idValues = id.split(com.dataserver.dataapi.Constants.delimiter);
            for (int i = 0; i < idValues.length; i++) {
                filters.add(new Filter(primaryFields.get(i).getName(), OperatorEnum.eq, idValues[i]));
            }
        } else {
            filters.add(new Filter(primaryFields.get(0).getName(), OperatorEnum.eq, id));
        }
        return filters;
    }

    private void addIdValue(List<Field> primaryFields, List<Map<String, Object>> datas) {
        for (Map<String, Object> data : datas) {
            addIdValue(primaryFields, data);
        }
    }

    private void addIdValue(List<Field> primaryFields, Map<String, Object> data) {
        String idValue = primaryFields.stream()
                .map(field -> data.get(field.getName()).toString())
                .collect(Collectors.joining(com.dataserver.dataapi.Constants.delimiter));
        if (!StringUtils.isEmpty(idValue)) {
            data.put(com.dataserver.dataapi.Constants.id, idValue);
        }
    }

    /**
     * 数据脱敏
     *
     * @param k
     * @param v
     * @param data
     */
    private void dataDesensitization(String k, Field v, Map<String, Object> data) {
        if (v.getSensitiveType() != null) {
            switch (v.getSensitiveType()) {
                case nonsensitive:
                    break;
                case id:
                    data.replace(k, DesensitizedUtils.idCardNum(String.valueOf(data.get(k))));
                    break;
                case mobile:
                    data.replace(k, DesensitizedUtils.mobilePhone(String.valueOf(data.get(k))));
                    break;
                case card:
                    data.replace(k, DesensitizedUtils.bankCard(String.valueOf(data.get(k))));
                    break;
                default:
                    data.replace(k, DesensitizedUtils.mobilePhone(String.valueOf(data.get(k))));
            }
        }
    }

    private String getToken(ServerHttpRequest request) {
        List<String> strings = request.getHeaders().get("Authorization");
        String authToken = null;
        if (strings != null) {
            authToken = strings.get(0);
        }
        return authToken;
    }
}
