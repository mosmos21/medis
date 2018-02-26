package jp.co.unirita.medis.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.form.DocumentInfoForm;
import jp.co.unirita.medis.logic.util.SearchLogic;
import jp.co.unirita.medis.util.exception.NotExistException;

@RequestMapping("/v1/search")

@RestController
public class SearchController {

	@Autowired
	SearchLogic searchLogic;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<DocumentInfoForm> getSearchResult(
			@AuthenticationPrincipal User user, @RequestParam(value = "tags", required = false) String tagName) throws NotExistException, UnsupportedEncodingException {

		return searchLogic.getSearchResult(tagName);

	}

}
