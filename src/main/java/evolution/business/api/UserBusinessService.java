package evolution.business.api;

import evolution.dto.model.UserDTO;
import evolution.dto.model.UserDTOForSave;
import evolution.dto.model.UserFullDTO;
import evolution.business.BusinessServiceExecuteResult;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 08.11.2017.
 */

public interface UserBusinessService {

    BusinessServiceExecuteResult<UserFullDTO> createNewUser(UserFullDTO userFullDTO);

    BusinessServiceExecuteResult<UserFullDTO> update(UserDTOForSave userDTO);

    Page<UserDTO> findAll(Integer page, Integer size, String sortType, List<String> sortProperties);

    Page<UserFullDTO> findAllFull(Integer page, Integer size, String sortType, List<String> sortProperties);

    List<UserDTO> findAll(String sortType, List<String> sortProperties);

    List<UserFullDTO> findAllFull();

    List<UserFullDTO> findAllFull(String sortType, List<String> sortProperties);

    Optional<UserFullDTO> findByUsernameFull(String username);

    Optional<UserDTO> findByUsername(String username);

    Page<UserDTO> findAllIsBlockFalse(Integer page, Integer size, String sortType, List<String> sortProperties);

    List<UserDTO> findAllIsBlockFalse(String sortType, List<String> sortProperties);

    List<UserDTO> findAllIsBlockFalse();

    Page<UserDTO> findAllIsBlockTrue(Integer page, Integer size, String sortType, List<String> sortProperties);

    List<UserDTO> findAllIsBlockTrue(String sortType, List<String> sortProperties);

    List<UserDTO> findAllIsBlockTrue();

    Optional<UserDTO> findOne(Long id);

    Page<UserFullDTO> findAllIsBlockFalseFull(Integer page, Integer size, String sortType, List<String> sortProperties);

    List<UserFullDTO> findAllIsBlockFalseFull(String sortType, List<String> sortProperties);

    List<UserFullDTO> findAllIsBlockFalseFull();

    Page<UserFullDTO> findAllIsBlockTrueFull(Integer page, Integer size, String sortType, List<String> sortProperties);

    List<UserFullDTO> findAllIsBlockTrueFull(String sortType, List<String> sortProperties);

    List<UserFullDTO> findAllIsBlockTrueFull();

    Optional<UserFullDTO> findOneUserFull(Long id);

    Optional<UserDTO> findOneIsBlockFalse(Long id);

    Optional<UserDTO> findOneIsBlockTrue(Long id);

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
