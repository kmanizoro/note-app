package com.app.note.service;

import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.note.bean.LoginDetail;
import com.app.note.bean.NoteInfo;
import com.app.note.bean.ReadOnlyTrans;
import com.app.note.bean.TokenDetail;
import com.app.note.bean.UserDetail;
import com.app.note.repo.LoginDetailRepo;
import com.app.note.repo.NoteInfoRepo;
import com.app.note.repo.TokenDetailRepo;
import com.app.note.repo.UserDetailRepo;
import com.app.note.util.AppConstants;
import com.app.note.util.AppException;
import com.app.note.util.AppUtil;

@Service
public class NoteServiceImpl implements INoteService{

	private static final Logger logger = LogManager.getLogger(NoteServiceImpl.class);

	@Autowired
	LoginDetailRepo loginDetailRepo;

	@Autowired
	NoteInfoRepo noteInfoRepo;

	@Autowired
	UserDetailRepo userDetailRepo;
	
	@Autowired
	TokenDetailRepo tokenDetailRepo;

	@ReadOnlyTrans
	public UserDetail getUserDetails(Long userId) {
		UserDetail userDetail = null;
		if (AppUtil.isNotNull(userId)) {
			userDetail = userDetailRepo.findById(userId).orElse(null);
		}
		return userDetail;
	}

	@ReadOnlyTrans
	public UserDetail getUserDetails(String userName) {
		UserDetail userDetail = null;
		try {
			if (AppUtil.isNotNull(userName)) {
				userDetail = userDetailRepo.findByUserNameAndActive(userName.toUpperCase());
			}
		} catch (AppException ex) {
			throw new AppException(ex);
		}
		return userDetail;
	}
	
	@ReadOnlyTrans
	public UserDetail getLoginUserDetails(String userNameOrMail) {
		UserDetail userDetail = null;
		try {
			if (AppUtil.isNotNull(userNameOrMail)) {
				userDetail = userDetailRepo.getLoginUserDetails(userNameOrMail.toUpperCase());
			}
		} catch (AppException ex) {
			throw new AppException(ex);
		}
		return userDetail;
	}

	@ReadOnlyTrans
	public UserDetail getUserDetailsByEmail(String userEmail) {
		UserDetail userDetail = null;
		try {
			if (AppUtil.isNotNull(userEmail)) {
				userDetail = userDetailRepo.findByUserEmailId(userEmail.toUpperCase());
			}
		} catch (AppException ex) {
			throw new AppException(ex);
		}
		return userDetail;
	}

	@ReadOnlyTrans
	public UserDetail getUserDetailsByUserName(String userName) {
		UserDetail userDetail = null;
		try {
			if (AppUtil.isNotNull(userName)) {
				userDetail = userDetailRepo.findByUserNameAndActive(userName.toUpperCase());
			}
		} catch (AppException ex) {
			throw new AppException(ex);
		}
		return userDetail;
	}

	@ReadOnlyTrans
	public boolean isUserExistsByUserName(String userName) {
		UserDetail detail = getUserDetailsByUserName(userName);
		return (AppUtil.isNotNull(detail) ? (AppUtil.isNotNull(detail.getUserName())) : false);
	}

	@ReadOnlyTrans
	public boolean isUserExistsByEMail(String userEmailId) {
		UserDetail detail = getUserDetailsByEmail(userEmailId);
		return (AppUtil.isNotNull(detail) ? (AppUtil.isNotNull(detail.getUserName())) : false);
	}

	@Transactional
	public String updateUserDetails(UserDetail userDetail) {
		try {
			userDetailRepo.save(userDetail);
			return AppConstants.TRANSACTION_SUCCESS;
		} catch (AppException ex) {
			logger.error(ex);
			return AppConstants.TRANSACTION_ERROR;
		}
	}

	@Transactional
	public void deleteUserDetails(Long userId) {
		if(AppUtil.isNotNull(userId)) {
			UserDetail details = userDetailRepo.findById(userId).orElse(null);
			if(AppUtil.isNotNull(details)) {
				details.setUserInd(0);
				userDetailRepo.save(details);
			}
		}
	}

	@ReadOnlyTrans
	public List<UserDetail> getUserDetailList() {
		List<UserDetail> userList = userDetailRepo.findAll();
		return userList;
	}

	@Transactional
	public String createUserDetails(UserDetail userDetails) {
		try {
			if (AppUtil.isNotNull(userDetails) && !isUserExistsByEMail(userDetails.getUserMail())
					&& !isUserExistsByUserName(userDetails.getUserName())) {
				userDetailRepo.save(userDetails);
			} else {
				return AppConstants.TRANSACTION_ERROR;
			}
		} catch (Exception ex) {
			logger.error(ex);
			return AppConstants.TRANSACTION_ERROR;
		}
		return AppConstants.TRANSACTION_SUCCESS;
	}

