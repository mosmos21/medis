package jp.co.unirita.medis.logic.util;

import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.form.user.UserLoginForm;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Data
public class LoginLogic {

    private final AuthenticationManager authManager;

    public void login(UserLoginForm loginForm) {
        Authentication auth = new UsernamePasswordAuthenticationToken(loginForm.getEmployeeNumber(), loginForm.getPassword());
        Authentication result = authManager.authenticate(auth);

        SecurityContextHolder.getContext().setAuthentication(result);
        User principal = (User)result.getPrincipal();
    }
}
