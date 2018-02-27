package jp.co.unirita.medis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.form.DocumentInfoForm;
import jp.co.unirita.medis.logic.document.DocumentListLogic;
import jp.co.unirita.medis.logic.util.ArgumentCheckLogic;
import jp.co.unirita.medis.util.exception.AuthorityException;
import jp.co.unirita.medis.util.exception.NotExistException;

@RestController
@RequestMapping("/v1/documents")
public class DocumentListController {

	@Autowired
	DocumentListLogic documentListLogic;
	@Autowired
	ArgumentCheckLogic argumentCheckLogic;

/*
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<DocumentInfoForm> getDocumentInfo() {
        // TODO 社員番号を取得する
        return documentListLogic.getAllDocumentInfoList("99999");
    }
*/

	@RequestMapping({ "{user:^[0-9a-z-A-Z]{4,10}$}", "{user:^[0-9a-z-A-Z]{4,10}$}/{type:^public|private$}"})
	@ResponseStatus(HttpStatus.OK)
	public List<DocumentInfoForm> getDocumentList(@AuthenticationPrincipal User user,
			@PathVariable(value = "user") String employeeNumber,
			@PathVariable(value = "type", required = false) String publishType,
			@RequestParam(value = "size", required = false) Integer maxSize) throws NotExistException, AuthorityException {

		argumentCheckLogic.checkUser(user, employeeNumber, "ドキュメント一覧");

		if (maxSize == null) {
			maxSize = -1;
		}

        return documentListLogic.getDocumentList(employeeNumber, publishType, maxSize);
	}
}