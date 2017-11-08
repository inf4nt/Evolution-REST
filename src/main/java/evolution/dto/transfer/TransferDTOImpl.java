package evolution.dto.transfer;

import evolution.dto.model.*;
import evolution.model.Dialog;
import evolution.model.Message;
import evolution.model.User;
import evolution.service.DateService;
import evolution.service.SecuritySupportService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Infant on 07.11.2017.
 */
@Service
public class TransferDTOImpl implements TransferDTO {

    private final DateService dateService;

    private final SecuritySupportService securitySupportService;

    private final ModelMapper modelMapper;

    @Autowired
    public TransferDTOImpl(DateService dateService,
                           SecuritySupportService securitySupportService,
                           ModelMapper modelMapper) {
        this.dateService = dateService;
        this.securitySupportService = securitySupportService;
        this.modelMapper = modelMapper;
    }

    @Override
    public MessageDTO modelToDTO(Message message) {
//        MessageDTO messageDTO = modelMapper.map(message, MessageDTO.class);
//        messageDTO.setCreatedDateTimestamp(message.getDateDispatch().getTime());
//        messageDTO.setCreatedDateString(dateService.getStringDateUTC(message.getDateDispatch().getTime()));
//
//        Optional<CustomSecurityUser> optional = securitySupportService.getPrincipal();
//        if (optional.isPresent()) {
//            UserDTO auth = modelMapper.map(optional.get().getUser(), UserDTO.class);
//            messageDTO.setSecond(auth);
//        }
//
//        Dialog dialog = message.getDialog();
//        if (!message.getSender().getId().equals(dialog.getFirst().getId())) {
//            messageDTO.setRecipient(modelMapper.map(dialog.getFirst(), UserDTO.class));
//        } else {
//            messageDTO.setRecipient(modelMapper.map(dialog.getSecond(), UserDTO.class));
//        }
//
//        messageDTO.setText(message.getMessage());
//        return messageDTO;
        return null;
    }


    @Override
    public UserDTO modelToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public List<MessageDTO> modelToDTOListMessage(List<Message> message) {
        return message.stream().map(o -> modelToDTO(o)).collect(Collectors.toList());
    }

    @Override
    public DialogDTO modelToDTO(Dialog dialog) {
//        Optional<CustomSecurityUser> optional = securitySupportService.getPrincipal();
//        DialogDTO dialogDTO = modelMapper.map(dialog, DialogDTO.class);
//        UserDTO firstDTO = modelMapper.map(dialog.getFirst(), UserDTO.class);
//        UserDTO secondDTO = modelMapper.map(dialog.getSecond(), UserDTO.class);
//        dialogDTO.setCreatedDateTimestamp(dialog.getCreateDate().getTime());
//        dialogDTO.setCreatedDateString(dateService.getStringDateUTC(dialog.getCreateDate().getTime()));
//        if (optional.isPresent()) {
//            User a = optional.get().getUser();
//            if (!dialog.getFirst().getId().equals(a.getId())) {
//                dialogDTO.setFirst(secondDTO);
//                dialogDTO.setSecond(firstDTO);
//            }
//        }
//        return dialogDTO;
        return null;
    }

    @Override
    public List<DialogDTO> modelToDTOListDialog(List<Dialog> dialog) {
        return dialog.stream().map(o -> modelToDTO(o)).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> modelToDTOListUser(List<User> user) {
        return user.stream().map(o -> modelToDTO(o)).collect(Collectors.toList());
    }

    @Override
    public User dtoToModel(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    @Override
    public List<User> dtoToModelListUser(List<UserDTO> userDTOList) {
        return userDTOList.stream().map(o -> dtoToModel(o)).collect(Collectors.toList());
    }

    @Override
    public User dtoToModel(UserDTOForSave userDTOForUpdate) {
        return modelMapper.map(userDTOForUpdate, User.class);
    }

    @Override
    public List<User> dtoUserForUpdateToModelListUser(List<UserDTOForSave> userDTOList) {
        return userDTOList.stream().map(o -> dtoToModel(o)).collect(Collectors.toList());
    }

    @Override
    public User dtoToModel(UserFullDTO userFullDTO) {
        return modelMapper.map(userFullDTO, User.class);
    }

    @Override
    public List<User> dtoUserFullToModelListUser(List<UserFullDTO> userFullDTOList) {
        return userFullDTOList.stream().map(o -> dtoToModel(o)).collect(Collectors.toList());
    }

    @Override
    public UserFullDTO modelToDTOUserFull(User user) {
        return modelMapper.map(user, UserFullDTO.class);
    }

    @Override
    public List<UserFullDTO> modelToDTO(List<User> userList) {
        return userList.stream().map(o -> modelToDTOUserFull(o)).collect(Collectors.toList());
    }
}
