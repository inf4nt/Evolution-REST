package evolution.dto.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude
@Data
public class MessageForUpdateDTO {

    private Long id;

    private String content;
}
