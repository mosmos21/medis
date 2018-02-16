package jp.co.unirita.medis.logic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.unirita.medis.domain.authority.AuthorityRepository;
import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.domain.user.UserRepository;
import jp.co.unirita.medis.domain.userdetail.UserDetail;
import jp.co.unirita.medis.domain.userdetail.UserDetailRepository;
import jp.co.unirita.medis.form.UserManagementForm;
import jp.co.unirita.medis.util.exception.ConflictException;
import jp.co.unirita.medis.util.exception.InvalidArgumentException;

@Service
public class UserManagementLogic {

	@Autowired
	UserRepository userRepository;
	@Autowired
	UserDetailRepository userDetailRepository;
	@Autowired
	AuthorityRepository authorityRepository;

	public List<UserManagementForm> loadUserManagement() {
		List<UserManagementForm> formList = new ArrayList<UserManagementForm>();
		UserManagementForm form = new UserManagementForm();
		List<UserDetail> detail = userDetailRepository.findAll();
		for(int i=0; i<detail.size(); i++){
			form = new UserManagementForm();
			form.setEmployeeNumber(detail.get(i).getEmployeeNumber());
			form.setLastName(detail.get(i).getLastName());
			form.setFirstName(detail.get(i).getFirstName());
			form.setLastNamePhonetic(detail.get(i).getLastNmaePhonetic());
			form.setFirstNmaePhonetic(detail.get(i).getFirstNamePhonetic());
			form.setMailaddress(detail.get(i).getMailaddress());
			form.setIcon(detail.get(i).isIcon());
			User user = userRepository.findFirstByEmployeeNumber(form.getEmployeeNumber());
			//Authority auth = authorityRepository.findFirstByAuthorityId(user.getAuthorityId());
			//form.setAuthorityId(auth.getAuthorityType());
			form.setAuthorityId(user.getAuthorityId());
			form.setEnabled(user.isEnabled());
			formList.add(form);
		}
		return formList;
	}

	public void updateUserManagement(UserManagementForm userManagementForm) throws InvalidArgumentException {
		User user = userRepository.findFirstByEmployeeNumber(userManagementForm.getEmployeeNumber());
		if (user == null) {
			throw new InvalidArgumentException("employeeNumber", userManagementForm.getEmployeeNumber(), "指定したIDのユーザは存在しません。");
		}
		user.setEnabled(userManagementForm.isEnabled);
		user.setAuthorityId(userManagementForm.getAuthorityId());
		userRepository.saveAndFlush(user);
	}

	public void createUserManagement(UserManagementForm userManagementForm) throws ConflictException {
		User check = userRepository.findFirstByEmployeeNumber(userManagementForm.getEmployeeNumber());
		if (check != null) {
			throw new ConflictException("employeeNumber", userManagementForm.getEmployeeNumber(),
					"重複したIDでユーザを新規作成する事はできません。");
		}
		User user = new User();
		UserDetail detail = new UserDetail();
		user.setEmployeeNumber(userManagementForm.getEmployeeNumber());
		detail.setEmployeeNumber(userManagementForm.getEmployeeNumber());
		detail.setLastName(userManagementForm.getLastName());
		detail.setFirstName(userManagementForm.getFirstName());
		detail.setLastNmaePhonetic(userManagementForm.getLastNamePhonetic());
		detail.setFirstNamePhonetic(userManagementForm.getFirstNmaePhonetic());
		detail.setMailaddress(userManagementForm.getMailaddress());
		detail.setIcon(userManagementForm.isIcon);
		user.setAuthorityId(userManagementForm.getAuthorityId());
		user.setEnabled(userManagementForm.isEnabled);
		user.setPassword(userManagementForm.getPassword());
		userRepository.saveAndFlush(user);
		userDetailRepository.saveAndFlush(detail);
	}
}
