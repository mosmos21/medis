package jp.co.unirita.medis.logic.system;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.domain.user.UserRepository;
import jp.co.unirita.medis.util.exception.DBException;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserLogic implements UserDetailsService{

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    UserRepository userRepository;

    /**
     * ユーザのロードを行う
     * @param id ロードを行う社員番号
     * @return ロードされたユーザデータ(@see jp.co.unirita.medis.domain.user.User)
     * @throws UsernameNotFoundException ユーザが見つからなかった場合に発生する例外
     */
    @Override
    public User loadUserByUsername(String id) throws UsernameNotFoundException {
    	try {
    		User user = userRepository.findOne(id);
            System.out.println(user);
            if(user == null) {
                UsernameNotFoundException e = new UsernameNotFoundException(String.format("can't find user by employee id (" + id + ")"));
                logger.error("error in loadUserByUsername()", e);
                throw e;
            }
            return user;
    	} catch (DBException e) {
    		logger.error("DB Runtime Error[class: UserLogic, method: loadUserByUsername]");
			throw new DBException("DB Runtime Error[class: UserLogic, method: loadUserByUsername]");
		}
    }
}
