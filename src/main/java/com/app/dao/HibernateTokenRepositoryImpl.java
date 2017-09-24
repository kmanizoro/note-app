package com.app.dao;

import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.model.LoginDetail;
import com.app.util.AppException;

@Repository("tokenRepositoryDao")
@Transactional
public class HibernateTokenRepositoryImpl extends AbstractDao<String,LoginDetail> implements PersistentTokenRepository {

	private static final Logger logger = Logger.getLogger(HibernateTokenRepositoryImpl.class);
	
	@Override
	public void createNewToken(PersistentRememberMeToken tokenSession) {
		logger.info("Creating new token for :"+tokenSession.getUsername());
		LoginDetail loginDetail = new LoginDetail();
		loginDetail.setUserName(tokenSession.getUsername());
		loginDetail.setLoginSessionid(tokenSession.getTokenValue());
		loginDetail.setLoginLog(tokenSession.getSeries());
		loginDetail.setLoginUpdate(tokenSession.getDate());
		persist(loginDetail);
	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String series) {
		logger.info("Series Id: "+series);
		try{
			Criteria criteria = createEntityCriteria();
			criteria.add(Restrictions.eq("login_log", series));
			LoginDetail loginDetail = (LoginDetail) criteria.uniqueResult();
			return new PersistentRememberMeToken(loginDetail.getUserName(), 
					loginDetail.getLoginLog(), loginDetail.getLoginSessionid(), loginDetail.getLoginUpdate());
	
		}catch(AppException ex){
			logger.info("Token Not Found");
			return null;
		}
	}

	@Override
	public void removeUserTokens(String userName) {
	//	logger.info("Removing Token if any for user : {}"+ userName);
    //    Criteria criteria = createEntityCriteria();
    //    criteria.add(Restrictions.eq("user_name", userName));
    //    LoginDetail loginDetail = (LoginDetail) criteria.uniqueResult();
    //    if (loginDetail != null) {
    //        logger.info("rememberMe was selected");
    //       delete(loginDetail);
    //    }
	}

	@Override
    public void updateToken(String seriesId, String tokenValue, Date lastUsed) {
        logger.info("Updating Token for seriesId : {}"+ seriesId);
        LoginDetail loginDetail = getByKey(seriesId);
        loginDetail.setLoginSessionid(tokenValue);
        loginDetail.setLoginUpdate(lastUsed);
        update(loginDetail);
    }

	
}
