package jp.co.unirita.medis.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jp.co.unirita.medis.domain.tag.Tag;
import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.form.document.CommentInfoForm;
import jp.co.unirita.medis.form.document.DocumentForm;
import jp.co.unirita.medis.logic.document.CommentLogic;
import jp.co.unirita.medis.logic.document.DocumentLogic;
import jp.co.unirita.medis.logic.util.ArgumentCheckLogic;
import jp.co.unirita.medis.util.exception.IdIssuanceUpperException;
import jp.co.unirita.medis.util.exception.NotExistException;

@RestController
@RequestMapping("/v1/documents")
public class DocumentController {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	DocumentLogic documentLogic;
	@Autowired
	ArgumentCheckLogic argumentCheckLogic;
	@Autowired
	CommentLogic commentLogic;

	/**
	 * 文書の内容を取得する
	 *
	 * @param documentId
	 *            取得する文書の文書ID
	 * @return 文書情報(@see jp.co.unirita.medis.form.DocumentForm)のリスト
	 * @throws NotExistException
	 *             文書IDが存在していない場合に発生する例外
	 */
	@GetMapping(value = "{documentId:^d[0-9]{10}$}")
	@ResponseStatus(HttpStatus.OK)
	public DocumentForm getDocument(@AuthenticationPrincipal User user,
			@PathVariable(value = "documentId") String documentId) throws NotExistException {
		logger.info("[method: getDocument] Get document list by " + documentId + ".");
		argumentCheckLogic.checkDocumentId(documentId);
		return documentLogic.getDocument(documentId);
	}

	/**
	 * 文書につけられたタグ一覧を取得する
	 *
	 * @param documentId
	 *            取得するタグ一覧をつけた文書の文書ID
	 * @return タグ情報(@see jp.co.unirita.medis.domain.tag.Tag)のリスト
	 * @throws NotExistException
	 *             文書IDが存在していない場合に発生する例外
	 */
	@GetMapping(value = "{documentId:^d[0-9]{10}$}/tags")
	@ResponseStatus(HttpStatus.OK)
	public List<Tag> getDocumentTagList(@PathVariable(value = "documentId") String documentId)
			throws NotExistException {
		logger.info("[method: getDocumentTagList] Get documentTagList list by " + documentId + ".");
		argumentCheckLogic.checkDocumentId(documentId);
		return documentLogic.getDocumentTags(documentId);
	}

	/**
	 * コメントの内容を取得する
	 *
	 * @param user
	 *            ログインしているユーザ
	 * @param documentId
	 *            新規作成された文書の文書ID
	 * @return コメント情報(@see jp.co.unirita.medis.form.CommentInfoForm)のリスト
	 * @throws NotExistException
	 *             文書IDが存在していない場合に発生する例外
	 */
	@GetMapping(value = "{documentId:^d[0-9]{10}$}/comments")
	@ResponseStatus(HttpStatus.OK)
	public List<CommentInfoForm> getComment(@AuthenticationPrincipal User user,
			@PathVariable(value = "documentId") String documentId) throws NotExistException {
		logger.info("[method: getComment] Get comment info list by " + documentId + ".");
		argumentCheckLogic.checkDocumentId(documentId);
		List<CommentInfoForm> documentInfo = commentLogic.getCommentInfo(documentId);
		return documentInfo;
	}

	/**
	 * 文書の内容を更新する
	 *
	 * @param user
	 *            ログインしているユーザ
	 * @param documentId
	 *            更新する文書の文書ID
	 * @return documentId 更新した文書の文書ID
	 * @throws NotExistException
	 *             文書IDが存在していない場合に発生する例外
	 * @throws IdIssuanceUpperException
	 *             IDの発行数が限界を超えたときに発生する例外
	 */
	@PostMapping(value = "{documentId:^d[0-9]{10}$}")
	@ResponseStatus(HttpStatus.CREATED)
	public String updateDocument(@AuthenticationPrincipal User user,
			@PathVariable(value = "documentId") String documentId, @RequestBody DocumentForm document)
			throws NotExistException, IdIssuanceUpperException {
		logger.info("[method: updateDocument] UpdateDocument list by " + documentId + ".");
		argumentCheckLogic.checkDocumentId(documentId);
		return documentLogic.update(document, user.getEmployeeNumber());
	}

