package jp.co.unirita.medis.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.domain.user.UserRepository;
import jp.co.unirita.medis.util.exception.InvalidArgsException;



@Service
public class ArgumentCheckLogic {

	@Autowired
	UserRepository userRepository;

	public void userCheck(User user, String employeeNumber) throws InvalidArgsException {

		List<String> userList = userRepository.findByEmployeeNumber();

		if (!userList.contains(employeeNumber)) {
			System.out.println(user.getEmployeeNumber() + " " + employeeNumber);
			throw new InvalidArgsException("employeeNumber", employeeNumber, "存在しないユーザです");
		}

		if (!user.getEmployeeNumber().equals(employeeNumber)) {
			System.out.println(user.getEmployeeNumber() + " " + employeeNumber);
			throw new InvalidArgsException("employeeNumber", employeeNumber, "他ユーザのドキュメント一覧は取得することができません");
		}
	}
}
