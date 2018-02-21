package jp.co.unirita.medis.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.domain.user.UserRepository;
import jp.co.unirita.medis.form.user.UserLoginForm;
import jp.co.unirita.medis.logic.LoginLogic;
import lombok.Data;

@RestController
@RequestMapping("/v1/login")
@Data
public class LoginController {

    @Autowired
    private final LoginLogic loginLogic;

    @Autowired
    private UserRepository repository;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    //ResponseEntity<String>
    User login(@RequestBody UserLoginForm data, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("ユーザID" + data.getEmployeeNumber() + "のログイン処理");
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
        User user = repository.findFirstByEmployeeNumber(data.getEmployeeNumber());
        user.setPassword("");
        return user;
    }
}
