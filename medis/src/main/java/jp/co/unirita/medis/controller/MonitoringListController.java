package jp.co.unirita.medis.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.form.DocumentInfoForm;
import jp.co.unirita.medis.logic.setting.MonitoringLogic;

@RequestMapping("/v1/documents")

@RestController
public class MonitoringListController {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	MonitoringLogic monitoringLogic;

	/**
     * 監視しているタグが付いた文書の一覧を取得する
     * @return 文書情報(@see jp.co.unirita.medis.form.DocumentInfoForm)のリスト
     */
	@GetMapping("/monitoring_tag")
	@ResponseStatus(HttpStatus.OK)
	public List<DocumentInfoForm> getMonitoringList(
			@AuthenticationPrincipal User user) {
		return monitoringLogic.getMonitoringList(user.getEmployeeNumber());
	}
}
