package jp.co.unirita.medis.logic.setting;

import java.lang.invoke.MethodHandles;
import java.util.List;
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
import jp.co.unirita.medis.form.InfomationForm;

@Service
@Transactional
public class InfomationLogic {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final static String TYPE_WRITE_COMMENT = "v0000000002";
    private final static String TYPE_READ_COMMENT  = "v0000000003";

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

    public List<InfomationForm> getAllInfomationList(String employeeNumber) {
        // 監視しているタグの一覧を取得
        List<String> tagIds = notificationConfigRepository.findByEmployeeNumber(employeeNumber)
                .stream().map(NotificationConfig::getTagId).collect(Collectors.toList());

        // タグが付与されている各文書の情報を取得
        List<String> documentIds = documentTagRepository.findByTagIdIn(tagIds)
                .stream().map(DocumentTag::getDocumentId).collect(Collectors.toList());
        List<String> templateIds = templateTagRepository.findByTagIdIn(tagIds)
                .stream().map(TemplateTag::getTagId).collect(Collectors.toList());
        List<String> docInfos = documentInfoRepository.findByDocumentIdIn(documentIds)
                .stream().map(DocumentInfo::getDocumentId).collect(Collectors.toList());
        List<String> tmpInfos = documentInfoRepository.findByTemplateIdIn(templateIds)
                .stream().map(DocumentInfo::getDocumentId).collect(Collectors.toList());
        List<String> mydocIds = documentInfoRepository.findByEmployeeNumber(employeeNumber)
                .stream().map(DocumentInfo::getDocumentId).collect(Collectors.toList());

        // 結合して更新情報フォームに変換
        List<String> ids = Stream.concat(Stream.concat(docInfos.stream(), tmpInfos.stream()), mydocIds.stream())
                .distinct().collect(Collectors.toList());
        List<UpdateInfo> updateInfos = updateInfoRepository.findByDocumentIdIn(ids);
        List<InfomationForm> list = updateInfos.stream()
                .filter(info -> info.getUpdateType().equals(TYPE_WRITE_COMMENT) || info.getUpdateType().equals(TYPE_READ_COMMENT))
                .map(info -> new InfomationForm(info, documentInfoRepository.findOne(info.getDocumentId()).getDocumentName()))
                .sorted((form1, form2) -> form2.getUpdateId().compareTo(form1.getDocumentId()))
                .collect(Collectors.toList());
        return list;
    }

    public List<InfomationForm> getInfomationList(String employeeNumber, String lastUpadteId) {
        List<InfomationForm> list = getAllInfomationList(employeeNumber).stream()
                .filter(info -> info.getUpdateId().compareTo(lastUpadteId) == 1)
                .collect(Collectors.toList());
        return list;
    }

/*    public List<InfomationForm> getInfomationList(String employeeNumber, String updateId, Integer maxSize) {
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
		List<TemplateTag> templateTag = new ArrayList<>();

		for (int i = 0; i < tagIdList.size(); i++) {
			templateTag.addAll(templateTagRepository.findByTagId(tagIdList.get(i)));
		}

		List<String> templateList = new ArrayList<>();

		for (TemplateTag tempId : templateTag) {
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
		List<DocumentInfo> documentInfo = documentInfoRepository.findByEmployeeNumber(employeeNumber);
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
				updateInfo.addAll(updateInfoRepository.findByDocumentIdAndUpdateTypeBetween(documentIdList.get(i), "v0000000002", "v0000000003"));
			}
		} else {
			for (int i = 0; i < documentIdList.size(); i++) {
				updateInfo.addAll(updateInfoRepository.findByDocumentIdAndUpdateTypeBetweenAndUpdateIdGreaterThan(documentIdList.get(i), "v0000000002", "v0000000003" , updateId));
			}
		}

		//updateInfoで取得した中のdocumentIdの一覧を取得
		List<String> lastDocumentIdList = new ArrayList<>();

		for (UpdateInfo updateinfo : updateInfo) {
			lastDocumentIdList.add(updateinfo.getDocumentId());
		}

		//documentNameの取得
		List<DocumentInfo> lastDocumentInfo = new ArrayList<>();

		for (int i = 0; i < lastDocumentIdList.size(); i++) {
			lastDocumentInfo.addAll(documentInfoRepository.findByDocumentId(lastDocumentIdList.get(i)));
		}

		List<String> documentNameList = new ArrayList<>();

		for (DocumentInfo lastDocInfo : lastDocumentInfo) {
			documentNameList.add(lastDocInfo.getDocumentName());
		}


		//updateInfoとcontentMainListの値をInfomationFormに格納
		List<InfomationForm> infomation = new ArrayList<>();

		for(int i = 0; i < documentNameList.size(); i++) {
			infomation.add(new InfomationForm(updateInfo.get(i), documentNameList.get(i)));
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
*/
}