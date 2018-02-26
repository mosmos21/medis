package jp.co.unirita.medis.logic.template;

import jp.co.unirita.medis.domain.templateinfo.TemplateInfo;
import jp.co.unirita.medis.domain.templateinfo.TemplateInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateListLogic {

    @Autowired
    TemplateInfoRepository templateInfoRepository;

    public List<TemplateInfo> getAllTemplateInfoList() {
        return templateInfoRepository.findAll();
    }

    public List<TemplateInfo> getAllTemplateInfoList(boolean publish) {
        return templateInfoRepository.findByTemplatePublish(publish);
    }

    public List<TemplateInfo> getATemplateInfoList(String employeeNumber) {
        return templateInfoRepository.findByEmployeeNumber(employeeNumber);
    }

    public List<TemplateInfo> getTemplateInfoList(String emplouyeeNumber, boolean publish) {
        return templateInfoRepository.findByEmployeeNumberAndTemplatePublish(emplouyeeNumber, publish);
    }
}