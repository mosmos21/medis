package jp.co.unirita.medis.domain.authority;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {
	Authority findFirstByAuthorityId(String id);
}