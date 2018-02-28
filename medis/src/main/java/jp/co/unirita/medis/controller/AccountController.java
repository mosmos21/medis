package jp.co.unirita.medis.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jp.co.unirita.medis.domain.authority.AuthorityRepository;
import jp.co.unirita.medis.domain.userdetail.UserDetail;
import jp.co.unirita.medis.domain.userdetail.UserDetailRepository;
import jp.co.unirita.medis.form.AccountForm;
import jp.co.unirita.medis.logic.setting.AccountLogic;
import jp.co.unirita.medis.util.exception.NotExistException;

@RestController
@RequestMapping("v1/accounts")
public class AccountController {

	@Autowired
	AccountLogic accountLogic;
	@Autowired
	AuthorityRepository authorityRepository;
	@Autowired
	UserDetailRepository userDetailRepository;

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "usercheck", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	void userCheck(@RequestBody @Valid AccountForm postData, HttpServletRequest request, HttpServletResponse response)
			throws NotExistException {
		accountLogic.userCheck(postData);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "keycheck", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	UserDetail keyCheck(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "secret", required = false) String key) throws NotExistException {
		return accountLogic.keyCheck(key);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "reset", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	void passwordReset(@RequestBody @Valid AccountForm postData, HttpServletRequest request, HttpServletResponse response) throws NotExistException {
		accountLogic.passwordReset(postData);
	}
}