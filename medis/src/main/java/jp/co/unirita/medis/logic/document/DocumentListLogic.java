package jp.co.unirita.medis.logic.document;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfoRepository;
import jp.co.unirita.medis.domain.user.UserRepository;

@Service
@Transactional
public class DocumentListLogic {

	@Autowired
	DocumentInfoRepository documentInfoRepository;

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
}