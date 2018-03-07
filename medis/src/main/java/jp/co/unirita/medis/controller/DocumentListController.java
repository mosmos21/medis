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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.form.document.DocumentInfoForm;
import jp.co.unirita.medis.logic.document.DocumentListLogic;

@RestController
@RequestMapping("/v1/documents")
public class DocumentListController {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	DocumentListLogic documentListLogic;

	/**
     * ユーザの文書一覧を取得する
     * @param user ログインしているユーザ
     * @param type public:公開済み文書 / private:下書き文書
     * @return 文書情報(@see jp.co.unirita.medis.form.DocumentInfoForm)のリスト
     */
	@GetMapping({"/{type:^public|private$}",""})
	@ResponseStatus(HttpStatus.OK)
	public List<DocumentInfoForm> getDocumentList(@AuthenticationPrincipal User user,
			@PathVariable(value = "type", required = false) String publishType) {
        return documentListLogic.getDocumentList(user.getEmployeeNumber(), publishType);
	}
}