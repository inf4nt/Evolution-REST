package evolution.service.builder;

import com.fasterxml.jackson.core.JsonProcessingException;
import evolution.model.jsonModel.JsonInformation;
import evolution.service.MyJacksonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Admin on 25.05.2017.
 */
@Service
public class JsonInformationBuilder {

    @Autowired
    private MyJacksonService jacksonService;

    public String buildJson(String httpStatus, String message, Object info) throws JsonProcessingException {
        JsonInformation j = new JsonInformation(httpStatus, message, info);
        return jacksonService.objectToJson(j);
    }
}
