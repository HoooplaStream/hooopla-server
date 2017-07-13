package fr.cseries.transmission.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.cseries.transmission.exception.JsonException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

public class JsonUtil {
    private static Log log = LogFactory.getLog(JsonUtil.class.getName());

    public static <T> T getObject(Class<T> clazz, String msg) throws JsonException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return mapper.readValue(msg, clazz);
        } catch (IOException e) {
            log.error(e);
            throw new JsonException(e.getMessage());
        }
    }

    public static <T> String getJson(T obj) throws JsonException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error(e);
            throw new JsonException(e.getMessage());
        }
    }
}
