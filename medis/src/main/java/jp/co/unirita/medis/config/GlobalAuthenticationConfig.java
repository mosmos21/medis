package jp.co.unirita.medis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import jp.co.unirita.medis.logic.UserLogic;

@Configuration
public class GlobalAuthenticationConfig extends GlobalAuthenticationConfigurerAdapter{

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userLogic());
    }

    @Bean
    UserLogic userLogic(){
        return new UserLogic();
    }
}
