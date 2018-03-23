package jp.co.unirita.medis.logic.system;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.unirita.medis.domain.authority.Authority;
import jp.co.unirita.medis.domain.authority.AuthorityRepository;
import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.domain.user.UserRepository;
import jp.co.unirita.medis.domain.userdetail.UserDetail;
import jp.co.unirita.medis.domain.userdetail.UserDetailRepository;
import jp.co.unirita.medis.form.system.UserManagementForm;
import jp.co.unirita.medis.logic.util.TagLogic;
import jp.co.unirita.medis.util.exception.ConflictException;
import jp.co.unirita.medis.util.exception.DBException;
import jp.co.unirita.medis.util.exception.IdIssuanceUpperException;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserManagementLogic {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
    UserRepository userRepository;
	@Autowired
	UserDetailRepository userDetailRepository;
	@Autowired
	AuthorityRepository authorityRepository;
	@Autowired
	TagLogic tagLogic;


    /**
     * すべてユーザ権限種別の取得
     * @return ユーザ権限種別(@see jp.co.unirita.medis.domain.authority.Authority)のリスト
     */
    public List<Authority> getAuthorityTypeList() {
    	try {
    		return authorityRepository.findAll();
    	} catch (DBException e) {
			throw new DBException("DB Runtime Error[class: UserManagementLogic, method: getAuthorityTypeList]");
		}
    }

    /**
     * ユーザ情報詳細の一覧取得
     * @return ユーザ情報詳細(@see jp.co.unirita.medis.form.UserManagementForm)のリスト
     */
	public List<UserManagementForm> getUserManagement() {
		try {
			List<UserManagementForm> forms = new ArrayList<>();
			List<UserDetail> details = userDetailRepository.findAll();

			for(UserDetail detail: details) {
			    UserManagementForm form = new UserManagementForm();
			    form.applyUserDetail(detail);
			    form.applyUser(userRepository.findOne(form.getEmployeeNumber()));
			    forms.add(form);
	        }
			return forms;
		} catch (DBException e) {
			throw new DBException("DB Runtime Error[class: UserManagementLogic, method: getUserManagement]");
		}
	}

    /**
     * ユーザの有効、無効化。ユーザ種別の変更
     * @param employeeNumber 社員番号
     * @param isEnabled 有効フラグ
     * @param authorityId　権限種別ID
     */
	public void updateUserManagement(String employeeNumber, boolean isEnabled, String authorityId) {
		try {
			User user = userRepository.findOne(employeeNumber);
			user.setEnabled(isEnabled);
			user.setAuthorityId(authorityId);
			userRepository.saveAndFlush(user);
		} catch (DBException e) {
			throw new DBException("DB Runtime Error[class: UserManagementLogic, method: updateUserManagement]");
		}
	}

    /**
     * 新規ユーザの作成を行う
     * @param userManagementForm 新規作成を行うユーザのフォーム(@see jp.co.unirita.medis.form.UserManagementForm)
     * @throws ConflictException 作成をしようとしている社員番号がすでに存在している場合に発生する例外
     * @return 作成したユーザの詳細情報
     */
	public UserDetail createUser(UserManagementForm userManagementForm) throws ConflictException, IdIssuanceUpperException {
		try {
			User existUser = userRepository.findOne(userManagementForm.getEmployeeNumber());
			if (existUser != null) {
				ConflictException e =  new ConflictException("employeeNumber", userManagementForm.getEmployeeNumber(),
						"emoloyee number must be unique. employeeNumber = " + existUser.getEmployeeNumber());
				logger.error("error in createUser()", e);
				throw e;
			}

			User user = new User();
			user.setEmployeeNumber(userManagementForm.getEmployeeNumber());
	        user.setAuthorityId(userManagementForm.getAuthorityId());
	        user.setEnabled(userManagementForm.isEnabled());
	        user.setPassword(userManagementForm.getPassword());
	        logger.info("[method: createUser] create new user: " + user.toString());
	        userRepository.saveAndFlush(user);

	        UserDetail detail = new UserDetail();
	        detail.setEmployeeNumber(userManagementForm.getEmployeeNumber());
	        detail.setLastName(userManagementForm.getLastName());
	        detail.setFirstName(userManagementForm.getFirstName());
	        detail.setLastNamePhonetic(userManagementForm.getLastNamePhonetic());
	        detail.setFirstNamePhonetic(userManagementForm.getFirstNamePhonetic());
	        detail.setMailaddress(userManagementForm.getMailaddress());
	        detail.setIcon(userManagementForm.isIcon());
	        logger.info("[method: createUser] create new use detail: " + detail.toString());
			userDetailRepository.saveAndFlush(detail);

			String systemTag = detail.getLastName() + " " + detail.getFirstName();
			tagLogic.createSystemTag(systemTag);

			return detail;
		} catch (DBException e) {
			throw new DBException("DB Runtime Error[class: UserManagementLogic, method: createUser]");
		}
	}
}
