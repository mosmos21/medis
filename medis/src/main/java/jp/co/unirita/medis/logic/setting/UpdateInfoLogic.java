package jp.co.unirita.medis.logic.setting;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jp.co.unirita.medis.domain.updateinfo.UpdateInfo;
import jp.co.unirita.medis.domain.updateinfo.UpdateInfoRepository;
import jp.co.unirita.medis.util.exception.IdIssuanceUpperException;

@Service
public class UpdateInfoLogic {

	@Autowired
	UpdateInfoRepository updateInfoRepository;

	public void saveUpdateInfo(String documentId, String updateType, String employeeNumber)
			throws IdIssuanceUpperException {
		UpdateInfo info = new UpdateInfo();
		Timestamp updateDate = new Timestamp(System.currentTimeMillis());
		info.setUpdateId(createNewUpdateId());
		info.setDocumentId(documentId);
		info.setUpdateType(updateType);
		info.setEmployeeNumber(employeeNumber);
		info.setUpdateDate(updateDate);
		updateInfoRepository.saveAndFlush(info);

	}

	public synchronized String createNewUpdateId() throws IdIssuanceUpperException {
		List<UpdateInfo> list = updateInfoRepository.findAll(new Sort(Sort.Direction.DESC, "updateId"));
		if (list.size() == 0) {
			return "u0000000000";
		}
		long idNum = Long.parseLong(list.get(0).getUpdateId().substring(1));
		if (idNum == 9999999999L) {
			throw new IdIssuanceUpperException("更新IDの発行限界");
		}
		return String.format("u%010d", idNum + 1);
	}

}
