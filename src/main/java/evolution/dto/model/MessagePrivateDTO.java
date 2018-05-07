package evolution.dto.model;

import lombok.Data;

import java.util.Date;

@Data
public class MessagePrivateDTO {

    private Long id;

    private UserDTO sender;

    private UserDTO recipient;

    private String text;

    private boolean isRead;

    private Date datePost;
}
