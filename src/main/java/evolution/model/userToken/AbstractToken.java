package evolution.model.userToken;

import lombok.*;

/**
 * Created by Admin on 07.06.2017.
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode
public abstract class AbstractToken {
    private String token;
}
