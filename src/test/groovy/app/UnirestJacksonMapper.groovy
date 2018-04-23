package app

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper as JacksonObjectMaooer
import com.mashape.unirest.http.ObjectMapper as unirestObjectMapper

class UnirestJacksonMapper implements unirestObjectMapper {

    private JacksonObjectMaooer jacksonObjectMapper = new JacksonObjectMaooer();

    public <T> T readValue(String value, Class<T> valueType) {
        try {
            return jacksonObjectMapper.readValue(value, valueType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String writeValue(Object value) {
        try {
            return jacksonObjectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