	/**
	 * 文書につけられたタグを更新する
	 *
	 * @param documentId
	 *            更新するタグ一覧をつけた文書の文書ID
	 * @throws NotExistException
	 *             文書IDが存在していない場合に発生する例外
	 * @throws IdIssuanceUpperException
	 */
	@PostMapping(value = "{documentId:^d[0-9]{10}$}/tags")
	@ResponseStatus(HttpStatus.CREATED)
	public void updateDocumentTagList(@PathVariable(value = "documentId") String documentId,
			@RequestBody List<Tag> tags) throws NotExistException, IdIssuanceUpperException {
		logger.info("[method: updateDocumentTagList] UpdateDocumentTagList list by DocumentId:" + documentId + ".");
		argumentCheckLogic.checkDocumentId(documentId);
		documentLogic.updateTags(documentId, tags);
	}

	/**
	 * 既読情報を更新する
	 *
	 * @param user
	 *            ログインしているユーザ
	 * @param documentId
	 *            コメントが記入されている文書の文書ID
	 * @param commentId
	 *            既読情報を更新するコメントID
	 * @throws NotExistException
	 *             文書ID、またはコメントIDが存在していない場合に発生する例外
	 */
	@PostMapping("{documentId:^d[0-9]{10}$}/comments/{commentId:^o[0-9]{10}$}/read")
	@ResponseStatus(HttpStatus.CREATED)
	public void alreadyRead(@AuthenticationPrincipal User user, @PathVariable(value = "documentId") String documentId,
			@PathVariable(value = "commentId") String commentId, @Valid HttpServletRequest request,
			HttpServletResponse response) throws NotExistException {
		logger.info("[method: alreedyRead] Set AlreadyRead And Send mail");
		argumentCheckLogic.checkDocumentId(documentId);
		argumentCheckLogic.checkCommentId(commentId);
		commentLogic.alreadyRead(documentId, commentId);
	}

	/**
	 * 新規文書を保存し、ドキュメントIDを付与する
	 *
	 * @param user
	 *            ログインしているユーザ
	 * @param document
	 *            ドキュメントフォーム(@see jp.co.unirita.medis.form.document.DocumentForm)
	 * @return document 新規作成した文書の文書ID
	 * @throws IdIssuanceUpperException
	 *             IDの発行数が限界を超えたときに発生する例外
	 */
	@PutMapping(value = "new")
	@ResponseStatus(HttpStatus.CREATED)
	public String saveDocument(@AuthenticationPrincipal User user, @RequestBody DocumentForm document)
			throws IdIssuanceUpperException {
		logger.info("[method: saveDocument] SaveDocument list by " + document.getDocumentId() + ".");
		return documentLogic.save(document, user.getEmployeeNumber());
	}

	/**
	 * 新規文書についているタグを保存する
	 *
	 * @param documentId
	 *            新規作成された文書の文書ID
	 * @param tags
	 *            タグエンティティ(@see jp.co.unirita.medis.domain.tag.Tag)
	 * @throws NotExistException
	 *             文書IDが存在していない場合に発生する例外
	 * @throws IdIssuanceUpperException
	 *             IDの発行数が限界を超えたときに発生する例外
	 */
	@PutMapping(value = "{documentId:^d[0-9]{10}$}/tags")
	@ResponseStatus(HttpStatus.CREATED)
	public void saveDocumentTagList(@PathVariable(value = "documentId") String documentId, @RequestBody List<Tag> tags)
			throws NotExistException, IdIssuanceUpperException {
		logger.info("[method: saveDocumentTagList] SaveDocumentTagList list by " + documentId + ".");
		argumentCheckLogic.checkDocumentId(documentId);
		documentLogic.saveTags(documentId, tags);
	}

	/**
	 * 新規コメントを保存し、コメントIDを付与する
	 *
	 * @param user
	 *            ログインしているユーザ
	 * @param documentId
	 *            コメントを記入する文書の文書ID
	 * @param value
	 *            valueをキーにしたコメントの内容
	 * @return コメント情報(@see jp.co.unirita.medis.form.CommentInfoForm)
	 * @throws NotExistException
	 *             更新IDが存在していない場合に発生する例外
	 * @throws IdIssuanceUpperException
	 *             IDの発行数が限界を超えたときに発生する例外
	 */
	@PutMapping("{documentId:^d[0-9]{10}$}/comments/create")
	@ResponseStatus(HttpStatus.CREATED)
	public CommentInfoForm commetAdd(@AuthenticationPrincipal User user,
			@PathVariable(value = "documentId") String documentId, @RequestBody Map<String, String> value,
			@Valid HttpServletRequest request, HttpServletResponse response)
			throws NotExistException, IdIssuanceUpperException {
		logger.info("[method: save] Add Comment EmployeeNumber:" + user.getEmployeeNumber() + "commentContent:"
				+ value.get("commentContent"));
		argumentCheckLogic.checkDocumentId(documentId);
		return commentLogic.save(documentId, user.getEmployeeNumber(), value);
	}
}
