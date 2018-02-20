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
import jp.co.unirita.medis.form.MonitoringForm;

@Service
@Transactional
public class MonitoringLogic {

	@Autowired
	NotificationConfigRepository notificationConfigRepository;
	@Autowired
	DocumentTagRepository documentTagRepository;
	@Autowired
	FixedTagRepository fixedTagRepository;
	@Autowired
	DocumentInfoRepository documentInfoRepository;
	@Autowired
	ContentFlameRepository contentFlameRepository;
	@Autowired
	ContentOtherRepository contentOtherRepository;


	public List<MonitoringForm> getMonitoringList(String employeeNumber, Integer maxSize) {
		//userが監視しているタグの一覧
		List<NotificationConfig> notificationConfig = notificationConfigRepository.findByEmployeeNumber(employeeNumber);
		List<String> tagList = new ArrayList<>();

		for (NotificationConfig tag : notificationConfig) {
			tagList.add(tag.getTagId());
		}

		//文書についているタグが付いている文書の一覧
		List<DocumentTag> documentTag = new ArrayList<>();

		for (int i = 0; i < tagList.size(); i++) {
			documentTag.addAll(documentTagRepository.findByTagId(tagList.get(i)));
		}
		List<String> documentList = new ArrayList<>();

		for (DocumentTag docId : documentTag) {
			documentList.add(docId.getDocumentId());
		}

		//テンプレートについているタグが付いている文書の一覧
		List<FixedTag> fixedTag = new ArrayList<>();

		for (int i = 0; i < tagList.size(); i++) {
			fixedTag.addAll(fixedTagRepository.findByTagId(tagList.get(i)));
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

		//documentIdのマージ(値は重複している)
		List<String> documentIdListBeforeMap = Stream.concat(docDocInfoId.stream(), tempDocInfoId.stream()).collect(Collectors.toList());

		//値を一意に
		Set<String> set = new HashSet<>(documentIdListBeforeMap);
		List<String> documentIdList = new ArrayList<>(set);

		//documentIdListのdocumentInfoの取得
		List<DocumentInfo> documentInfoList = new ArrayList<>();

		for (int i = 0; i < documentIdList.size(); i++) {
			documentInfoList.addAll(documentInfoRepository.findByDocumentId(documentIdList.get(i)));
		}


		//contentOther(documentTitle)の取得
		List<ContentFlame> contentFlame = new ArrayList<>();

		for (int i = 0; i < documentIdList.size(); i++) {
			contentFlame.addAll(contentFlameRepository.findByDocumentIdAndContentOrderAndLineNumber(documentIdList.get(i), 1, 1));
		}

		List<String> contentIdList = new ArrayList<>();

		for (ContentFlame contentflame : contentFlame) {
			contentIdList.add(contentflame.getContentId());
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
		List<MonitoringForm> monitoring = new ArrayList<>();

		for(int i = 0; i < contentMainList.size(); i++) {
			monitoring.add(new MonitoringForm(documentInfoList.get(i), contentMainList.get(i)));
		}

		monitoring.sort(new Comparator<MonitoringForm>(){
			@Override
			public int compare(MonitoringForm i1, MonitoringForm i2) {
				return i2.getDocumentId().compareTo(i1.getDocumentId());
			}
		});

		if (maxSize != -1 && monitoring.size() > maxSize) {
			monitoring = monitoring.subList(0, maxSize);
		}

		return monitoring;
	}
}
