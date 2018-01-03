package evolution.dto.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.util.List;

@Data
@JsonInclude
public class FeedSaveDTO {

    private String content;

    private Long senderId;

    private Long toUserId;

    private List<String> tags;
}
