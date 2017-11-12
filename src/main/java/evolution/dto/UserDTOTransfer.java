package evolution.dto;


import evolution.dto.model.UserDTO;
import evolution.dto.model.UserDTOForSave;
import evolution.dto.model.UserDTOForUpdate;
import evolution.dto.model.UserFullDTO;
import evolution.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Infant on 11.11.2017.
 */
@Service
public class UserDTOTransfer {

    private final ModelMapper modelMapper;

    @Autowired
    public UserDTOTransfer(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User dtoToModel(UserFullDTO userFullDTO) {
        return modelMapper.map(userFullDTO, User.class);
    }

    public User dtoToModel(UserDTOForUpdate userDTOForUpdate) {
        return modelMapper.map(userDTOForUpdate, User.class);
    }

    public User dtoToModel(UserDTOForSave userDTOForSave) {
        return modelMapper.map(userDTOForSave, User.class);
    }

    public User dtoToModel(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public UserDTOForUpdate modelToDTOForUpdate(User user) {
        return modelMapper.map(user, UserDTOForUpdate.class);
    }

    public UserFullDTO modelToDTOFull(User user) {
        return modelMapper.map(user, UserFullDTO.class);
    }

    public UserDTOForSave modelToDTOForSave(User user) {
        return modelMapper.map(user, UserDTOForSave.class);
    }

    public UserDTO modelToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
