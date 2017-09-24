package evolution.controller;



import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Admin on 03.03.2017.
 */
//@Controller
//@RequestMapping("/")
//public class IndexController {
//
//    @GetMapping
//    public String index () {
//        return "redirect:/welcome";
//    }
//
//    @GetMapping(value = "/welcome")
//    public String welcome () {
//        return "login/login";
//    }
//
//    @GetMapping(value = "/logout")
//    public String logout (HttpServletRequest request, HttpServletResponse response) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null)
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//        return "redirect:/welcome";
//    }
//
//    @GetMapping(value = "/registration/view")
//    public ModelAndView formRegistration() {
//        return new ModelAndView("user/registration");
//    }
//
//    @GetMapping(value = "/restore-password/view")
//    public ModelAndView formRestorePassword() {
//        return new ModelAndView("user/restore-password");
//    }
//
//}
