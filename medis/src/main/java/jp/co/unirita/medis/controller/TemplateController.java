package jp.co.unirita.medis.controller;

import jp.co.unirita.medis.domain.blockbase.BlockBase;

import jp.co.unirita.medis.form.template.TemplateForm;
import jp.co.unirita.medis.logic.BlockLogic;
import jp.co.unirita.medis.logic.TemplateLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/templates")
public class TemplateController {

    @Autowired
    BlockLogic blockLogic;

    @Autowired
    TemplateLogic templateLogic;

    @GetMapping(value = "blocks")
    @ResponseStatus(HttpStatus.OK)
    public List<BlockBase> getBlockList(){
        return blockLogic.getBlockList();
    }

    @GetMapping(value = "{templateId:^t[0-9]{10}+$}")
    public TemplateForm getTemplate(@PathVariable(value ="templateId") String templateId) {
        System.out.println("get template [id = " + templateId + "]");

        // TODO 存在チェック


        TemplateForm template = templateLogic.getTemplate(templateId);
        System.out.println(template);
        return template;
    }

    @PutMapping(value = "new")
    @ResponseStatus(HttpStatus.CREATED)
    public TemplateForm createTemplate(@RequestBody TemplateForm template) throws Exception{
        // TODO 社員番号を取得するようにする
        templateLogic.save(template, "99999");
        return template;
    }
}
