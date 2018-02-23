package jp.co.unirita.medis.controller;

import jp.co.unirita.medis.form.blockbase.BlockBaseForm;

import jp.co.unirita.medis.domain.tag.Tag;
import jp.co.unirita.medis.form.template.TemplateForm;
import jp.co.unirita.medis.logic.BlockLogic;
import jp.co.unirita.medis.logic.TemplateLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/templates")
public class TemplateController {

    private static final Logger logger = LoggerFactory.getLogger(TemplateController.class);

    @Autowired
    BlockLogic blockLogic;

    @Autowired
    TemplateLogic templateLogic;

    @GetMapping(value = "blocks")
    @ResponseStatus(HttpStatus.OK)
    public List<BlockBaseForm> getBlockList(){
        return blockLogic.getBlockList();
    }

    @GetMapping(value = "{templateId:^t[0-9]{10}+$}")
    @ResponseStatus(HttpStatus.OK)
    public TemplateForm getTemplate(@PathVariable(value ="templateId") String templateId) {
        System.out.println("get template [id = " + templateId + "]");

        // TODO 存在チェック

        TemplateForm template = templateLogic.getTemplate(templateId);
        System.out.println(template);
        return template;
    }

    @GetMapping(value = "{templateId:^t[0-9]{10}+$}/tags")
    @ResponseStatus(HttpStatus.OK)
    public List<Tag> getTemplateTagList(@PathVariable(value ="templateId") String templateId){
        return templateLogic.getTemplateTags(templateId);
    }

    @PostMapping(value = "{templateId:^t[0-9]{10}+$}")
    @ResponseStatus(HttpStatus.CREATED)
    public TemplateForm updateTemplate(@RequestBody TemplateForm template) throws Exception {
        // TODO 社員番号を確認
        templateLogic.update(template, "99999");
        return template;
    }

    @PostMapping(value = "{templateId:^t[0-9]{10}+$}/{templatePublish:^public|private$}")
    @ResponseStatus(HttpStatus.CREATED)
    public void toggleTemplate(
            @PathVariable(value = "templateId") String templateId,
            @PathVariable(value = "templatePublish") String templatePublish) throws Exception {

        logger.info("[method: toggleTemplate] Toggle open state of templateID '" + templateId + "' to " + templatePublish + ".");
        boolean publish = templatePublish.equals("public");
        templateLogic.toggleTemplatePublish(templateId, publish);
    }

    @PostMapping(value = "{templateId:^t[0-9]{10}+$}/tags")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateTemplateTagList(@PathVariable String templateId, @RequestBody List<Tag> tags) throws Exception {
        templateLogic.updateTags(templateId, tags);
    }

    @PutMapping(value = "new")
    @ResponseStatus(HttpStatus.CREATED)
    public TemplateForm saveTemplate(@RequestBody TemplateForm template) throws Exception{
        // TODO 社員番号を取得するようにする
        templateLogic.save(template, "99999");
        return template;
    }

    @PutMapping(value = "{templateId:^t[0-9]{10}+$}/tags")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveTemplateTagList(@PathVariable String templateId, @RequestBody List<Tag> tags) throws Exception {
        templateLogic.saveTags(templateId, tags);
    }
}
