package jp.co.unirita.medis.logic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.unirita.medis.domain.contentframe.ContentFrame;
import jp.co.unirita.medis.domain.contentframe.ContentFrameRepository;
import jp.co.unirita.medis.domain.contentother.ContentOther;
import jp.co.unirita.medis.domain.contentother.ContentOtherRepository;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfoRepository;
import jp.co.unirita.medis.domain.documenttag.DocumentTag;
import jp.co.unirita.medis.domain.documenttag.DocumentTagRepository;
import jp.co.unirita.medis.domain.fixedtag.FixedTag;
import jp.co.unirita.medis.domain.fixedtag.FixedTagRepository;
import jp.co.unirita.medis.domain.notificationconfig.NotificationConfig;
import jp.co.unirita.medis.domain.notificationconfig.NotificationConfigRepository;
import jp.co.unirita.medis.domain.updateinfo.UpdateInfo;
import jp.co.unirita.medis.domain.updateinfo.UpdateInfoRepository;
import jp.co.unirita.medis.form.InfomationForm;

@Service
@Transactional
public class InfomationLogic {

	@Autowired
	NotificationConfigRepository notificationConfigRepository;
	@Autowired
	DocumentTagRepository documentTagRepository;
	@Autowired
	FixedTagRepository fixedTagRepository;
	@Autowired
	DocumentInfoRepository documentInfoRepository;
	@Autowired
	UpdateInfoRepository updateInfoRepository;
	@Autowired
	ContentFrameRepository contentFrameRepository;
	@Autowired
	ContentOtherRepository contentOtherRepository;



	public List<InfomationForm> getInfomationList(String employeeNumber, String updateId, Integer maxSize) {
		//userが監視しているタグの一覧
		List<NotificationConfig> notificationConfig = notificationConfigRepository.findByEmployeeNumber(employeeNumber);
		List<String> tagIdList = new ArrayList<>();

		for (NotificationConfig tag : notificationConfig) {
			tagIdList.add(tag.getTagId());
		}

		//文書についているタグが付いている文書の一覧
		List<DocumentTag> documentTag = new ArrayList<>();

		for (int i = 0; i < tagIdList.size(); i++) {
			documentTag.addAll(documentTagRepository.findByTagId(tagIdList.get(i)));
		}
		List<String> documentList = new ArrayList<>();

		for (DocumentTag docId : documentTag) {
			documentList.add(docId.getDocumentId());
		}

		//テンプレートについているタグが付いている文書の一覧
		List<FixedTag> fixedTag = new ArrayList<>();

		for (int i = 0; i < tagIdList.size(); i++) {
			fixedTag.addAll(fixedTagRepository.findByTagId(tagIdList.get(i)));
		}

		List<String> templateList = new ArrayList<>();

		for (FixedTag tempId : fixedTag) {
			templateList.add(tempId.getTemplateId());
		}

		//documentListのidのdocument_info一覧
		List<DocumentInfo> docDocInfo = new ArrayList<>();

		for (int i = 0; i < documentList.size(); i++) {
			docDocInfo.addAll(documentInfoRepository.findByDocumentId(documentList.get(i)));
		}

		List<String> docDocInfoId = new ArrayList<>();

		for (DocumentInfo diid : docDocInfo) {
			docDocInfoId.add(diid.getDocumentId());
		}

		//templateListのidが付いているdocument_info一覧
		List<DocumentInfo> tempDocInfo = new ArrayList<>();

		for (int i = 0; i < templateList.size(); i++) {
			tempDocInfo.addAll(documentInfoRepository.findByTemplateId(templateList.get(i)));
		}

		List<String> tempDocInfoId = new ArrayList<>();

		for (DocumentInfo tiid : tempDocInfo) {
			tempDocInfoId.add(tiid.getDocumentId());
		}

		//自分の文書のdocumentId一覧取得
		List<DocumentInfo> documentInfo = documentInfoRepository.findByEmployeeNumberOrderByDocumentCreateDateDesc(employeeNumber);
		List<String> myDocInfoId = new ArrayList<>();

		for (DocumentInfo miid : documentInfo) {
			myDocInfoId.add(miid.getDocumentId());
		}

		//documentIdのマージ(値は重複している)
		List<String> documentIdTempListBeforeMap = Stream.concat(docDocInfoId.stream(), tempDocInfoId.stream()).collect(Collectors.toList());
		List<String> documentIdListBeforeMap = Stream.concat(documentIdTempListBeforeMap.stream(), myDocInfoId.stream()).collect(Collectors.toList());

		//値を一意に
		Set<String> set = new HashSet<>(documentIdListBeforeMap);
		List<String> documentIdList = new ArrayList<>(set);

		//update_infoの値取得
		List<UpdateInfo> updateInfo = new ArrayList<>();

		if (updateId == null) {
			for (int i = 0; i < documentIdList.size(); i++) {
				updateInfo.addAll(updateInfoRepository.findByDocumentId(documentIdList.get(i)));
			}
		} else {
			for (int i = 0; i < documentIdList.size(); i++) {
				updateInfo.addAll(updateInfoRepository.findByDocumentIdAndUpdateIdGreaterThan(documentIdList.get(i), updateId));
			}
		}


		//updateInfoで取得した中のdocumentIdの一覧を取得
		List<String> lastDocumentIdList = new ArrayList<>();

		for (UpdateInfo updateinfo : updateInfo) {
			lastDocumentIdList.add(updateinfo.getDocumentId());
		}

		//contentOther(documentTitle)の取得
		List<ContentFrame> contentFrame = new ArrayList<>();

		for (int i = 0; i < lastDocumentIdList.size(); i++) {
			contentFrame.addAll(contentFrameRepository.findByDocumentIdAndContentOrderAndLineNumber(lastDocumentIdList.get(i), 1, 1));
		}

		List<String> contentIdList = new ArrayList<>();

		for (ContentFrame contentframe : contentFrame) {
			contentIdList.add(contentframe.getContentId());
		}

		List<ContentOther> contentOther = new ArrayList<>();

		for (int i = 0; i < contentIdList.size(); i++) {
			contentOther.addAll(contentOtherRepository.findByContentId(contentIdList.get(i)));
		}

		List<String> contentMainList = new ArrayList<>();

		for (ContentOther contentother : contentOther) {
			contentMainList.add(contentother.getContentMain());
		}

		//updateInfoとcontentMainListの値をInfomationFormに格納
		List<InfomationForm> infomation = new ArrayList<>();

		for(int i = 0; i < contentMainList.size(); i++) {
			infomation.add(new InfomationForm(updateInfo.get(i), contentMainList.get(i)));
		}

		infomation.sort(new Comparator<InfomationForm>(){
			@Override
			public int compare(InfomationForm i1, InfomationForm i2) {
				return i2.getUpdateId().compareTo(i1.getUpdateId());
			}
		});

		if (maxSize != -1 && infomation.size() > maxSize) {
			infomation = infomation.subList(0, maxSize);
		}

		return infomation;
	}
}