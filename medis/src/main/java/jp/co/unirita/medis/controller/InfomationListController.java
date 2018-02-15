package jp.co.unirita.medis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.form.InfomationForm;
import jp.co.unirita.medis.logic.InfomationLogic;
import jp.co.unirita.medis.util.exception.InvalidArgumentException;


@RestController
public class InfomationListController {

	@Autowired
	private InfomationLogic infomationLogic;

	@RequestMapping(path = {"infomations/{user}/{lastUpdateId}", "infomations/{user}/{lastUpdateId}/{size}"}, method = RequestMethod.GET)
	public  List<InfomationForm> getInfomationList(
		@AuthenticationPrincipal User user, @PathVariable(value = "user") String employeeNumber,
		@PathVariable(value = "lastUpdateId") String updateId, @PathVariable(value = "size", required = false) Integer maxSize) throws InvalidArgumentException {
		System.out.println(1);
		return infomationLogic.getInfomationList(employeeNumber, updateId, maxSize);

	}
}
