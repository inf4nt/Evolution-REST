package evolution.common;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by Infant on 28.09.2017.
 */

@NoArgsConstructor @Getter
public enum ServiceStatus {

    TRUE,
    FALSE,
    NOT_STARTED,
    AUTH_NOT_FOUND,
    EXPECTATION_FAILED
}
