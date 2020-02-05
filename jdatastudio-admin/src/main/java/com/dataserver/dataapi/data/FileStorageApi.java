package com.dataserver.dataapi.data;

import lombok.SneakyThrows;

/**
 * Created by gongxinyi on 2019-05-25.
 */
public interface FileStorageApi {
    @SneakyThrows
    String saveBase64File(String base64Str);

    @SneakyThrows
    String getBase64Str(String objectName);
}
