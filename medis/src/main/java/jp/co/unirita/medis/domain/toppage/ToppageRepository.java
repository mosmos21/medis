package jp.co.unirita.medis.domain.toppage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToppageRepository extends JpaRepository<Toppage, Toppage.PK>{
    List<Toppage> findAllByEmployeeNumberOrderByToppageOrderAsc(String employeeNumber);
}
