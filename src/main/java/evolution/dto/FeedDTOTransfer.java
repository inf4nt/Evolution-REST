package evolution.dto;

import evolution.dto.model.FeedDTO;
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

    @Autowired
    public FeedDTOTransfer(ModelMapper modelMapper,
                           DateService dateService) {
        this.modelMapper = modelMapper;
        this.dateService = dateService;
    }

    public FeedDTO modelToDTO(Feed feed) {
        FeedDTO dto = modelMapper.map(feed, FeedDTO.class);
        dto.setTags(Arrays.stream(feed.getTags().split("#"))
                .skip(1)
                .collect(Collectors.toList()));
        dto.setCreatedDateTimestamp(feed.getDate().getTime());
        dto.setCreatedDateString(dateService.formatDateUTC(feed.getDate()));

        return null;
    }

    public Feed dtoToModel(FeedDTO feedDTO) {
        return null;
    }

}
