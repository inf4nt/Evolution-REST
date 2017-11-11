package evolution.business.api;

import evolution.dto.model.UserDTO;
import evolution.dto.model.UserDTOForSave;
import evolution.dto.model.UserDTOForUpdate;
import evolution.dto.model.UserFullDTO;
import evolution.business.BusinessServiceExecuteResult;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 08.11.2017.
 */

public interface UserBusinessService {

    BusinessServiceExecuteResult<UserFullDTO> createNewUserFull(UserDTOForSave userDTOForSave);

    BusinessServiceExecuteResult<UserFullDTO> updateFull(UserDTOForUpdate userDTOForUpdate);

    BusinessServiceExecuteResult<UserDTOForSave> createNewUser(UserDTOForSave userDTOForSave);

    BusinessServiceExecuteResult<UserDTOForUpdate> update(UserDTOForUpdate userDTOForUpdate);

    Page<UserDTO> findAll(Integer page, Integer size, String sortType, List<String> sortProperties);

    Page<UserFullDTO> findAllFull(Integer page, Integer size, String sortType, List<String> sortProperties);

    List<UserDTO> findAll(String sortType, List<String> sortProperties);

    List<UserFullDTO> findAllFull();

    List<UserFullDTO> findAllFull(String sortType, List<String> sortProperties);

    Optional<UserFullDTO> findByUsernameFull(String username);

    Optional<UserDTO> findByUsername(String username);

    Page<UserDTO> findAllIsBlock(boolean isBlock, Integer page, Integer size, String sortType, List<String> sortProperties);

    List<UserDTO> findAllIsBlock(boolean isBlock, String sortType, List<String> sortProperties);

    List<UserDTO> findAllIsBlock(boolean isBlock);

    Optional<UserDTO> findOne(Long id);

    Page<UserFullDTO> findAllIsBlockFull(boolean isBlock, Integer page, Integer size, String sortType, List<String> sortProperties);

    List<UserFullDTO> findAllIsBlockFull(boolean isBlock, String sortType, List<String> sortProperties);

    List<UserFullDTO> findAllIsBlockFull(boolean isBlock);

    Optional<UserFullDTO> findOneUserFull(Long id);

    Optional<UserDTO> findOneIsBlock(Long id, boolean isBlock);

    BusinessServiceExecuteResult setRole(Long userId, String role);

    BusinessServiceExecuteResult setPasswordBySecretKey(String newPassword, String secretKey);

    BusinessServiceExecuteResult setPasswordBySecretKey(String newPassword, Long id);

    BusinessServiceExecuteResult setPasswordByOldPassword(Long userId, UserFullDTO userFullDTO, String newPassword);

    BusinessServiceExecuteResult setNewSecretKey(Long id);

    BusinessServiceExecuteResult delete(Long id);

    BusinessServiceExecuteResult delete(List<Long> ids);

    BusinessServiceExecuteResult deleteAll();

    BusinessServiceExecuteResult activatedUser(String secretKey);

    BusinessServiceExecuteResult deactivatedUser(Long id);

    BusinessServiceExecuteResult deactivatedUser(String secretKey);

    BusinessServiceExecuteResult sendBlockToUser(Long id);

    BusinessServiceExecuteResult sendUnBlockToUser(Long id);
}
