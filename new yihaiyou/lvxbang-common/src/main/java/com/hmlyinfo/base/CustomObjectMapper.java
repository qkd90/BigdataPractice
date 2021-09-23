package com.hmlyinfo.base;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author aokunsang
 * @description 解决Date类型返回json格式为自定义格式
 * @date 2013-5-28
 */
public class CustomObjectMapper extends ObjectMapper {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public CustomObjectMapper() {
		CustomSerializerFactory factory = new CustomSerializerFactory();

		factory.addGenericMapping(Date.class, new JsonSerializer<Date>() {
			@Override
			public void serialize(Date value,
			                      JsonGenerator jsonGenerator,
			                      SerializerProvider provider)
				throws IOException, JsonProcessingException {
				jsonGenerator.writeString(sdf.format(value));
			}
		});
		this.setSerializerFactory(factory);
	}
}  