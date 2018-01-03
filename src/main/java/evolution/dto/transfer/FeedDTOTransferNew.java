package evolution.dto.transfer;

import evolution.dto.model.FeedDTO;
import evolution.model.Feed;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class FeedDTOTransferNew {

    private final ModelMapper modelMapper;

    @Autowired
    public FeedDTOTransferNew(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public FeedDTO modelToDTO(Feed feed) {
        FeedDTO dto = modelMapper.map(feed, FeedDTO.class);

        // todo check this
        if (feed.getTags() != null) {
            dto.setTags(Arrays.stream(feed.getTags().split("#"))
                    .skip(1)
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}