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


@RequestMapping("/infomations")

@RestController
public class InfomationListController {

	@Autowired
	private InfomationLogic infomationLogic;

	@RequestMapping(path = {"{user}", "{user}/{lastUpdateId}", "{user}/{lastUpdateId}/{size}"}, method = RequestMethod.GET)
	public  List<InfomationForm> getInfomationList(
		@AuthenticationPrincipal User user, @PathVariable(value = "user") String employeeNumber,
		@PathVariable(value = "lastUpdateId", required = false) String updateId, @PathVariable(value = "size", required = false) Integer maxSize) throws InvalidArgumentException {

		if (maxSize == null) {
			maxSize = -1;
		}
		return infomationLogic.getInfomationList(employeeNumber, updateId, maxSize);

	}
}
