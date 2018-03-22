package jp.co.unirita.medis.logic.document;

import java.sql.Timestamp;
import java.util.List;

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

	@Autowired
	UpdateInfoRepository updateInfoRepository;

	public List<UpdateInfo> getUpdateInfo(String id) {
		try {
			return updateInfoRepository.findByDocumentId(id);
		} catch (DBException e) {
			throw new DBException("DB Runtime Error[class: UpdateInfoLogic, method: getUpdateInfo]");
		}
	}

	public synchronized String createNewUpdateId() throws IdIssuanceUpperException {
		try {
			List<UpdateInfo> list = updateInfoRepository.findAll(new Sort(Sort.Direction.DESC, "updateId"));
			if (list.size() == 0) {
				return "u0000000000";
			}
			long idNum = Long.parseLong(list.get(0).getUpdateId().substring(1));
			if (idNum == 9999999999L) {
				throw new IdIssuanceUpperException("更新IDの発行限界");
			}
			return String.format("u%010d", idNum + 1);
		} catch (DBException e) {
			throw new DBException("DB Runtime Error[class: UpdateInfoLogic, method: createNewUpdateId]");
		}
	}

	public void saveUpdateInfo(String documentId, String updateType, String employeeNumber) throws IdIssuanceUpperException {
		try {
			UpdateInfo info = new UpdateInfo();
			Timestamp updateDate = new Timestamp(System.currentTimeMillis());
			info.setUpdateId(createNewUpdateId());
			info.setDocumentId(documentId);
			info.setUpdateType(updateType);
			info.setEmployeeNumber(employeeNumber);
			info.setUpdateDate(updateDate);
			updateInfoRepository.saveAndFlush(info);
		} catch (DBException e) {
			throw new DBException("DB Runtime Error[class: UpdateInfoLogic, method: saveUpdateInfo]");
		}
	}
}
