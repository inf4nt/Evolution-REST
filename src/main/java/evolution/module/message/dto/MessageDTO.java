package evolution.message.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import evolution.user.dto.UserDTO;
import lombok.AllArgsConstructor;
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
