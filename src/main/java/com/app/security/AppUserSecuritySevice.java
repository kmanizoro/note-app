package com.app.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.model.UserDetail;
import com.app.service.INoteService;

@Service("appUserSecuritySevice")
public class AppUserSecuritySevice implements UserDetailsService {

	private static final Logger logger = LogManager.getLogger(AppUserSecuritySevice.class);
	
	@Autowired
	private INoteService noteService;
	
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
		UserDetail userDetail = null;
		try{
			userDetail = noteService.getUserDetails(userName);
			logger.info("UserDetails {}"+userDetail);
			if(userDetail==null){
				throw new UsernameNotFoundException("UserName Not Found");
			}
		}catch(Exception ex){
			logger.error("Error in loadUserByUsername", ex);
		}
		return new User(userDetail.getUserName(),userDetail.getUserPass(),true,true,true,true,grantedAuthorities(userDetail));
	}
	
	private List<GrantedAuthority> grantedAuthorities(UserDetail userDetail)  {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+userDetail.getUserRole()));
		return grantedAuthorities;
	}
	
}
