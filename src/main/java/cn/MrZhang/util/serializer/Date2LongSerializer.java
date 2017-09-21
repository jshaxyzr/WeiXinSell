package cn.MrZhang.util.serializer;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 
 * Title:Date2LongSerializer 
 * @Description: TODO  自定义序列化方式 将date 类型的时间戳 转化为秒为单位
 * @author MrZhang
 * @date 2017年9月20日 下午4:51:06 
 * @version V1.0
 */
public class Date2LongSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException, JsonProcessingException {
        // TODO Auto-generated method stub
        gen.writeNumber(value.getTime() / 1000);
    }

}
