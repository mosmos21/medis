package jp.co.unirita.medis.logic.template;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jp.co.unirita.medis.domain.documentInfo.DocumentInfoRepository;
import jp.co.unirita.medis.logic.util.IdIssuanceLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.unirita.medis.domain.tag.Tag;
import jp.co.unirita.medis.domain.tag.TagRepository;
import jp.co.unirita.medis.domain.templatecontent.TemplateContent;
import jp.co.unirita.medis.domain.templatecontent.TemplateContentRepository;
import jp.co.unirita.medis.domain.templateinfo.TemplateInfo;
import jp.co.unirita.medis.domain.templateinfo.TemplateInfoRepository;
import jp.co.unirita.medis.domain.templateitem.TemplateItem;
import jp.co.unirita.medis.domain.templateitem.TemplateItemRepository;
import jp.co.unirita.medis.domain.templatetag.TemplateTag;
import jp.co.unirita.medis.domain.templatetag.TemplateTagRepository;
import jp.co.unirita.medis.form.template.TemplateContentForm;
import jp.co.unirita.medis.form.template.TemplateForm;
import jp.co.unirita.medis.logic.util.TagLogic;
import jp.co.unirita.medis.util.exception.DBException;
import jp.co.unirita.medis.util.exception.IdIssuanceUpperException;

@Service
@Transactional(rollbackFor = Exception.class)
public class TemplateLogic {

    private static final Logger logger = LoggerFactory.getLogger(TemplateLogic.class);

    @Autowired
    TemplateInfoRepository templateInfoRepository;
    @Autowired
    DocumentInfoRepository documentInfoRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    TemplateTagRepository templateTagRepository;
    @Autowired
    TemplateContentRepository templateContentRepository;
    @Autowired
    TemplateItemRepository templateItemRepository;
    @Autowired
    TagLogic tagLogic;
    @Autowired
    IdIssuanceLogic idIssuenceLogic;


    public TemplateForm getTemplate(String id) {
    	try {
    		TemplateForm template = new TemplateForm();
            template.setTemplateId(id);
            template.setTemplateName(templateInfoRepository.findOne(id).getTemplateName());
            template.setContents((ArrayList<TemplateContentForm>)getTemplateContents(id));
            return template;
    	} catch (DBException e) {
    		logger.error("DB Runtime Error[class: TemplateLogic, method: getTemplate]");
			throw new DBException("DB Runtime Error[class: TemplateLogic, method: getTemplate]");
		}
    }

    private List<TemplateContentForm> getTemplateContents(String templateId) {
    	try {
    		List<TemplateContentForm> contents = new ArrayList<>();

            List<TemplateContent> list = templateContentRepository.findByTemplateIdOrderByContentOrderAsc(templateId);
            for(TemplateContent tc: list) {
                TemplateContentForm content = new TemplateContentForm();
                content.setBlockId(tc.getBlockId());
                int order = tc.getContentOrder();
                content.setContentOrder(order);
                List<TemplateItem> templateItems = templateItemRepository.findByTemplateIdAndContentOrderOrderByLineNumberAsc(templateId, order);
                List<String> items = templateItems.stream().map(t -> t.getValue()).collect(Collectors.toList());
                content.setItems((ArrayList<String>)items);
                contents.add(content);
            }
            return contents;
    	} catch (DBException e) {
    		logger.error("DB Runtime Error[class: TemplateLogic, method: getTemplateContents]");
			throw new DBException("DB Runtime Error[class: TemplateLogic, method: getTemplateContents]");
		}
    }

    public List<Tag> getTemplateTags(String id) {
    	try {
    		List<TemplateTag> templateTagList = templateTagRepository.findByTemplateId(id);
            return tagRepository.findByTagIdIn(templateTagList.stream().map(TemplateTag::getTagId).collect(Collectors.toList()));
    	} catch (DBException e) {
    		logger.error("DB Runtime Error[class: TemplateLogic, method: getTemplateTags]");
			throw new DBException("DB Runtime Error[class: TemplateLogic, method: getTemplateTags]");
		}
    }

