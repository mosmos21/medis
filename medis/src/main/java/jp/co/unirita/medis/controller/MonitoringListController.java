package jp.co.unirita.medis.controller;

import java.util.List;

import jp.co.unirita.medis.util.exception.AuthorityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.logic.setting.MonitoringLogic;
import jp.co.unirita.medis.logic.util.ArgumentCheckLogic;
import jp.co.unirita.medis.util.exception.NotExistException;

@RequestMapping("/v1/documents")

@RestController
public class MonitoringListController {

	@Autowired
	private MonitoringLogic monitoringLogic;
	@Autowired
	ArgumentCheckLogic argumentCheckLogic;

	@RequestMapping(path = {"{user}/monitoring_tag"}, method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<DocumentInfo> getMonitoringList(
		@AuthenticationPrincipal User user, @PathVariable(value = "user") String employeeNumber,
		@RequestParam(value = "size", required = false) Integer maxSize) throws NotExistException, AuthorityException {

		argumentCheckLogic.checkUser(user, employeeNumber, "監視文書一覧");

		if (maxSize == null) {
			maxSize = -1;
		}

		return monitoringLogic.getMonitoringList(employeeNumber, maxSize);

	}
}
