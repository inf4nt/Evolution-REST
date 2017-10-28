package evolution.helper;

import evolution.model.Dialog;
import evolution.model.Message;
import evolution.model.User;
import evolution.security.model.CustomSecurityUser;
import evolution.service.SecuritySupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Infant on 28.10.2017.
 */
@Service
public class HelperDataService {

    @Value("${model.message.maxfetch}")
    private Integer messageMaxFetch;

    @Value("${model.message.defaultsort}")
    private String defaultMessageSortType;

    @Value("${model.message.defaultsortproperties}")
    private String defaultMessageSortProperties;

    @Value("${model.dialog.maxfetch}")
    private Integer dialogMaxFetch;

    @Value("${model.dialog.defaultsort}")
    private String defaultDialogSortType;

    @Value("${model.dialog.defaultsortproperties}")
    private String defaultDialogSortProperties;

    private Integer page;

    private Integer size;

    private String sortType;

    private List<String> sortProperties;

    private final SecuritySupportService securitySupportService;

    @Autowired
    public HelperDataService(SecuritySupportService securitySupportService) {
        this.securitySupportService = securitySupportService;
    }

    private List<String> getDefaultSortProperties(String line) {
        String arr[] = line.split(",");

        return Arrays.stream(arr)
                .map(o -> o.trim())
                .collect(Collectors.toList());
    }

    private void setDefaultValuesForMessageRestService(Integer page, Integer size, String sortType, List<String> sortProperties) {
        if (page == null || size == null) {
            this.page = 0;
            this.size = messageMaxFetch;
        }

        if (sortType == null || sortType.isEmpty()) {
            this.sortType = defaultMessageSortType.toUpperCase();
        }

        if (sortProperties == null || !sortProperties.isEmpty()) {
            this.sortProperties = getDefaultSortProperties(defaultMessageSortProperties);
        }
    }

    private void setDefaultValuesForDialogRestService(Integer page, Integer size, String sortType, List<String> sortProperties) {
        if (page == null || size == null) {
            this.page = 0;
            this.size = dialogMaxFetch;
        }

        if (sortType == null || sortType.isEmpty()) {
            this.sortType = defaultDialogSortType.toUpperCase();
        }

        if (sortProperties == null || !sortProperties.isEmpty()) {
            this.sortProperties = getDefaultSortProperties(defaultDialogSortProperties);
        }
    }

    public Pageable getPageableForMessage(Integer page, Integer size, String sortType, List<String> sortProperties) {
        setDefaultValuesForMessageRestService(page, size, sortType, sortProperties);
        return new PageRequest(this.page, this.size, new Sort(Sort.Direction.valueOf(this.sortType), this.sortProperties));
    }

    public Pageable getPageableForDialog(Integer page, Integer size, String sortType, List<String> sortProperties) {
        setDefaultValuesForDialogRestService(page, size, sortType, sortProperties);
        return new PageRequest(this.page, this.size, new Sort(Sort.Direction.valueOf(this.sortType), this.sortProperties));
    }

    public List repairDialog(List list, User auth, Class clazz) {
        if (clazz == Message.class) {
            return repairDialogForListMessage(list, auth);
        } else if (clazz == Dialog.class) {
            return repairDialogForListMessage(list, auth);
        }
        return new ArrayList();
    }

    public Dialog repairDialog(Dialog dialog, User authUser) {
        if (!dialog.getFirst().getId().equals(authUser.getId())) {
            dialog.setSecond(dialog.getFirst());
            dialog.setFirst(authUser);
        }
        return dialog;
    }

    public Message repairDialog(Message message, User auth) {
        message.setDialog(repairDialog(message.getDialog(), auth));
        return message;
    }

    public List<Dialog> repairDialogForListDialog(List<Dialog> dialog, User auth) {
        return dialog
                .stream()
                .map(o -> repairDialog(o, auth))
                .collect(Collectors.toList());
    }

    public List<Message> repairDialogForListMessage(List<Message> list, User auth) {
        return list
                .stream()
                .map(o -> repairDialog(o, auth))
                .collect(Collectors.toList());
    }


    public Page<Message> repairMessage(Page<Message> page) {

        Optional<CustomSecurityUser> optional = securitySupportService.getPrincipal();
        if (page != null && !page.getContent().isEmpty()) {
            if (optional.isPresent()) {
                page.map(o -> repairDialog(o, optional.get().getUser()));
            }
        }

        return page;
    }

    public Page<Dialog> repairDialog(Page<Dialog> page) {

        Optional<CustomSecurityUser> optional = securitySupportService.getPrincipal();
        if (page != null && !page.getContent().isEmpty()) {
            if (optional.isPresent()) {
                page.map(o -> repairDialog(o, optional.get().getUser()));
            }
        }

        return page;
    }
}
