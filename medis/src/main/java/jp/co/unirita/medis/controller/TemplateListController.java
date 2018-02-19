package jp.co.unirita.medis.controller;

import jp.co.unirita.medis.domain.templateinfo.TemplateInfo;
import jp.co.unirita.medis.domain.templateinfo.TemplateInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/v1/templates")
public class TemplateListController {

    @Autowired
    TemplateInfoRepository repository;

    @GetMapping
    public List<TemplateInfo> getTemplateInfoList(){
        // TODO 権限を見る

        List<TemplateInfo> list = repository.findAll();
        list.sort((i1, i2) -> i1.getTemplateId().compareTo(i2.getTemplateId()));
        return list;
    }
}
