package com.app.note.repo;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;

import com.app.note.bean.LoginDetail;
import com.app.note.util.AppException;
import com.app.note.util.AppUtil;

@Repository("tokenRepositoryDao")
//@Transactional
public class HibernateTokenRepositoryImpl implements PersistentTokenRepository {

	private static final Logger logger = LogManager.getLogger(HibernateTokenRepositoryImpl.class);
	
	@Autowired
	LoginDetailRepo loginDetailRepo;
	
	@Override
	public void createNewToken(PersistentRememberMeToken tokenSession) {
		logger.info("Creating new token for :"+tokenSession.getUsername());
		LoginDetail loginDetail = new LoginDetail();
		loginDetail.setUserName(tokenSession.getUsername());
		loginDetail.setLoginSessionid(tokenSession.getTokenValue());
		loginDetail.setLoginLog(tokenSession.getSeries());
		loginDetail.setLoginUpdate(tokenSession.getDate());
		loginDetailRepo.save(loginDetail);
	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String series) {
		logger.info("Series Id: "+series);
		try{;
			LoginDetail loginDetail = loginDetailRepo.getLoginDetailsByLog(series);
			return new PersistentRememberMeToken(loginDetail.getUserName(), 
					loginDetail.getLoginLog(), loginDetail.getLoginSessionid(), loginDetail.getLoginUpdate());
	
		}catch(AppException ex){
			logger.info("Token Not Found");
			return null;
		}
	}

	@Override
	public void removeUserTokens(String userName) {

	}

	@Override
    public void updateToken(String seriesId, String tokenValue, Date lastUsed) {
        logger.info("Updating Token for seriesId : {}"+ seriesId);
        if(AppUtil.isNotNull(seriesId, tokenValue)) {
        	LoginDetail loginDetail = loginDetailRepo.findById(new Long(seriesId)).orElse(new LoginDetail());
            loginDetail.setLoginSessionid(tokenValue);
            loginDetail.setLoginUpdate(lastUsed);
            loginDetailRepo.save(loginDetail);
        }
    }

	
}
