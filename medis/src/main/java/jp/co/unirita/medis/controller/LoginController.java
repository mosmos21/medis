package jp.co.unirita.medis.controller;

import jp.co.unirita.medis.logic.LoginLogic;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/login/")
@Data
public class LoginController {

    @Autowired
    private final LoginLogic loginLogic;

    @RequestMapping(value = "login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> login(@RequestBody LoginLogic.LoginData data, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("ユーザID" + data.getLoginId() + "のログイン処理");
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
        return new ResponseEntity<>("Login OK", null, HttpStatus.OK);
    }
}
