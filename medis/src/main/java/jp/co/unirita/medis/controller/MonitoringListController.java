package jp.co.unirita.medis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.form.DocumentInfoForm;
import jp.co.unirita.medis.logic.MonitoringLogic;
import jp.co.unirita.medis.util.exception.InvalidArgumentException;

@RequestMapping("/v1/documents")

@RestController
public class MonitoringListController {

	@Autowired
	private MonitoringLogic monitoringLogic;

	@RequestMapping(path = {"{user}/monitoring_tag"}, method = RequestMethod.GET)
	public List<DocumentInfoForm> getMonitoringList(
		@AuthenticationPrincipal User user, @PathVariable(value = "user") String employeeNumber,
		@RequestParam(value = "size", required = false) Integer maxSize) throws InvalidArgumentException {

		if (maxSize == null) {
			maxSize = -1;
		}

		return monitoringLogic.getMonitoringList(employeeNumber, maxSize);

	}
}
