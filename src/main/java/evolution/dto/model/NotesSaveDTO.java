package evolution.dto.model;

import lombok.Data;

import java.util.Date;

@Data
public class NotesSaveDTO {

    private Long id;

    private String text;

    private UserDTO sender;

    private Date datePost;

}
