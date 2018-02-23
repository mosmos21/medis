package jp.co.unirita.medis.logic.system;

import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserLogic implements UserDetailsService{

    @Autowired
    UserRepository userRepository;

    @Override
    public User loadUserByUsername(String id) throws UsernameNotFoundException {
        System.out.println("find user by employeeID(" + id + ")");
        User user = userRepository.findFirstByEmployeeNumber(id);
        System.out.println(user);
        if(user == null) {
            throw new UsernameNotFoundException(String.format("can't find user by employee id (" + id + ")"));
        }
        return user;
    }
}
