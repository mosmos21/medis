package jp.co.unirita.medis.controller;

import java.util.List;

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

import jp.co.unirita.medis.domain.authority.Authority;
import jp.co.unirita.medis.domain.authority.AuthorityRepository;
import jp.co.unirita.medis.form.UserManagementForm;
import jp.co.unirita.medis.logic.system.UserManagementLogic;
import jp.co.unirita.medis.util.exception.ConflictException;
import jp.co.unirita.medis.util.exception.NotExistException;

@RestController
@RequestMapping("v1/system")
public class UserManagementController {

	@Autowired
	UserManagementLogic userManagementLogic;
	@Autowired
	AuthorityRepository authorityRepository;

	@RequestMapping(value = "users", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	List<UserManagementForm> getUserManagement(@RequestParam(value = "size", required = false) Integer maxSize) {
		if(maxSize == null) maxSize = -1;
		List<UserManagementForm> info = userManagementLogic.getUserManagement(maxSize);
		return info;
	}

	@RequestMapping(value = "users/authority", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	List<Authority> getAuthorityTypeList() {
		List<Authority> info = authorityRepository.findAll();
		return info;
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "users/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	void updateUserManagement(@RequestBody UserManagementForm postData, HttpServletRequest request,
			HttpServletResponse response) throws NotExistException {
		userManagementLogic.updateUserManagement(postData);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "users/new", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	void newUserManagement(@RequestBody @Valid UserManagementForm postData, HttpServletRequest request,
			HttpServletResponse response) throws ConflictException {
		userManagementLogic.newUserManagement(postData);
	}
}