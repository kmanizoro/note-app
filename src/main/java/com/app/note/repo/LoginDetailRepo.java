package com.app.note.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.note.bean.LoginDetail;

public interface LoginDetailRepo extends JpaRepository<LoginDetail, Long> {

	@Query(value = "SELECT COUNT(loginId) FROM LoginDetail WHERE loginSessionid = ?1 and loginActUserid = ?2")
	Long checkValidUserSession(String sessionId, Long userId);
	
	@Query(value = "SELECT l FROM LoginDetail l WHERE l.loginLog = ?1")
	LoginDetail getLoginDetailsByLog(String log);
}
