package com.app.note.repo;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.app.note.bean.LoginDetail;
import com.app.note.bean.NoteInfo;
import com.app.note.bean.TokenDetail;
import com.app.note.bean.UserDetail;

@Transactional
public interface INoteDao {

	public List<UserDetail> getUserDetailList();
	
	public UserDetail getUserDetails(Long userId);
	
	public UserDetail getUserDetails(String userName);
	
	public UserDetail getUserDetailsByEmail(String userEmail);
	
	public String updateUserDetails(UserDetail userDetail);
	
	public void deleteUserDetails(Long userId);
	
	public String createUserDetails(UserDetail userDetails);
	
	public boolean isUserExistsByUserName(String userName);
	
	public boolean isUserExistsByEMail(String emailId);
	
	public String createTokenDetails(String userName,TokenDetail tokenDetail);
	
	public List<TokenDetail> getTokenDetailsList(String userName);
	
	public TokenDetail getTokenDetails(String tokenHash);
	
	public String activateAllTokenDetails(String userName);
	
	public LoginDetail putUserLoginDetails(LoginDetail loginDetail);
	
	public String saveListOfNoteInfos(List<NoteInfo> listOfNotes);
	
	public String deleteListOfNoteInfos(List<Long> listOfNotes);
	
	public LoginDetail getLoginDetail(Long loginId);
	
	public List<NoteInfo> getListOfNoteInfos(Long userId);
	
	public boolean checkValidUserSession(String sessionId,Long userId);
}
