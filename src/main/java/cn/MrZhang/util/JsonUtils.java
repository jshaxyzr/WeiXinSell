package cn.MrZhang.util;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * 
 * Title:JsonUtils
 * @Description: TODO
 * @author MrZhang
 * @date 2017年7月24日 下午3:09:26 
 * @version V1.0
 */
public class JsonUtils {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);// 设置map值为NULL时 不转换
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);// 禁止遇到空原始类型时抛出异常，用默认值null代替
        // 也可以在配置文件中配置
        // spring.jackson.deserialization.=true false spring.jackson.serialization=true

    }

    private JsonUtils() {

    }

    /**
     * 
    * @Title: objectToJson
    * @Description: TODO 将对象转换成json字符串。
    * @param data
    * @return String
     */
    public static String objectToJson(Object data) {
        try {
            String string = MAPPER.writeValueAsString(data);
            return string;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
    * 
    * @Title: jsonToPojo
    * @Description: TODO 将json结果集转化为对象
    * @param jsonData 
    * @param beanType
    * @return T
    */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            T t = MAPPER.readValue(jsonData, beanType);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
    * 
    * @Title: jsonToList
    * @Description: TODO 将json数据转换成pojo对象list
    * @param jsonData
    * @param beanType
    * @return List<T>
    */
    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            List<T> list = MAPPER.readValue(jsonData, javaType);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 
    * @Title: toFormatJson
    * @Description: TODO 将对象转换为JSON字符串并格式化
    * @param value
    * @return String
     */
    public static String toFormatJson(Object value) {
        try {
            // enum转换为数值
            // mapper.configure(SerializationFeature.WRITE_ENUMS_USING_INDEX,true);
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 
    * @Title: formatJson
    * @Description: TODO 格式化json
    * @param json
    * @return String
     */
    public static String formatJson(String json) {
        Object object = jsonToPojo(json, Object.class);
        return toFormatJson(object);
    }

}
