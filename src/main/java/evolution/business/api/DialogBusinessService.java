package evolution.business.api;


import evolution.business.BusinessServiceExecuteResult;
import evolution.dto.model.DialogDTO;

import java.util.Optional;

/**
 * Created by Infant on 08.11.2017.
 */
public interface DialogBusinessService {

    Optional<DialogDTO> findOne(Long id);

    BusinessServiceExecuteResult<DialogDTO> save ();

}
