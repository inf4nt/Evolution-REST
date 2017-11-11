package evolution.dto.transfer;

import evolution.common.GenderEnum;
import evolution.dto.model.UserDTO;
import evolution.dto.model.UserDTOForSave;
import evolution.dto.model.UserDTOForUpdate;
import evolution.dto.model.UserFullDTO;
import evolution.model.User;
import evolution.model.UserAdditionalData;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.OperationNotSupportedException;
import javax.transaction.NotSupportedException;

/**
 * Created by Infant on 11.11.2017.
 */
@Service("userTransferDTO")
public class UserTransferDTO implements TransferDTO<User, UserDTOForSave, UserDTOForUpdate, UserFullDTO, UserDTO> {

    private final ModelMapper modelMapper;

    @Autowired
    public UserTransferDTO(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDTOForSave modelToDtoSave(User user) {
        return modelMapper.map(user, UserDTOForSave.class);
    }

    @Override
    public UserDTOForUpdate modelToDtoUpdate(User user) {
        return modelMapper.map(user, UserDTOForUpdate.class);
    }

    @Override
    public UserFullDTO modelToDtoFull(User user) {
        return modelMapper.map(user, UserFullDTO.class);
    }

    @Override
    public UserDTO modelToDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public User dtoSaveToModel(UserDTOForSave userDTOForSave) {
        User user = modelMapper.map(userDTOForSave, User.class);
        UserAdditionalData uad = new UserAdditionalData();

//        if (userDTOForSave.getGender() != null) {
//            uad.setGender(GenderEnum.valueOf(userDTOForSave.getGender().toUpperCase()));
//        }
//
//        uad.setState(userDTOForSave.getState());
//        uad.setCountry(userDTOForSave.getCountry());
//        user.setNickname(userDTOForSave.getNickname());
//        user.getUserAdditionalData().setUsername(userDTOForSave.getUsername());
//        user.getUserAdditionalData().setPassword(userDTOForSave.getPassword());
        user.setLastName(userDTOForSave.getLastName());
        user.setFirstName(userDTOForSave.getFirstName());


        user.setUserAdditionalData(uad);
        return user;
    }

    @Override
    public User dtoUpdateToModel(UserDTOForUpdate userDTOForUpdate, User original) {
        original.setFirstName(userDTOForUpdate.getFirstName());
        original.setLastName(userDTOForUpdate.getLastName());
        original.setNickname(userDTOForUpdate.getNickname());
//        original.getUserAdditionalData().setCountry(userDTOForUpdate.getCountry());
//        original.getUserAdditionalData().setState(userDTOForUpdate.getState());
//        original.getUserAdditionalData().setGender(GenderEnum.valueOf(userDTOForUpdate.getGender().toUpperCase()));
        return original;
    }

    @Override
    public User dtoFullToModel(UserFullDTO userFullDTO) {
        return modelMapper.map(userFullDTO, User.class);
    }

    @Override
    public User dtoToModel(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
