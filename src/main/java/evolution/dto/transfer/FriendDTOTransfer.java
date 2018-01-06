package evolution.dto.transfer;

import evolution.dto.model.FriendDTO;
import evolution.dto.model.FriendDTOLazy;
import evolution.dto.model.FriendResultActionDTO;
import evolution.dto.model.UserDTO;
import evolution.model.Friend;
import evolution.model.User;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendDTOTransfer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDTOTransfer userDTOTransfer;

    private final ModelMapper modelMapper;

    @Autowired
    public FriendDTOTransfer(UserDTOTransfer userDTOTransfer,
                             ModelMapper modelMapper) {
        this.userDTOTransfer = userDTOTransfer;
        this.modelMapper = modelMapper;
    }

    public FriendDTO modelToDTO(Friend friend) {
        UserDTO first = userDTOTransfer.modelToDTO(friend.getPk().getFirst());
        UserDTO second = userDTOTransfer.modelToDTO(friend.getPk().getSecond());
        UserDTO action = null;

        if (first.getId().equals(friend.getActionUser().getId())) {
            action = first;
        } else if (second.getId().equals(friend.getActionUser().getId())) {
            action = second;
        } else {
            logger.warn("action user in FriendDTO is null!!!!");
        }

        return new FriendDTO(first, second, action, friend.getStatus());
    }

    public FriendDTO modelToDTO(Friend friend, Long auth) {
        FriendDTO friendDTO = modelToDTO(friend);
        if (auth.equals(friendDTO.getSecond().getId())) {
            UserDTO fd = friendDTO.getFirst();
            friendDTO.setFirst(friendDTO.getSecond());
            friendDTO.setSecond(fd);
        }
        return friendDTO;
    }

    public FriendDTOLazy modelToDTOLazy(Friend friend) {
        FriendDTOLazy f = modelMapper.map(friend, FriendDTOLazy.class);
        f.setFirst(userDTOTransfer.modelToDTO(friend.getPk().getFirst()));
        f.setSecond(userDTOTransfer.modelToDTO(friend.getPk().getSecond()));
        f.setActionUser(userDTOTransfer.modelToDTO(friend.getActionUser()));
        return f;
    }

    public FriendDTOLazy modelToDTOLazy(Friend friend, User auth) {
        FriendDTOLazy f = modelToDTOLazy(friend);
        if (auth.getId().equals(f.getSecond().getId())) {
            UserDTO ud = f.getFirst();
            f.setFirst(f.getSecond());
            f.setSecond(ud);
        }
        return f;
    }

    public FriendDTOLazy modelToDTOLazy(Friend friend, Long auth) {
        return modelToDTOLazy(friend, new User(auth));
    }

    public FriendResultActionDTO modelToResultActionDTO(Friend friend) {
        FriendResultActionDTO dto = new FriendResultActionDTO();
        dto.setFirst(userDTOTransfer.modelToDTO(friend.getPk().getFirst()));
        dto.setSecond(userDTOTransfer.modelToDTO(friend.getPk().getSecond()));
        dto.setActionUser(userDTOTransfer.modelToDTO(friend.getActionUser()));
        dto.setStatus(friend.getStatus());
        return dto;
    }
}
