package evolution.dto.transfer;

import evolution.dto.model.FeedDTO;
import evolution.dto.model.UserDTO;
import evolution.model.Feed;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class FeedDTOTransfer implements TransferDTO<FeedDTO, Feed> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserDTOTransfer userDTOTransfer;

    @Override
    public FeedDTO modelToDTO(Feed feed) {
        FeedDTO dto = new FeedDTO();
        dto.setId(feed.getId());
        dto.setActive(feed.isActive());
        dto.setDate(feed.getDate());
        dto.setContent(feed.getContent());
        dto.setSender(userDTOTransfer.modelToDTO(feed.getSender()));
        dto.setToUser(userDTOTransfer.modelToDTO(feed.getToUser()));

        // todo check this
        if (feed.getTags() != null) {
            dto.setTags(Arrays.stream(feed.getTags().split("#"))
                    .skip(1)
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}
