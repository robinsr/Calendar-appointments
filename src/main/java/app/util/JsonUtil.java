package app.util;

import com.fasterxml.jackson.databind.*;
import java.io.*;

public class JsonUtil {
    public static String dataToJson(Object data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw, data);
            return sw.toString();
        } catch (IOException e) {
            throw new RuntimeException("IOEXception while mapping object (" + data + ") to JSON (" + e.getMessage() + ")");
        }
    }

    public static Object jsonToData(String payload, Class c) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(payload, c);
        } catch (IOException e) {
            throw new RuntimeException("IOEXception while parsing JSON (" + e.getMessage() + ")");
        }
    }
}
