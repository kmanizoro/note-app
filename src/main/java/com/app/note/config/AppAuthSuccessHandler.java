package com.app.note.config;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.app.note.bean.LoginDetail;
import com.app.note.bean.UserDetail;
import com.app.note.service.INoteService;
import com.app.note.util.AppConstants;
import com.app.note.util.AppUtil;

public class AppAuthSuccessHandler implements AuthenticationSuccessHandler{

	@Autowired
	private INoteService noteService;
	
	private static final Logger logger = LogManager.getLogger(AppAuthSuccessHandler.class);
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth)
			throws IOException, ServletException {
		HttpSession httpSession = req.getSession();
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(httpSession!=null){
			putLoginDetails(user,httpSession);
		}
		redirectStrategy.sendRedirect(req, res, determinTargetUrl(auth));
		clearAutheticationAttributes(req);
	}

	protected String determinTargetUrl(Authentication authentication){
		Collection<? extends GrantedAuthority> collAuths = authentication.getAuthorities();
		if(collAuths!=null && !collAuths.isEmpty()){
			for(GrantedAuthority authority:collAuths){
				if(authority!=null && 
						(("ROLE_ADMIN").equals(authority.getAuthority())||
								("ROLE_ADMIN").equals(authority.getAuthority()) ||
										("ROLE_ENDUSER").equals(authority.getAuthority()))){
					return AppConstants.URL_DASHBOARD;
				}
			}
		}
		return AppConstants.URL_PROFILE;
	}
	
	protected void clearAutheticationAttributes(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if(session==null){
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
	
	private void putLoginDetails(User user,HttpSession session){
		UserDetail detail = null;
		try{
			detail = noteService.getUserDetails(user.getUsername()); 
			if(AppUtil.isNotNull(detail)){
				
				String tmpSessionId = AppUtil.getTokenString();
				
				LoginDetail loginDetail = new LoginDetail();
				loginDetail.setUserDetail(detail);
				loginDetail.setUserName(detail.getUserName());
				loginDetail.setLoginActUserid(detail.getUserId());
				loginDetail.setLoginLog(session.getId());
				loginDetail.setLoginSessionid(tmpSessionId);
				loginDetail.setLoginInd((byte)AppConstants.VALID_IND);
				loginDetail.setLoginUpdate(AppUtil.getCurrentDate());
				
				loginDetail = noteService.putUserLoginDetails(loginDetail);
				
				session.setAttribute(AppConstants.SESSION_USER_NAME, detail.getUserName());
				session.setAttribute(AppConstants.SESSION_USER_DISP_NAME, detail.getUserDisplayname());
				session.setAttribute(AppConstants.SESSION_USER_ID, detail.getUserId());
				session.setAttribute(AppConstants.SESSION_LOGIN_ID, loginDetail.getLoginId());
				session.setAttribute(AppConstants.SESSION_LOGIN_SESSION_ID, tmpSessionId);
			}
			
		}catch(Exception ex){
			logger.error("Error In putLoginDetails ",ex);
		}
	}
	
	public RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

}