	@Transactional
	public String createTokenDetails(String userName, TokenDetail tokenDetail) {
		try {
			UserDetail userDetail = userDetailRepo.findByUserName(userName);
			if (AppUtil.isNotNull(userDetail)) {
				tokenDetail.setUserName(userName);
				tokenDetail.setUserDetail(userDetail);
				tokenDetailRepo.save(tokenDetail);
			} else {
				return AppConstants.TRANSACTION_ERROR;
			}
		} catch (Exception ex) {
			logger.error(ex);
			return AppConstants.TRANSACTION_ERROR;
		}
		return AppConstants.TRANSACTION_SUCCESS;
	}

	@Transactional
	public String activateAllTokenDetails(String userName) {
		try {
			List<TokenDetail> tokenDetailsList = getTokenDetailsList(userName);
			if (AppUtil.isNotNull(tokenDetailsList) && tokenDetailsList.size() > 0) {
				Iterator<TokenDetail> tokenIter = tokenDetailsList.iterator();
				while (tokenIter.hasNext()) {
					TokenDetail tokenDetail = tokenIter.next();
					if (AppUtil.isNotNull(tokenDetail)) {
						tokenDetail.setTokenInd(AppConstants.VALID_IND);
						tokenDetailRepo.save(tokenDetail);
					}
				}
				return AppConstants.TRANSACTION_SUCCESS;
			} else {
				return AppConstants.TRANSACTION_ERROR;
			}
		} catch (Exception ex) {
			logger.error(ex);
			return AppConstants.TRANSACTION_ERROR;
		}
	}

	@ReadOnlyTrans
	public TokenDetail getTokenDetails(String tokenHash) {
		TokenDetail tokenDetail = null;
		try {
			tokenDetail = tokenDetailRepo.findByTokenHash(tokenHash);
		} catch (Exception exception) {
			logger.error(exception);
		}
		return tokenDetail;
	}

	@ReadOnlyTrans
	public List<TokenDetail> getTokenDetailsList(String userName) {
		List<TokenDetail> resultList = null;
		try {
			resultList = tokenDetailRepo.findAllByUserName(userName);
		} catch (Exception exception) {
			logger.error(exception);
		}
		return resultList;
	}

	@Transactional
	public LoginDetail putUserLoginDetails(LoginDetail loginDetail) {
		try {
			if (AppUtil.isNotNull(loginDetail)) {
				loginDetailRepo.save(loginDetail);
			}
		} catch (Exception ex) {
			logger.error(ex);
			throw new AppException(ex);
		}
		return loginDetail;
	}

	@Transactional
	public String saveListOfNoteInfos(List<NoteInfo> listOfNotes) {
		try {
			noteInfoRepo.saveAll(listOfNotes);
		} catch (Exception ex) {
			logger.error(ex);
			return AppConstants.TRANSACTION_ERROR;
		}
		return AppConstants.TRANSACTION_SUCCESS;
	}

	@ReadOnlyTrans
	public boolean checkOldNote(Integer noteId) {
		Long countRows = null;
		try {
			if (AppUtil.isNotNull(noteId)) {
				countRows = noteInfoRepo.checkOldNote(noteId);
			}
		} catch (Exception ex) {
			logger.error("checkValidUserSession ", ex);
			return false;
		}
		return (countRows != null && countRows > 0);
	}

	@ReadOnlyTrans
	public NoteInfo getNoteInfoByNoteId(Integer noteId) {
		NoteInfo noteInfo = null;
		try {
			if (AppUtil.isNotNull(noteId)) {
				noteInfo = noteInfoRepo.findById(noteId).orElse(null);
			}
		} catch (Exception ex) {
			logger.error(ex);
			return null;
		}
		return noteInfo;
	}

	@Transactional
	public String deleteListOfNoteInfos(List<Long> listOfNotes) {
		try {
			for (Long noteId : listOfNotes) {
				if (noteId != null && noteId != 0) {
					NoteInfo noteInfo = noteInfoRepo.findById(noteId.intValue()).orElse(null);
					if(noteInfo!=null) {
						noteInfo.setNoteInd(0);
						noteInfoRepo.save(noteInfo);
					}
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
			return AppConstants.TRANSACTION_ERROR;
		}
		return AppConstants.TRANSACTION_SUCCESS;
	}

	@ReadOnlyTrans
	public LoginDetail getLoginDetail(Long loginId) {
		LoginDetail loginDetail = null;
		if (AppUtil.isNotNull(loginId)) {
			loginDetail = loginDetailRepo.findById(loginId).orElse(null);
		}
		return loginDetail;
	}

	@ReadOnlyTrans
	public List<NoteInfo> getListOfNoteInfos(Long userId) {
		List<NoteInfo> noteList = null;
		try {
			noteList = noteInfoRepo.getListOfNoteInfosByUserId(userId);
		} catch (Exception ex) {
			logger.error("Error in getListOfNoteInfos " + ex);
		}
		return noteList;
	}

	@ReadOnlyTrans
	public boolean checkValidUserSession(String sessionId, Long userId) {
		Long countRows = null;
		try {
			if (AppUtil.isNotNull(sessionId) && AppUtil.isNotNull(userId)) {
				countRows = loginDetailRepo.checkValidUserSession(sessionId, userId);
			}
		} catch (AppException ex) {
			logger.error("checkValidUserSession ", ex);
			return false;
		}
		return (countRows > 0);
	}
}
