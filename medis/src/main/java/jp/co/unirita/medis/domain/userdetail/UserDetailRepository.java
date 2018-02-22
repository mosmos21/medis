package jp.co.unirita.medis.domain.userdetail;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetail,String>{
List<UserDetail> findAllByEmployeeNumber(String employeeNumber);
UserDetail findByEmployeeNumberAndMailaddress(String employeeNumber,String mailaddress);
}
