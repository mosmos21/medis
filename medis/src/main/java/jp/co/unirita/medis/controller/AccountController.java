package jp.co.unirita.medis.controller;

import jp.co.unirita.medis.form.AccountForm;
import jp.co.unirita.medis.logic.setting.AccountLogic;
import jp.co.unirita.medis.util.exception.NotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("v1/accounts")
public class AccountController {

	@Autowired
	AccountLogic accountLogic;

    /**
     * パスワード再設定のための一時キーを発行する
     * ユーザの整合性チェックが行われ、DBに一時キーが保管されたのちメールが送信される。
     * @param form パスワードを再設定するユーザ(@see jp.co.unirita.medis.form.AccountForm)
     * @return 発行結果
     */
	@PostMapping(value = "usercheck")
    @ResponseStatus(HttpStatus.CREATED)
	public Map<String, String> userCheck(@RequestBody @Valid AccountForm form) {
		Map<String, String> result = accountLogic.checkUserIntegrity(form.getEmployeeNumber(), form.getMailaddress());
		if(result.get("result").equals("NG")) {
		    return result;
        }
        String key = accountLogic.issueTempKey(form.getEmployeeNumber());
		accountLogic.sendMail(form.getMailaddress(), key);
		return result;
	}

    /**
     * 送信された一時キーの有効性を確かめる
     * @param key 一時キー
     * @return チェック結果
     */
	@PostMapping(value = "keycheck")
    @ResponseStatus(HttpStatus.CREATED)
	public Map<String, String> keyCheck(@RequestParam(value = "secret", required = false) String key) {
		return accountLogic.checkTempKeyIntegrity(key);
	}

    /**
     * パスワードの再設定を行う
     * @param form パスワードを再設定するユーザ(@see jp.co.unirita.medis.form.AccountForm)
     * @throws NotExistException ユーザが存在しない場合に発生する例外
     */
	@PostMapping(value = "reset")
    @ResponseStatus(HttpStatus.CREATED)
	void passwordReset(@RequestBody @Valid AccountForm form) throws NotExistException {
		accountLogic.passwordReset(form.getEmployeeNumber(), form.getMailaddress(), form.getPassword());
	}
}