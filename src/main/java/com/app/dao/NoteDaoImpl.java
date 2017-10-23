package com.app.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.bean.ReadOnlyTrans;
import com.app.model.LoginDetail;
import com.app.model.NoteInfo;
import com.app.model.TokenDetail;
import com.app.model.UserDetail;
import com.app.util.AppConstants;
import com.app.util.AppException;
import com.app.util.AppUtil;

@Repository
@Transactional
public class NoteDaoImpl implements INoteDao{

	private static final Logger logger = Logger.getLogger(NoteDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public NoteDaoImpl(){
	}
	
	public NoteDaoImpl (SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}

	public Session getSession(){
		Session session;
		try{
			session=this.sessionFactory.getCurrentSession();
		}catch(HibernateException exception){
			session =sessionFactory.openSession();
		}
		return session;
	}
	
	@SuppressWarnings("unchecked")
	@ReadOnlyTrans
	public UserDetail getUserDetails(Long userId) {
		List<UserDetail> userDetail = null;
		if(AppUtil.isNotNull(userId)){
			String query 	= "from UserDetail where userId = "+userId;
			Query userQuery = getSession().createQuery(query);
			userDetail		= userQuery.list();
		}
		return (AppUtil.isNotNull(userDetail) ? userDetail.get(0) : null);
	}

	
	
	@SuppressWarnings("unchecked")
	@ReadOnlyTrans
	public UserDetail getUserDetails(String userName) {
		List<UserDetail> userDetail = null;
		try{
			if(AppUtil.isNotNull(userName)){
				String query 	= "from UserDetail where userInd = 1 and upper(userName) = "+"'"+userName.toUpperCase()+"'";
				Query userQuery = getSession().createQuery(query);
				userDetail 		= (List<UserDetail>) userQuery.list();
			}
		}catch(AppException ex){
			throw new AppException(ex);
		}
		return ((userDetail!=null && userDetail.size()>0) ? userDetail.get(0) : null);
	}
	
	@SuppressWarnings("unchecked")
	@ReadOnlyTrans
	public UserDetail getUserDetailsByEmail(String userEmail){
		List<UserDetail> userDetail = null;
		try{
			if(AppUtil.isNotNull(userEmail)){
				String query 	= "from UserDetail where upper(userMail) = :emailId";
				Query userQuery = getSession().createQuery(query);
				userQuery.setParameter("emailId", userEmail.toUpperCase());
				userDetail 		= (List<UserDetail>) userQuery.list();
			}
		}catch(AppException ex){
			throw new AppException(ex);
		}
		return ((userDetail!=null && userDetail.size()>0) ? userDetail.get(0) : null);
	}

	@SuppressWarnings("unchecked")
	@ReadOnlyTrans
	public UserDetail getUserDetailsByUserName(String userName) {
		List<UserDetail> userDetail = null;
		try{
			if(AppUtil.isNotNull(userName)){
				String query 	= "from UserDetail where upper(userName) = "+"'"+userName.toUpperCase()+"'";
				Query userQuery = getSession().createQuery(query);
				userDetail 		= (List<UserDetail>) userQuery.list();
			}
		}catch(AppException ex){
			throw new AppException(ex);
		}
		return ((userDetail!=null && userDetail.size()>0) ? userDetail.get(0) : null);
	}
	
	@ReadOnlyTrans
	public boolean isUserExistsByUserName(String userName){
		UserDetail detail			 = null;
		try{
			if(AppUtil.isNotNull(userName)){
				String query 	= "from UserDetail where upper(userName) = "+"'"+userName.toUpperCase()+"'";
				Query userQuery = getSession().createQuery(query);
				if (AppUtil.isNotNull(userQuery) && 
						(userQuery.list() == null || userQuery.list().size() == 0)) {
					return false; 
				}else{
					detail 			=  (AppUtil.isNotNull(userQuery) && AppUtil.isNotNull(userQuery.list()) 
												&& userQuery.list().size()>0) ?(UserDetail) userQuery.list().get(0) : null;
				}
			}
		}catch(AppException ex){
			throw new AppException(ex);
		}
		return (AppUtil.isNotNull(detail)? (AppUtil.isNotNull(detail.getUserName())) : false);
	}
	@ReadOnlyTrans
	public boolean isUserExistsByEMail(String userEmailId){
		UserDetail detail			 = null;
		try{
			if(AppUtil.isNotNull(userEmailId)){
				String query 	= "from UserDetail where upper(userMail) = "+"'"+userEmailId.toUpperCase()+"'";
				Query userQuery = getSession().createQuery(query);
				if (AppUtil.isNotNull(userQuery) && 
						(userQuery.list() == null || userQuery.list().size() == 0)) {
					return false; 
				}else{
					detail 			=  (AppUtil.isNotNull(userQuery) && AppUtil.isNotNull(userQuery.list()) 
												&& userQuery.list().size()>0) ?(UserDetail) userQuery.list().get(0) : null;
				}
			}
		}catch(AppException ex){
			throw new AppException(ex);
		}
		return (AppUtil.isNotNull(detail)? (AppUtil.isNotNull(detail.getUserName())) : false);
	}
	
	@Transactional
	public String updateUserDetails(UserDetail userDetail) {
		try{
			getSession().saveOrUpdate(userDetail);
			return AppConstants.TRANSACTION_SUCCESS;
		}catch(AppException ex){
			logger.error(ex);
			return AppConstants.TRANSACTION_ERROR;
		}
	}

	@Transactional
	public void deleteUserDetails(Long userId) {
		String query = "update UserDetail set userInd = 0 where userId = "+userId;
		Query userQuery = getSession().createQuery(query);
		userQuery.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@ReadOnlyTrans
	public List<UserDetail> getUserDetailList() {
		List<UserDetail> userList = 
				getSession().
				createCriteria(UserDetail.class).
				setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		return userList;
	}

	@Transactional
	public String createUserDetails(UserDetail userDetails) {
		try{
			if (AppUtil.isNotNull(userDetails) && 
					!isUserExistsByEMail(userDetails.getUserMail()) &&
						!isUserExistsByUserName(userDetails.getUserName())) {
				getSession().persist(userDetails);
			}else{
				return AppConstants.TRANSACTION_ERROR;
			}	
		}catch(Exception ex){
			logger.error(ex);
			return AppConstants.TRANSACTION_ERROR;
		}
		return AppConstants.TRANSACTION_SUCCESS;
	}
	
	@Transactional
	public String createTokenDetails(String userName,TokenDetail tokenDetail){
		try{
			UserDetail userDetail = getUserDetailsByUserName(userName);
			if (AppUtil.isNotNull(userDetail) && isUserExistsByUserName(userName)) {
				tokenDetail.setUserName(userName);
				//tokenDetail.setUserId(userDetail.getUserId());
				tokenDetail.setUserDetail(userDetail);
				getSession().persist(tokenDetail);
			}else{
				return AppConstants.TRANSACTION_ERROR;
			}
		}catch(Exception ex){
			logger.error(ex);
			return AppConstants.TRANSACTION_ERROR;
		}
		return AppConstants.TRANSACTION_SUCCESS;
	}
	
	@Transactional
	public String activateAllTokenDetails(String userName){
		try{
			List<TokenDetail> tokenDetailsList = getTokenDetailsList(userName);
			if (AppUtil.isNotNull(tokenDetailsList) && tokenDetailsList.size()>0) {
				Iterator<TokenDetail> tokenIter = tokenDetailsList.iterator();
				while(tokenIter.hasNext()){
					TokenDetail tokenDetail = tokenIter.next();
					if (AppUtil.isNotNull(tokenDetail)){
						tokenDetail.setTokenInd(AppConstants.VALID_IND);
						getSession().merge(tokenDetail);
					}
				}
				return AppConstants.TRANSACTION_SUCCESS;
			}else{
				return AppConstants.TRANSACTION_ERROR;
			}
		}catch(Exception ex){
			logger.error(ex);
			return AppConstants.TRANSACTION_ERROR;
		}
	}

	@SuppressWarnings("unchecked")
	@ReadOnlyTrans
	public TokenDetail getTokenDetails(String tokenHash){
		TokenDetail tokenDetail = null;
		List<TokenDetail> queryList	= null;
		try{
			String query 	 = "from TokenDetail where tokenHash = :tokenHash";
			Query tokenQuery = getSession().createQuery(query);
			tokenQuery.setParameter("tokenHash", tokenHash);
			queryList = tokenQuery.list();
			if (AppUtil.isNotNull(queryList) && queryList.size()>0) {
				tokenDetail = (TokenDetail) queryList.get(0);
			}
		}catch(Exception exception){
			logger.error(exception);
		}
		return tokenDetail;
	}
	
	@SuppressWarnings("unchecked")
	@ReadOnlyTrans
	public List<TokenDetail> getTokenDetailsList(String userName){
		List<TokenDetail> resultList	= null;
		try{
			String query 	 = "from TokenDetail where userName = :userName";
			Query tokenQuery = getSession().createQuery(query);
			tokenQuery.setParameter("userName", userName);
			resultList = tokenQuery.list();
		}catch(Exception exception){
			logger.error(exception);
		}
		return resultList;
	}
	
	@Transactional
	public LoginDetail putUserLoginDetails(LoginDetail loginDetail){
		try{
			if (AppUtil.isNotNull(loginDetail)) {
				getSession().save(loginDetail);
			}
		}catch(Exception ex){
			logger.error(ex);
			throw new AppException(ex);
		}
		return loginDetail;
	}
	
	@Transactional
	public String saveListOfNoteInfos(List<NoteInfo> listOfNotes){
		try{
			for(NoteInfo noteInfo:listOfNotes) {
				if(noteInfo!=null){
					if(checkOldNote(noteInfo.getNoteId())){
						getSession().saveOrUpdate("NoteInfo", noteInfo);
					}else{
						getSession().merge(noteInfo);
					}
				}
			}
		}catch(Exception ex){
			logger.error(ex);
			//throw new AppException(ex);
			return AppConstants.TRANSACTION_ERROR;
		}
		return AppConstants.TRANSACTION_SUCCESS;
	}
	
	@ReadOnlyTrans
	public boolean checkOldNote(Integer noteId){
		Long countRows = null;
		try{
			if(AppUtil.isNotNull(noteId)){
				String query = "Select count(noteId) from NoteInfo where noteId = :noteId and (noteTitle!='' or noteDescription!='')";
				Query noteInfoQuery = getSession().createQuery(query);
				noteInfoQuery.setParameter("noteId", noteId);
				countRows = (Long) noteInfoQuery.uniqueResult();
			}
		}catch(Exception ex){
			logger.error("checkValidUserSession ",ex);
			return false;
		}
		return (countRows!=null && countRows>0);
	}
	
	@ReadOnlyTrans
	public NoteInfo getNoteInfoByNoteId(Integer noteId){
		NoteInfo noteInfo = null;
		try{
			if(AppUtil.isNotNull(noteId)) {
				//getSession().persist(noteInfo);
				noteInfo = getSession().get(NoteInfo.class, noteId);
			}
		}catch(Exception ex){
			logger.error(ex);
			return null;
		}
		return noteInfo;
	}
	
	@Transactional
	public String deleteListOfNoteInfos(List<Long> listOfNotes){
		try{
			for(Long noteId:listOfNotes) {
				if(noteId!=null && noteId!=0){
					String query = "update NoteInfo set noteInd = :noteInd where noteId = :noteId";
					Query deleteQuery = getSession().createQuery(query);
					deleteQuery.setParameter("noteInd", new Integer(0));
					deleteQuery.setParameter("noteId", noteId.intValue());
					deleteQuery.executeUpdate();
				}
			}
		}catch(Exception ex){
			logger.error(ex);
			return AppConstants.TRANSACTION_ERROR;
		}
		return AppConstants.TRANSACTION_SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@ReadOnlyTrans
	public LoginDetail getLoginDetail(Long loginId) {
		List<LoginDetail> loginDetail = null;
		if(AppUtil.isNotNull(loginId)){
			String query 	= "from LoginDetail where loginId = "+loginId;
			Query loginQuery = getSession().createQuery(query);
			loginDetail		= loginQuery.list();
		}
		return (AppUtil.isNotNull(loginDetail) ? loginDetail.get(0) : null);
	}
	
	@SuppressWarnings("unchecked")
	@ReadOnlyTrans
	public List<NoteInfo> getListOfNoteInfos(Long userId) {
		List<NoteInfo> noteList = null;
		try{
			String query = "from NoteInfo where userDetail.userId = :userId";
			Query userNotesQuery = getSession().createQuery(query);
			userNotesQuery.setParameter("userId", userId);
			noteList = userNotesQuery.list();
		}catch(Exception ex){
			logger.error("Error in getListOfNoteInfos "+ex);
		}	
		return noteList;
	}
	
	@ReadOnlyTrans
	public boolean checkValidUserSession(String sessionId,Long userId){
		Long countRows = null;
		try{
			if(AppUtil.isNotNull(sessionId) && AppUtil.isNotNull(userId)){
				String query = "Select count(loginId) from LoginDetail where loginSessionid = :sessionId and loginActUserid = :userId";
				Query loginQuery = getSession().createQuery(query);
				loginQuery.setParameter("sessionId", sessionId);
				loginQuery.setParameter("userId", userId);
				countRows = (Long) loginQuery.uniqueResult();
			}
		}catch(AppException ex){
			logger.error("checkValidUserSession ",ex);
			return false;
		}
		return (countRows>0);
	}
}
