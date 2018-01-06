package evolution.dto.transfer;

import evolution.dto.model.UserDTO;
import evolution.dto.model.UserDTOLazy;
import evolution.dto.model.UserSaveDTO;
import evolution.model.User;
import evolution.model.UserAdditionalData;
import evolution.security.model.CustomSecurityUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDTOTransfer implements TransferDTOLazy<UserDTO, UserDTOLazy, User> {

    private final ModelMapper modelMapper;

    @Autowired
    public UserDTOTransfer(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDTO modelToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTOLazy modelToDTOLazy(User user) {
        UserDTOLazy userDTOLazy = new UserDTOLazy();

        userDTOLazy.setId(user.getId());
        userDTOLazy.setFirstName(user.getFirstName());
        userDTOLazy.setLastName(user.getLastName());
        userDTOLazy.setNickname(user.getNickname());
        userDTOLazy.setRole(user.getRole().name());

        userDTOLazy.setActive(user.getUserAdditionalData().isActive());
        userDTOLazy.setBlock(user.getUserAdditionalData().isBlock());
        userDTOLazy.setCountry(user.getUserAdditionalData().getCountry());
        userDTOLazy.setGender(user.getUserAdditionalData().getGender());
        userDTOLazy.setRegistrationDate(user.getUserAdditionalData().getRegistrationDate());
        userDTOLazy.setState(user.getUserAdditionalData().getState());
        userDTOLazy.setSecretKey(user.getUserAdditionalData().getSecretKey());
        userDTOLazy.setUsername(user.getUserAdditionalData().getUsername());
        return userDTOLazy;
    }

    public UserDTO modelToDTO(CustomSecurityUser user) {
        return modelMapper.map(user.getUser(), UserDTO.class);
    }

    public User dtoToModel(UserSaveDTO saveDTO) {
        User res = new User();

        res.setFirstName(saveDTO.getFirstName());
        res.setLastName(saveDTO.getLastName());
        res.setNickname(saveDTO.getNickname());

        UserAdditionalData ad = new UserAdditionalData();
        ad.setUser(res);
        ad.setUsername(saveDTO.getUsername());
        ad.setCountry(saveDTO.getCountry());
        ad.setState(saveDTO.getState());
        ad.setGender(saveDTO.getGender());

        res.setUserAdditionalData(ad);

        return res;
    }

//    public User dtoToModel(UserDTO userDTO) {
//        return modelMapper.map(userDTO, User.class);
//    }

//    public User dtoMoModel(UserDTOLazy userDTOLazy) {
//        User user = dtoToModel(userDTOLazy);
//        UserAdditionalData additionalData = new UserAdditionalData();
//
//        additionalData.setCountry(userDTOLazy.getCountry());
//        additionalData.setState(user.getUserAdditionalData().getState());
//        additionalData.setGender(userDTOLazy.getGender());
//
//        additionalData.setUsername(userDTOLazy.getUsername());
//        additionalData.setActive(userDTOLazy.isActive());
//        additionalData.setBlock(userDTOLazy.isBlock());
//        additionalData.setRegistrationDate(userDTOLazy.getRegistrationDate());
//        additionalData.setSecretKey(user.getUserAdditionalData().getSecretKey());
//
//        additionalData.setUser(user);
//        user.setUserAdditionalData(additionalData);
//        return null;
//    }

}
