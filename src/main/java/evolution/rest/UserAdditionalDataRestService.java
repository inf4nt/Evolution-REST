package evolution.rest;

import evolution.model.UserAdditionalData;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Created by Infant on 29.10.2017.
 */
public interface UserAdditionalDataRestService {

    ResponseEntity<Page<UserAdditionalData>> findAll(Integer page, Integer size, String sortType, List<String> sortProperties);

    ResponseEntity<UserAdditionalData> findUserAdditionalDataByUser(Long userId);

    ResponseEntity<HttpStatus> existByUser(Long userId);
}
