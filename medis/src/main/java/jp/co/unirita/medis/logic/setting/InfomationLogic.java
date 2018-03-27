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
import jp.co.unirita.medis.domain.documenttag.DocumentTagRepository;
import jp.co.unirita.medis.domain.notificationconfig.NotificationConfigRepository;
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
            List<String> mydocIds = documentInfoRepository.findByEmployeeNumber(employeeNumber)
                    .stream().map(DocumentInfo::getDocumentId).collect(Collectors.toList());

            // 結合して更新情報フォームに変換
            /*
            List<String> ids = Stream.concat(Stream.concat(docInfos.stream(), tmpInfos.stream()), mydocIds.stream())
                    .distinct().collect(Collectors.toList());
                    */
            List<UpdateInfo> writeUpdateInfos = updateInfoRepository.findByDocumentIdIn(mydocIds);
            List<InfomationForm> writeList = writeUpdateInfos.stream()
            		.filter(info -> info.getUpdateType().equals(TYPE_WRITE_COMMENT))
            		.map(info -> new InfomationForm(info, documentInfoRepository.findOne(info.getDocumentId()).getDocumentName()))
            		.collect(Collectors.toList());
            List<UpdateInfo> readUpdateInfos = updateInfoRepository.findByEmployeeNumber(employeeNumber);
            List<InfomationForm> readList = readUpdateInfos.stream()
            		.filter(info -> info.getUpdateType().equals(TYPE_READ_COMMENT))
            		.map(info -> new InfomationForm(info, documentInfoRepository.findOne(info.getDocumentId()).getDocumentName()))
            		.collect(Collectors.toList());
            List<InfomationForm> list = Stream.concat(writeList.stream(), readList.stream())
                    .sorted(Comparator.comparing(InfomationForm::getUpdateId).reversed())
                    .collect(Collectors.toList());
            return list;
    	} catch (DBException e) {
    		logger.error("DB Runtime Error[class: infomationLogic, method: getAllInfomationList]");
			throw new DBException("DB Runtime Error[class: infomationLogic, method: getAllInfomationList]");
		}
    }

    public List<InfomationForm> getInfomationList(String employeeNumber, String lastUpadteId) {
        List<InfomationForm> list = getAllInfomationList(employeeNumber).stream()
                .filter(info -> info.getUpdateId().compareTo(lastUpadteId) >= 1)
                .collect(Collectors.toList());
        return list;
    }
}