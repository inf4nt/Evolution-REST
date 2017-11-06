package evolution.transfer;

import evolution.dto.MessageDTO;
import evolution.dto.UserDTO;
import evolution.model.Message;
import evolution.model.User;

/**
 * Created by Infant on 07.11.2017.
 */
public interface TransferDTO {

    MessageDTO modelToDTO(Message message);

    Message DTOToModel(MessageDTO messageDTO);

    UserDTO modelToDTO(User user);

    User DTOToModel(UserDTO userDTO);
}
