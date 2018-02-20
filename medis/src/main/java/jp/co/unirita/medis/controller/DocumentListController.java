package jp.co.unirita.medis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.form.DocumentInfoForm;
import jp.co.unirita.medis.logic.DocumentListLogic;
import jp.co.unirita.medis.util.exception.InvalidArgumentException;

@RestController
@RequestMapping("/v1/documents")
public class DocumentListController {

	@Autowired
	DocumentListLogic documentListLogic;

	@RequestMapping({ "{user}", "{user}/{type}"})
	public List<DocumentInfoForm> getDocumentList(@AuthenticationPrincipal User user,
			@PathVariable(value = "user") String employeeNumber,
			@PathVariable(value = "type", required = false) String publishType,
			@RequestParam(value = "size", required = false) Integer maxSize) throws InvalidArgumentException {

		if (publishType == null ) {
			publishType = "all";
		}
		if (maxSize == null) {
			maxSize = -1;
		}

		return documentListLogic.getDocumentList(user, employeeNumber, publishType, maxSize);
	}
}