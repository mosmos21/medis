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

import jp.co.unirita.medis.domain.contentflame.ContentFlame;
import jp.co.unirita.medis.domain.contentflame.ContentFlameRepository;
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
	ContentFlameRepository contentFlameRepository;
	@Autowired
	ContentOtherRepository contentOtherRepository;



	public List<InfomationForm> getInfomationList(String employeeNumber, String updateId, Integer maxSize) {
		//userが監視しているタグの一覧
		List<NotificationConfig> notificationConfig = notificationConfigRepository.findByEmployeeNumber(employeeNumber);
		List<String> tagList = new ArrayList<>();

		for (NotificationConfig tag : notificationConfig) {
			tagList.add(tag.getTagId());
		}

		//文書についているタグが付いている文書の一覧
		List<DocumentTag> documentTagTemp = new ArrayList<>();
		List<DocumentTag> documentTag = new ArrayList<>();

//		List<DocumentTag> documentTag = documentTagRepository.findByTagId(tagList);
		for (int i = 0; i < tagList.size(); i++) {
			documentTagTemp = documentTagRepository.findByTagId(tagList.get(i));
			documentTag = Stream.concat(documentTag.stream(), documentTagTemp.stream()).collect(Collectors.toList());
			documentTagTemp = null;
		}
		List<String> documentList = new ArrayList<>();

		for (DocumentTag docId : documentTag) {
			documentList.add(docId.getDocumentId());
		}

		//テンプレートについているタグが付いている文書の一覧
		List<FixedTag> fixedTagTemp = new ArrayList<>();
		List<FixedTag> fixedTag = new ArrayList<>();

//		List<FixedTag> fixedTag = fixedTagRepository.findByTagId(tagList);
		for (int i = 0; i < tagList.size(); i++) {
			fixedTagTemp = fixedTagRepository.findByTagId(tagList.get(i));
			fixedTag = Stream.concat(fixedTag.stream(), fixedTagTemp.stream()).collect(Collectors.toList());
			fixedTagTemp = null;
		}

		List<String> templateList = new ArrayList<>();

		for (FixedTag tempId : fixedTag) {
			templateList.add(tempId.getTemplateId());
		}

		//documentListのidのdocument_info一覧
		List<DocumentInfo> docDocInfoTemp = new ArrayList<>();
		List<DocumentInfo> docDocInfo = new ArrayList<>();

//  	List<DocumentInfo> docDocInfo = documentInfoRepository.findByDocumentId(documentList);
		for (int i = 0; i < documentList.size(); i++) {
			docDocInfoTemp = documentInfoRepository.findByDocumentId(documentList.get(i));
			docDocInfo = Stream.concat(docDocInfo.stream(), docDocInfoTemp.stream()).collect(Collectors.toList());
			docDocInfoTemp = null;
		}

		List<String> docDocInfoId = new ArrayList<>();

		for (DocumentInfo diid : docDocInfo) {
			docDocInfoId.add(diid.getDocumentId());
		}

		//templateListのidが付いているdocument_info一覧
		List<DocumentInfo> tempDocInfoTemp = new ArrayList<>();
		List<DocumentInfo> tempDocInfo = new ArrayList<>();

//  	List<DocumentInfo> tempDocInfo = documentInfoRepository.findByTemplateId(templateList);
		for (int i = 0; i < templateList.size(); i++) {
			tempDocInfoTemp = documentInfoRepository.findByTemplateId(templateList.get(i));
			tempDocInfo = Stream.concat(tempDocInfo.stream(), tempDocInfoTemp.stream()).collect(Collectors.toList());
			tempDocInfoTemp = null;
		}

		List<String> tempDocInfoId = new ArrayList<>();

		for (DocumentInfo tiid : tempDocInfo) {
			tempDocInfoId.add(tiid.getDocumentId());
		}

		//自分の文書のdocumentId一覧取得
		List<DocumentInfo> documentInfo = documentInfoRepository.findByEmployeeNumberOrderByDocumentCreateDateAsc(employeeNumber);
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
		List<UpdateInfo> updateInfoTemp = new ArrayList<>();
		List<UpdateInfo> updateInfo = new ArrayList<>();

//  	List<UpdateInfo> updateInfo = updateInfoRepository.findByDocumentIdAndUpdateIdGreaterThan(documentIdList, updateId);
		if (updateId == null) {
			for (int i = 0; i < documentIdList.size(); i++) {
				updateInfoTemp = updateInfoRepository.findByDocumentId(documentIdList.get(i));
				updateInfo = Stream.concat(updateInfo.stream(), updateInfoTemp.stream()).collect(Collectors.toList());
				updateInfoTemp = null;
			}
		} else {
			for (int i = 0; i < documentIdList.size(); i++) {
				updateInfoTemp = updateInfoRepository.findByDocumentIdAndUpdateIdGreaterThan(documentIdList.get(i), updateId);
				updateInfo = Stream.concat(updateInfo.stream(), updateInfoTemp.stream()).collect(Collectors.toList());
				updateInfoTemp = null;
			}
		}


		//updateInfoで取得した中のdocumentIdの一覧を取得
		List<String> lastDocumentIdList = new ArrayList<>();

		for (UpdateInfo updateinfo : updateInfo) {
			lastDocumentIdList.add(updateinfo.getDocumentId());
		}

		//contentOther(documentTitle)の取得
		List<ContentFlame> contentFlameTemp = new ArrayList<>();
		List<ContentFlame> contentFlame = new ArrayList<>();

// 		List<ContentFlame> contentFlame = contentFlameRepository.findByDocumentIdAndContentOrderAndLineNumber(lastDocumentIdList, 1, 1);
		for (int i = 0; i < lastDocumentIdList.size(); i++) {
			contentFlameTemp = contentFlameRepository.findByDocumentIdAndContentOrderAndLineNumber(lastDocumentIdList.get(i), 1, 1);
			contentFlame = Stream.concat(contentFlame.stream(), contentFlameTemp.stream()).collect(Collectors.toList());
			contentFlameTemp = null;
		}

		List<String> contentIdList = new ArrayList<>();

		for (ContentFlame contentflame : contentFlame) {
			contentIdList.add(contentflame.getContentId());
		}

		List<ContentOther> contentOtherTemp = new ArrayList<>();
		List<ContentOther> contentOther = new ArrayList<>();

//		List<ContentOther> contentOther = contentOtherRepository.findByContentId(contentIdList);
		for (int i = 0; i < contentIdList.size(); i++) {
			contentOtherTemp = contentOtherRepository.findByContentId(contentIdList.get(i));
			contentOther = Stream.concat(contentOther.stream(), contentOtherTemp.stream()).collect(Collectors.toList());
			contentOtherTemp = null;
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

		if (maxSize != -1) {
			infomation = infomation.subList(0, maxSize);
		}
		return infomation;
	}
}