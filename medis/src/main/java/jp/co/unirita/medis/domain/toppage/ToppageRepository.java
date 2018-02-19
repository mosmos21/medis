package jp.co.unirita.medis.domain.toppage;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToppageRepository extends JpaRepository<Toppage, Toppage.PK>{
    List<Toppage> findAllByEmployeeNumberOrderByToppageOrderAsc(String employeeNumber);
}
