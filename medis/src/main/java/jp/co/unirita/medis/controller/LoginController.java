package jp.co.unirita.medis.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.domain.user.UserRepository;
import jp.co.unirita.medis.form.util.UserLoginForm;
import jp.co.unirita.medis.logic.util.LoginLogic;
import lombok.Data;

import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping("/v1")
@Data
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private final LoginLogic loginLogic;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "login")
    @ResponseStatus(HttpStatus.CREATED)
    User login(@RequestBody UserLoginForm data, HttpServletRequest request, HttpServletResponse response) {
        loginLogic.login(data);

        CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if(csrf != null) {
            Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
            String token = csrf.getToken();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if((cookie == null || token != null && !token.equals(cookie.getValue()))
                    && (authentication != null || authentication.isAuthenticated())){
                cookie = new Cookie("XSRF-TOKEN", token);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
        User user = userRepository.findOne(data.getEmployeeNumber());
        User result = new User();
        result.setEmployeeNumber(user.getEmployeeNumber());
        result.setAuthorityId(user.getAuthorityId());
        result.setEnabled(user.isEnabled());
        return result;
    }

    @GetMapping(value = "logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(HttpSession session) {
        session.invalidate();
    }
}
