package evolution.user.business;

import evolution.business.BusinessServiceExecuteResult;
import evolution.message.business.api.MessageBusinessService;
import evolution.user.business.api.UserBusinessService;
import evolution.common.BusinessServiceExecuteStatus;
import evolution.user.common.UserRoleEnum;
import evolution.user.crud.api.UserCrudManagerService;

import evolution.user.dto.UserDTO;
import evolution.user.dto.UserDTOLazy;
import evolution.user.dto.UserSaveDTO;
import evolution.user.dto.UserUpdateDTO;
import evolution.user.dto.UserSetPasswordDTO;
import evolution.user.dto.transfer.UserDTOTransfer;
import evolution.user.model.User;
import evolution.service.DateService;
import evolution.security.service.SecuritySupportService;
import evolution.service.UserTechnicalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    private final MessageBusinessService messageBusinessService;

    @Autowired
    private UserTechnicalService userTechnicalService;

    @Autowired
    public UserBusinessServiceImpl(SecuritySupportService securitySupportService,
                                   UserCrudManagerService userCrudManagerService,
                                   DateService dateService,
                                   UserDTOTransfer userDTOTransfer,
                                   MessageBusinessService messageBusinessService) {
        this.securitySupportService = securitySupportService;
        this.userCrudManagerService = userCrudManagerService;
        this.dateService = dateService;
        this.userDTOTransfer = userDTOTransfer;
        this.messageBusinessService = messageBusinessService;
    }

    @Override
    @Transactional
    public BusinessServiceExecuteResult<User> createNewUser(UserSaveDTO userSaveDTO) {
        Optional<User> ou = userCrudManagerService.findByUsernameLazy(userSaveDTO.getUsername());
        if (ou.isPresent()) {
            logger.info("user by username " + userSaveDTO.getUsername() + ", is already exist !");
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.USER_IS_ALREADY_EXIST_REGISTRATION_FAILED);
        }

        User user = userDTOTransfer.dtoToModel(userSaveDTO);

        String encodePassword = userTechnicalService.encodePassword(userSaveDTO.getPassword());
        user.getUserAdditionalData().setPassword(encodePassword);
        user.getUserAdditionalData().setBlock(false);
        user.getUserAdditionalData().setActive(false);
        user.getUserAdditionalData().setSecretKey(UUID.randomUUID().toString());
        user.getUserAdditionalData().setRegistrationDate(dateService.getCurrentDateInUTC());
        user.setRole(UserRoleEnum.USER);

        User result = userCrudManagerService.save(user);

        messageBusinessService.createFirstMessageAfterRegistration(result.getId());

        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, result);
    }

    @Override
    public BusinessServiceExecuteResult<UserDTOLazy> createNewUser2(UserSaveDTO userSaveDTO) {
        BusinessServiceExecuteResult<User> b = createNewUser(userSaveDTO);
        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK) {
            return BusinessServiceExecuteResult
                    .build(BusinessServiceExecuteStatus.OK, b.getResultObjectOptional().map(o -> userDTOTransfer.modelToDTOLazy(o)));
        }
        return BusinessServiceExecuteResult.build(b.getExecuteStatus());
    }

    @Override
    public BusinessServiceExecuteResult<BusinessServiceExecuteStatus> createNewUser3(UserSaveDTO userSaveDTO) {
        BusinessServiceExecuteResult<User> b = createNewUser(userSaveDTO);
        return BusinessServiceExecuteResult.build(b.getExecuteStatus());
    }

    @Override
    @Transactional
    public BusinessServiceExecuteResult<User> update(UserUpdateDTO user) {
        if (!securitySupportService.isAllowedFull(user.getId())) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.FORBIDDEN);
        }
        Optional<User> optional = userCrudManagerService.findOneLazy(user.getId());
        if (!optional.isPresent()) {
            logger.info("user by id " + user.getId() + " not found");
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
        }
        User original = optional.get();

        original.setFirstName(user.getFirstName());
        original.setLastName(user.getLastName());
        original.setNickname(user.getNickname());
        original.getUserAdditionalData().setCountry(user.getCountry());
        original.getUserAdditionalData().setState(user.getState());
        original.getUserAdditionalData().setGender(user.getGender());

        User result = userCrudManagerService.save(original);

        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, result);
    }

    @Override
    public BusinessServiceExecuteResult<UserDTOLazy> update2(UserUpdateDTO user) {
        BusinessServiceExecuteResult<User> b = update(user);
        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK && b.getResultObjectOptional().isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, b.getResultObjectOptional().map(o -> userDTOTransfer.modelToDTOLazy(o)));
        }
        return BusinessServiceExecuteResult.build(b.getExecuteStatus());
    }

    @Override
    public BusinessServiceExecuteResult<BusinessServiceExecuteStatus> update3(UserUpdateDTO user) {
        BusinessServiceExecuteResult<User> b = update(user);
        return BusinessServiceExecuteResult.build(b.getExecuteStatus());
    }

    @Override
    public List<UserDTO> findAll(String sortType, List<String> sortProperties) {
        List<User> list = userCrudManagerService
                .findAll(sortType, sortProperties);
        return userDTOTransfer.modelToDTO(list);
    }

    @Override
    public Page<UserDTO> findAll(Integer page, Integer size, String sortType, List<String> sortProperties) {
        Page<User> p = userCrudManagerService.findAll(page, size, sortType, sortProperties);
        return userDTOTransfer
                .modelToDTO(p);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserDTOLazy> findAllLazy() {
        List<User> list = userCrudManagerService.findAllLazy();
        return userDTOTransfer.modelToDTOLazy(list);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserDTOLazy> findAllLazy(String sortType, List<String> sortProperties) {
        List<User> list = userCrudManagerService.findAllLazy(sortType, sortProperties);
        return userDTOTransfer.modelToDTOLazy(list);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Page<UserDTOLazy> findAllLazy(Integer page, Integer size, String sortType, List<String> sortProperties) {
        Page<User> p = userCrudManagerService.findAllLazy(page, size, sortType, sortProperties);
        return userDTOTransfer.modelToDTOLazy(p);
    }

    @Override
    public Optional<UserDTO> findByUsername(String username) {
        Optional<User> op = userCrudManagerService.findByUsernameLazy(username);
        return userDTOTransfer.modelToDTO(op);
    }

    @Override
    public BusinessServiceExecuteResult<UserDTOLazy> findByUsernameLazy(String username) {
        if (!securitySupportService.isAllowedFull(username)) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.FORBIDDEN);
        }

        Optional<User> op = userCrudManagerService.findByUsernameLazy(username);
        if (op.isPresent()) {
            Optional<UserDTOLazy> opl =  userDTOTransfer.modelToDTOLazy(op);
            if (opl.isPresent()) {
                return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, opl.get());
            }
        }
        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NO_CONTENT);
    }

    @Override
    public Page<UserDTO> findAllAndIsBlock(boolean isBlock, Integer page, Integer size, String sortType, List<String> sortProperties) {
        Page<User> p = userCrudManagerService
                .findAllAndIsBlock(isBlock, page, size, sortType, sortProperties);
        return userDTOTransfer.modelToDTO(p);
    }

    @Override
    public List<UserDTO> findAllAndIsBlock(boolean isBlock, String sortType, List<String> sortProperties) {
        List<User> list = userCrudManagerService.findAllAndIsBlock(isBlock, sortType, sortProperties);
        return userDTOTransfer.modelToDTO(list);
    }

    @Override
    public List<UserDTO> findAllAndIsBlock(boolean isBlock) {
        List<User> list = userCrudManagerService.findAllAndIsBlock(isBlock);
        return userDTOTransfer.modelToDTO(list);
    }

    @Override
    public Optional<UserDTO> findOne(Long id) {
        Optional<User> op = userCrudManagerService.findOne(id);
        return userDTOTransfer.modelToDTO(op);
    }

    @Override
    public Optional<User> findOneModel(Long id) {
        return userCrudManagerService.findOne(id);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Page<UserDTOLazy> findAllAndIsBlockLazy(boolean isBlock, Integer page, Integer size, String sortType, List<String> sortProperties) {
        Page<User> p = userCrudManagerService
                .findAllAndIsBlockLazy(isBlock, page, size, sortType, sortProperties);
        return userDTOTransfer.modelToDTOLazy(p);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserDTOLazy> findAllAndIsBlockLazy(boolean isBlock, String sortType, List<String> sortProperties) {
        List<User> list = userCrudManagerService.findAllAndIsBlockLazy(isBlock, sortType, sortProperties);
        return userDTOTransfer.modelToDTOLazy(list);
    }

    @Override
    public List<UserDTO> findAllAndIsActive(boolean isActive) {
        List<User> list = userCrudManagerService.findAllAndIsActive(isActive);
        return userDTOTransfer.modelToDTO(list);
    }

    @Override
    public List<UserDTO> findAllAndIsActive(boolean isActive, String sortType, List<String> sortProperties) {
        List<User> list = userCrudManagerService.findAllAndIsActive(isActive, sortType, sortProperties);
        return userDTOTransfer.modelToDTO(list);
    }

    @Override
    public Page<UserDTO> findAllAndIsActive(boolean isActive, Integer page, Integer size, String sortType, List<String> sortProperties) {
        Page<User> p = userCrudManagerService.findAllAndIsActive(isActive, page, size, sortType, sortProperties);
        return userDTOTransfer.modelToDTO(p);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserDTOLazy> findAllAndIsActiveLazy(boolean isActive) {
        List<User> list = userCrudManagerService.findAllAndIsActiveLazy(isActive);
        return userDTOTransfer.modelToDTOLazy(list);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Page<UserDTOLazy> findAllAndIsActiveLazy(boolean isActive, Integer page, Integer size, String sortType, List<String> sortProperties) {
        Page<User> p = userCrudManagerService.findAllAndIsActiveLazy(isActive, page, size, sortType, sortProperties);
        return userDTOTransfer.modelToDTOLazy(p);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserDTOLazy> findAllAndIsActiveLazy(boolean isActive, String sortType, List<String> sortProperties) {
        List<User> list = userCrudManagerService.findAllAndIsActiveLazy(isActive, sortType, sortProperties);
        return userDTOTransfer.modelToDTOLazy(list);
    }

    @Override
    public List<UserDTO> findAll() {
        List<User> list = userCrudManagerService.findAll();
        return userDTOTransfer.modelToDTO(list);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserDTOLazy> findAllAndIsBlockLazy(boolean isBlock) {
        List<User> list = userCrudManagerService.findAllAndIsBlockLazy(isBlock);
        return userDTOTransfer.modelToDTOLazy(list);
    }

    @Override
    public BusinessServiceExecuteResult<UserDTOLazy> findOneLazy(Long id) {
        if (!securitySupportService.isAllowedFull(id)) {
            logger.warn("FORBIDDEN !!!");
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.FORBIDDEN);
        }
        Optional<UserDTOLazy> o = userCrudManagerService
                .findOneLazy(id)
                .map(op -> userDTOTransfer.modelToDTOLazy(op));

        if (o.isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, o.get());
        }
        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NO_CONTENT);
    }

    @Override
    public Optional<UserDTO> findOneAndIsBlock(Long id, boolean isBlock) {
        Optional<User> op = userCrudManagerService.findOneAndIsBlock(id, isBlock);
        return userDTOTransfer.modelToDTO(op);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Optional<UserDTOLazy> findOneAndIsBlockLazy(Long id, boolean isBlock) {
        Optional<User> op = userCrudManagerService.findOneAndIsBlockLazy(id, isBlock);
        return userDTOTransfer.modelToDTOLazy(op);
    }

    @Override
    public Optional<UserDTO> findOneAndIsActive(Long id, boolean isActive) {
        Optional<User> op = userCrudManagerService.findOneAndIsActive(id, isActive);
        return userDTOTransfer.modelToDTO(op);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Optional<UserDTOLazy> findOneAndIsActiveLazy(Long id, boolean isActive) {
        Optional<User> op = userCrudManagerService.findOneAndIsActiveLazy(id, isActive);
        return userDTOTransfer.modelToDTOLazy(op);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BusinessServiceExecuteResult<UserDTOLazy> setRole(Long userId, String role) {
        Optional<User> user = userCrudManagerService.findOne(userId);
        if (!user.isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
        }
        user.get().setRole(UserRoleEnum.valueOf(role.toUpperCase()));
        User r = userCrudManagerService.save(user.get());
        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, userDTOTransfer.modelToDTOLazy(r));
    }

    @Override
    @Transactional
    public BusinessServiceExecuteResult<UserDTOLazy> setPasswordBySecretKey(String newPassword, String secretKey) {
        Optional<User> optional = userCrudManagerService.findUserBySecretKeyLazy(secretKey);
        if (!optional.isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
        }

        if (!securitySupportService.isAllowed(optional.get().getUserAdditionalData().getUsername())) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.FORBIDDEN);
        }

        String newPasswordEncode = userTechnicalService.encodePassword(newPassword);
        optional.get().getUserAdditionalData().setPassword(newPasswordEncode);
        User r = userCrudManagerService.save(optional.get());

        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, userDTOTransfer.modelToDTOLazy(r));
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BusinessServiceExecuteResult<UserDTOLazy> setPasswordByUserId(String newPassword, Long id) {
        Optional<User> optional = userCrudManagerService.findOneLazy(id);
        if (!optional.isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
        }

        String newPasswordEncode = userTechnicalService.encodePassword(newPassword);
        optional.get().getUserAdditionalData().setPassword(newPasswordEncode);
        User r = userCrudManagerService.save(optional.get());
        //todo maybe set new secret key
        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, userDTOTransfer.modelToDTOLazy(r));
    }

    @Override
    @Transactional
    public BusinessServiceExecuteResult<UserDTOLazy> setPasswordByOldPassword(UserSetPasswordDTO user) {
        if (!securitySupportService.isAllowed(user.getId())) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.FORBIDDEN);
        }

        Optional<User> op = userCrudManagerService.findOneLazy(user.getId());
        if (!op.isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
        }
        User u = op.get();
        String newPassword = userTechnicalService.encodePassword(user.getNewPassword());
        if (!userTechnicalService.matches(user.getOldPassword(), u.getUserAdditionalData().getPassword())) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.EXPECTATION_FAILED);
        }
        u.getUserAdditionalData().setPassword(newPassword);
        User r = userCrudManagerService.save(u);

        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, userDTOTransfer.modelToDTOLazy(r));
    }

    @Override
    @Transactional
    public BusinessServiceExecuteResult<UserDTOLazy> generateNewSecretKey(Long id) {
        if (securitySupportService.isAllowedFull(id)) {
            Optional<User> optional = userCrudManagerService.findOneLazy(id);
            if (!optional.isPresent()) {
                return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
            }
            optional.get().getUserAdditionalData().setSecretKey(UUID.randomUUID().toString());
            User r = userCrudManagerService.save(optional.get());
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, userDTOTransfer.modelToDTOLazy(r));
        } else {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.FORBIDDEN);
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BusinessServiceExecuteResult<BusinessServiceExecuteStatus> delete(Long id) {
        userCrudManagerService.delete(id);
        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BusinessServiceExecuteResult<BusinessServiceExecuteStatus> delete(List<Long> ids) {
        userCrudManagerService.delete(ids);
        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BusinessServiceExecuteResult<BusinessServiceExecuteStatus> deleteAll() {
        userCrudManagerService.deleteAll();
        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK);
    }

    @Override
    @Transactional
    public BusinessServiceExecuteResult<UserDTOLazy> activatedUser(String secretKey) {
        Optional<User> optional = userCrudManagerService.findUserBySecretKeyLazy(secretKey);
        if (!optional.isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
        }

        optional.get().getUserAdditionalData().setActive(true);
        User r = userCrudManagerService.save(optional.get());

        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, userDTOTransfer.modelToDTOLazy(r));
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BusinessServiceExecuteResult<UserDTOLazy> deactivatedUser(Long id) {
        Optional<User> optional = userCrudManagerService.findOneLazy(id);
        if (!optional.isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
        }

        optional.get().getUserAdditionalData().setActive(false);
        User r = userCrudManagerService.save(optional.get());

        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, userDTOTransfer.modelToDTOLazy(r));
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BusinessServiceExecuteResult<UserDTOLazy> deactivatedUser(String secretKey) {
        Optional<User> optional = userCrudManagerService.findUserBySecretKeyLazy(secretKey);
        if (!optional.isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
        }

        optional.get().getUserAdditionalData().setActive(false);
        User r = userCrudManagerService.save(optional.get());

        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, userDTOTransfer.modelToDTOLazy(r));
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BusinessServiceExecuteResult<UserDTOLazy> sendBlockToUser(Long id) {
        Optional<User> optional = userCrudManagerService.findOneLazy(id);
        if (!optional.isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
        }

        optional.get().getUserAdditionalData().setBlock(true);
        User r = userCrudManagerService.save(optional.get());

        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, userDTOTransfer.modelToDTOLazy(r));
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BusinessServiceExecuteResult<UserDTOLazy> sendUnBlockToUser(Long id) {
        Optional<User> optional = userCrudManagerService.findOneLazy(id);
        if (!optional.isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
        }

        optional.get().getUserAdditionalData().setActive(false);
        User r = userCrudManagerService.save(optional.get());

        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, userDTOTransfer.modelToDTOLazy(r));
    }
}
