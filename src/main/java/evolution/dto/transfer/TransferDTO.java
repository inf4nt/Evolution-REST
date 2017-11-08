package evolution.dto.transfer;

import evolution.dto.model.*;
import evolution.model.Dialog;
import evolution.model.Message;
import evolution.model.User;

import java.util.List;

/**
 * Created by Infant on 07.11.2017.
 */
public interface TransferDTO {

    MessageDTO modelToDTO(Message message);

    List<MessageDTO> modelToDTOListMessage(List<Message> message);

    DialogDTO modelToDTO(Dialog dialog);

    List<DialogDTO> modelToDTOListDialog(List<Dialog> dialog);

    UserDTO modelToDTO(User user);

    List<UserDTO> modelToDTOListUser(List<User> user);

    User dtoToModel(UserDTO user);

    List<User> dtoToModelListUser(List<UserDTO> userDTOList);

    User dtoToModel(UserFullDTO userFullDTO);

    List<User> dtoUserFullToModelListUser(List<UserFullDTO> userFullDTOList);

    UserFullDTO modelToDTOUserFull(User user);

    List<UserFullDTO> modelToDTO(List<User> userList);

    User dtoToModel(UserDTOForSave userDTOForUpdate);

    List<User> dtoUserForUpdateToModelListUser(List<UserDTOForSave> userDTOList);
}
