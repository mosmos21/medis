package jp.co.unirita.medis.logic.document;

import java.lang.invoke.MethodHandles;
import java.sql.Timestamp;
import java.util.List;

import jp.co.unirita.medis.logic.util.IdIssuanceLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.unirita.medis.domain.updateinfo.UpdateInfo;
import jp.co.unirita.medis.domain.updateinfo.UpdateInfoRepository;
import jp.co.unirita.medis.util.exception.DBException;
import jp.co.unirita.medis.util.exception.IdIssuanceUpperException;

@Service
@Transactional(rollbackFor = Exception.class)
public class UpdateInfoLogic {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	IdIssuanceLogic idIssuanceLogic;
	@Autowired
	UpdateInfoRepository updateInfoRepository;

	public List<UpdateInfo> getUpdateInfo(String id) {
		try {
			return updateInfoRepository.findByDocumentId(id);
		} catch (DBException e) {
			logger.error("DB Runtime Error[class: UpdateInfoLogic, method: getUpdateInfo]");
			throw new DBException("DB Runtime Error[class: UpdateInfoLogic, method: getUpdateInfo]");
		}
	}


	public void saveUpdateInfo(String documentId, String updateType, String employeeNumber) throws IdIssuanceUpperException {
		try {
			UpdateInfo info = new UpdateInfo();
			Timestamp updateDate = new Timestamp(System.currentTimeMillis());
			info.setUpdateId(idIssuanceLogic.createUpdateId());
			info.setDocumentId(documentId);
			info.setUpdateType(updateType);
			info.setEmployeeNumber(employeeNumber);
			info.setUpdateDate(updateDate);
			updateInfoRepository.saveAndFlush(info);
		} catch (DBException e) {
			logger.error("DB Runtime Error[class: UpdateInfoLogic, method: saveUpdateInfo]");
			throw new DBException("DB Runtime Error[class: UpdateInfoLogic, method: saveUpdateInfo]");
		}
	}
}
