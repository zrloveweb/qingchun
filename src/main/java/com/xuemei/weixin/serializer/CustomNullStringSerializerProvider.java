package com.xuemei.weixin.serializer;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;

/**
 * @author 薛梅
 * @createTime 2016/10/25 14:17
 */
@SuppressWarnings("serial")
public class CustomNullStringSerializerProvider extends DefaultSerializerProvider {

    public CustomNullStringSerializerProvider() {
        super();
    }

    public CustomNullStringSerializerProvider(CustomNullStringSerializerProvider provider, SerializationConfig config,
                                              SerializerFactory jsf) {
        super(provider, config, jsf);
    }

    @Override
    public DefaultSerializerProvider createInstance(SerializationConfig config, SerializerFactory jsf) {
        return new CustomNullStringSerializerProvider(this, config, jsf);
    }

    @Override
    public JsonSerializer<Object> findNullValueSerializer(BeanProperty property) throws JsonMappingException {
        if (property.getType().getRawClass().equals(String.class)) {
            return EmptyStringSerializer.INSTANCE;
        } else {
            return super.findNullValueSerializer(property);
        }
    }

}
