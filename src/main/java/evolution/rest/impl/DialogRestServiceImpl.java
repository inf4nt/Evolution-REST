package evolution.rest.impl;

import evolution.data.DialogDataService;
import evolution.helper.HelperDataService;
import evolution.helper.HelperRestService;
import evolution.model.Dialog;
import evolution.model.User;
import evolution.rest.api.DialogRestService;
import evolution.service.SecuritySupportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 05.11.2017.
 */
@Service
public class DialogRestServiceImpl implements DialogRestService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final DialogDataService dialogDataService;

    private final HelperDataService helperDataService;

    private final HelperRestService<Dialog> helperRestService;

    private final SecuritySupportService securitySupportService;

    @Autowired
    public DialogRestServiceImpl(DialogDataService dialogDataService,
                                 HelperDataService helperDataService,
                                 HelperRestService<Dialog> helperRestService,
                                 SecuritySupportService securitySupportService) {
        this.dialogDataService = dialogDataService;
        this.helperDataService = helperDataService;
        this.helperRestService = helperRestService;
        this.securitySupportService = securitySupportService;
    }

    @Override
    public ResponseEntity<Page<Dialog>> findAll(Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable pageable = helperDataService.getPageableForDialog(page, size, sort, sortProperties);
        Page<Dialog> p = dialogDataService.findAll(pageable);
        return helperRestService.getResponseForPage(p);
    }

    @Override
    public ResponseEntity<Dialog> findOne(Long dialogId) {
        Optional<Dialog> optional = dialogDataService.findOne(dialogId);
        return helperRestService.getResponseForOptional(optional);
    }

    @Override
    public ResponseEntity<Dialog> findDialogByIdAndAuthUser(Long dialogId) {
        User auth = securitySupportService.getAuthenticationPrincipal().getUser();
        Optional<Dialog> optional = dialogDataService.findDialogByIdAndSomeUser(dialogId, auth.getId());
        return helperRestService.getResponseForOptional(optional);
    }
}
