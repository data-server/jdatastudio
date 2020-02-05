package com.dataserver.connector.elasticsearch.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author paul
 * @version V1.0
 * @description json工具类, 依赖jackson
 * @date 2017年7月10日 上午10:54:43
 * @update 2017年7月10日 上午10:54:43
 */
public class JsonUtil {
    private static ObjectMapper INSTANCE = new ObjectMapper();

    static {
        INSTANCE.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private JsonUtil() {
    }

    /**
     * @param obj 准备转换对象
     * @return
     * @throws JsonProcessingException
     * @description 对象转换成json字符串
     * @author paul
     * @date 2017年7月10日 上午10:54:50
     * @update 2017年7月10日 上午10:54:50
     * @version V1.0
     */
    public static String toJsonStr(Object obj) {
        try {
            return INSTANCE.writeValueAsString(obj);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param json 准备转换json
     * @param type 转换类型
     * @return
     * @throws Exception 转换异常
     * @description json字符串转换成对象
     * @author paul
     * @date 2017年7月10日 上午11:08:34
     * @update 2017年7月10日 上午11:08:34
     * @version V1.0
     */
    @SuppressWarnings("unchecked")
    public static <T> T parseJson(String json, String type) {
        try {
            return (T) parseJson(json, Class.forName(type));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param json  准备转换json
     * @param clazz 转换类型
     * @return
     * @description json字符串转换成对象
     * @author paul
     * @date 2017年7月10日 上午11:12:58
     * @update 2017年7月10日 上午11:12:58
     * @version V1.0
     */
    public static <T> T parseJson(String json, Class<T> clazz) {
        try {
            return (T) INSTANCE.readValue(json, clazz);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param json  准备转换json
     * @param clazz 集合元素类型
     * @return
     * @description json字符串转换成对象集合
     * @author paul
     * @date 2017年8月12日 下午1:28:27
     * @update 2017年8月12日 下午1:28:27
     * @version V1.0
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> parseJsonList(String json, Class<T> clazz) {
        try {
            JavaType javaType = getCollectionType(ArrayList.class, clazz);
            return (List<T>) INSTANCE.readValue(json, javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param collectionClass 集合类
     * @param elementClasses  集合元素类
     * @return
     * @description 获取泛型的ColloectionType
     * @author paul
     * @date 2017年8月12日 下午2:17:38
     * @update 2017年8月12日 下午2:17:38
     * @version V1.0
     */
    private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return INSTANCE.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }
}
