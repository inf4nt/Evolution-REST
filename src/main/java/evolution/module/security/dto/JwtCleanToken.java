package evolution.security.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude
public class JwtCleanToken {

    private String username;
}
