package jp.co.unirita.medis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.form.BookmarkForm;
import jp.co.unirita.medis.logic.BookmarkListLogic;
import jp.co.unirita.medis.util.exception.InvalidArgumentException;

@RequestMapping("/documents")

@RestController
public class BookmarkListController {

	@Autowired
	private BookmarkListLogic bookmarkListLogic;

	@RequestMapping(path = {"{user}/bookmark", "{user}/bookmark/{size}"}, method = RequestMethod.GET)
	public List<BookmarkForm> getBookmarkList (
		@AuthenticationPrincipal User user, @PathVariable(value = "user") String employeeNumber,
		@PathVariable(value = "size", required = false) Integer maxSize) throws InvalidArgumentException {

		if (maxSize == null) {
			maxSize = -1;
		}

		return bookmarkListLogic.getBookmarkList(employeeNumber, maxSize);

	}
}
