package jp.co.unirita.medis.controller;

import java.util.ArrayList;
import java.util.List;

import jp.co.unirita.medis.util.exception.AuthorityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.logic.util.ArgumentCheckLogic;
import jp.co.unirita.medis.logic.document.DocumentListLogic;
import jp.co.unirita.medis.util.exception.NotExistException;

@RestController
@RequestMapping("/v1/documents")
public class DocumentListController {

	@Autowired
	DocumentListLogic documentListLogic;
	@Autowired
	ArgumentCheckLogic argumentCheckLogic;


	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<DocumentInfo> getDocumentInfo() {
        // TODO 社員番号を取得する
        return documentListLogic.getAllDocumentInfoList("99999");
    }

	@RequestMapping({ "{user:^[0-9a-z-A-Z]{5,10}$}", "{user:^[0-9a-z-A-Z]{5,10}$}/{type:^public|private$}"})
	@ResponseStatus(HttpStatus.OK)
	public List<DocumentInfo> getDocumentList(@AuthenticationPrincipal User user,
			@PathVariable(value = "user") String employeeNumber,
			@PathVariable(value = "type", required = false) String publishType,
			@RequestParam(value = "size", required = false) Integer maxSize) throws NotExistException, AuthorityException {

		argumentCheckLogic.checkUser(user, employeeNumber, "ドキュメント一覧");

		List<DocumentInfo> list = new ArrayList<>();

		if(publishType == null) {
		    list = documentListLogic.getAllDocumentInfoList(employeeNumber);
        } else {
		    list = documentListLogic.getDocumentInfoList(employeeNumber, publishType.equals("public"));
        }
        return list;
	}
}