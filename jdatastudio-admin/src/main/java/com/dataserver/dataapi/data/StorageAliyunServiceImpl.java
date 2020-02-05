package com.dataserver.dataapi.data;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.dataserver.admin.util.SnowFlake;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service(value = "aliyun")
public class StorageAliyunServiceImpl implements FileStorageApi {
    @Autowired
    SnowFlake snowFlake;

    @Autowired
    OSSClient ossClient;

    static final String bucketName = "jdatastudio";

    /**
     * 保存base64文件
     *
     * @param base64Str
     * @return 唯一id
     */
    @Override
    @SneakyThrows
    public String saveBase64File(String base64Str) {
        String objectName = String.valueOf(snowFlake.nextId());
        ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(base64Str.getBytes("UTF-8")));
        return "aliyun:" + objectName;
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
        String objectName = fileId.replace("aliyun:", "");
        // 调用ossClient.getObject返回一个OSSObject实例，该实例包含文件内容及文件元信息。
        OSSObject ossObject = ossClient.getObject(bucketName, objectName);
        // 调用ossObject.getObjectContent获取文件输入流，可读取此输入流获取其内容。
        InputStream content = ossObject.getObjectContent();
        StringBuffer stringBuffer = new StringBuffer();
        if (content != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                stringBuffer.append(line);
            }
            // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
            content.close();
        }

        return stringBuffer.toString();
    }
}
