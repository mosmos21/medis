package jp.co.unirita.medis.logic.template;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.unirita.medis.domain.templateinfo.TemplateInfo;
import jp.co.unirita.medis.domain.templateinfo.TemplateInfoRepository;
import jp.co.unirita.medis.util.exception.DBException;

@Service
@Transactional(rollbackFor = Exception.class)
public class TemplateListLogic {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    TemplateInfoRepository templateInfoRepository;

    public List<TemplateInfo> getAllTemplateInfoList() {
    	try {
    		return templateInfoRepository.findAll(new Sort(Sort.Direction.DESC,"templateId"));
    	} catch (DBException e) {
    		logger.error("DB Runtime Error[class: TemplateListLogic, method: getAllTemplateInfoList]");
			throw new DBException("DB Runtime Error[class: TemplateListLogic, method: getAllTemplateInfoList]");
		}
    }

    public List<TemplateInfo> getAllTemplateInfoList(boolean publish) {
    	try {
    		return templateInfoRepository.findByTemplatePublishOrderByTemplateIdDesc(publish);
    	} catch (DBException e) {
    		logger.error("DB Runtime Error[class: TemplateListLogic, method: getAllTemplateInfoList(boolean publish)]");
			throw new DBException("DB Runtime Error[class: TemplateListLogic, method: getAllTemplateInfoList(boolean publish)]");
		}
    }

    public List<TemplateInfo> getAllTemplateInfoList(String employeeNumber) {
    	try {
    		return templateInfoRepository.findByEmployeeNumber(employeeNumber);
    	} catch (DBException e) {
    		logger.error("DB Runtime Error[class: TemplateListLogic, method: getAllTemplateInfoList(String employeeNumber)]");
			throw new DBException("DB Runtime Error[class: TemplateListLogic, method: getAllTemplateInfoList(String employeeNumber)]");
		}
    }

    public List<TemplateInfo> getTemplateInfoList(String emplouyeeNumber, boolean publish) {
    	try {
    		return templateInfoRepository.findByEmployeeNumberAndTemplatePublish(emplouyeeNumber, publish);
    	} catch (DBException e) {
    		logger.error("DB Runtime Error[class: TemplateListLogic, method: getAllTemplateInfoList(String emplouyeeNumber, boolean publish)]");
			throw new DBException("DB Runtime Error[class: TemplateListLogic, method: getAllTemplateInfoList(String emplouyeeNumber, boolean publish)]");
		}
    }
}
