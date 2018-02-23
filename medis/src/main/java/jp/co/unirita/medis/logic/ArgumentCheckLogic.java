package jp.co.unirita.medis.logic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public void userCheck(User user, String employeeNumber, String contents) throws InvalidArgsException {

		List<User> userList = userRepository.findAll();

		for (User user2 : userList) {
			System.out.println(user2);
		}

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

/*		List<String> updateIdList = updateInfoRepository.findByUpdateId();

		if (!updateIdList.contains(updateId)) {
			System.out.println(updateId);
			throw new InvalidArgsException("lastUpdateId", updateId, "存在しない更新IDです");
		}
*/
	}
}
