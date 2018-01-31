package jp.co.unirita.medis.logic;

import jp.co.unirita.medis.domain.user.User;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
@Data
public class LoginLogic {

    private final AuthenticationManager authManager;

    public void login(LoginData loginData) {
        Authentication auth = new UsernamePasswordAuthenticationToken(loginData.getLoginId(), loginData.getPassword());
        Authentication result = authManager.authenticate(auth);

        SecurityContextHolder.getContext().setAuthentication(result);
        User principal = (User)result.getPrincipal();
    }

    @Data
    public static class LoginData implements Serializable {
        private static final long serialVersionUID = 1L;
        private String loginId;
        private String password;
    }
}
