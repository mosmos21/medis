package jp.co.unirita.medis.logic;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfoRepository;
import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.domain.user.UserRepository;
import jp.co.unirita.medis.util.exception.InvalidArgumentException;

@Service
@Transactional
public class DocumentListLogic {

	@Autowired
	UserRepository userRepository;
	@Autowired
	DocumentInfoRepository documentInfoRepository;

	public List<DocumentInfo> getDocumentList(User user, String employeeNumber, String publishType, Integer maxSize) throws InvalidArgumentException {

		if (!user.getEmployeeNumber().equals(employeeNumber)) {
			System.out.println(user.getEmployeeNumber() + " " + employeeNumber);
			throw new InvalidArgumentException("employeeNumber", employeeNumber, "他ユーザのすべてのドキュメント一覧は取得することができません");
		}

		List<DocumentInfo> documentInfo = new ArrayList<>();

		if (publishType.equals("all")) { // ユーザのすべてのドキュメントを取得する場合
			documentInfo = documentInfoRepository.findByEmployeeNumberOrderByDocumentCreateDateDesc(employeeNumber);
		} else if (publishType.equals("public")) { // 公開タイプが指定されている場合
			documentInfo = documentInfoRepository.findByEmployeeNumberAndDocumentPublishOrderByDocumentCreateDateDesc(
					employeeNumber, true);
		} else if (publishType.equals("private")) { // 公開タイプが指定されている場合
			documentInfo = documentInfoRepository.findByEmployeeNumberAndDocumentPublishOrderByDocumentCreateDateDesc(
					employeeNumber, false);
		}

		if (maxSize != -1 && documentInfo.size() > maxSize) {
			documentInfo = documentInfo.subList(0, maxSize);
		}

		return documentInfo;

	}
}