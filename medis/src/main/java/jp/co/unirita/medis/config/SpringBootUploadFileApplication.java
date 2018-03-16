package jp.co.unirita.medis.config;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jp.co.unirita.medis.logic.setting.SettingLogic;

@SpringBootApplication
public class SpringBootUploadFileApplication implements CommandLineRunner {

	@Resource
	SettingLogic settingLogic;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootUploadFileApplication.class, args);
	}

	@Override
	public void run(String... arg) throws Exception {
		settingLogic.deleteAll();
		settingLogic.init();
	}
}
