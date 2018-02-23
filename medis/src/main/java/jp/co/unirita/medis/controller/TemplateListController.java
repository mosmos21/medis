package jp.co.unirita.medis.controller;

import jp.co.unirita.medis.domain.templateinfo.TemplateInfo;
import jp.co.unirita.medis.domain.templateinfo.TemplateInfoRepository;
import jp.co.unirita.medis.logic.TemplateLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/templates")
public class TemplateListController {

    private static final Logger logger = LoggerFactory.getLogger(TemplateListController.class);

    @Autowired
    TemplateLogic templateLogic;

    @GetMapping
    public List<TemplateInfo> getAllTemplateInfoList() {
        String employeeNumber = "99999";// TODO 社員番号を取得するようにする
        logger.info("[method: getAllTemplateInfoList] Get template info list by " + employeeNumber + ".");
        // TODO ユーザ権限が一般ユーザの場合エラーにする
        return templateLogic.getAllTemplateInfoList();
    }

    @GetMapping(value = "public")
    public List<TemplateInfo> getTemplateInfoList() {
        String employeeNumber = "99999"; // TODO 社員番号を取得するようにする
        logger.info("[method: getTemplateInfoList] Get template info list by " + employeeNumber + ".");
        return templateLogic.getTemplteInfoList();
    }
}
