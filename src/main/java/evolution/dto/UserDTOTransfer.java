package evolution.dto;


import evolution.dto.modelOld.UserDTO;
import evolution.dto.modelOld.UserForSaveDTO;
import evolution.dto.modelOld.UserForUpdateDTO;
import evolution.dto.modelOld.UserFullDTO;
import evolution.dto.model.UserDTOLazy;
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

    public User dtoToModel(UserForUpdateDTO userForUpdateDTO) {
        return modelMapper.map(userForUpdateDTO, User.class);
    }

    public User dtoToModel(UserForSaveDTO userForSaveDTO) {
        return modelMapper.map(userForSaveDTO, User.class);
    }

    public User dtoToModel(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public UserForUpdateDTO modelToDTOForUpdate(User user) {
        return modelMapper.map(user, UserForUpdateDTO.class);
    }

    @Deprecated
    public UserFullDTO modelToDTOFull(User user) {
        return modelMapper.map(user, UserFullDTO.class);
    }

    public UserForSaveDTO modelToDTOForSave(User user) {
        return modelMapper.map(user, UserForSaveDTO.class);
    }

    @Deprecated
    public UserDTO modelToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
