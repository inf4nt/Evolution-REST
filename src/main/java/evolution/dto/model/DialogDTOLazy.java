package evolution.dto.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude
public class DialogDTOLazy extends DialogDTO {

    private List<MessageDTO> messageList;
}
