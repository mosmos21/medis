package jp.co.unirita.medis.logic.template;

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
import jp.co.unirita.medis.util.exception.IdIssuanceUpperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TemplateLogic {

    private static final Logger logger = LoggerFactory.getLogger(TemplateLogic.class);

    @Autowired
    TemplateInfoRepository templateInfoRepository;
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

    public List<Tag> getTemplateTags(String id) {
        System.out.println("get fix tag[templateId = " + id + "]");
        List<TemplateTag> templateTagList = templateTagRepository.findByTemplateIdOrderByTagOrderAsc(id);
        List<Tag> tags = new ArrayList<>();
        for(TemplateTag t: templateTagList){
            tags.add(tagRepository.findByTagId(t.getTagId()));
        }
        return tags;
    }

    public TemplateForm getTemplate(String id) {
        TemplateForm template = new TemplateForm();
        template.setTemplateId(id);
        template.setTemplateName(templateInfoRepository.findOne(id).getTemplateName());
        template.setContents((ArrayList<TemplateContentForm>)getTemplateContents(id));
        return template;
    }

    private List<TemplateContentForm> getTemplateContents(String templateId) {
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
    }

    public void toggleTemplatePublish(String templateId, boolean templatePublish) {
        TemplateInfo info = templateInfoRepository.findOne(templateId);
        info.setTemplatePublish(templatePublish);
        templateInfoRepository.save(info);
        logger.info("[method: toggleTemplatePublish] Update info of templateID '" + templateId + "' " + info);
    }

    public void save(TemplateForm templateForm, String employeeNumber) throws IdIssuanceUpperException{
        String id = saveTemplateInfo(templateForm, employeeNumber);
        templateForm.setTemplateId(id);

        saveTemplateContent(id, templateForm.getContents());
    }

    public void saveTags(String templateId, List<Tag> tags) throws  IdIssuanceUpperException {
        int order = 1;
        for(Tag tag : tags) {
            if(tag.getTagId() == null) {
                tag.setTagId(tagLogic.getNewTagId());
            }
            templateTagRepository.save(new TemplateTag(templateId, order, tag.getTagId()));
            order++;
        }
    }

    public void update(TemplateForm templateForm, String employeeNumber) throws IdIssuanceUpperException {
        saveTemplateInfo(templateForm, employeeNumber);
        updateTemplateContent(templateForm.getTemplateId(), templateForm.getContents());
    }

    // TODO IDがついていないものにはつける
    public void updateTags(String tempalateId, List<Tag> tags) throws IdIssuanceUpperException {
        List<TemplateTag> oldTags = templateTagRepository.findByTemplateIdOrderByTagOrderAsc(tempalateId);

        int common = Math.min(oldTags.size(), tags.size());
        int order = 1;
        for(Tag tag: tags) {
            if(common < order){
                break;
            }
            templateTagRepository.save(new TemplateTag(tempalateId, order, tag.getTagId()));
            order++;
        }
        if(oldTags.size() < tags.size()) {
            List<Tag> addTags = tags.subList(common, tags.size());
            for(Tag tag: addTags) {
                templateTagRepository.save(new TemplateTag(tempalateId, order, tag.getTagId()));
                order++;
            }
        } else {
            for(;order <= oldTags.size(); order++) {
                templateTagRepository.delete(new TemplateTag.PK(tempalateId, order));
            }
        }
    }

    private String saveTemplateInfo(TemplateForm templateForm, String employeeNumber) throws IdIssuanceUpperException {
        String templateId = templateForm.getTemplateId() == null ?  createNewTemplateId() : templateForm.getTemplateId();
        String templateName = templateForm.getTemplateName();
        Timestamp templateCreateDate = new Timestamp(System.currentTimeMillis());
        boolean templatePublish = templateForm.isPublish();
        TemplateInfo info = new TemplateInfo(templateId, employeeNumber, templateName, templateCreateDate, templatePublish);
        templateInfoRepository.save(info);
        return info.getTemplateId();
    }

    private void saveTemplateContent(String templateId, List<TemplateContentForm> contenets) {
        for(TemplateContentForm content: contenets) {
            templateContentRepository.save(new TemplateContent(templateId, content.getContentOrder(), content.getBlockId()));
            saveContentItems(templateId, content.getContentOrder(), content.getItems());
        }
    }

    private void saveContentItems(String templateId, int contentOrder, List<String> items) {
        int lineNumber = 1;
        for(String value: items) {
            templateItemRepository.save(new TemplateItem(templateId, contentOrder, lineNumber, value));
            lineNumber++;
        }
    }

    private void updateTemplateContent(String templateId, List<TemplateContentForm> contents) {
        List<TemplateContent> oldContents = templateContentRepository.findByTemplateIdOrderByContentOrderAsc(templateId);

        int common = Math.min(oldContents.size(), contents.size());
        for(TemplateContentForm content : contents){
            if(common < content.getContentOrder()) {
                break;
            }
            templateContentRepository.save(new TemplateContent(templateId, content.getContentOrder(), content.getBlockId()));
            updateContentItem(templateId, content.getContentOrder(), content.getItems());
        }
        if(oldContents.size() < contents.size()) {
            List<TemplateContentForm> addContents = contents.subList(common, contents.size());
            for(TemplateContentForm content: addContents) {
                templateContentRepository.save(new TemplateContent(templateId, content.getContentOrder(), content.getBlockId()));
                saveContentItems(templateId, content.getContentOrder(), content.getItems());
            }
        } else {
            for(int i = common + 1;i <= oldContents.size(); i++) {
                templateContentRepository.delete(new TemplateContent.PK(templateId, i));
            }
        }
    }

    private void updateContentItem(String templateId, int contentOrder, List<String> items) {
        List<TemplateItem> oldItems = templateItemRepository.findByTemplateIdAndContentOrderOrderByLineNumberAsc(templateId, contentOrder);

        int lineNumber = 1;
        int common = Math.min(oldItems.size(), items.size());

        for(String value: items) {
            if(common < lineNumber){
                break;
            }
            templateItemRepository.save(new TemplateItem(templateId, contentOrder, lineNumber, value));
            lineNumber++;
        }
        if(oldItems.size() < items.size()) {
            List<String> addItems = items.subList(lineNumber-1, items.size());
            for(String value: addItems) {
                templateItemRepository.save(new TemplateItem(templateId, contentOrder, lineNumber, value));
                lineNumber++;
            }
        } else {
            for(;lineNumber <= oldItems.size(); lineNumber++) {
                templateItemRepository.delete(new TemplateItem.PK(templateId, contentOrder, lineNumber));
            }
        }
    }

    private String createNewTemplateId() throws IdIssuanceUpperException{
        List<TemplateInfo> list = templateInfoRepository.findAll(new Sort(Sort.Direction.DESC, "templateId"));
        if(list.size()  == 0) {
            return "t0000000000";
        }
        long idNum = Long.parseLong(list.get(0).getTemplateId().substring(1));
        if(idNum == 9999999999L){
            throw new IdIssuanceUpperException("Can not issue any more ID");
        }
        return String.format("t%010d", idNum + 1);
    }
}
