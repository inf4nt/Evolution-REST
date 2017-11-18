package evolution.dto;

import evolution.common.FriendActionEnum;
import evolution.dto.model.FriendActionDTO;
import evolution.dto.model.FriendDTO;
import evolution.dto.model.FriendDTOFull;
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

    private final UserDTOTransfer userDTOTransfer;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ModelMapper modelMapper;

    @Autowired
    public FriendDTOTransfer(UserDTOTransfer userDTOTransfer, ModelMapper modelMapper) {
        this.userDTOTransfer = userDTOTransfer;
        this.modelMapper = modelMapper;
    }

    public FriendDTO modelToDTO(Friend friend) {
        UserDTO u1 = userDTOTransfer.modelToDTO(friend.getPk().getFirst());
        UserDTO u2 = userDTOTransfer.modelToDTO(friend.getPk().getSecond());
        return new FriendDTO(u1, u2, friend.getStatus());
    }

    public FriendDTOFull modelToDTOFull(Friend friend) {
        return modelMapper.map(friend, FriendDTOFull.class);
    }

//    public Friend dtoToModel(FriendActionDTO friendActionDTO) {
//        Friend friend = new Friend();
//        Friend.FriendEmbeddable pk;
//
//        if (friendActionDTO.getActionUserId() > friendActionDTO.getRecipientUserId()) {
//            pk = new Friend.FriendEmbeddable(new User(friendActionDTO.getActionUserId()), new User(friendActionDTO.getRecipientUserId()));
//        } else {
//            pk = new Friend.FriendEmbeddable(new User(friendActionDTO.getRecipientUserId()), new User(friendActionDTO.getActionUserId()));
//        }
//        friend.setPk(pk);
//        friend.setActionUser(new User(friendActionDTO.getActionUserId()));
//
//
//        return friend;
//    }

}
