package evolution.dto.modelOld;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Created by Infant on 07.11.2017.
 */
@Data
@JsonInclude
public class DialogDTO {

    private Long id;

    private UserDTO first;

    private UserDTO second;

    private Long createdDateTimestamp;

    private String createdDateString;
}
