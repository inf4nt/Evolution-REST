package evolution.controller;



import evolution.business.api.UserBusinessService;
import evolution.dto.model.UserDTOForSave;
import evolution.dto.model.UserFullDTO;
import evolution.model.User;
import evolution.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

/**
 * Created by Infant on 07.11.2017.
 */
@RestController
@RequestMapping(value = "/test")
@CrossOrigin
public class TestMessageController {

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    private final UserBusinessService userBusinessService;

    @Autowired
    public TestMessageController(ModelMapper modelMapper,
                                 UserRepository userRepository, UserBusinessService userBusinessService) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;

        this.userBusinessService = userBusinessService;
    }

    @GetMapping(value = "/dto/{id}")
    public UserFullDTO getDTO(@PathVariable Long id) {
        Optional<User> user = userRepository.findOneUserByIdLazy(id);
        return user.map(o -> modelMapper
                .map(o, UserFullDTO.class))
                .orElse(null);
    }

    @GetMapping(value = "/model/{id}")
    public User getModel(@PathVariable Long id) {
        User user = userRepository.findOneUserByIdLazy(id).get();

        UserDTOForSave userDTOForSave = modelMapper.map(user, UserDTOForSave.class);

        System.out.println("userDTOForSave " + userDTOForSave);

        userDTOForSave.setFirstName("LOOOOOX");
        userDTOForSave.setLastName("POOOOCCC");

        User r = modelMapper.map(userDTOForSave, User.class);

        return r;
    }

    @GetMapping(value = "/post")
    public UserDTOForSave testPost() {
        UserDTOForSave userDTOForSave = new UserDTOForSave();
        userDTOForSave.setLastName("ln_post");
        userDTOForSave.setFirstName("fn_post");
        userDTOForSave.setNickname("nickname_post");
        userDTOForSave.getUserAdditionalData().setUsername("username_post");
        userDTOForSave.getUserAdditionalData().setPassword("1111");

        return userBusinessService.createNewUser(userDTOForSave).getResultObjectOptional().orElseGet(null);
    }

}
