package jp.co.unirita.medis.logic.setting;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfoRepository;
import jp.co.unirita.medis.domain.documenttag.DocumentTag;
import jp.co.unirita.medis.domain.documenttag.DocumentTagRepository;
import jp.co.unirita.medis.domain.notificationconfig.NotificationConfig;
import jp.co.unirita.medis.domain.notificationconfig.NotificationConfigRepository;
import jp.co.unirita.medis.domain.templatetag.TemplateTag;
import jp.co.unirita.medis.domain.templatetag.TemplateTagRepository;
import jp.co.unirita.medis.domain.updateinfo.UpdateInfo;
import jp.co.unirita.medis.domain.updateinfo.UpdateInfoRepository;

@Service
@Transactional
public class MonitoringLogic {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private static final String TYPE_CREATE_DOCUMENT = "v0000000000";
	private static final String TYPE_UPDATE_DOCUMENT = "v0000000001";

	@Autowired
	NotificationConfigRepository notificationConfigRepository;
	@Autowired
	DocumentTagRepository documentTagRepository;
	@Autowired
	TemplateTagRepository templateTagRepository;
	@Autowired
	DocumentInfoRepository documentInfoRepository;
	@Autowired
	UpdateInfoRepository updateInfoRepository;


	public List<DocumentInfo> getMonitoringList(String employeeNumber) {
		//userが監視しているタグの一覧
		List<NotificationConfig> notificationConfig = notificationConfigRepository.findByEmployeeNumber(employeeNumber);
		List<String> tagIdList = new ArrayList<>();

		for (NotificationConfig tag : notificationConfig) {
			tagIdList.add(tag.getTagId());
		}

		//文書についているタグが付いている文書の一覧
		List<DocumentTag> documentTag = new ArrayList<>();

		for (String ids : tagIdList) {
			documentTag.addAll(documentTagRepository.findByTagId(ids));
		}
		List<String> documentList = new ArrayList<>();

		for (DocumentTag docId : documentTag) {
			documentList.add(docId.getDocumentId());
		}

		//テンプレートについているタグが付いている文書の一覧
		List<TemplateTag> templateTag = new ArrayList<>();

		for (String ids : tagIdList) {
			templateTag.addAll(templateTagRepository.findByTagId(ids));
		}

		List<String> templateList = new ArrayList<>();

		for (TemplateTag tempId : templateTag) {
			templateList.add(tempId.getTemplateId());
		}

		//documentListのidのdocument_info一覧
		List<DocumentInfo> docDocInfo = new ArrayList<>();

		for (String docs : documentList) {
			docDocInfo.addAll(documentInfoRepository.findByDocumentId(docs));
		}

		List<String> docDocInfoId = new ArrayList<>();

		for (DocumentInfo info : docDocInfo) {
			docDocInfoId.add(info.getDocumentId());
		}

		//templateListのidが付いているdocument_info一覧
		List<DocumentInfo> tempDocInfo = new ArrayList<>();

		for (int i = 0; i < templateList.size(); i++) {
			tempDocInfo.addAll(documentInfoRepository.findByTemplateId(templateList.get(i)));
		}

		List<String> tempDocInfoId = new ArrayList<>();

		for (DocumentInfo Info : tempDocInfo) {
			tempDocInfoId.add(Info.getDocumentId());
		}

		//documentIdのマージ(値は重複している)
		List<String> documentIdListBeforeMap = Stream.concat(docDocInfoId.stream(), tempDocInfoId.stream()).collect(Collectors.toList());

		//値を一意に
		Set<String> set = new HashSet<>(documentIdListBeforeMap);
		List<String> documentIdList = new ArrayList<>(set);

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

		documentInfoList.sort(Comparator.comparing(DocumentInfo::getDocumentCreateDate).reversed());

		return documentInfoList;
	}
}
