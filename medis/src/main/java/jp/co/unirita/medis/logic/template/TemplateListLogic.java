package jp.co.unirita.medis.logic.template;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jp.co.unirita.medis.domain.templateinfo.TemplateInfo;
import jp.co.unirita.medis.domain.templateinfo.TemplateInfoRepository;

@Service
public class TemplateListLogic {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    TemplateInfoRepository templateInfoRepository;

    public List<TemplateInfo> getAllTemplateInfoList() {
        return templateInfoRepository.findAll(new Sort(Sort.Direction.DESC,"templateId"));
    }

    public List<TemplateInfo> getAllTemplateInfoList(boolean publish) {
        return templateInfoRepository.findByTemplatePublishOrderByTemplateIdDesc(publish);
    }

    public List<TemplateInfo> getAllTemplateInfoList(String employeeNumber) {
        return templateInfoRepository.findByEmployeeNumber(employeeNumber);
    }

    public List<TemplateInfo> getTemplateInfoList(String emplouyeeNumber, boolean publish) {
        return templateInfoRepository.findByEmployeeNumberAndTemplatePublish(emplouyeeNumber, publish);
    }
}
