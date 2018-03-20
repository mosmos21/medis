package jp.co.unirita.medis.logic.setting;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
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
import jp.co.unirita.medis.domain.updateinfo.UpdateInfoRepository;
import jp.co.unirita.medis.domain.userdetail.UserDetail;
import jp.co.unirita.medis.domain.userdetail.UserDetailRepository;
import jp.co.unirita.medis.form.document.DocumentInfoForm;
import jp.co.unirita.medis.util.exception.DBException;

@Service
@Transactional(rollbackFor = Exception.class)
public class MonitoringLogic {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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
	@Autowired
	UserDetailRepository userDetailRepository;


	public List<DocumentInfoForm> getMonitoringList(String employeeNumber) {
		try {
			List<String> tagIdList = notificationConfigRepository.findByEmployeeNumber(employeeNumber).stream()
	                .map(NotificationConfig::getTagId)
	                .collect(Collectors.toList());
			List<String> templateIdList = templateTagRepository.findByTagIdIn(tagIdList).stream()
	                .map(TemplateTag::getTemplateId)
	                .collect(Collectors.toList());
		    List<String> documentIdList = documentTagRepository.findByTagIdIn(tagIdList).stream()
	                .map(DocumentTag::getDocumentId)
	                .collect(Collectors.toList());
		    List<DocumentInfo> documentInfoList1 = documentInfoRepository.findByTemplateIdIn(templateIdList);
		    List<DocumentInfo> documentInfoList2 = documentInfoRepository.findByDocumentIdIn(documentIdList);
		    List<DocumentInfo> documentInfoList = Stream.concat(documentInfoList1.stream(), documentInfoList2.stream())
		    		.distinct()
		    		.sorted(Comparator.comparing(DocumentInfo::getDocumentCreateDate).reversed())
		    		.collect(Collectors.toList());
		    List<UserDetail> userDetail = new ArrayList<>();
		    for (DocumentInfo docInfo : documentInfoList) {
				userDetail.add(userDetailRepository.findOne(docInfo.getEmployeeNumber()));
			}
		    List<DocumentInfoForm> form = new ArrayList<>();
			for (int i = 0; i < documentInfoList.size(); i++) {
				form.add(new DocumentInfoForm(documentInfoList.get(i), userDetail.get(i)));
			}
			return form;
		} catch (DBException e) {
			throw new DBException("Internal Server Error");
		}
	}
}