    public void toggleTemplatePublish(String templateId, boolean templatePublish) {
    	try {
    		TemplateInfo info = templateInfoRepository.findOne(templateId);
            info.setTemplatePublish(templatePublish);
            templateInfoRepository.save(info);
            logger.info("[method: toggleTemplatePublish] Update info of templateID '" + templateId + "' " + info);
    	} catch (DBException e) {
    		logger.error("DB Runtime Error[class: TemplateLogic, method: toggleTemplatePublish]");
			throw new DBException("DB Runtime Error[class: TemplateLogic, method: toggleTemplatePublish]");
		}
    }

    public boolean isUsed(String templateId) {
        return documentInfoRepository.findByTemplateId(templateId).size() > 0;
    }

    public String save(TemplateForm templateForm, String employeeNumber) throws IdIssuanceUpperException{
    	try {
    		templateItemRepository.deleteByTemplateId(templateForm.getTemplateId());
    		templateContentRepository.deleteByTemplateId(templateForm.getTemplateId());
    		String id = saveTemplateInfo(templateForm, employeeNumber);
            templateForm.setTemplateId(id);
            saveTemplateContent(id, templateForm.getContents());
            return id;
    	} catch (DBException e) {
    		logger.error("DB Runtime Error[class: TemplateLogic, method: save]");
			throw new DBException("DB Runtime Error[class: TemplateLogic, method: save]");
		}
    }

    private String saveTemplateInfo(TemplateForm templateForm, String employeeNumber) throws IdIssuanceUpperException {
    	try {
    		String templateId = templateForm.getTemplateId() == null ?  idIssuenceLogic.createTemplateId() : templateForm.getTemplateId();
            String templateName = templateForm.getTemplateName();
            Timestamp templateCreateDate = new Timestamp(System.currentTimeMillis());
            boolean templatePublish = templateForm.isPublish();
            TemplateInfo info = new TemplateInfo(templateId, employeeNumber, templateName, templateCreateDate, templatePublish);
            templateInfoRepository.save(info);
            return info.getTemplateId();
    	} catch (DBException e) {
    		logger.error("DB Runtime Error[class: TemplateLogic, method: saveTemplateInfo]");
			throw new DBException("DB Runtime Error[class: TemplateLogic, method: saveTemplateInfo]");
		}
    }

    private void saveTemplateContent(String templateId, List<TemplateContentForm> contenets) {
    	try {
    		for(TemplateContentForm content: contenets) {
                templateContentRepository.save(new TemplateContent(templateId, content.getContentOrder(), content.getBlockId()));
                saveContentItems(templateId, content.getContentOrder(), content.getItems());
            }
    	} catch (DBException e) {
    		logger.error("DB Runtime Error[class: TemplateLogic, method: saveTemplateContent]");
			throw new DBException("DB Runtime Error[class: TemplateLogic, method: saveTemplateContent]");
		}
    }

    private void saveContentItems(String templateId, int contentOrder, List<String> items) {
    	try {
    		int lineNumber = 1;
            for(String value: items) {
                templateItemRepository.save(new TemplateItem(templateId, contentOrder, lineNumber, value));
                lineNumber++;
            }
    	} catch (DBException e) {
    		logger.error("DB Runtime Error[class: TemplateLogic, method: saveContentItems]");
			throw new DBException("DB Runtime Error[class: TemplateLogic, method: saveContentItems]");
		}
    }

    public void saveTags(final String templateId, List<Tag> tags) throws  IdIssuanceUpperException {
    	try {
    		templateTagRepository.deleteByTemplateId(templateId);
    		int order = 1;
            for(Tag tag : tagLogic.applyTags(tags)) {
                if(tag.getTagId() == null) {
                    tag.setTagId(idIssuenceLogic.createTagId());
                }
                templateTagRepository.save(new TemplateTag(templateId, order, tag.getTagId()));
                order++;
            }
    	} catch (DBException e) {
    		logger.error("DB Runtime Error[class: TemplateLogic, method: saveTags]");
			throw new DBException("DB Runtime Error[class: TemplateLogic, method: saveTags]");
		}
    }

    public void delete(String templateId) {
        templateTagRepository.deleteByTemplateId(templateId);
        templateItemRepository.deleteByTemplateId(templateId);
        templateContentRepository.deleteByTemplateId(templateId);
        templateInfoRepository.delete(templateId);
    }
}
