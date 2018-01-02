package evolution.dto.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude
public class MessageDTO {

    private Long id;

    private UserDTO sender;

    private DialogDTO dialog;

    private String message;

    private Date dateDispatch;
}
