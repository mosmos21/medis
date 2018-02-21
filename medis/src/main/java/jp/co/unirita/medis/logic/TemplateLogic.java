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
        // templateForm info
        String id = saveTemplateInfo(templateForm, employeeNumber);
        templateForm.setTemplateId(id);
        System.out.println("create new templateForm info [id = " + id + ", name = " + templateForm.getTemplateName() + "]");
        saveTemplateContent(id, templateForm.getContents());
    }

    private String saveTemplateInfo(TemplateForm templateForm, String employeeNumber) throws Exception {
        String templateId = createNewTemplateId();
        String templateName = templateForm.getTemplateName();
        Timestamp templateCreateDate = new Timestamp(System.currentTimeMillis());
        boolean isTemplatePublish = templateForm.isPublish();
        TemplateInfo info = new TemplateInfo(templateId, employeeNumber, templateName, templateCreateDate, isTemplatePublish);
        templateInfoRepository.save(info);
        return info.getTemplateId();
    }

    private void saveTemplateContent(String templateId, List<TemplateContentForm> contenets) {
        int order = 1;
        for(TemplateContentForm content: contenets) {
            templateContentRepository.save(new TemplateContent(templateId, content.getBlockId(), order, content.getItems().size()));
            saveContentItems(templateId, order, content.getItems());
            order++;
        }
    }

    private void saveContentItems(String templateId, int contentOrder, List<String> items) {
        int lineNumber = 1;
        for(String value: items) {
            templateItemRepository.save(new TemplateItem(templateId, contentOrder, lineNumber, value));
            lineNumber++;
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
