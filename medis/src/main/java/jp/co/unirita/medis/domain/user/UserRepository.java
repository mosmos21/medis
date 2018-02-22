package jp.co.unirita.medis.domain.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findFirstByEmployeeNumber(String id);
    List<String> findByEmployeeNumber();
}
