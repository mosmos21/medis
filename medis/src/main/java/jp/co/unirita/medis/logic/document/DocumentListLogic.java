package jp.co.unirita.medis.logic.document;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfoRepository;
import jp.co.unirita.medis.domain.updateinfo.UpdateInfo;
import jp.co.unirita.medis.domain.updateinfo.UpdateInfoRepository;
import jp.co.unirita.medis.form.DocumentInfoForm;

@Service
@Transactional
public class DocumentListLogic {

	@Autowired
	DocumentInfoRepository documentInfoRepository;
	@Autowired
	UpdateInfoRepository updateInfoRepository;


	public List<DocumentInfoForm> getDocumentList(String employeeNumber, String publishType, Integer maxSize) {

		List<DocumentInfo> documentInfo = new ArrayList<>();

		if(publishType == null) {
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

		for (int i = 0; i < documentIdList.size(); i++) {
			updateInfoList.addAll(updateInfoRepository.findFirst1ByDocumentIdAndUpdateTypeBetweenOrderByUpdateIdDesc(documentIdList.get(i), "v0000000000", "v0000000001"));
		}

		//updateInfoListのdocumentIdの一覧の取得
		List<String> updateDocIdList = new ArrayList<>();

		for (UpdateInfo upDocId : updateInfoList) {
			updateDocIdList.add(upDocId.getDocumentId());
		}

		//updateDocIdListのdocumentInfoの取得
		List<DocumentInfo> documentInfoList = new ArrayList<>();

		for (int i = 0; i < updateDocIdList.size(); i++) {
			documentInfoList.addAll(documentInfoRepository.findByDocumentId(updateDocIdList.get(i)));
		}

		//documentInfoListとupdateInfoListの値をDocumentInfoFormに格納
		List<DocumentInfoForm> documentInfoForm = new ArrayList<>();

		for(int i = 0; i < documentInfoList.size(); i++) {
			documentInfoForm.add(new DocumentInfoForm(documentInfoList.get(i), updateInfoList.get(i)));
		}

		documentInfoForm.sort(new Comparator<DocumentInfoForm>(){
			@Override
			public int compare(DocumentInfoForm i1, DocumentInfoForm i2) {
				return i2.getUpdateDate().compareTo(i1.getUpdateDate());
			}
		});

		if (maxSize != -1 && documentInfoForm.size() > maxSize) {
			documentInfoForm = documentInfoForm.subList(0, maxSize);
		}

		return documentInfoForm;

	}
/*
	public List<DocumentInfo> getAllDocumentInfoList() {
		return documentInfoRepository.findAll();
	}

	public List<DocumentInfo> getAllDocumentInfoList(String employeeNumber) {
		return documentInfoRepository.findByEmployeeNumber(employeeNumber);
	}

	public List<DocumentInfo> getDocumentInfoList(boolean publish) {
		return documentInfoRepository.findByDocumentPublish(publish);
	}

	public List<DocumentInfo> getDocumentInfoList(String emplouyeeNumber, boolean publish) {
		return documentInfoRepository.findByEmployeeNumberAndDocumentPublish(emplouyeeNumber, publish);
	}
*/
}