package evolution.dto;


import evolution.dto.modelOld.UserDTO;
import evolution.dto.model.UserSaveDTO;
import evolution.dto.model.UserUpdateDTO;
import evolution.dto.modelOld.UserFullDTO;
import evolution.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Infant on 11.11.2017.
 */
@Service
@Deprecated
public class UserDTOTransfer {

    private final ModelMapper modelMapper;

    @Autowired
    public UserDTOTransfer(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User dtoToModel(UserFullDTO userFullDTO) {
        return modelMapper.map(userFullDTO, User.class);
    }

    public User dtoToModel(UserUpdateDTO userUpdateDTO) {
        return modelMapper.map(userUpdateDTO, User.class);
    }

    public User dtoToModel(UserSaveDTO userSaveDTO) {
        return modelMapper.map(userSaveDTO, User.class);
    }

    public User dtoToModel(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public UserUpdateDTO modelToDTOForUpdate(User user) {
        return modelMapper.map(user, UserUpdateDTO.class);
    }

    @Deprecated
    public UserFullDTO modelToDTOFull(User user) {
        return modelMapper.map(user, UserFullDTO.class);
    }

    public UserSaveDTO modelToDTOForSave(User user) {
        return modelMapper.map(user, UserSaveDTO.class);
    }

    @Deprecated
    public UserDTO modelToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
