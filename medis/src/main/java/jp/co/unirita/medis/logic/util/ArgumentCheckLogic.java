package jp.co.unirita.medis.logic.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.unirita.medis.domain.comment.Comment;
import jp.co.unirita.medis.domain.comment.CommentRepository;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfoRepository;
import jp.co.unirita.medis.domain.templateinfo.TemplateInfo;
import jp.co.unirita.medis.domain.templateinfo.TemplateInfoRepository;
import jp.co.unirita.medis.domain.updateinfo.UpdateInfo;
import jp.co.unirita.medis.domain.updateinfo.UpdateInfoRepository;
import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.domain.user.UserRepository;
import jp.co.unirita.medis.util.exception.AuthorityException;
import jp.co.unirita.medis.util.exception.DBException;
import jp.co.unirita.medis.util.exception.NotExistException;


@Service
@Transactional(rollbackFor = Exception.class)
public class ArgumentCheckLogic {

    private static final Logger logger = LoggerFactory.getLogger(ArgumentCheckLogic.class);

    private static final String ADMINISTRATOR_AUTHORITY_ID = "a0000000000";

	@Autowired
	UserRepository userRepository;
	@Autowired
	UpdateInfoRepository updateInfoRepository;
	@Autowired
    TemplateInfoRepository templateInfoRepository;
	@Autowired
	DocumentInfoRepository documentInfoRepository;
	@Autowired
	CommentRepository commentRepository;

	public void checkAdminAuthority(String employeeNumber) throws AuthorityException {
		try {
			String authorityId = userRepository.findOne(employeeNumber).getAuthorityId();
	        if(!authorityId.equals(ADMINISTRATOR_AUTHORITY_ID)) {
	            AuthorityException e = new AuthorityException("authorityId", authorityId, "User does not have administrator authority.");
	            logger.error("[method: checkAdminAuthority] The employee number '" + employeeNumber + "' does not have administrator authoriry.", e);
	            throw e;
	        }
		} catch (DBException e) {
			throw new DBException("DB Runtime Error[class: ArgumrntCheckLogic, method: checkAdminAuthority]");
		}
    }

    public void checkUserExist(String employeeNumber) throws NotExistException{
    	try {
    		User user = userRepository.findOne(employeeNumber);
    	    if(user == null) {
                NotExistException e =  new NotExistException("user", null, "User does not exist.");
                logger.error("[method: checkUserExist] The employee number '" + employeeNumber + "' does not exist.", e);
                throw e;
            }
    	} catch (DBException e) {
			throw new DBException("DB Runtime Error[class: ArgumrntCheckLogic, method: checkUserExist]");
		}
    }

	public void checkUser(User user, String employeeNumber, String contents) throws NotExistException, AuthorityException {
		try {
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
		} catch (DBException e) {
			throw new DBException("DB Runtime Error[class: ArgumrntCheckLogic, method: checkUser]");
		}
	}

	public void checkLastUpdateId(String updateId) throws NotExistException {
		try {
			UpdateInfo info = updateInfoRepository.findOne(updateId);
			if (info == null) {
				System.out.println(updateId);
				throw new NotExistException("lastUpdateId", updateId, "存在しない更新IDです");
			}
		} catch (DBException e) {
			throw new DBException("DB Runtime Error[class: ArgumrntCheckLogic, method: checkLastUpdateId]");
		}
	}

	public void checkTemplateId(String templateId) throws NotExistException {
		try {
			TemplateInfo info = templateInfoRepository.findOne(templateId);
	        if(info == null) {
	            NotExistException e = new NotExistException("templateId", templateId, "The templateId does not exist.");
	            logger.error("[method: checkTemlateId] The templateID " + templateId + " does not exist.", e);
	            throw e;
	        }
		} catch (DBException e) {
			throw new DBException("DB Runtime Error[class: ArgumrntCheckLogic, method: checkTemplateId]");
		}
    }

	public void checkDocumentId(String documentId) throws NotExistException {
		try {
			DocumentInfo info = documentInfoRepository.findOne(documentId);
			if (info == null) {
				System.out.println(documentId);
				throw new NotExistException("documentId", documentId, "存在しないドキュメントIDです");
			}
		} catch (DBException e) {
			throw new DBException("DB Runtime Error[class: ArgumrntCheckLogic, method: checkDocumentId]");
		}
	}

	public void checkCommentId(String commentId) throws NotExistException {
		try {
			Comment info = commentRepository.findOne(commentId);
			if (info == null) {
				System.out.println(commentId);
				throw new NotExistException("commentId", commentId, "存在しないコメントIDです");
			}
		} catch (DBException e) {
			throw new DBException("DB Runtime Error[class: ArgumrntCheckLogic, method: checkCommentId]");
		}
	}
}
