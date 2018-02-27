package jp.co.unirita.medis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.form.InfomationForm;
import jp.co.unirita.medis.logic.setting.InfomationLogic;
import jp.co.unirita.medis.logic.util.ArgumentCheckLogic;
import jp.co.unirita.medis.util.exception.AuthorityException;
import jp.co.unirita.medis.util.exception.NotExistException;


@RequestMapping("/v1/infomations")

@RestController
public class InfomationListController {

	@Autowired
	private InfomationLogic infomationLogic;
	@Autowired
	ArgumentCheckLogic argumentCheckLogic;

	@RequestMapping(path = {"{user:^[0-9a-z-A-Z]{4,10}$}", "{user:^[0-9a-z-A-Z]{4,10}$}/{lastUpdateId:^u[0-9]{10}+$}"}, method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public  List<InfomationForm> getInfomationList(
		@AuthenticationPrincipal User user,
		@PathVariable(value = "user") String employeeNumber,
		@PathVariable(value = "lastUpdateId", required = false) String updateId,
		@RequestParam(value = "size", required = false) Integer maxSize) throws NotExistException, AuthorityException {

		argumentCheckLogic.checkUser(user, employeeNumber, "更新情報一覧");

		if (updateId != null) {
			argumentCheckLogic.checkLastUpdateId(updateId);
		}

		if (maxSize == null) {
			maxSize = -1;
		}

		return infomationLogic.getInfomationList(employeeNumber, updateId, maxSize);
	}
}
