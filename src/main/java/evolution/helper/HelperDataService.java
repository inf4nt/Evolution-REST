package evolution.helper;

import evolution.model.Dialog;
import evolution.model.Message;
import evolution.model.User;
import evolution.security.model.CustomSecurityUser;
import evolution.service.SecuritySupportService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @Value("${model.friend.maxfetch}")
    private Integer friendMaxFetch;

    @Value("${model.user.maxfetch}")
    private Integer userMaxFetch;

    @Value("${model.user.defaultsort}")
    private String defaultUserSortType;

    @Value("${model.user.defaultsortproperties}")
    private String defaultUserSortProperties;

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


    private Pageable getPageableForRestService(Integer page, Integer size, String sortType, List<String> sortProperties,
                                              Integer defaultMaxFetch, String defaultSortType, String defaultSortProperties) {
        Integer pageResult = page;
        Integer sizeResult = size;
        String sortTypeResult = sortType;
        List<String> sortPropertiesResult = sortProperties;

        if (page == null || size == null) {
            sizeResult = defaultMaxFetch;
        }

        if (sortType == null || sortType.isEmpty()) {
            sortTypeResult = defaultSortType.toUpperCase();
        }

        if (sortProperties == null || !sortProperties.isEmpty()) {
            sortPropertiesResult = getDefaultSortProperties(defaultSortProperties);
        }

        return new PageRequest(pageResult, sizeResult, new Sort(Sort.Direction.valueOf(sortTypeResult), sortPropertiesResult));
    }

    private Sort getSortForRestService(String sortType, List<String> sortProperties,
                                               String defaultSortType, String defaultSortProperties) {

        String sortTypeResult = sortType;
        List<String> sortPropertiesResult = sortProperties;

        if (sortType == null || sortType.isEmpty()) {
            sortTypeResult = defaultSortType.toUpperCase();
        }

        if (sortProperties == null || !sortProperties.isEmpty()) {
            sortPropertiesResult = getDefaultSortProperties(defaultSortProperties);
        }

        return new Sort(Sort.Direction.valueOf(sortTypeResult), sortPropertiesResult);
    }

    private Pageable getPageableForRestService(Integer page, Integer size,
                                              Integer defaultMaxFetch) {
        Integer pageResult = 0;
        Integer sizeResult = 0;

        if (page == null || size == null) {
            sizeResult = defaultMaxFetch;
        }

        return new PageRequest(pageResult, sizeResult);
    }



    public Pageable getPageableForMessage(Integer page, Integer size, String sortType, List<String> sortProperties) {
        return getPageableForRestService(page, size, sortType, sortProperties,
                this.messageMaxFetch, this.defaultMessageSortType, this.defaultMessageSortProperties);
    }

    public Pageable getPageableForDialog(Integer page, Integer size, String sortType, List<String> sortProperties) {
        return getPageableForRestService(page, size, sortType, sortProperties,
                this.dialogMaxFetch, this.defaultDialogSortType, this.defaultDialogSortProperties);
    }

    public Pageable getPageableForUser(Integer page, Integer size, String sortType, List<String> sortProperties) {
        return getPageableForRestService(page, size, sortType, sortProperties,
                this.userMaxFetch, this.defaultUserSortType, this.defaultUserSortProperties);
    }

    public Sort getSortForUser(String sortType, List<String> sortProperties) {
        return getSortForRestService(sortType, sortProperties,
                this.defaultUserSortType, this.defaultUserSortProperties);
    }

    public Pageable getPageableForFriend(Integer page, Integer size) {
        return getPageableForRestService(page, size,
                this.friendMaxFetch);
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


    public Page<Message> repairPageMessage(Page<Message> page) {

        Optional<CustomSecurityUser> optional = securitySupportService.getPrincipal();
        if (page != null && !page.getContent().isEmpty()) {
            if (optional.isPresent()) {
                page.map(o -> repairDialog(o, optional.get().getUser()));
            }
        }

        return page;
    }

    public Page<Dialog> repairPageDialog(Page<Dialog> page) {

        Optional<CustomSecurityUser> optional = securitySupportService.getPrincipal();
        if (page != null && !page.getContent().isEmpty()) {
            if (optional.isPresent()) {
                page.map(o -> repairDialog(o, optional.get().getUser()));
            }
        }

        return page;
    }
}
