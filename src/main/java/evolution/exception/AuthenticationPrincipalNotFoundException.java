package evolution.exception;

/**
 * Created by Infant on 12.10.2017.
 */
public class AuthenticationPrincipalNotFoundException extends RuntimeException {

    public AuthenticationPrincipalNotFoundException() {
    }

    public AuthenticationPrincipalNotFoundException(String message) {
        super(message);
    }
}
