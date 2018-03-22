package jp.co.unirita.medis.logic.util;

import jp.co.unirita.medis.controller.TemplateController;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfoRepository;
import jp.co.unirita.medis.domain.documenttag.DocumentTag;
import jp.co.unirita.medis.domain.documenttag.DocumentTagRepository;
import jp.co.unirita.medis.domain.tag.Tag;
import jp.co.unirita.medis.domain.tag.TagRepository;
import jp.co.unirita.medis.domain.templatetag.TemplateTag;
import jp.co.unirita.medis.domain.templatetag.TemplateTagRepository;
import jp.co.unirita.medis.domain.userdetail.UserDetailRepository;
import jp.co.unirita.medis.form.document.DocumentInfoForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class SearchLogic {

	private static final Logger logger = LoggerFactory.getLogger(TemplateController.class);

	@Autowired
	TagRepository tagRepository;
	@Autowired
	DocumentTagRepository documentTagRepository;
	@Autowired
	TemplateTagRepository templateTagRepository;
	@Autowired
	DocumentInfoRepository documentInfoRepository;
	@Autowired
	UserDetailRepository userDetailRepository;

	public List<DocumentInfoForm> findDocuments(List<String> tagNameList) {
	    List<String> tagIdList = tagRepository.findByTagNameIn(tagNameList).stream()
                .map(Tag::getTagId)
                .collect(Collectors.toList());
	    List<String> templateIdList = templateTagRepository.findByTagIdIn(tagIdList).stream()
                .map(TemplateTag::getTemplateId)
                .collect(Collectors.toList());
	    List<String> documentIdList = documentTagRepository.findByTagIdIn(tagIdList).stream()
                .map(DocumentTag::getDocumentId)
                .collect(Collectors.toList());
	    List<DocumentInfo> documentInfoList1 = documentInfoRepository.findByTemplateIdIn(templateIdList);
	    List<DocumentInfo> documentInfoList2 = documentInfoRepository.findByDocumentIdIn(documentIdList);

		List<DocumentInfoForm> result = Stream.concat(documentInfoList1.stream(), documentInfoList2.stream()).distinct()
				.filter(info -> info.isDocumentPublish())
				.map(info -> new DocumentInfoForm(info, userDetailRepository.findOne(info.getEmployeeNumber())))
				.sorted(Comparator.comparing(DocumentInfoForm::getDocumentCreateDate).reversed())
				.collect(Collectors.toList());
		logger.info("[method: findDocuments] find by: " + tagNameList);
		logger.info("[method: findDocuments] result: " + result.size());
		return result;
    }
}
