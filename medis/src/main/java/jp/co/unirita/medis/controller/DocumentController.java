package jp.co.unirita.medis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.form.CommentInfoForm;
import jp.co.unirita.medis.logic.CommentLogic;
import jp.co.unirita.medis.util.exception.InvalidArgumentException;

@RestController
@RequestMapping("/documents")
public class DocumentController {

	@Autowired
	CommentLogic commentlogic;

	@RequestMapping(value = { "{documentId}/comments" }, method = RequestMethod.GET)
	public List<CommentInfoForm> getfind(@AuthenticationPrincipal User user,
			@PathVariable(value = "documentId") String documentId) throws InvalidArgumentException {
		List<CommentInfoForm> list = commentlogic.getCommentInfo(documentId);

		return list;
	}
}



