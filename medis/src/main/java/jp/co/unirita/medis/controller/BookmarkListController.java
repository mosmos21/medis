package jp.co.unirita.medis.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.form.DocumentInfoForm;
import jp.co.unirita.medis.logic.BookmarkLogic;
import jp.co.unirita.medis.util.exception.InvalidArgumentException;

@RequestMapping("/v1/documents")

@RestController
public class BookmarkListController {

	@Autowired
	private BookmarkLogic bookmarkLogic;

	@RequestMapping(path = {"{user}/bookmark"}, method = RequestMethod.GET)
	public List<DocumentInfoForm> getBookmarkList(
		@AuthenticationPrincipal User user, @PathVariable(value = "user") String employeeNumber,
		@RequestParam(value = "size", required = false) Integer maxSize) throws InvalidArgumentException {

		if (maxSize == null) {
			maxSize = -1;
		}

		return bookmarkLogic.getBookmarkList(employeeNumber, maxSize);
	}

	@RequestMapping(path = {"{user}/bookmark/{documentId}"}, method = RequestMethod.POST)
	public void updateBookmark(
		@AuthenticationPrincipal User user, @PathVariable(value = "user") String employeeNumber,
		@PathVariable(value = "documentId") String documentId, @Valid HttpServletRequest request,
		HttpServletResponse response) throws InvalidArgumentException {
		bookmarkLogic.updateBookmark(employeeNumber, documentId);
	}
}
