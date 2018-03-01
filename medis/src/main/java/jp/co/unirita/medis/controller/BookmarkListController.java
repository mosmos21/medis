package jp.co.unirita.medis.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.form.DocumentInfoForm;
import jp.co.unirita.medis.logic.setting.BookmarkLogic;
import jp.co.unirita.medis.logic.util.ArgumentCheckLogic;
import jp.co.unirita.medis.util.exception.IdIssuanceUpperException;
import jp.co.unirita.medis.util.exception.NotExistException;

@RequestMapping("/v1/documents/bookmark")

@RestController
public class BookmarkListController {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	private BookmarkLogic bookmarkLogic;
	@Autowired
	ArgumentCheckLogic argumentCheckLogic;

	/**
     * ブックマークしている文書一覧を取得する
     * @return 文書情報(@see jp.co.unirita.medis.form.DocumentInfoForm)のリスト
     */
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<DocumentInfoForm> getBookmarkList(
			@AuthenticationPrincipal User user) {
		return bookmarkLogic.getBookmarkList(user.getEmployeeNumber());
	}

	/**
     * 文書をブックマークする
     * 既にブックマークされている文書は、ブックマークを取り消す
     * @param documentId お気に入りした文書ID
     * @throws NotExistException 文書IDが存在していない場合に発生する例外
	 * @throws IdIssuanceUpperException 新規で発行するブックマークIDの発行数が限界になった場合に発生する例外
     */
	@PostMapping("/documentId:^d[0-9]{10}+$")
	@ResponseStatus(HttpStatus.CREATED)
	public void updateBookmark(
		@AuthenticationPrincipal User user,
		@PathVariable(value = "documentId") String documentId
	) throws NotExistException, IdIssuanceUpperException {
		argumentCheckLogic.checkDocumentId(documentId);
		bookmarkLogic.updateBookmark(user.getEmployeeNumber(), documentId);
	}
}
