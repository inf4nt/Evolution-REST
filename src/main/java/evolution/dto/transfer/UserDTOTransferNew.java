package evolution.dto.transfer;

import evolution.dto.model.UserDTO;
import evolution.dto.model.UserDTOLazy;
import evolution.dto.model.UserSaveDTO;
import evolution.model.User;
import evolution.model.UserAdditionalData;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDTOTransferNew {

    @Autowired
    private ModelMapper modelMapper;

    public UserDTO modelToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

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

    public List<UserDTO> modelToDTO(List<User> list) {
        return list
                .stream()
                .map(o -> modelToDTO(o))
                .collect(Collectors.toList());
    }

    public List<UserDTOLazy> modelToDTOLazy(List<User> list) {
        return list
                .stream()
                .map(o -> modelToDTOLazy(o))
                .collect(Collectors.toList());
    }

    public Page<UserDTO> modelToDTO(Page<User> page) {
        return page
                .map(o -> modelToDTO(o));
    }

    public Page<UserDTOLazy> modelToDTOLazy(Page<User> page) {
        return page
                .map(o -> modelToDTOLazy(o));
    }

    public Optional<UserDTO> modelToDTO(Optional<User> page) {
        return page
                .map(o -> modelToDTO(o));
    }

    public Optional<UserDTOLazy> modelToDTOLazy(Optional<User> page) {
        return page
                .map(o -> modelToDTOLazy(o));
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
