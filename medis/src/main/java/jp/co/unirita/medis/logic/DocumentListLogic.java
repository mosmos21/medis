package jp.co.unirita.medis.logic;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.unirita.medis.domain.contentframe.ContentFrame;
import jp.co.unirita.medis.domain.contentframe.ContentFrameRepository;
import jp.co.unirita.medis.domain.contentother.ContentOther;
import jp.co.unirita.medis.domain.contentother.ContentOtherRepository;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfoRepository;
import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.domain.user.UserRepository;
import jp.co.unirita.medis.form.DocumentInfoForm;
import jp.co.unirita.medis.util.exception.InvalidArgumentException;

@Service
@Transactional
public class DocumentListLogic {

	@Autowired
	UserRepository userRepository;
	@Autowired
	DocumentInfoRepository documentInfoRepository;
	@Autowired
	ContentFrameRepository contentFrameRepository;
	@Autowired
	ContentOtherRepository contentOtherRepository;

	public List<DocumentInfoForm> getDocumentList(User user, String employeeNumber, String publishType, Integer maxSize) throws InvalidArgumentException {

		if (!user.getEmployeeNumber().equals(employeeNumber)) {
			System.out.println(user.getEmployeeNumber() + " " + employeeNumber);
			throw new InvalidArgumentException("employeeNumber", employeeNumber, "他ユーザのすべてのドキュメント一覧は取得することができません");
		}

		List<DocumentInfo> documentInfoList = new ArrayList<>();

		if (publishType.equals("all")) { // ユーザのすべてのドキュメントを取得する場合
			documentInfoList = documentInfoRepository.findByEmployeeNumberOrderByDocumentCreateDateDesc(employeeNumber);
		} else if (publishType.equals("public")) { // 公開タイプが指定されている場合
			documentInfoList = documentInfoRepository.findByEmployeeNumberAndIsDocumentPublishOrderByDocumentCreateDateDesc(
					employeeNumber, true);
		} else if (publishType.equals("private")) { // 公開タイプが指定されている場合
			documentInfoList = documentInfoRepository.findByEmployeeNumberAndIsDocumentPublishOrderByDocumentCreateDateDesc(
					employeeNumber, false);
		}

		//idのリストを取得
		List<String> documentIdList = new ArrayList<>();

		for (DocumentInfo docInfo : documentInfoList) {
			documentIdList.add(docInfo.getDocumentId());
		}

		//contentOther(documentTitle)の取得
		List<ContentFrame> contentFrame = new ArrayList<>();

		for (int i = 0; i < documentIdList.size(); i++) {
			contentFrame.addAll(contentFrameRepository.findByDocumentIdAndContentOrderAndLineNumber(documentIdList.get(i), 1, 1));
		}

		List<String> contentIdList = new ArrayList<>();

		for (ContentFrame contentframe : contentFrame) {
			contentIdList.add(contentframe.getContentId());
		}

		for (String string : contentIdList) {
			System.out.println(string);
		}

		List<ContentOther> contentOther = new ArrayList<>();

		for (int i = 0; i < contentIdList.size(); i++) {
			contentOther.addAll(contentOtherRepository.findByContentId(contentIdList.get(i)));
		}

		List<String> contentMainList = new ArrayList<>();

		for (ContentOther contentother : contentOther) {
			contentMainList.add(contentother.getContentMain());
		}

		//documentInfoListとcontentMainListの値をFormに格納
		List<DocumentInfoForm> documentInfo = new ArrayList<>();

		for(int i = 0; i < contentMainList.size(); i++) {
			documentInfo.add(new DocumentInfoForm(documentInfoList.get(i), contentMainList.get(i)));
		}

		if (maxSize != -1 && documentInfo.size() > maxSize) {
			documentInfo = documentInfo.subList(0, maxSize);
		}

		return documentInfo;

	}
}
