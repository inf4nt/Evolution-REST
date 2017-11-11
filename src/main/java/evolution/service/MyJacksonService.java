//package evolution.service;
//
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//
///**
// * Created by Admin on 04.05.2017.
// */
//@Service
//public class MyJacksonService {
//
//    @Autowired
//    private ObjectMapper mapper;
//
//    public String objectToJson(Object obj) throws JsonProcessingException {
//        if (obj == null)
//            return null;
//        return mapper.writeValueAsString(obj);
//    }
//
//    public Object jsonToObject(String json, Class clazz) throws IOException {
//        return mapper.readValue(json, clazz);
//    }
//}
