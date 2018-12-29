package com.app.note.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.note.bean.UserDetail;
import com.app.note.service.INoteService;

@Service("appUserSecuritySevice")
public class AppUserSecuritySevice implements UserDetailsService {

	private static final Logger logger = LogManager.getLogger(AppUserSecuritySevice.class.getName());
	
	@Autowired
	private INoteService noteService;
	
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
		UserDetail userDetail = null;
		try{
			userDetail = noteService.getLoginUserDetails(userName);
			logger.info("UserDetails {}"+userDetail);
			if(userDetail==null){
				throw new UsernameNotFoundException("UserName Not Found");
			}
		}catch(Exception ex){
			//logger.error("Error in loadUserByUsername", ex);
		}
		return new User(userDetail.getUserName(),userDetail.getUserPass(),true,true,true,true,grantedAuthorities(userDetail));
	}
	
	private List<GrantedAuthority> grantedAuthorities(UserDetail userDetail)  {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+userDetail.getUserRole()));
		return grantedAuthorities;
	}
	
}
