package com.jinax.hospital_management_backend.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jinax.hospital_management_backend.Entity.BaseEnum;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author : chara
 */
@Component
public class BaseEnumToStringSerializer extends JsonSerializer<BaseEnum> {
    @Override
    public void serialize(BaseEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeNumber(value.getCode());
    }
}
