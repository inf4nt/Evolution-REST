package evolution.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Admin on 11.06.2017.
 */
@AllArgsConstructor
@Getter
public enum FeedAttribute {

    LIKE(1),
    REPOST(2),
    POST(3);

    private final long id;
}
