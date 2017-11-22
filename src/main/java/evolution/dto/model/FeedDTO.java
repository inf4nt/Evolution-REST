package evolution.dto.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * Created by Infant on 08.11.2017.
 */
@Data
@JsonInclude
public class FeedDTO {

    private Long id;

    private String content;

    private Long createdDateTimestamp;

    private String createdDateString;

    private UserDTO sender;

    private UserDTO toUser;

    private List<String> tags;

    private boolean isActive;
}
