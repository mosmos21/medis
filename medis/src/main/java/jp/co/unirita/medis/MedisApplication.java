package jp.co.unirita.medis;

import jp.co.unirita.medis.logic.setting.SettingLogic;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class MedisApplication implements CommandLineRunner {

	@Resource
	SettingLogic settingLogic;

	public static void main(String[] args) {
		SpringApplication.run(MedisApplication.class, args);
	}

	@Override
	public void run(String... arg) throws Exception {
		settingLogic.deleteAll();
		settingLogic.init();
	}
}
