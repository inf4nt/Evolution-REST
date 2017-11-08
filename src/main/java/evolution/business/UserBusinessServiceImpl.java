package evolution.business;

import evolution.business.api.UserBusinessService;
import evolution.common.BusinessServiceExecuteStatus;
import evolution.common.UserRoleEnum;
import evolution.crud.api.UserCrudManagerService;
import evolution.dto.model.UserDTO;
import evolution.dto.model.UserDTOForSave;
import evolution.dto.model.UserFullDTO;
import evolution.dto.transfer.TransferDTO;
import evolution.model.User;
import evolution.service.DateService;
import evolution.service.SecuritySupportService;
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

    private final TransferDTO transferDTO;

    private final UserCrudManagerService userCrudManagerService;

    private final DateService dateService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserBusinessServiceImpl(SecuritySupportService securitySupportService, TransferDTO transferDTO, UserCrudManagerService userCrudManagerService, DateService dateService, PasswordEncoder passwordEncoder) {
        this.securitySupportService = securitySupportService;
        this.transferDTO = transferDTO;
        this.userCrudManagerService = userCrudManagerService;
        this.dateService = dateService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public BusinessServiceExecuteResult<UserFullDTO> createNewUser(UserFullDTO userFullDTO) {
        Optional<User> ou = userCrudManagerService.findByUsername(userFullDTO.getUserAdditionalDataDTO().getUsername());
        if (ou.isPresent()) {
            logger.info("user by username " + userFullDTO.getUserAdditionalDataDTO().getUsername() + ", is already exist !");
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.USER_IS_ALREADY_REGISTRATION_FAILED);
        }

        User user = transferDTO.dtoToModel(userFullDTO);
        // todo check valid
        if (user.getId() != null)
            user.setId(null);
        //set current date in UTC
        user.getUserAdditionalData().setRegistrationDate(dateService.getCurrentDateInUTC());
        //encode password
        String encodePassword = passwordEncoder.encode(user.getUserAdditionalData().getPassword());
        //set encoded password
        user.getUserAdditionalData().setPassword(encodePassword);
        //set default role
        user.setRole(UserRoleEnum.USER);

        user.getUserAdditionalData().setUser(user);

        //generate secret key
        String secretKey = UUID.randomUUID().toString();
        user.getUserAdditionalData().setSecretKey(secretKey);
        //set block
        user.getUserAdditionalData().setBlock(false);
        //set active
        user.getUserAdditionalData().setActive(false);

        // todo create letter about registration

        User result = userCrudManagerService.save(user);
        UserFullDTO resultDTO = transferDTO.modelToDTOUserFull(result);

        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, resultDTO);
    }

    @Override
    public BusinessServiceExecuteResult<UserFullDTO> update(UserDTOForSave userDTO) {
        if (!securitySupportService.isAllowed(userDTO.getId())) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.FORBIDDEN);
        }

        User user = transferDTO.dtoToModel(userDTO);


        return null;
    }

    @Override
    public Page<UserDTO> findAll(Integer page, Integer size, String sortType, List<String> sortProperties) {
        Page<User> p = userCrudManagerService.findAll(page, size, sortType, sortProperties);
        return p.map(user -> transferDTO.modelToDTO(user));
    }

    @Override
    public List<UserDTO> findAll(String sortType, List<String> sortProperties) {
        return null;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserFullDTO> findAll() {
        List<User> list = userCrudManagerService.findAll();
        return list.stream()
                .map(user -> transferDTO.modelToDTOUserFull(user))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserFullDTO> findByUsername(String username) {
        Optional<User> optional = userCrudManagerService.findByUsername(username);
        return optional.map(user -> transferDTO.modelToDTOUserFull(user));
    }

    @Override
    public Page<UserDTO> findAllIsBlockFalse(Integer page, Integer size, String sortType, List<String> sortProperties) {
        Page<User> p = userCrudManagerService.findUserAllByIsBlock(false, page, size, sortType, sortProperties);
        return p.map(user -> transferDTO.modelToDTO(user));
    }

    @Override
    public List<UserDTO> findAllIsBlockFalse(String sortType, List<String> sortProperties) {
        List<User> list = userCrudManagerService.findUserAllByIsBlock(false, sortType, sortProperties);
        return list.stream()
                .map(user -> transferDTO.modelToDTO(user))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findAllIsBlockFalse() {
        List<User> list = userCrudManagerService.findUserAllByIsBlock(false);
        return list.stream()
                .map(user -> transferDTO.modelToDTO(user))
                .collect(Collectors.toList());
    }

    @Override
    public Page<UserDTO> findAllIsBlockTrue(Integer page, Integer size, String sortType, List<String> sortProperties) {
        Page<User> p = userCrudManagerService.findUserAllByIsBlock(true, page, size, sortType, sortProperties);
        return p.map(user -> transferDTO.modelToDTO(user));
    }

    @Override
    public List<UserDTO> findAllIsBlockTrue(String sortType, List<String> sortProperties) {
        List<User> list = userCrudManagerService.findUserAllByIsBlock(true, sortType, sortProperties);
        return list.stream()
                .map(user -> transferDTO.modelToDTO(user))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findAllIsBlockTrue() {
        List<User> list = userCrudManagerService.findUserAllByIsBlock(true);
        return list.stream()
                .map(user -> transferDTO.modelToDTO(user))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> findOne(Long id) {
        return userCrudManagerService
                .findOne(id)
                .map(user -> transferDTO.modelToDTO(user));
    }

    @Override
    public BusinessServiceExecuteResult<UserFullDTO> findOneUserFullDTO(Long id) {
        if (securitySupportService.isAllowed(id)) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.FORBIDDEN);
        }
        Optional<User> optional = userCrudManagerService.findOne(id);
        return BusinessServiceExecuteResult
                .build(optional.map(user -> transferDTO.modelToDTOUserFull(user)));
    }

    @Override
    public Optional<UserDTO> findOneIsBlockFalse(Long id) {
        return null;
    }

    @Override
    public Optional<UserDTO> findOneIsBlockTrue(Long id) {
        return null;
    }

    @Override
    public BusinessServiceExecuteResult setRole(Long userId, String role) {
        return null;
    }

    @Override
    public BusinessServiceExecuteResult setPasswordBySecretKey(String newPassword, String secretKey) {
        return null;
    }

    @Override
    public BusinessServiceExecuteResult setPasswordBySecretKey(String newPassword, Long id) {
        return null;
    }

    @Override
    public BusinessServiceExecuteResult setPasswordByOldPassword(Long userId, UserFullDTO userFullDTO, String newPassword) {
        return null;
    }

    @Override
    public BusinessServiceExecuteResult setNewSecretKey(Long id) {
        return null;
    }

    @Override
    public BusinessServiceExecuteResult delete(Long id) {
        return null;
    }

    @Override
    public BusinessServiceExecuteResult delete(List<Long> ids) {
        return null;
    }

    @Override
    public BusinessServiceExecuteResult deleteAll() {
        return null;
    }

    @Override
    public BusinessServiceExecuteResult activatedUser(String secretKey) {
        return null;
    }

    @Override
    public BusinessServiceExecuteResult deactivatedUser(Long id) {
        return null;
    }

    @Override
    public BusinessServiceExecuteResult deactivatedUser(String secretKey) {
        return null;
    }

    @Override
    public BusinessServiceExecuteResult sendBlockToUser(Long id) {
        return null;
    }

    @Override
    public BusinessServiceExecuteResult sendUnBlockToUser(Long id) {
        return null;
    }
}
