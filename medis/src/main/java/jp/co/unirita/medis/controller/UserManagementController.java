package jp.co.unirita.medis.controller;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.unirita.medis.logic.system.MailLogic;
import jp.co.unirita.medis.util.exception.IdIssuanceUpperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jp.co.unirita.medis.domain.authority.Authority;
import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.domain.userdetail.UserDetail;
import jp.co.unirita.medis.form.system.UserManagementForm;
import jp.co.unirita.medis.logic.setting.SettingLogic;
import jp.co.unirita.medis.logic.system.AccountLogic;
import jp.co.unirita.medis.logic.system.UserManagementLogic;
import jp.co.unirita.medis.logic.util.ArgumentCheckLogic;
import jp.co.unirita.medis.util.exception.AuthorityException;
import jp.co.unirita.medis.util.exception.ConflictException;
import jp.co.unirita.medis.util.exception.NotExistException;

@RestController
@RequestMapping("v1/system")
public class UserManagementController {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    ArgumentCheckLogic argumentCheckLogic;
    @Autowired
    AccountLogic accountLogic;
	@Autowired
	UserManagementLogic userManagementLogic;
	@Autowired
	SettingLogic settingLogic;
	@Autowired
	MailLogic mailLogic;


    /**
     * ユーザ情報の一覧を取得する
     * @param user ログインしているユーザ
     * @return ユーザ情報詳細(@see jp.co.unirita.medis.form.UserManagementForm)のリスト
     * @throws AuthorityException ログインしているユーザに管理者権限がない場合に発生する例外
     */
	@GetMapping(value = "users")
	@ResponseStatus(HttpStatus.OK)
	public List<UserManagementForm> getUserManagement(
            @AuthenticationPrincipal User user
    ) throws AuthorityException {
        logger.info("[method: getUserManagement] employeeNumber = " + user.getEmployeeNumber());
	    argumentCheckLogic.checkAdminAuthority(user.getEmployeeNumber());
		List<UserManagementForm> info = userManagementLogic.getUserManagement();
		return info;
	}

    /**
     * ユーザ権限種別マスタの一覧を取得する
     * @param user ログインしているユーザ
     * @return ユーザ権限種別(@see jp.co.unirita.medis.domain.authority.Authority)のリスト
     * @throws AuthorityException ログインしているユーザに管理者権限がない場合に発生する例外
     */
	@GetMapping(value = "users/authority")
    @ResponseStatus(HttpStatus.OK)
	public List<Authority> getAuthorityTypeList(
	        @AuthenticationPrincipal User user
    ) throws AuthorityException {
        logger.info("[method: getAuthorityTypeList] employeeNumber = " + user.getEmployeeNumber());
	    argumentCheckLogic.checkAdminAuthority(user.getEmployeeNumber());
		return userManagementLogic.getAuthorityTypeList();
	}

    /**
     * ユーザの有効、無効化、ユーザ権限の更新を行う
     * @param user ログインしているユーザ
     * @param data 更新を行うユーザのデータ
     * @throws AuthorityException ログインしているユーザに管理者権限がない場合に発生する例外
     * @throws NotExistException 更新をしようとしているユーザが見つからなかった場合に発生する例外
     */
	@PostMapping(value = "users/update")
    @ResponseStatus(HttpStatus.CREATED)
	public void updateUserManagement(
            @AuthenticationPrincipal User user,
	        @RequestBody UserManagementForm data
    ) throws AuthorityException, NotExistException {
        logger.info("[method: updateUserManagement] employeeNumber = " + user.getEmployeeNumber());
	    argumentCheckLogic.checkAdminAuthority(user.getEmployeeNumber());
	    argumentCheckLogic.checkUserExist(data.getEmployeeNumber());
		userManagementLogic.updateUserManagement(data.getEmployeeNumber(), data.isEnabled(), data.getAuthorityId());
	}

    /**
     * 新規ユーザの作成を行う
     * @param user　ログインしているユーザ
     * @param data　作成を行うユーザのデータ
     * @throws AuthorityException　ログインしているユーザに管理者権限がない場合に発生する例外
     * @throws ConflictException　作成をしようとしているユーザの社員番号がすでに存在している場合に発生する例外
     */
	@PostMapping(value = "users/new")
    @ResponseStatus(HttpStatus.CREATED)
	public void createUser(
            @AuthenticationPrincipal User user,
            @RequestBody UserManagementForm data
    ) throws AuthorityException, ConflictException, IdIssuanceUpperException {
        logger.info("[method: createUser] employeeNumber = " + user.getEmployeeNumber());
        argumentCheckLogic.checkAdminAuthority(user.getEmployeeNumber());

		UserDetail detail = userManagementLogic.createUser(data);

		//コメント通知設定初期化
		Map<String, Boolean> map = new HashMap<>();
		String mail = "mailNotification";
		String browser = "browserNotification";
		map.put(mail, false);
		map.put(browser, false);
		settingLogic.createNotificationComment(detail.getEmployeeNumber(), map);

		String key =accountLogic.issueTempKey(detail.getEmployeeNumber());
		mailLogic.sendResetPasswordLink(detail.getMailaddress(), key);
	}
}