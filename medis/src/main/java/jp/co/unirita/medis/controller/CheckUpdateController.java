package jp.co.unirita.medis.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;

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
import jp.co.unirita.medis.logic.util.ArgumentCheckLogic;
import jp.co.unirita.medis.util.exception.NotExistException;

@RequestMapping("/v1/update")

@RestController
public class CheckUpdateController {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	ArgumentCheckLogic argumentCheckLogic;
	@Autowired
	CheckUpdateLogic checkUpdateLogic;

	/**
	 * 定期的にUpdateInfoテーブルを監視して、Sbackbar通知をするために必要な最初のUpdateIDを取得
	 * @return updateId 最新のUpdateID
	 */
	@GetMapping("/latest")
	@ResponseStatus(HttpStatus.CREATED)
	public Map<String, String> CheckLatestUpdateId() {
		logger.info("[method ChackLatest] Get LatestUpdateID");

		return checkUpdateLogic.getLatestUpdateId();

	}

	/**
	 * 	Snackbar通知のために定期的に呼ばれ、ログインユーザに関わるコメント、既読、監視タグのついた文書情報を取得のためにUpdateInfoテーブルを監視
	 *
	 * @param user ログインしているユーザ
	 * @param updateId ログインユーザがもつ最新のUpdateID
	 * @return uopdateInfo ログインユーザが持つ最新のUpdateID以降の、ログインユーザに関わるコメント、既読、監視タグ更新情報
	 * @throws NotExistException UpdateIDが存在していない場合に発生する例外
	 */
	@GetMapping(value = "{updateId:^u[0-9]{10}$}")
	@ResponseStatus(HttpStatus.CREATED)
	public List<SnackbarNotificationsForm> CheckUpdate(@AuthenticationPrincipal User user,
			@PathVariable(value = "updateId") String updateId) throws NotExistException {
		logger.info("[method updatetypeConfirmation] GetUpdatetypeConfirmation by DocumentId :"
				+ user.getEmployeeNumber() + "after UpdateId" + updateId + ".");
		argumentCheckLogic.checkLastUpdateId(updateId);
		return checkUpdateLogic.updatetypeConfirmation(user.getEmployeeNumber(), updateId);
	}
}
