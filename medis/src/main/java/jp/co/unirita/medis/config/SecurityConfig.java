package jp.co.unirita.medis.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		RequestMatcher csrfRequestMatcher = new RequestMatcher() {
			// CSRFのチェックをしないURL
			private AntPathRequestMatcher[] requestMatchers = { new AntPathRequestMatcher("/v1/login/**") };

			@Override
			public boolean matches(HttpServletRequest request) {
				for (AntPathRequestMatcher rm : requestMatchers) {
					if (rm.matches(request)) {
						return false;
					}
				}
				return true;
			}
		};

		// ログインしなくてもアクセスできるURL
		http.authorizeRequests().antMatchers("/v1/login").permitAll().anyRequest().authenticated() // 上記にマッチしなければ未認証の場合エラー
				.and().csrf().requireCsrfProtectionMatcher(csrfRequestMatcher)
				.csrfTokenRepository(this.csrfTokenRepository());

		http.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/v1/logout")); // ログアウト処理のパス

	}

	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
	}
}
