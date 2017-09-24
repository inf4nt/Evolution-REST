package evolution.common;



import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Infant on 08.07.2017.
 */

@AllArgsConstructor
@Getter
public enum PublicationCategoryEnum {

    IT(1),
    TECHNICS(2),
    SROPT(3),
    MACHINE_AND_TECHNICS(4);

    private final long id;
}