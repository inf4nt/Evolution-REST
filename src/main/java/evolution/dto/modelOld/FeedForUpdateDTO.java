package evolution.dto.modelOld;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude
public class FeedForUpdateDTO {

    private Long id;

    private String content;

    private Long senderId;

    private Long toUserId;

    private List<String> tags;

    private boolean isActive;
}
