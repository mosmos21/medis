package jp.co.unirita.medis.logic.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.unirita.medis.domain.documentInfo.DocumentInfoRepository;
import jp.co.unirita.medis.domain.updateinfo.UpdateInfoRepository;
import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.domain.user.UserRepository;
import jp.co.unirita.medis.util.exception.InvalidArgsException;



@Service
public class ArgumentCheckLogic {

	@Autowired
	UserRepository userRepository;
	@Autowired
	UpdateInfoRepository updateInfoRepository;
	@Autowired
	DocumentInfoRepository documentInfoRepository;

	public void userCheck(User user, String employeeNumber, String contents) throws InvalidArgsException {

		List<User> userList = userRepository.findAll();

		List<String> employeeNumberList = new ArrayList<>();

		for (User list : userList) {
			employeeNumberList.add(list.getEmployeeNumber());
		}

		if (!employeeNumberList.contains(employeeNumber)) {
			System.out.println(user.getEmployeeNumber() + " " + employeeNumber);
			throw new InvalidArgsException("employeeNumber", employeeNumber, "存在しないユーザです");
		}

		if (!user.getEmployeeNumber().equals(employeeNumber)) {
			System.out.println(user.getEmployeeNumber() + " " + employeeNumber);
			throw new InvalidArgsException("employeeNumber", employeeNumber, "他ユーザの"+contents+"は取得することができません");
		}
	}

	public void lastUpdateIdCheck(String updateId) throws InvalidArgsException {

		int count = updateInfoRepository.countByUpdateId(updateId);

		if (count == 0) {
			System.out.println(updateId);
			throw new InvalidArgsException("lastUpdateId", updateId, "存在しない更新IDです");
		}
	}

	public void documentIdCheck(String documentId) throws InvalidArgsException {

		int count = documentInfoRepository.countBydocumentId(documentId);

		if (count == 0) {
			System.out.println(documentId);
			throw new InvalidArgsException("documentId", documentId, "存在しないドキュメントIDです");
		}
	}
}