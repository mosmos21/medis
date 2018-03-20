package jp.co.unirita.medis.logic.setting;

import java.lang.invoke.MethodHandles;
import java.util.Comparator;
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
import jp.co.unirita.medis.form.setting.InfomationForm;
import jp.co.unirita.medis.util.exception.DBException;

@Service
@Transactional(rollbackFor = Exception.class)
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
    	try {
    		// 監視しているタグの一覧を取得
            List<String> tagIds = notificationConfigRepository.findByEmployeeNumber(employeeNumber)
                    .stream().map(NotificationConfig::getTagId).collect(Collectors.toList());

            // タグが付与されている各文書の情報を取得
            List<String> documentIds = documentTagRepository.findByTagIdIn(tagIds)
                    .stream().map(DocumentTag::getDocumentId).collect(Collectors.toList());
            List<String> templateIds = templateTagRepository.findByTagIdIn(tagIds)
                    .stream().map(TemplateTag::getTemplateId).collect(Collectors.toList());
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
                    .sorted(Comparator.comparing(InfomationForm::getUpdateId).reversed())
                    .collect(Collectors.toList());
            return list;
    	} catch (DBException e) {
			throw new DBException("DB Runtime Error[method: getAllInfomationList]");
		}
    }

    public List<InfomationForm> getInfomationList(String employeeNumber, String lastUpadteId) {
        List<InfomationForm> list = getAllInfomationList(employeeNumber).stream()
                .filter(info -> info.getUpdateId().compareTo(lastUpadteId) == 1)
                .collect(Collectors.toList());
        return list;
    }
}