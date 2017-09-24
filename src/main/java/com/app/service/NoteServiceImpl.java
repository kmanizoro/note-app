package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.INoteDao;
import com.app.model.LoginDetail;
import com.app.model.NoteInfo;
import com.app.model.TokenDetail;
import com.app.model.UserDetail;
import com.app.util.AppConstants;
import com.app.util.AppUtil;

@Component
@Service
@Transactional
public class NoteServiceImpl implements INoteService{

	@Autowired
	private INoteDao noteDao;
	
	public UserDetail getUserDetails(Long userId) {
		
		if(AppUtil.isNotNull(userId)){
			return noteDao.getUserDetails(userId);
		}
		return null;
	}

	@Override
	public List<UserDetail> getUserDetailList() {
	
		return noteDao.getUserDetailList();
	}

	@Override
	public String updateUserDetails(UserDetail userDetail) {
		if(AppUtil.isNotNull(userDetail) && 
				AppUtil.isNotNull(userDetail.getUserId())){
			return noteDao.updateUserDetails(userDetail);
		}
		return null;
	}

	@Override
	public void deleteUserDetails(Long userId) {
		if(AppUtil.isNotNull(userId)){
			noteDao.deleteUserDetails(userId);
		}
	}

	@Override
	public UserDetail getUserDetails(String userName) {
		if(AppUtil.isNotNull(userName)){
			return	noteDao.getUserDetails(userName);
		}
		return null;
	}
	
	public UserDetail getUserDetailsByEmail(String userEmail){
		if(AppUtil.isNotNull(userEmail)){
			return	noteDao.getUserDetailsByEmail(userEmail);
		}
		return null;
	}

	@Override
	public String createUserDetails(UserDetail userDetails) {
		
		if (AppUtil.isNull(userDetails)) {
			return AppConstants.RESPONSE_ERROR;
		}
		
		return noteDao.createUserDetails(userDetails);
	}

	@Override
	public boolean isUserExistsByUserName(String userName) {
		if (AppUtil.isNotNull(userName)) {
			return noteDao.isUserExistsByUserName(userName);
		}
		return false;
	}

	@Override
	public boolean isUserExistsByEMail(String emailId) {
		if (AppUtil.isNotNull(emailId)) {
			return noteDao.isUserExistsByEMail(emailId);
		}
		return false;
	}
	
	public String createTokenDetails(String userName,TokenDetail tokenDetail){
		if (AppUtil.isNotNull(userName,tokenDetail)) {
			return noteDao.createTokenDetails(userName,tokenDetail);
		}
		return AppConstants.RESPONSE_ERROR;
	}
	
	public List<TokenDetail> getTokenDetailsList(String userName){
		if (AppUtil.isNotNull(userName)) {
			return noteDao.getTokenDetailsList(userName);
		}
		return null;
	}
	
	public TokenDetail getTokenDetails(String tokenHash){
		if (AppUtil.isNotNull(tokenHash)) {
			return noteDao.getTokenDetails(tokenHash);
		}
		return null;
	}
	
	public String activateAllTokenDetails(String userName){
		if (AppUtil.isNotNull(userName)) {
			return noteDao.activateAllTokenDetails(userName);
		}
		return null;
	}

	
	public LoginDetail putUserLoginDetails(LoginDetail loginDetail) {
		if (AppUtil.isNotNull(loginDetail)) {
			return noteDao.putUserLoginDetails(loginDetail);
		}
		return null;
	}
	
	public String saveListOfNoteInfos(List<NoteInfo> listOfNotes){
		if (AppUtil.isNotNull(listOfNotes)) {
			return noteDao.saveListOfNoteInfos(listOfNotes);
		}
		return null;
	}
	
	public String deleteListOfNoteInfos(List<Long> listOfNotes){
		if (AppUtil.isNotNull(listOfNotes)) {
			return noteDao.deleteListOfNoteInfos(listOfNotes);
		}
		return null;
	}
	
	public LoginDetail getLoginDetail(Long loginId){
		if (AppUtil.isNotNull(loginId)) {
			return noteDao.getLoginDetail(loginId);
		}
		return null;
	}
	
	public List<NoteInfo> getListOfNoteInfos(){
		return noteDao.getListOfNoteInfos();
	}
	
	public boolean checkValidUserSession(String sessionId,Long userId){
		if(AppUtil.isNotNull(sessionId,userId)){
			return noteDao.checkValidUserSession(sessionId, userId);
		}
		return false;
	}
	
}
