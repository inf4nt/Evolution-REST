package evolution.dto;

import lombok.Data;

/**
 * Created by Infant on 24.09.2017.
 */
@Data
public class DialogDTO {

    private Long id;

    private UserLightDTO first;

    private UserLightDTO second;
}
