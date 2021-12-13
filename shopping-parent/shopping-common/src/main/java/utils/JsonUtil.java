package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import exception.BusinessException;
import org.apache.poi.ss.formula.functions.T;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String getJson(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (Exception e) {
            throw new BusinessException("JSON转换字符串异常");
        }
    }

    public static T getJsonStrToClass(String s, Class<T> tClass) {
        try {
            return objectMapper.readValue(s, tClass);
        } catch (Exception e) {
            throw new BusinessException("JSON转换字符串异常");
        }
    }

}
