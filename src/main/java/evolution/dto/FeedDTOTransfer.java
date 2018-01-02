package evolution.dto;

import evolution.dto.modelOld.FeedDTO;
import evolution.model.Feed;
import evolution.service.DateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class FeedDTOTransfer {

    private final ModelMapper modelMapper;

    private final DateService dateService;

    private final UserDTOTransfer userTransaction;

    @Autowired
    public FeedDTOTransfer(ModelMapper modelMapper,
                           DateService dateService,
                           UserDTOTransfer userTransaction) {
        this.modelMapper = modelMapper;
        this.dateService = dateService;
        this.userTransaction = userTransaction;
    }

    public FeedDTO modelToDTO(Feed feed) {
        FeedDTO dto = modelMapper.map(feed, FeedDTO.class);

        if (feed.getTags() != null) {
            dto.setTags(Arrays.stream(feed.getTags().split("#"))
                    .skip(1)
                    .collect(Collectors.toList()));
        }

        if (feed.getToUser() != null) {
            dto.setToUser(userTransaction.modelToDTO(feed.getToUser()));
        }

        dto.setContent(feed.getContent());
        dto.setCreatedDateTimestamp(feed.getDate().getTime());
        dto.setCreatedDateString(dateService.formatDateUTC(feed.getDate()));

        return dto;
    }
}
