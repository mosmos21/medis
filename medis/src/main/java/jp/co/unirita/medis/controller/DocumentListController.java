package jp.co.unirita.medis.controller;

import java.util.List;

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
import jp.co.unirita.medis.domain.documentInfo.DocumentInfoRepository;
import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.logic.ArgumentCheckLogic;
import jp.co.unirita.medis.logic.DocumentListLogic;
import jp.co.unirita.medis.util.exception.InvalidArgsException;

@RestController
@RequestMapping("/v1/documents")
public class DocumentListController {

    @Autowired
    DocumentInfoRepository documentInfoRepository;
	@Autowired
	DocumentListLogic documentListLogic;
	@Autowired
	ArgumentCheckLogic argumentCheckLogic;


	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<DocumentInfo> getDocumentInfo() {
        // TODO 社員番号を取得する
        List<DocumentInfo> list = documentInfoRepository.findByEmployeeNumber("99999");
        list.sort((i1, i2) -> i1.getDocumentId().compareTo(i2.getDocumentId()));
        return list;
    }

	@RequestMapping({ "{user}", "{user}/{type:^public|private$}"})
	@ResponseStatus(HttpStatus.OK)
	public List<DocumentInfo> getDocumentList(@AuthenticationPrincipal User user,
			@PathVariable(value = "user") String employeeNumber,
			@PathVariable(value = "type", required = false) String publishType,
			@RequestParam(value = "size", required = false) Integer maxSize) throws InvalidArgsException {

		argumentCheckLogic.userCheck(user, employeeNumber, "ドキュメント一覧");

		if (publishType == null ) {
			publishType = "all";
		}

		if (maxSize == null) {
			maxSize = -1;
		}

		return documentListLogic.getDocumentList(employeeNumber, publishType, maxSize);
	}
}