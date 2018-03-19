package jp.co.unirita.medis.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.logic.setting.SettingLogic;

@Controller
@RequestMapping("/v1/icon")
public class IconController {

	@Autowired
	SettingLogic settingLogic;

	List<String> files = new ArrayList<String>();

	@PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> handleFileUpload(@AuthenticationPrincipal User user, @RequestParam("file") MultipartFile file) {
		String message = "";
		try {
			settingLogic.store(user.getEmployeeNumber(), file);
			files.add(file.getOriginalFilename());
			message = "You successfully uploaded " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} catch (Exception e) {
			message = "FAIL to upload " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}

    @GetMapping(value = "{employeeNumber:.+}", produces = "image/png")
	public ResponseEntity<Resource> getFile(@PathVariable String employeeNumber, @AuthenticationPrincipal User user) {
		Resource file;
		if(employeeNumber.equals("me")) {
			file = settingLogic.loadFile(user.getEmployeeNumber() + ".png");
		} else {
			file = settingLogic.loadFile(employeeNumber + ".png");
		}
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
}
