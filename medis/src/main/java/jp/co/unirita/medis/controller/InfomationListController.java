package jp.co.unirita.medis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
