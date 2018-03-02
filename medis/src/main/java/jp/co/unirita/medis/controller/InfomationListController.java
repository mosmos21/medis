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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.form.setting.InfomationForm;
import jp.co.unirita.medis.logic.setting.InfomationLogic;
import jp.co.unirita.medis.logic.util.ArgumentCheckLogic;
import jp.co.unirita.medis.util.exception.NotExistException;


@RequestMapping("/v1/infomations")

@RestController
public class InfomationListController {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	InfomationLogic infomationLogic;
	@Autowired
	ArgumentCheckLogic argumentCheckLogic;

    /**
     * 更新情報を取得する
     * lastUpadateIdが指定されていた場合はそのIDより後の更新のみを取得する
     * @param user ログインしているユーザ
     * @param updateId　前回取得時に最後に更新された更新ID
     * @return 更新Id情報(@see jp.co.unirita.medis.form.InfomationForm)のリスト
     * @throws NotExistException 更新IDが存在していない場合に発生する例外
     */
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public  List<InfomationForm> getInfomationList(
		@AuthenticationPrincipal User user,
		@RequestParam(value = "lastUpdateId", required = false) String updateId
	) throws NotExistException {
		if (updateId != null) {
			argumentCheckLogic.checkLastUpdateId(updateId);
			return infomationLogic.getInfomationList(user.getEmployeeNumber(), updateId);
		}
		return infomationLogic.getAllInfomationList(user.getEmployeeNumber());
	}
}
