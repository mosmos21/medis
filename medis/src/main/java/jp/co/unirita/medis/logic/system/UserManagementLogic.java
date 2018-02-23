package jp.co.unirita.medis.logic.system;

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
import jp.co.unirita.medis.util.exception.NotExistException;

@Service
public class UserManagementLogic {

	@Autowired
	UserRepository userRepository;
	@Autowired
	UserDetailRepository userDetailRepository;
	@Autowired
	AuthorityRepository authorityRepository;

	public List<UserManagementForm> getUserManagement(int maxSize) {
		List<UserManagementForm> formList = new ArrayList<UserManagementForm>();
		UserManagementForm form = new UserManagementForm();
		List<UserDetail> detail = userDetailRepository.findAll();
		int size = detail.size();
		if(maxSize != -1 && maxSize < size) size = maxSize;
		for(int i=0; i<size; i++){
			form = new UserManagementForm();
			form.setEmployeeNumber(detail.get(i).getEmployeeNumber());
			form.setLastName(detail.get(i).getLastName());
			form.setFirstName(detail.get(i).getFirstName());
			form.setLastNamePhonetic(detail.get(i).getLastNamePhonetic());
			form.setFirstNmaePhonetic(detail.get(i).getFirstNamePhonetic());
			form.setMailaddress(detail.get(i).getMailaddress());
			form.setIcon(detail.get(i).isIcon());
			User user = userRepository.findFirstByEmployeeNumber(form.getEmployeeNumber());
			form.setAuthorityId(user.getAuthorityId());
			form.setEnabled(user.isEnabled());
			formList.add(form);
		}
		return formList;
	}

	public void updateUserManagement(UserManagementForm userManagementForm) throws NotExistException {
		User user = userRepository.findFirstByEmployeeNumber(userManagementForm.getEmployeeNumber());
		if (user == null) {
			throw new NotExistException("employeeNumber", userManagementForm.getEmployeeNumber(), "指定したIDのユーザは存在しません。");
		}
		user.setEnabled(userManagementForm.Enabled);
		user.setAuthorityId(userManagementForm.getAuthorityId());
		userRepository.saveAndFlush(user);
	}

	public void newUserManagement(UserManagementForm userManagementForm) throws ConflictException {
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
		detail.setLastNamePhonetic(userManagementForm.getLastNamePhonetic());
		detail.setFirstNamePhonetic(userManagementForm.getFirstNmaePhonetic());
		detail.setMailaddress(userManagementForm.getMailaddress());
		detail.setIcon(userManagementForm.Icon);
		user.setAuthorityId(userManagementForm.getAuthorityId());
		user.setEnabled(userManagementForm.Enabled);
		user.setPassword(userManagementForm.getPassword());
		userRepository.saveAndFlush(user);
		userDetailRepository.saveAndFlush(detail);
	}
}
