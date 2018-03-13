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
import jp.co.unirita.medis.domain.updateinfo.UpdateInfoRepository;
import jp.co.unirita.medis.domain.userdetail.UserDetail;
import jp.co.unirita.medis.domain.userdetail.UserDetailRepository;
import jp.co.unirita.medis.form.document.DocumentInfoForm;

@Service
@Transactional
public class DocumentListLogic {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	DocumentInfoRepository documentInfoRepository;
	@Autowired
	UpdateInfoRepository updateInfoRepository;
	@Autowired
	UserDetailRepository userDetailRepository;


	public List<DocumentInfoForm> getDocumentList(String employeeNumber, String publishType) {
		List<DocumentInfo> documentInfo = new ArrayList<>();
		if (publishType == null) {
			documentInfo = documentInfoRepository.findByEmployeeNumber(employeeNumber);
	    } else {
	    	documentInfo = documentInfoRepository.findByEmployeeNumberAndDocumentPublish(employeeNumber, publishType.equals("public"));
	    }

		List<UserDetail> userDetail = new ArrayList<>();
	    for (DocumentInfo docInfo : documentInfo) {
			userDetail.add(userDetailRepository.findOne(docInfo.getEmployeeNumber()));
		}
	    List<DocumentInfoForm> form = new ArrayList<>();
		for (int i = 0; i < documentInfo.size(); i++) {
			form.add(new DocumentInfoForm(documentInfo.get(i), userDetail.get(i)));
		}
		form.sort(Comparator.comparing(DocumentInfoForm::getDocumentCreateDate).reversed());

		return form;
	}
}