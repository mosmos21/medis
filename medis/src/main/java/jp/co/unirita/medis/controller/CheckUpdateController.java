package jp.co.unirita.medis.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.form.setting.SnackbarNotificationsForm;
import jp.co.unirita.medis.logic.system.CheckUpdateLogic;

@RequestMapping("/v1/update")

@RestController
public class CheckUpdateController {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	CheckUpdateLogic checkUpdateLogic;

	@GetMapping(value = "{updateId:^u[0-9]{10}$}")
	@ResponseStatus(HttpStatus.CREATED)
	public List<SnackbarNotificationsForm> CheckUpdate(@AuthenticationPrincipal User user,
			@PathVariable(value = "updateId") String updateId) {
		// 例外処理
		logger.info("[method ChackUpdate ] CheckUpdate by DocumentId :" + user.getEmployeeNumber() + "after UpdateId"
				+ updateId + ".");

 		return checkUpdateLogic.updateSelect(user.getEmployeeNumber(), updateId);
	}
}
