package evolution.business;

import evolution.business.api.UserBusinessService;
import evolution.common.BusinessServiceExecuteStatus;
import evolution.common.UserRoleEnum;
import evolution.crud.api.UserCrudManagerService;
import evolution.dto.UserDTOTransfer;
import evolution.dto.model.UserDTO;
import evolution.dto.model.UserDTOForSave;
import evolution.dto.model.UserDTOForUpdate;
import evolution.dto.model.UserFullDTO;

import evolution.model.User;
import evolution.model.UserAdditionalData;
import evolution.service.DateService;
import evolution.service.SecuritySupportService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    private final PasswordEncoder passwordEncoder;

    private final UserDTOTransfer userDTOTransfer;

    @Autowired
    public UserBusinessServiceImpl(SecuritySupportService securitySupportService,
                                   UserCrudManagerService userCrudManagerService,
                                   DateService dateService,
                                   PasswordEncoder passwordEncoder,
                                   UserDTOTransfer userDTOTransfer) {
        this.securitySupportService = securitySupportService;
        this.userCrudManagerService = userCrudManagerService;
        this.dateService = dateService;
        this.passwordEncoder = passwordEncoder;
        this.userDTOTransfer = userDTOTransfer;
    }

    @Override
    public BusinessServiceExecuteResult<UserFullDTO> createNewUserFull(UserDTOForSave userDTOForSave) {
        return null;
    }

    @Override
    public BusinessServiceExecuteResult<UserFullDTO> updateFull(UserDTOForUpdate userDTOForUpdate) {
        return null;
    }

    @Override
    public BusinessServiceExecuteResult<UserDTOForSave> createNewUser(UserDTOForSave userDTOForSave) {
        Optional<User> ou = userCrudManagerService.findByUsername(userDTOForSave.getUserAdditionalData().getUsername());
        if (ou.isPresent()) {
            logger.info("user by username " + userDTOForSave.getUserAdditionalData().getUsername() + ", is already exist !");
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.USER_IS_ALREADY_REGISTRATION_FAILED);
        }

        User user = userDTOTransfer.dtoToModel(userDTOForSave);
        String encodePassword = passwordEncoder.encode(user.getUserAdditionalData().getPassword());

        user.getUserAdditionalData().setPassword(encodePassword);

        user.getUserAdditionalData().setBlock(false);
        user.getUserAdditionalData().setActive(false);
        user.getUserAdditionalData().setSecretKey(UUID.randomUUID().toString());
        user.getUserAdditionalData().setRegistrationDate(dateService.getCurrentDateInUTC());
        user.setRole(UserRoleEnum.USER);
        user.getUserAdditionalData().setUser(user);


