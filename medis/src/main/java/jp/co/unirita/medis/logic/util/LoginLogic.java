package jp.co.unirita.medis.logic.util;

import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.form.user.UserLoginForm;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

@Service
@Data
public class LoginLogic {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final AuthenticationManager authManager;

    public void login(UserLoginForm loginForm) {
        Authentication auth = new UsernamePasswordAuthenticationToken(loginForm.getEmployeeNumber(), loginForm.getPassword());
        Authentication result = authManager.authenticate(auth);

        SecurityContextHolder.getContext().setAuthentication(result);
        User principal = (User)result.getPrincipal();
    }
}
