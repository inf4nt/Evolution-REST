package evolution.dto.modelOld;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude
@Deprecated
public class DialogFullDTO {

    private Long id;

    private UserDTO first;

    private UserDTO second;

    private List<Message> messageList = new ArrayList<>();

    private Long createdDateTimestamp;

    private String createdDateString;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude
    public static class Message {

        private Long id;

        private UserDTO sender;

        private Long dialogId;

        private String text;

        private Long createdDateTimestamp;

        private String createdDateString;

    }

}
