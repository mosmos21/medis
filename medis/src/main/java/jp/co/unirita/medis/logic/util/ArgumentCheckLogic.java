package jp.co.unirita.medis.logic.util;

import java.util.ArrayList;
import java.util.List;

import jp.co.unirita.medis.domain.authority.Authority;
import jp.co.unirita.medis.domain.templateinfo.TemplateInfoRepository;
import jp.co.unirita.medis.util.exception.AuthorityException;
import jp.co.unirita.medis.util.exception.NotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.unirita.medis.domain.documentInfo.DocumentInfoRepository;
import jp.co.unirita.medis.domain.updateinfo.UpdateInfoRepository;
import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.domain.user.UserRepository;


@Service
public class ArgumentCheckLogic {

    private static final Logger logger = LoggerFactory.getLogger(ArgumentCheckLogic.class);

    private static final String ADMINISTRATOR_AUTHORITY_ID = "0";

	@Autowired
	UserRepository userRepository;
	@Autowired
	UpdateInfoRepository updateInfoRepository;
	@Autowired
    TemplateInfoRepository templateInfoRepository;
	@Autowired
	DocumentInfoRepository documentInfoRepository;

	public void checkAdminAuthority(String employeeNumber) throws AuthorityException, NotExistException {
	    checkUserExist(employeeNumber);
	    String authorityId = userRepository.findOne(employeeNumber).getAuthorityId();
        if(!authorityId.equals(ADMINISTRATOR_AUTHORITY_ID)) {
            AuthorityException e = new AuthorityException("authorityId", authorityId, "User does not have administrator authority.");
            logger.error("[method: checkAdminAuthority] The employee number '" + employeeNumber + "' does not have administrator authoriry.", e);
            throw e;
        }
    }

    public void checkUserExist(String employeeNumber) throws AuthorityException{
	    User user = userRepository.findOne(employeeNumber);
	    if(user == null) {
            AuthorityException e =  new AuthorityException("user", null, "User does not exist.");
            logger.error("[method: checkUserExist] The employee number '" + employeeNumber + "' does not exist.", e);
            throw e;
        }
    }

	public void checkUser(User user, String employeeNumber, String contents) throws NotExistException, AuthorityException {

		List<User> userList = userRepository.findAll();

		List<String> employeeNumberList = new ArrayList<>();

		for (User list : userList) {
			employeeNumberList.add(list.getEmployeeNumber());
		}

		if (!employeeNumberList.contains(employeeNumber)) {
			System.out.println(user.getEmployeeNumber() + " " + employeeNumber);
			throw new NotExistException("employeeNumber", employeeNumber, "存在しないユーザです");
		}

		if (!user.getEmployeeNumber().equals(employeeNumber)) {
			System.out.println(user.getEmployeeNumber() + " " + employeeNumber);
			throw new AuthorityException("employeeNumber", employeeNumber, "他ユーザの"+contents+"は取得することができません");
		}
	}

	public void checkLastUpdateId(String updateId) throws NotExistException {

		int count = updateInfoRepository.countByUpdateId(updateId);

		if (count == 0) {
			System.out.println(updateId);
			throw new NotExistException("lastUpdateId", updateId, "存在しない更新IDです");
		}
	}

	public void checkTemplateId(String templateId) throws NotExistException {
        int count = templateInfoRepository.countByTemplateId(templateId);
        if(count == 0) {
            NotExistException e = new NotExistException("templateId", templateId, "The templateId does not exist.");
            logger.error("[method: checkTemlateId] The templateID " + templateId + " does not exist.", e);
            throw e;
        }
    }

	public void checkDocumentId(String documentId) throws NotExistException {

		int count = documentInfoRepository.countBydocumentId(documentId);

		if (count == 0) {
			System.out.println(documentId);
			throw new NotExistException("documentId", documentId, "存在しないドキュメントIDです");
		}
	}
}
