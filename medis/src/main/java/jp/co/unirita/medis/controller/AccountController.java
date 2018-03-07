package jp.co.unirita.medis.controller;

import jp.co.unirita.medis.form.system.AccountForm;
import jp.co.unirita.medis.logic.system.AccountLogic;
import jp.co.unirita.medis.util.exception.NotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.util.Map;

@RestController
@RequestMapping("v1/accounts")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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
     * @return チェック結果
     */
	@PostMapping(value = "keycheck")
    @ResponseStatus(HttpStatus.CREATED)
	public Map<String, String> keyCheck(@RequestBody Map<String, String> body) {
		System.out.println(body.get("secret"));
		return accountLogic.checkTempKeyIntegrity(body.get("secret"));
	}

    /**
     * パスワードの再設定を行う
     * @param form パスワードを再設定するユーザ(@see jp.co.unirita.medis.form.AccountForm)
     * @throws NotExistException ユーザが存在しない場合に発生する例外
     */
	@PostMapping(value = "reset")
    @ResponseStatus(HttpStatus.CREATED)
	void passwordReset(@RequestBody AccountForm form) throws NotExistException {
		accountLogic.passwordReset(form.getEmployeeNumber(), form.getMailaddress(), form.getPassword());
	}
}