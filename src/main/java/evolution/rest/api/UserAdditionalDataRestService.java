package evolution.rest.api;

import evolution.dto.model.UserAdditionalDataDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Created by Infant on 29.10.2017.
 */
public interface UserAdditionalDataRestService {

    ResponseEntity<Page<UserAdditionalDataDTO>> findAll(Integer page, Integer size, String sortType, List<String> sortProperties);

    ResponseEntity<UserAdditionalDataDTO> findUserAdditionalDataByUser(Long userId);

    ResponseEntity<HttpStatus> existByUser(Long userId);
}
