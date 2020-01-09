package com.sl.springbootshirojwt.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class FastJsonConfig {

    @Bean
    public HttpMessageConverters fastJsonMessageConverter() {

        //创建FastJson的消息转换器
        FastJsonHttpMessageConverter convert = new FastJsonHttpMessageConverter();
        //创建FastJson的配置对象
        com.alibaba.fastjson.support.config.FastJsonConfig config = new com.alibaba.fastjson.support.config.FastJsonConfig();
        //对Json数据进行格式化
        config.setSerializerFeatures(SerializerFeature.PrettyFormat);

        //处理中文乱码
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        convert.setSupportedMediaTypes(fastMediaTypes);

        convert.setFastJsonConfig(config);
        HttpMessageConverter<?> con =convert;
        return new HttpMessageConverters(con);
    }
}
