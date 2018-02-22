package jp.co.unirita.medis.logic;

import jp.co.unirita.medis.domain.templatecontent.TemplateContent;
import jp.co.unirita.medis.domain.templatecontent.TemplateContentRepository;
import jp.co.unirita.medis.domain.templateinfo.TemplateInfo;
import jp.co.unirita.medis.domain.templateinfo.TemplateInfoRepository;
import jp.co.unirita.medis.domain.templateitem.TemplateItem;
import jp.co.unirita.medis.domain.templateitem.TemplateItemRepository;
import jp.co.unirita.medis.form.template.TemplateContentForm;
import jp.co.unirita.medis.form.template.TemplateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.sql.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TemplateLogic {

    @Autowired
    TemplateInfoRepository templateInfoRepository;
    @Autowired
    TemplateContentRepository templateContentRepository;
    @Autowired
    TemplateItemRepository templateItemRepository;

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

    public void save(TemplateForm templateForm, String employeeNumber) throws Exception{
        String id = saveTemplateInfo(templateForm, employeeNumber);
        templateForm.setTemplateId(id);

        saveTemplateContent(id, templateForm.getContents());
    }

    public void update(TemplateForm templateForm, String employeeNumber) throws Exception {
        saveTemplateInfo(templateForm, employeeNumber);
        updateTemplateContent(templateForm.getTemplateId(), templateForm.getContents());
    }

    private String saveTemplateInfo(TemplateForm templateForm, String employeeNumber) throws Exception {
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

    private String createNewTemplateId() throws Exception{
        List<TemplateInfo> list = templateInfoRepository.findAll(new Sort(Sort.Direction.DESC, "templateId"));
        if(list.size()  == 0) {
            return "t0000000000";
        }
        long idNum = Long.parseLong(list.get(0).getTemplateId().substring(1));
        if(idNum == 9999999999L){
            throw new Exception("文書の発行限界");
        }
        return String.format("t%010d", idNum + 1);
    }
}
