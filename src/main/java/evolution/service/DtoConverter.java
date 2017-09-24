package evolution.service;

import evolution.dto.DialogDTO;
import evolution.dto.FeedDTO;
import evolution.dto.UserLightDTO;
import evolution.model.dialog.Dialog;
import evolution.model.feed.Feed;
import evolution.model.user.UserLight;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Infant on 24.09.2017.
 */
@Service
public class DtoConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public DtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<UserLightDTO> userLightToDTOList(List<UserLight> list) {
        return list
                .stream()
                .map(o -> userLightToDTO(o))
                .collect(Collectors.toList());
    }

    public UserLightDTO userLightToDTO(UserLight userLight) {
        return modelMapper.map(userLight, UserLightDTO.class);
    }

    public FeedDTO feedToDTO(Feed feed) {
        return modelMapper.map(feed, FeedDTO.class);
    }

    public List<FeedDTO> feedToDTOList(List<Feed> list) {
        return list
                .stream()
                .map(o -> feedToDTO(o))
                .collect(Collectors.toList());
    }
}
