package evolution.dto.modelOld;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.util.List;

@Data
@JsonInclude
public class FeedForSaveDTO {

    private String content;

    private Long senderId;

    private Long toUserId;

    private List<String> tags;
}
