package evolution.security.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude
public class JwtCleanToken {

    private String username;
}
