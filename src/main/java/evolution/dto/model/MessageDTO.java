package evolution.dto.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Created by Infant on 06.11.2017.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageDTO {

    private Long id;

//    private Long senderId;
//
//    private String senderFirstName;
//
//    private String senderLastName;
//
//    private Long recipientId;
//
//    private String recipientFirstName;

//    private String recipientLastName;

    private UserDTO sender;

    private UserDTO recipient;

    private UserDTO dialogSecond;

    private DialogDTO dialogDTO;

//    private DialogDTO dialogDTO;

//    private Long dialogId;
//
//    private UserDTO dialogSecond;

    private String text;

    private Long createdDateTimestamp;

    private String createdDateString;
}
