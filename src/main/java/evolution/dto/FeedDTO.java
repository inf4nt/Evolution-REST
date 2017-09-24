package evolution.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by Infant on 24.09.2017.
 */
@Data
public class FeedDTO {

    private Long id;

    private String content;

    private Date date;

    private UserLightDTO sender;

    private UserLightDTO toUser;

    private String tags;

    private List<String> listTags;

}
