package evolution.module.message.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import evolution.module.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude
public class DialogDTO {

    private Long id;

    private UserDTO first;

    private UserDTO second;

    private Date createDate;
}
