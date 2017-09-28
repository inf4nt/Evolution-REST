package evolution.service;


import evolution.model.user.UserLight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * Created by Admin on 17.04.2017.
 */
//@Service
//public class SearchService {
//
//    @Autowired
//    private UserDaoService userDaoService;
//
//
//    public List<UserLight> searchUser(String string, int page, int size) {
//        if (string.matches("^[a-zA-Z]+$")) {
//            return userDaoService.findStandardUserByFirstOrLastName(string, new PageRequest(page, size));
//        }
//        if (string.matches("^[a-zA-Z]+\\s[a-zA-Z]+$")) {
//            String regex[] = string.split(" ");
//            return userDaoService.findStandardUserByFirstLastName(regex[0], regex[1], new PageRequest(page, size));
//        }
//        throw new NoResultException();
//    }
//}
