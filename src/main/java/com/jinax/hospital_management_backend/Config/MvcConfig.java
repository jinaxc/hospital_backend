package com.jinax.hospital_management_backend.Config;

import com.jinax.hospital_management_backend.Component.IntegerCodeToEnumConverterFactory;
import com.jinax.hospital_management_backend.Component.StringCodeToEnumConverterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : chara
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new IntegerCodeToEnumConverterFactory());
        registry.addConverterFactory(new StringCodeToEnumConverterFactory());
    }
}
