package evolution.friend.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RelationshipStatus {

    PENDING(1),
    ACCEPTED(2),
    NOT_FOUND(3);

    private final int id;
}
