package evolution.business.api;


import evolution.business.BusinessServiceExecuteResult;
import evolution.common.BusinessServiceExecuteStatus;
import evolution.dto.model.UserDTO;
import evolution.dto.model.UserDTOLazy;
import evolution.dto.modelOld.UserForSaveDTO;
import evolution.dto.modelOld.UserForUpdateDTO;
import evolution.dto.model.UserSetPasswordDTO;
import evolution.model.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 08.11.2017.
 */

public interface UserBusinessService {

    List<UserDTO> findAllAndIsBlock(boolean isBlock);

    List<UserDTO> findAllAndIsBlock(boolean isBlock, String sortType, List<String> sortProperties);

    Page<UserDTO> findAllAndIsBlock(boolean isBlock, Integer page, Integer size, String sortType, List<String> sortProperties);

    List<UserDTOLazy> findAllAndIsBlockLazy(boolean isBlock);

    Page<UserDTOLazy> findAllAndIsBlockLazy(boolean isBlock, Integer page, Integer size, String sortType, List<String> sortProperties);

    List<UserDTOLazy> findAllAndIsBlockLazy(boolean isBlock, String sortType, List<String> sortProperties);

    List<UserDTO> findAllAndIsActive(boolean isActive);

    List<UserDTO> findAllAndIsActive(boolean isActive, String sortType, List<String> sortProperties);

    Page<UserDTO> findAllAndIsActive(boolean isActive, Integer page, Integer size, String sortType, List<String> sortProperties);

    List<UserDTOLazy> findAllAndIsActiveLazy(boolean isActive);

    Page<UserDTOLazy> findAllAndIsActiveLazy(boolean isActive, Integer page, Integer size, String sortType, List<String> sortProperties);

    List<UserDTOLazy> findAllAndIsActiveLazy(boolean isActive, String sortType, List<String> sortProperties);

    List<UserDTO> findAll();

    List<UserDTO> findAll(String sortType, List<String> sortProperties);

    Page<UserDTO> findAll(Integer page, Integer size, String sortType, List<String> sortProperties);

    List<UserDTOLazy> findAllLazy();

    List<UserDTOLazy> findAllLazy(String sortType, List<String> sortProperties);

    Page<UserDTOLazy> findAllLazy(Integer page, Integer size, String sortType, List<String> sortProperties);

    Optional<UserDTO> findOne(Long id);

    BusinessServiceExecuteResult<UserDTOLazy> findOneLazy(Long id);

    BusinessServiceExecuteResult<User> createNewUser(UserForSaveDTO userForSaveDTO);

    BusinessServiceExecuteResult<UserDTOLazy> createNewUser2(UserForSaveDTO userForSaveDTO);

    BusinessServiceExecuteResult<BusinessServiceExecuteStatus> createNewUser3(UserForSaveDTO userForSaveDTO);

    BusinessServiceExecuteResult<User> update(UserForUpdateDTO user);

    BusinessServiceExecuteResult<UserDTOLazy> update2(UserForUpdateDTO user);

    BusinessServiceExecuteResult<BusinessServiceExecuteStatus> update3(UserForUpdateDTO user);

    Optional<UserDTO> findByUsername(String username);

    BusinessServiceExecuteResult<UserDTOLazy> findByUsernameLazy(String username);

    Optional<UserDTO> findOneAndIsBlock(Long id, boolean isBlock);

    Optional<UserDTOLazy> findOneAndIsBlockLazy(Long id, boolean isBlock);

    Optional<UserDTO> findOneAndIsActive(Long id, boolean isActive);

    Optional<UserDTOLazy> findOneAndIsActiveLazy(Long id, boolean isActive);

    BusinessServiceExecuteResult<BusinessServiceExecuteStatus> delete(Long id);

    BusinessServiceExecuteResult<BusinessServiceExecuteStatus> delete(List<Long> ids);

    BusinessServiceExecuteResult<BusinessServiceExecuteStatus> deleteAll();

    BusinessServiceExecuteResult<UserDTOLazy> setRole(Long userId, String role);

    BusinessServiceExecuteResult<UserDTOLazy> setPasswordBySecretKey(String newPassword, String secretKey);

    BusinessServiceExecuteResult<UserDTOLazy> setPasswordByUserId(String newPassword, Long id);

    BusinessServiceExecuteResult<UserDTOLazy> setPasswordByOldPassword(UserSetPasswordDTO userSetPasswordDTO);

    BusinessServiceExecuteResult<UserDTOLazy> generateNewSecretKey(Long id);

    BusinessServiceExecuteResult<UserDTOLazy> activatedUser(String secretKey);

    BusinessServiceExecuteResult<UserDTOLazy> deactivatedUser(Long id);

    BusinessServiceExecuteResult<UserDTOLazy> deactivatedUser(String username);

    BusinessServiceExecuteResult<UserDTOLazy> sendBlockToUser(Long id);

    BusinessServiceExecuteResult<UserDTOLazy> sendUnBlockToUser(Long id);
}
