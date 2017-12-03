package evolution.business;

import evolution.business.api.UserBusinessService;
import evolution.common.BusinessServiceExecuteStatus;
import evolution.common.UserRoleEnum;
import evolution.crud.api.UserCrudManagerService;
import evolution.dto.UserDTOTransfer;
import evolution.dto.model.UserDTO;
import evolution.dto.model.UserForSaveDTO;
import evolution.dto.model.UserForUpdateDTO;
import evolution.dto.model.UserFullDTO;

import evolution.model.User;
import evolution.service.DateService;
import evolution.service.SecuritySupportService;
import evolution.service.UserTechnicalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Infant on 08.11.2017.
 */
@Service
public class UserBusinessServiceImpl implements UserBusinessService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SecuritySupportService securitySupportService;

    private final UserCrudManagerService userCrudManagerService;

    private final DateService dateService;

    private final UserDTOTransfer userDTOTransfer;

    @Autowired
    private UserTechnicalService userTechnicalService;

    @Autowired
    public UserBusinessServiceImpl(SecuritySupportService securitySupportService,
                                   UserCrudManagerService userCrudManagerService,
                                   DateService dateService,
                                   UserDTOTransfer userDTOTransfer) {
        this.securitySupportService = securitySupportService;
        this.userCrudManagerService = userCrudManagerService;
        this.dateService = dateService;
        this.userDTOTransfer = userDTOTransfer;
    }

    @Override
    public BusinessServiceExecuteResult<User> createNewUserGlobal(UserForSaveDTO userForSaveDTO) {
        Optional<User> ou = userCrudManagerService.findByUsername(userForSaveDTO.getUsername());
        if (ou.isPresent()) {
            logger.info("user by username " + userForSaveDTO.getUsername() + ", is already exist !");
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.USER_IS_ALREADY_EXIST_REGISTRATION_FAILED);
        }

        User user = userDTOTransfer.dtoToModel(userForSaveDTO);

        String encodePassword = userTechnicalService.encodePassword(userForSaveDTO.getPassword());
        user.getUserAdditionalData().setUsername(userForSaveDTO.getUsername());
        user.getUserAdditionalData().setCountry(userForSaveDTO.getCountry());
        user.getUserAdditionalData().setState(userForSaveDTO.getState());
        user.getUserAdditionalData().setPassword(encodePassword);
        user.getUserAdditionalData().setGender(userForSaveDTO.getGender());
        user.getUserAdditionalData().setBlock(false);
        user.getUserAdditionalData().setActive(false);
        user.getUserAdditionalData().setSecretKey(UUID.randomUUID().toString());
        user.getUserAdditionalData().setRegistrationDate(dateService.getCurrentDateInUTC());
        user.setRole(UserRoleEnum.USER);
        user.getUserAdditionalData().setUser(user);

        User result = userCrudManagerService.save(user);

        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, result);
    }

    @Override
    public BusinessServiceExecuteResult<User> updateGlobal(UserForUpdateDTO userForUpdateDTO) {
        if (!securitySupportService.isAllowedFull(userForUpdateDTO.getId())) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.FORBIDDEN);
        }
        Optional<User> optional = userCrudManagerService.findOneLazy(userForUpdateDTO.getId());
        if (!optional.isPresent()) {
            logger.info("user by id " + userForUpdateDTO.getId() + " not found");
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
        }
        User original = optional.get();

        original.setFirstName(userForUpdateDTO.getFirstName());
        original.setLastName(userForUpdateDTO.getLastName());
        original.setNickname(userForUpdateDTO.getNickname());
        original.getUserAdditionalData().setCountry(userForUpdateDTO.getCountry());
        original.getUserAdditionalData().setState(userForUpdateDTO.getState());
        original.getUserAdditionalData().setGender(userForUpdateDTO.getGender());

        User result = userCrudManagerService.save(original);

        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, result);
    }

    @Override
    public BusinessServiceExecuteResult<UserFullDTO> createNewUserFull(UserForSaveDTO userForSaveDTO) {
        BusinessServiceExecuteResult<User> b = createNewUserGlobal(userForSaveDTO);
        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK) {
            return BusinessServiceExecuteResult
                    .build(BusinessServiceExecuteStatus.OK, b.getResultObjectOptional().map(o -> userDTOTransfer.modelToDTOFull(o)));
        }
        return BusinessServiceExecuteResult.build(b.getExecuteStatus());
    }

    @Override
    public BusinessServiceExecuteResult<UserFullDTO> updateFull(UserForUpdateDTO userForUpdateDTO) {
        BusinessServiceExecuteResult<User> b = updateGlobal(userForUpdateDTO);
        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK) {
            return BusinessServiceExecuteResult
                    .build(BusinessServiceExecuteStatus.OK, b.getResultObjectOptional().map(o -> userDTOTransfer.modelToDTOFull(o)));
        }
        return BusinessServiceExecuteResult.build(b.getExecuteStatus());
    }

    @Override
    public BusinessServiceExecuteResult<UserFullDTO> createNewUser(UserForSaveDTO userForSaveDTO) {
        BusinessServiceExecuteResult<User> b = createNewUserGlobal(userForSaveDTO);
        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK) {
            return BusinessServiceExecuteResult
                    .build(BusinessServiceExecuteStatus.OK, b.getResultObjectOptional().map(o -> userDTOTransfer.modelToDTOFull(o)));
        }
        return BusinessServiceExecuteResult.build(b.getExecuteStatus());
    }

    @Override
    public BusinessServiceExecuteResult<UserFullDTO> update(UserForUpdateDTO userForUpdateDTO) {
        BusinessServiceExecuteResult<User> b = updateGlobal(userForUpdateDTO);
        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK) {
            return BusinessServiceExecuteResult
                    .build(BusinessServiceExecuteStatus.OK, b.getResultObjectOptional().map(o -> userDTOTransfer.modelToDTOFull(o)));
        }
        return BusinessServiceExecuteResult.build(b.getExecuteStatus());
    }

    @Override
    public Page<UserDTO> findAll(Integer page, Integer size, String sortType, List<String> sortProperties) {
        return userCrudManagerService
                .findAll(page, size, sortType, sortProperties)
                .map(user -> userDTOTransfer.modelToDTO(user));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Page<UserFullDTO> findAllFull(Integer page, Integer size, String sortType, List<String> sortProperties) {
        return userCrudManagerService
                .findAllLazy(page, size, sortType, sortProperties)
                .map(o -> userDTOTransfer.modelToDTOFull(o));
    }

    @Override
    public List<UserDTO> findAll(String sortType, List<String> sortProperties) {
        return userCrudManagerService
                .findAll(sortType, sortProperties)
                .stream()
                .map(o -> userDTOTransfer.modelToDTO(o))
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserFullDTO> findAllFull() {
        return userCrudManagerService
                .findAll()
                .stream()
                .map(o -> userDTOTransfer.modelToDTOFull(o))
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserFullDTO> findAllFull(String sortType, List<String> sortProperties) {
        return userCrudManagerService
                .findAllLazy(sortType, sortProperties)
                .stream()
                .map(o -> userDTOTransfer.modelToDTOFull(o))
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Optional<UserFullDTO> findByUsernameFull(String username) {
        return userCrudManagerService
                .findByUsername(username)
                .map(o -> userDTOTransfer.modelToDTOFull(o));
    }

    @Override
    public Optional<UserDTO> findByUsername(String username) {
        return userCrudManagerService
                .findByUsername(username)
                .map(o -> userDTOTransfer.modelToDTO(o));
    }

    @Override
    public Page<UserDTO> findAllIsBlock(boolean isBlock, Integer page, Integer size, String sortType, List<String> sortProperties) {
        return userCrudManagerService
                .findUserAllByIsBlock(isBlock, page, size, sortType, sortProperties)
                .map(o -> userDTOTransfer.modelToDTO(o));
    }

    @Override
    public List<UserDTO> findAllIsBlock(boolean isBlock, String sortType, List<String> sortProperties) {
        return userCrudManagerService
                .findUserAllByIsBlock(isBlock, sortType, sortProperties)
                .stream()
                .map(o -> userDTOTransfer.modelToDTO(o))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findAllIsBlock(boolean isBlock) {
        return userCrudManagerService
                .findUserAllByIsBlock(isBlock)
                .stream()
                .map(o -> userDTOTransfer.modelToDTO(o))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> findOne(Long id) {
        return userCrudManagerService
                .findOne(id)
                .map(user -> userDTOTransfer.modelToDTO(user));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Page<UserFullDTO> findAllIsBlockFull(boolean isBlock, Integer page, Integer size, String sortType, List<String> sortProperties) {
        return userCrudManagerService
                .findUserAllByIsBlock(isBlock, page, size, sortType, sortProperties)
                .map(o -> userDTOTransfer.modelToDTOFull(o));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserFullDTO> findAllIsBlockFull(boolean isBlock, String sortType, List<String> sortProperties) {
        return userCrudManagerService
                .findUserAllByIsBlock(isBlock, sortType, sortProperties)
                .stream()
                .map(o -> userDTOTransfer.modelToDTOFull(o))
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserFullDTO> findAllIsBlockFull(boolean isBlock) {
        return userCrudManagerService
                .findUserAllByIsBlock(isBlock)
                .stream()
                .map(o -> userDTOTransfer.modelToDTOFull(o))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserFullDTO> findOneUserFull(Long id) {
        if (!securitySupportService.isAllowedFull(id)) {
            logger.warn("FORBIDDEN !!!");
            return Optional.empty();
        }
        return userCrudManagerService
                .findOneLazy(id)
                .map(o -> userDTOTransfer.modelToDTOFull(o));
    }

    @Override
    public Optional<UserDTO> findOneIsBlock(Long id, boolean isBlock) {
        return userCrudManagerService
                .findOneUserByIdAndIsBlock(id, isBlock)
                .map(o -> userDTOTransfer.modelToDTO(o));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BusinessServiceExecuteResult setRole(Long userId, String role) {
        Optional<User> user = userCrudManagerService.findOne(userId);
        if (!user.isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
        }
        user.get().setRole(UserRoleEnum.valueOf(role.toUpperCase()));
        User r = userCrudManagerService.save(user.get());
        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, r);
    }

    @Override
    public BusinessServiceExecuteResult setPasswordBySecretKey(String newPassword, String secretKey) {
        Optional<User> optional = userCrudManagerService.findUserBySecretKeyLazy(secretKey);
        if (!optional.isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
        }

        String newPasswordEncode = userTechnicalService.encodePassword(newPassword);
        optional.get().getUserAdditionalData().setPassword(newPasswordEncode);
        User r = userCrudManagerService.save(optional.get());
        //todo maybe set new secret key
        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, r);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BusinessServiceExecuteResult setPasswordByUserId(String newPassword, Long id) {
        Optional<User> optional = userCrudManagerService.findOneLazy(id);
        if (!optional.isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
        }

        String newPasswordEncode = userTechnicalService.encodePassword(newPassword);
        optional.get().getUserAdditionalData().setPassword(newPasswordEncode);
        User r = userCrudManagerService.save(optional.get());
        //todo maybe set new secret key
        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, r);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BusinessServiceExecuteResult<UserFullDTO> setPasswordByOldPassword(UserFullDTO userFullDTO) {
        String newPassword = userTechnicalService.encodePassword(userFullDTO.getUserAdditionalData().getPassword());
        Optional<User> op = userCrudManagerService.findOneLazy(userFullDTO.getId());
        if (!op.isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
        }
        User u = op.get();
        u.getUserAdditionalData().setPassword(newPassword);
        userCrudManagerService.save(u);

        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, userDTOTransfer.modelToDTOFull(u));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BusinessServiceExecuteResult setNewSecretKey(Long id) {
        Optional<User> optional = userCrudManagerService.findOneLazy(id);
        if (!optional.isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
        }
        optional.get().getUserAdditionalData().setSecretKey(UUID.randomUUID().toString());
        User r = userCrudManagerService.save(optional.get());
        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, r);
    }

    @Override
    public BusinessServiceExecuteResult delete(Long id) {
        if (!securitySupportService.isAllowedFull(id)) {
            logger.warn("FORBIDDEN !!!");
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.FORBIDDEN);
        }
        userCrudManagerService.delete(id);
        Optional<User> optional = userCrudManagerService.findOne(id);
        if (optional.isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.EXPECTATION_FAILED);
        } else {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK);
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BusinessServiceExecuteResult delete(List<Long> ids) {
        ids.forEach(o -> userCrudManagerService.delete(o));

        for (Long id : ids) {
            Optional<User> user = userCrudManagerService.findOne(id);
            if (user.isPresent())
                return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.EXPECTATION_FAILED);
        }

        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK);
    }

    @Override
    public BusinessServiceExecuteResult deleteAll() {
        List<User> list = userCrudManagerService.findAll();
        list.forEach(o -> userCrudManagerService.delete(o.getId()));

        list.clear();
        list = userCrudManagerService.findAll();
        if (!list.isEmpty()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.EXPECTATION_FAILED);
        }

        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK);
    }

    @Override
    public BusinessServiceExecuteResult activatedUser(String secretKey) {
        Optional<User> optional = userCrudManagerService.findUserBySecretKeyLazy(secretKey);
        if (!optional.isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
        }

        optional.get().getUserAdditionalData().setActive(true);
        User r = userCrudManagerService.save(optional.get());

        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, r);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BusinessServiceExecuteResult deactivatedUser(Long id) {
        Optional<User> optional = userCrudManagerService.findOneLazy(id);
        if (!optional.isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
        }

        optional.get().getUserAdditionalData().setActive(false);
        User r = userCrudManagerService.save(optional.get());

        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, r);
    }

    @Override
    public BusinessServiceExecuteResult deactivatedUser(String secretKey) {
        Optional<User> optional = userCrudManagerService.findUserBySecretKeyLazy(secretKey);
        if (!optional.isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
        }

        optional.get().getUserAdditionalData().setActive(false);
        User r = userCrudManagerService.save(optional.get());

        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, r);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BusinessServiceExecuteResult sendBlockToUser(Long id) {
        Optional<User> optional = userCrudManagerService.findOneLazy(id);
        if (!optional.isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
        }

        optional.get().getUserAdditionalData().setBlock(true);
        User r = userCrudManagerService.save(optional.get());

        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, r);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BusinessServiceExecuteResult sendUnBlockToUser(Long id) {
        Optional<User> optional = userCrudManagerService.findOneLazy(id);
        if (!optional.isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
        }

        optional.get().getUserAdditionalData().setActive(false);
        User r = userCrudManagerService.save(optional.get());

        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, r);
    }
}
