package jp.co.unirita.medis.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import jp.co.unirita.medis.util.exception.AuthorityException;
import jp.co.unirita.medis.util.exception.NotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.logic.util.ArgumentCheckLogic;
import jp.co.unirita.medis.logic.setting.BookmarkLogic;

@RequestMapping("/v1/documents")

@RestController
public class BookmarkListController {

	@Autowired
	private BookmarkLogic bookmarkLogic;
	@Autowired
	ArgumentCheckLogic argumentCheckLogic;

	@RequestMapping(path = {"{user}/bookmark"}, method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<DocumentInfo> getBookmarkList(
		@AuthenticationPrincipal User user, @PathVariable(value = "user") String employeeNumber,
		@RequestParam(value = "size", required = false) Integer maxSize) throws NotExistException, AuthorityException {

		argumentCheckLogic.checkUser(user, employeeNumber, "お気に入り文書一覧");

		if (maxSize == null) {
			maxSize = -1;
		}

		return bookmarkLogic.getBookmarkList(employeeNumber, maxSize);
	}

	@RequestMapping(path = {"{user}/bookmark/{documentId:^d[0-9]{10}+$}"}, method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void updateBookmark(
		@AuthenticationPrincipal User user, @PathVariable(value = "user") String employeeNumber,
		@PathVariable(value = "documentId") String documentId, @Valid HttpServletRequest request,
		HttpServletResponse response) throws NotExistException, AuthorityException {

		argumentCheckLogic.checkUser(user, employeeNumber, "お気に入り情報");

		argumentCheckLogic.checkDocumentId(documentId);

		bookmarkLogic.updateBookmark(employeeNumber, documentId);
	}
}
