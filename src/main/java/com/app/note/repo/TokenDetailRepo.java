package com.app.note.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.note.bean.TokenDetail;

public interface TokenDetailRepo extends JpaRepository<TokenDetail, Integer> {

	@Query(value="SELECT t FROM TokenDetail t WHERE t.tokenHash = ?1")
	TokenDetail findByTokenHash(String hashVal);
	
	@Query(value="SELECT t FROM TokenDetail t WHERE t.userName = ?1")
	List<TokenDetail> findAllByUserName(String userName);
}
