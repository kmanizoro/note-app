package com.app.note.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.note.bean.UserDetail;

public interface UserDetailRepo extends JpaRepository<UserDetail, Long> {

	@Query(value="SELECT u FROM UserDetail u WHERE upper(u.userMail) = ?1 ")
	UserDetail findByUserEmailId(String emailId);
	
	@Query(value="SELECT u FROM UserDetail u WHERE u.userInd = 1 and upper(u.userName) = ?1 ")
	UserDetail findByUserNameAndActive(String userName);
	
	@Query(value="SELECT u FROM UserDetail u WHERE u.userInd = 1 and upper(u.userName) = ?1 OR upper(u.userMail) = ?1")
	UserDetail getLoginUserDetails(String userNameOrMail);
	
	@Query(value="SELECT u FROM UserDetail u WHERE upper(u.userName) = ?1 ")
	UserDetail findByUserName(String userName);
}
