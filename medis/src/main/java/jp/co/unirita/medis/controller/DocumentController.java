package jp.co.unirita.medis.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jp.co.unirita.medis.domain.tag.Tag;
import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.form.CommentInfoForm;
import jp.co.unirita.medis.form.document.DocumentForm;
import jp.co.unirita.medis.logic.CommentLogic;
import jp.co.unirita.medis.logic.DocumentLogic;
import jp.co.unirita.medis.util.exception.InvalidArgsException;

@RestController
@RequestMapping("/v1/documents")
public class DocumentController {

	private static final Logger logger = LoggerFactory.getLogger(TemplateListController.class);

	@Autowired
	CommentLogic commentLogic;
	@Autowired
	DocumentLogic documentLogic;

	@RequestMapping(value = { "{documentId}/comments" }, method = RequestMethod.GET)
	public List<CommentInfoForm> getDocumentInfo(@AuthenticationPrincipal User user,
			@PathVariable(value = "documentId") String documentId) throws InvalidArgsException {
		logger.info("[method: getDocumetnInfo] Get document info list by " + documentId + ".");
		List<CommentInfoForm> documentInfo = commentLogic.getCommentInfo(documentId);

		return documentInfo;
	}

	@GetMapping(value = "{documentId:^d[0-9]{10}+$}")
	@ResponseStatus(HttpStatus.OK)
	public DocumentForm getDocument(@PathVariable(value = "documentId") String documentId) {
		System.out.println("get document [id = " + documentId + "]");

		// TODO 存在チェック

		DocumentForm document = documentLogic.getDocument(documentId);
		System.out.println(document);
		return document;
	}

	@GetMapping(value = "{documentId:^d[0-9]{10}+$}/tags")
	@ResponseStatus(HttpStatus.OK)
	public List<Tag> getDocumentTagList(@PathVariable(value = "documentId") String documentId) {
		return documentLogic.getDocumentTags(documentId);
	}

	@PostMapping(value = "{documentId:^d[0-9]{10}+$}")
	@ResponseStatus(HttpStatus.CREATED)
	public DocumentForm updateDocument(@RequestBody DocumentForm document) throws Exception {
		// TODO 社員番号を確認
		documentLogic.update(document, "99999");
		return document;
	}

	@PostMapping(value = "{documentId:^d[0-9]{10}+$}/tags")
	@ResponseStatus(HttpStatus.CREATED)
	public void updateDocumentTagList(@PathVariable(value = "documentId") String documentId,
			@RequestBody List<Tag> tags) throws Exception {
		documentLogic.updateTags(documentId, tags);
	}

	@PutMapping(value = "new")
	@ResponseStatus(HttpStatus.CREATED)
	public DocumentForm saveDocument(@RequestBody DocumentForm document) throws Exception {
		// TODO 社員番号を取得するようにする
		documentLogic.save(document, "99999");
		return document;
	}

	@PutMapping(value = "{documentId:^d[0-9]{10}+$}/tags")
	@ResponseStatus(HttpStatus.CREATED)
	public void saveDocumentTagList(@PathVariable(value = "documentId") String documentId, @RequestBody List<Tag> tags)
			throws Exception {
		documentLogic.saveTags(documentId, tags);
	}

	@RequestMapping(path = { "{documentId:^d[0-9]{10}+$}/comments/{commentId}/read" }, method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void alreadyRead(@AuthenticationPrincipal User user, @PathVariable(value = "documentId") String documentId,
			@PathVariable(value = "commentId") String commentId, @Valid HttpServletRequest request,
			HttpServletResponse response) throws InvalidArgsException {
		logger.info("[method: alreedyRead] Change Rread boolean And Send mail");
		commentLogic.alreadyRead(documentId, commentId);

	}
}
