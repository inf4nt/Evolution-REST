package evolution.business;

import evolution.business.api.DialogBusinessService;
import evolution.dto.model.DialogDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DialogBusinessServiceImpl implements DialogBusinessService {

    @Override
    public Optional<DialogDTO> findOne(Long id) {
        return null;
    }

    @Override
    public BusinessServiceExecuteResult<DialogDTO> save() {
        return null;
    }
}
