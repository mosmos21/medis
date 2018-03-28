package jp.co.unirita.medis.logic.system;

import java.lang.invoke.MethodHandles;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.unirita.medis.domain.tempkeyInfo.TempkeyInfo;
import jp.co.unirita.medis.domain.tempkeyInfo.TempkeyInfoRepository;
import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.domain.user.UserRepository;
import jp.co.unirita.medis.domain.userdetail.UserDetail;
import jp.co.unirita.medis.domain.userdetail.UserDetailRepository;
import jp.co.unirita.medis.util.exception.DBException;
import jp.co.unirita.medis.util.exception.NotExistException;

@Service
@SpringBootApplication
@Transactional(rollbackFor = Exception.class)
public class AccountLogic {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final long MILLIS_OF_30_MINUTES = 1800000L;

    @Autowired
    UserRepository userRepository;
	@Autowired
	UserDetailRepository userDetailRepository;
	@Autowired
	TempkeyInfoRepository tempkeyInfoRepository;


    public UserDetail getUserDetail(String employeeNumber) {
    	try {
    		return userDetailRepository.findOne(employeeNumber);
    	} catch (DBException e) {
    		logger.error("DB Runtime Error[class: AccountLogic, method: getUserDetail]");
			throw new DBException("DB Runtime Error[class: AccountLogic, method: getUserDetail]");
		}
    }

    public Map<String, String> checkUserIntegrity(String employeeNumber, String mailaddress) {
    	try {
    		Map<String, String> result = new HashMap<>();
            UserDetail detail = userDetailRepository.findOne(employeeNumber);
            if (detail == null) {
                result.put("result", "NG");
                result.put("message", "入力された社員番号が存在しません");
                return result;
            }
            if (!mailaddress.equals(detail.getMailaddress())) {
                result.put("result", "NG");
                result.put("message", "入力された社員番号とメールアドレスが不正です");
                return result;
            }
            result.put("result", "OK");
            return result;
    	} catch (DBException e) {
    		logger.error("DB Runtime Error[class: AccountLogic, method: checkUserIntegrity]");
			throw new DBException("DB Runtime Error[class: AccountLogic, method: checkUserIntegrity]");
		}
    }

    public String issueTempKey(String employeeNumber) {
    	try {
    		TempkeyInfo info = new TempkeyInfo();
            info.setEmployeeNumber(employeeNumber);
            info.setTempKey(UUID.randomUUID().toString().replace("-", ""));
            info.setChangeDate(new Timestamp(System.currentTimeMillis()));
            tempkeyInfoRepository.saveAndFlush(info);
            return info.getTempKey();
    	} catch (DBException e) {
    		logger.error("DB Runtime Error[class: AccountLogic, method: issueTempKey]");
			throw new DBException("DB Runtime Error[class: AccountLogic, method: issueTempKey]");
		}
    }

	public Map<String, String> checkTempKeyIntegrity(String key) {
		try {
			Map<String, String> result = new HashMap<>();
			TempkeyInfo info = tempkeyInfoRepository.findByTempKey(key);
	        if(info == null) {
	            result.put("result", "NG");
	            result.put("message", "登録されていないキーを使用しています");
	            return result;

	        }
			if (new Timestamp(System.currentTimeMillis() - MILLIS_OF_30_MINUTES).after(info.getChangeDate())) {
	            result.put("result", "NG");
	            result.put("message", "メール発行から30分を過ぎています");
	            return result;
			}
	        result.put("result", "OK");
	        UserDetail detail = userDetailRepository.findOne(info.getEmployeeNumber());
	        result.put("employeeNumber", detail.getEmployeeNumber());
	        result.put("mailaddress", detail.getMailaddress());
	        return result;
		} catch (DBException e) {
			logger.error("DB Runtime Error[class: AccountLogic, method: checkTempKeyIntegrity]");
			throw new DBException("DB Runtime Error[class: AccountLogic, method: checkTempKeyIntegrity]");
		}
	}

	public void passwordReset(String employeeNumber, String mailadress, String password) throws NotExistException {
		try {
			UserDetail detail = userDetailRepository.findOne(employeeNumber);
	        if(detail == null) {
	            NotExistException e =  new NotExistException("employeeNumber", employeeNumber, "存在していない社員番号を参照しています");
	            logger.error("error in passwordRest()", e);
	            throw e;
	        }
			if (userDetailRepository.findByEmployeeNumberAndMailaddress(employeeNumber,mailadress) == null) {
	            NotExistException e = new NotExistException("mailaddress", mailadress,"ユーザ名とメールアドレスの組み合わせが間違っています");
	            logger.error("error in passwordRest()", e);
	            throw e;
	        }
			User user = userRepository.findOne(employeeNumber);
			user.setPassword(new BCryptPasswordEncoder().encode(password));
			userRepository.saveAndFlush(user);
		} catch (DBException e) {
			logger.error("DB Runtime Error[class: AccountLogic, method: passwordReset]");
			throw new DBException("DB Runtime Error[class: AccountLogic, method: passwordReset]");
		}
	}
}