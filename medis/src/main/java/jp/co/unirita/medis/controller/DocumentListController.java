package jp.co.unirita.medis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfoRepository;
import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.util.exception.InvalidArgumentException;

@RestController
@RequestMapping("/documents")
public class DocumentListController {

	@Autowired
	DocumentInfoRepository documentInfoRepository;

	@RequestMapping({ "{employeeNumber}", "{employeeNumber}/{type}", "{employeeNumber}/{type}/{size}" })
	public List<DocumentInfo> getDocumentsOfemployeeId(@AuthenticationPrincipal User user,
			@PathVariable(value = "employeeNumber") String employeeNumber,
			@PathVariable(value = "type", required = false) String publishType,
			@PathVariable(value = "size", required = false) Integer maxSize) throws InvalidArgumentException {

		List<DocumentInfo> list = null;
		if (publishType == null && maxSize == null) { // ユーザのすべてのドキュメントを取得する場合
			if (!user.getEmployeeNumber().equals(employeeNumber)) {
				System.out.println(user.getEmployeeNumber() + " " + employeeNumber);
				throw new InvalidArgumentException("employeeNumber", employeeNumber, "他ユーザのすべてのドキュメント一覧は取得することができません");
			}
			list = documentInfoRepository.findByEmployeeNumberOrderByDocumentCreateDateAsc(employeeNumber);
		} else if (maxSize == null) { // 公開タイプが指定されている場合
			list = documentInfoRepository.findByEmployeeNumberAndIsDocumentPublishOrderByDocumentCreateDateAsc(
					employeeNumber, publishType.equals("public"));
		} else { // 公開タイプと最大取得数が指定されている場合
			list = documentInfoRepository.findByEmployeeNumber(employeeNumber, new PageRequest(1, maxSize))
					.getContent();
		}
		return list;
	}
}