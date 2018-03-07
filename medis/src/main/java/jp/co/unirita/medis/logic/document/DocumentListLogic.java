package jp.co.unirita.medis.logic.document;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfoRepository;
import jp.co.unirita.medis.domain.updateinfo.UpdateInfo;
import jp.co.unirita.medis.domain.updateinfo.UpdateInfoRepository;
import jp.co.unirita.medis.form.document.DocumentInfoForm;

@Service
@Transactional
public class DocumentListLogic {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private static final String TYPE_CREATE_DOCUMENT = "v0000000000";
	private static final String TYPE_UPDATE_DOCUMENT = "v0000000001";

	@Autowired
	DocumentInfoRepository documentInfoRepository;
	@Autowired
	UpdateInfoRepository updateInfoRepository;


	public List<DocumentInfoForm> getDocumentList(String employeeNumber, String publishType) {

		List<DocumentInfo> documentInfo = new ArrayList<>();

		if (publishType == null) {
			documentInfo = documentInfoRepository.findByEmployeeNumber(employeeNumber);
	    } else {
	    	documentInfo = documentInfoRepository.findByEmployeeNumberAndDocumentPublish(employeeNumber, publishType.equals("public"));
	    }

		//ユーザの作成した文書のdocumentId一覧の取得
		List<String> documentIdList = new ArrayList<>();

		for (DocumentInfo docList : documentInfo) {
			documentIdList.add(docList.getDocumentId());
		}

		//各documentIdごとの最新のupdateIdをもったupdate_infoのリストの取得
		List<UpdateInfo> updateInfoList = new ArrayList<>();

		for (String ids : documentIdList) {
			updateInfoList.add(updateInfoRepository
					.findFirstByDocumentIdAndUpdateTypeBetweenOrderByUpdateIdDesc(ids, TYPE_CREATE_DOCUMENT, TYPE_UPDATE_DOCUMENT));
		}

		//updateInfoListのdocumentIdの一覧の取得
		List<String> updateDocIdList = new ArrayList<>();

		for (UpdateInfo upDocId : updateInfoList) {
			updateDocIdList.add(upDocId.getDocumentId());
		}

		//updateDocIdListのdocumentInfoの取得
		List<DocumentInfo> documentInfoList = new ArrayList<>();

		for (String ids : updateDocIdList) {
			documentInfoList.addAll(documentInfoRepository.findByDocumentId(ids));
		}

		//documentInfoListとupdateInfoListの値をDocumentInfoFormに格納
		List<DocumentInfoForm> documentInfoForm = new ArrayList<>();

		for(int i = 0; i < documentInfoList.size(); i++) {
			documentInfoForm.add(new DocumentInfoForm(documentInfoList.get(i), updateInfoList.get(i)));
		}

		documentInfoForm.sort(Comparator.comparing(DocumentInfoForm::getUpdateDate).reversed());

		return documentInfoForm;
	}
}