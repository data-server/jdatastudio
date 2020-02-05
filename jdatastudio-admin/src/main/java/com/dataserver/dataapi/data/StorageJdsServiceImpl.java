package com.dataserver.dataapi.data;

import com.dataserver.admin.util.SnowFlake;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service("jds")
public class StorageJdsServiceImpl implements FileStorageApi {

    @Autowired
    SnowFlake snowFlake;
    @Value("${jdatastudio.storage.rootPath}")
    String rootPath;

    /**
     * 保存base64文件
     *
     * @param base64Str
     * @return 唯一id
     */
    @Override
    @SneakyThrows
    public String saveBase64File(String base64Str) {
        String fileId = String.valueOf(snowFlake.nextId());
        if(!Files.exists(Paths.get(rootPath))){
            Files.createDirectories(Paths.get(rootPath));
        }
        Files.write(Paths.get(rootPath + File.separator + fileId), base64Str.getBytes("UTF-8"));
        return "jds:" + fileId;
    }

    /**
     * 根据文件id，返回base64
     *
     * @param fileId
     * @return
     */
    @Override
    @SneakyThrows
    public String getBase64Str(String fileId) {
        String fileName = fileId.replace("jds:", "");
        if(!Files.exists(Paths.get(rootPath))){
            Files.createDirectories(Paths.get(rootPath));
        }
        Path path = Paths.get(rootPath + File.separator + fileName);
        return Files.exists(path) ? new String(Files.readAllBytes(path), StandardCharsets.UTF_8) : null;
    }
}