//        User user = transferDTO.dtoToModel(userFullDTO);
//
//        // todo check valid
//        if (user.getId() != null)
//            user.setId(null);
//        //set current date in UTC
//        user.getUserAdditionalData().setRegistrationDate(dateService.getCurrentDateInUTC());
//        //encode password
//        String encodePassword = passwordEncoder.encode(user.getUserAdditionalData().getPassword());
//        //set encoded password
//        user.getUserAdditionalData().setPassword(encodePassword);
//        //set default role
//        user.setRole(UserRoleEnum.USER);
//
//        user.getUserAdditionalData().setUser(user);
//
//        //generate secret key
//        String secretKey = UUID.randomUUID().toString();
//        user.getUserAdditionalData().setSecretKey(secretKey);
//        //set block
//        user.getUserAdditionalData().setBlock(false);
//        //set active
//        user.getUserAdditionalData().setActive(false);
//
//        // todo create letter about registration
//
        User result = userCrudManagerService.save(user);
        UserDTOForSave userDTOForSave1 = userDTOTransfer.modelToDTOForSave(result);

        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, userDTOForSave1);

    }

    @Override
    public BusinessServiceExecuteResult<UserDTOForUpdate> update(UserDTOForUpdate userDTOForUpdate) {
        return null;
    }

    @Override
    public Page<UserDTO> findAll(Integer page, Integer size, String sortType, List<String> sortProperties) {
        Page<User> p = userCrudManagerService.findAll(page, size, sortType, sortProperties);
        return p.map(user -> userDTOTransfer.modelToDTO(user));
    }

    @Override
    public Page<UserFullDTO> findAllFull(Integer page, Integer size, String sortType, List<String> sortProperties) {
        Page<User> p = userCrudManagerService.findAllLazy(page, size, sortType, sortProperties);
//        return p.map(user -> transferDTO.modelToDTOUserFull(user));
        return null;
    }

    @Override
    public List<UserDTO> findAll(String sortType, List<String> sortProperties) {
        List<User> list = userCrudManagerService.findAll(sortType, sortProperties);
//        return list.stream().map(o -> transferDTO.modelToDTO(o)).collect(Collectors.toList());
        return null;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserFullDTO> findAllFull() {
        List<User> list = userCrudManagerService.findAll();
//        return list.stream()
//                .map(user -> transferDTO.modelToDTOUserFull(user))
//                .collect(Collectors.toList());
        return null;
    }

    @Override
    public List<UserFullDTO> findAllFull(String sortType, List<String> sortProperties) {
        List<User> list = userCrudManagerService.findAllLazy(sortType, sortProperties);
        return list.stream().map(o -> userDTOTransfer.modelToDTOFull(o)).collect(Collectors.toList());
    }

    @Override
    public Optional<UserFullDTO> findByUsernameFull(String username) {
        return userCrudManagerService.findByUsername(username)
                .map(o -> userDTOTransfer.modelToDTOFull(o));
    }

    @Override
    public Optional<UserDTO> findByUsername(String username) {
        Optional<User> optional = userCrudManagerService.findByUsername(username);
        return optional.map(user -> userDTOTransfer.modelToDTO(user));
    }

    @Override
    public Page<UserDTO> findAllIsBlock(boolean isBlock, Integer page, Integer size, String sortType, List<String> sortProperties) {
        return null;
    }

    @Override
    public List<UserDTO> findAllIsBlock(boolean isBlock, String sortType, List<String> sortProperties) {
        return null;
    }

    @Override
    public List<UserDTO> findAllIsBlock(boolean isBlock) {
        return null;
    }

    @Override
    public Optional<UserDTO> findOne(Long id) {
        return userCrudManagerService
                .findOne(id)
                .map(user -> userDTOTransfer.modelToDTO(user));
    }

    @Override
    public Page<UserFullDTO> findAllIsBlockFull(boolean isBlock, Integer page, Integer size, String sortType, List<String> sortProperties) {
        return null;
    }

    @Override
    public List<UserFullDTO> findAllIsBlockFull(boolean isBlock, String sortType, List<String> sortProperties) {
        return null;
    }

    @Override
    public List<UserFullDTO> findAllIsBlockFull(boolean isBlock) {
        return null;
    }

    @Override
    public Optional<UserFullDTO> findOneUserFull(Long id) {
        if (!securitySupportService.isAllowed(id)) {
            logger.warn("FORBIDDEN !!!");
            return Optional.empty();
        }
//        return userCrudManagerService
//                .findOneFetchUserAdditionalData(id)
//                .map(o -> transferDTO.modelToDTOUserFull(o));
        return null;
    }

    @Override
    public Optional<UserDTO> findOneIsBlock(Long id, boolean isBlock) {
        return null;
    }

    @Override
    public BusinessServiceExecuteResult setRole(Long userId, String role) {
        if (!securitySupportService.isAllowed(userId)) {
            logger.warn("FORBIDDEN !!!");
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.FORBIDDEN);
        }
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

        String newPasswordEncode = passwordEncoder.encode(newPassword);
        optional.get().getUserAdditionalData().setPassword(newPasswordEncode);
        User r = userCrudManagerService.save(optional.get());
        //todo maybe set new secret key
        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, r);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BusinessServiceExecuteResult setPasswordBySecretKey(String newPassword, Long id) {
        Optional<User> optional = userCrudManagerService.findOneFetchUserAdditionalData(id);
        if (!optional.isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
        }

        String newPasswordEncode = passwordEncoder.encode(newPassword);
        optional.get().getUserAdditionalData().setPassword(newPasswordEncode);
        User r = userCrudManagerService.save(optional.get());
        //todo maybe set new secret key
        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, r);
    }

    @Override
    public BusinessServiceExecuteResult setPasswordByOldPassword(Long userId, UserFullDTO userFullDTO, String newPassword) {
        return null;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BusinessServiceExecuteResult setNewSecretKey(Long id) {
        Optional<User> optional = userCrudManagerService.findOneFetchUserAdditionalData(id);
        if (!optional.isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
        }
        optional.get().getUserAdditionalData().setSecretKey(UUID.randomUUID().toString());
        User r = userCrudManagerService.save(optional.get());
        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, r);
    }

    @Override
    public BusinessServiceExecuteResult delete(Long id) {
        if (!securitySupportService.isAllowed(id)) {
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
        Optional<User> optional = userCrudManagerService.findOneFetchUserAdditionalData(id);
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
        Optional<User> optional = userCrudManagerService.findOneFetchUserAdditionalData(id);
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
        Optional<User> optional = userCrudManagerService.findOneFetchUserAdditionalData(id);
        if (!optional.isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
        }

        optional.get().getUserAdditionalData().setActive(false);
        User r = userCrudManagerService.save(optional.get());

        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, r);
    }
}
