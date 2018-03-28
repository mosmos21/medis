package jp.co.unirita.medis;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import jp.co.unirita.medis.logic.setting.SettingLogic;

@SpringBootApplication
public class MedisApplication extends WebMvcConfigurerAdapter implements CommandLineRunner {

	@Autowired
	private MessageSource messageSource;

	@Resource
	SettingLogic settingLogic;

	public static void main(String[] args) {
		SpringApplication.run(MedisApplication.class, args);
	}

	@Override
	public void run(String... arg) throws Exception {
//		settingLogic.deleteAll();
//		settingLogic.init();
	}

//	//messages.properties用
//	@Bean
//	public LocalValidatorFactoryBean validator() {
//		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
//		localValidatorFactoryBean.setValidationMessageSource(messageSource);
//		return localValidatorFactoryBean;
//	}
//
//	@Override
//    public org.springframework.validation.Validator getValidator() {
//        return validator();
//    }
}
