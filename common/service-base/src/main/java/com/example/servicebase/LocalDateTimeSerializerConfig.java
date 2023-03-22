package com.example.servicebase;

/**
 * com.example.servicebase
 *
 * @author xzwnp
 * 2023/3/15
 * 20:22
 */

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * LocalDateTime序列化支持
 */
@Configuration
public class LocalDateTimeSerializerConfig {

	//yyyy-MM-dd HH:mm:ss
	@Value("${spring.jackson.date-format}")
	private String pattern;

	//LocalDateTime序列化
	@Bean
	public LocalDateTimeSerializer localDateTimeSerializer() {
		return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(pattern));
	}

	//LocalDateTime反序列化
	@Bean
	public LocalDateTimeDeserializer localDateTimeDeserializer() {
		return new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(pattern));
	}

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
		return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder
			.serializerByType(LocalDateTime.class, localDateTimeSerializer())
			.deserializerByType(LocalDateTime.class, localDateTimeDeserializer());
	}
}

