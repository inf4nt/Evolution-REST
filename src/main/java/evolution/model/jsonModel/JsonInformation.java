package evolution.model.jsonModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * Created by Admin on 25.05.2017.
 */
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Deprecated
public class JsonInformation {

    @JsonProperty
    private String httpStatus;
    @JsonProperty
    private String message;
    @JsonProperty
    private Object info;
}
