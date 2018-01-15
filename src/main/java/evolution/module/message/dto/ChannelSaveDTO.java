package evolution.module.message.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude
public class ChannelSaveDTO {

    private String channelName;

    private boolean isPrivate;

    private Long whoCreateId;

    @JsonSetter("isPrivate")
    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }
}
