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
import jp.co.unirita.medis.form.CommentCreateForm;
import jp.co.unirita.medis.form.CommentInfoForm;
import jp.co.unirita.medis.form.document.DocumentForm;
import jp.co.unirita.medis.logic.document.CommentLogic;
import jp.co.unirita.medis.logic.document.DocumentLogic;
import jp.co.unirita.medis.util.exception.NotExistException;

@RestController
@RequestMapping("/v1/documents")
public class DocumentController {

	private static final Logger logger = LoggerFactory.getLogger(TemplateListController.class);

	@Autowired
	CommentLogic commentLogic;
	@Autowired
	DocumentLogic documentLogic;

	@GetMapping(value = "{documentId:^d[0-9]{10}+$}/comments")
	@ResponseStatus(HttpStatus.OK)
	public List<CommentInfoForm> getComment(@AuthenticationPrincipal User user,
			@PathVariable(value = "documentId") String documentId) throws NotExistException {
		logger.info("[method: getComment] Get comment info list by " + documentId + ".");
		List<CommentInfoForm> documentInfo = commentLogic.getCommentInfo(documentId);

		return documentInfo;
	}

	@GetMapping(value = "{documentId:^d[0-9]{10}+$}")
	@ResponseStatus(HttpStatus.OK)
	public DocumentForm getDocument(@PathVariable(value = "documentId") String documentId) {
		logger.info("[method: getDocument] Get document list by " + documentId + ".");
		// TODO 存在チェック

		DocumentForm document = documentLogic.getDocument(documentId);
		return document;
	}

	@GetMapping(value = "{documentId:^d[0-9]{10}+$}/tags")
	@ResponseStatus(HttpStatus.OK)
	public List<Tag> getDocumentTagList(@PathVariable(value = "documentId") String documentId) {
		logger.info("[method: getDocumentTagList] Get documentTagList list by " + documentId + ".");
		return documentLogic.getDocumentTags(documentId);
	}

	@PostMapping(value = "{documentId:^d[0-9]{10}+$}")
	@ResponseStatus(HttpStatus.CREATED)
	public DocumentForm updateDocument(@RequestBody DocumentForm document) throws Exception {
		// TODO 社員番号を確認
		logger.info("[method: updateDocument] Get updateDocument list by " + document.getDocumentId()+ ".");
		documentLogic.update(document, "99999");
		return document;
	}

	@PostMapping(value = "{documentId:^d[0-9]{10}+$}/tags")
	@ResponseStatus(HttpStatus.CREATED)
	public void updateDocumentTagList(@PathVariable(value = "documentId") String documentId,
			@RequestBody List<Tag> tags) throws Exception {
		logger.info("[method: updateDocumentTagList] Get updateDocumentTagList list by DocumentId:"+documentId +"TagId"+ tags.get(0).getTagId() + ".");
		documentLogic.updateTags(documentId, tags);
	}

	@PutMapping(value = "new")
	@ResponseStatus(HttpStatus.CREATED)
	public DocumentForm saveDocument(@RequestBody DocumentForm document) throws Exception {
		logger.info("[method: saveDocument] Get saveDocument list by " + document.getDocumentId() + ".");
		// TODO 社員番号を取得するようにする
		documentLogic.save(document, "99999");
		return document;
	}

	@PutMapping(value = "{documentId:^d[0-9]{10}+$}/tags")
	@ResponseStatus(HttpStatus.CREATED)
	public void saveDocumentTagList(@PathVariable(value = "documentId") String documentId, @RequestBody List<Tag> tags)
			throws Exception {
		logger.info("[method: saveDocumentTagList] Get saveDocumentTagList list by " + documentId + ".");
		documentLogic.saveTags(documentId, tags);
	}

	@RequestMapping(path = { "{documentId:^d[0-9]{10}+$}/comments/{commentId}/read" }, method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void alreadyRead(@AuthenticationPrincipal User user, @PathVariable(value = "documentId") String documentId,
			@PathVariable(value = "commentId") String commentId, @Valid HttpServletRequest request,
			HttpServletResponse response) throws NotExistException {
		logger.info("[method: alreedyRead] Change alreadyRead boolean And Send mail");
		commentLogic.alreadyRead(documentId, commentId);

	}

	@RequestMapping(path = { "{documentId:^d[0-9]{10}+$}/comments/create" }, method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void commetAdd(@RequestBody @Valid CommentCreateForm postData,
			@PathVariable(value = "documentId") String documentId, @Valid HttpServletRequest request,
			HttpServletResponse response) throws NotExistException {
		logger.info("[method: save] Add Comment EmployeeNumber:" + postData.getEmployeeNumber() + "value:"
				+ postData.getValue());
		commentLogic.sava(documentId, postData);

	}
}
