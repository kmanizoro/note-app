package com.app.note.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import com.app.note.util.AppConstants;
import com.app.note.util.AppPasswordEncoder;

@Component
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	@Qualifier("appUserSecuritySevice")
	UserDetailsService userDetailsService;
	
	@Autowired
	PersistentTokenRepository persistentTokenRepository;
	
	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.authenticationProvider(authenticationProvider())
		.userDetailsService(userDetailsService)
		.passwordEncoder(getPasswordEncoder());
	}
	
	protected void configure(HttpSecurity httpSecurity) throws Exception{
		httpSecurity.authorizeRequests().antMatchers(
				"/",
				AppConstants.URL_COMMON_ERROR,
				AppConstants.URL_ERRORS,
				AppConstants.URL_LOGIN_ERROR,
				AppConstants.URL_USER_LIST,
				AppConstants.URL_SIGNUP,
				AppConstants.URL_LOGOUT,
				AppConstants.URL_REQUEST_EMAIL_VERIFICATION,
				AppConstants.URL_REQUEST_FORGOT_PASSWORD,
				AppConstants.URL_NEW_USER_VERIFICATION).permitAll();
		
		//httpSecurity.logout().clearAuthentication(true)
		//.logoutSuccessUrl(AppConstants.URL_LOGOUT)
		//.logoutRequestMatcher(new AntPathRequestMatcher(AppConstants.URL_LOGOUT));
		
		///access("hasRole('ADMIN') or hasRole('DBUSER') or hasRole('VIEWER') or hasRole('ENDUSER') ").
		httpSecurity.authorizeRequests().antMatchers(AppConstants.URL_DELETE_USER).access("hasRole('ADMIN')")
		
		.antMatchers(AppConstants.URL_DASHBOARD,
					 AppConstants.URL_WELCOME,
					 AppConstants.URL_SAVE_PROFILE,
					 AppConstants.URL_PROFILE,
					 AppConstants.URL_API,
					 AppConstants.URL_SEND_SUPPORT,
					 AppConstants.URL_SUPPORT,
					 AppConstants.URL_GET_DASHBOARD,
					 AppConstants.URL_SAVE_DASHBOARD,
					 AppConstants.URL_DELETE_DASHBOARD)
		.access("hasRole('ADMIN') or hasRole('DBUSER') or hasRole('ENDUSER')")
		
		
		
		.and().formLogin().loginPage(AppConstants.URL_LOGIN)
		.loginProcessingUrl(AppConstants.URL_LOGIN)
		.defaultSuccessUrl(AppConstants.URL_DASHBOARD, true)
		.successHandler(getAuthenticationSuccessHandler())
		//.failureUrl(AppConstants.URL_LOGIN_ERROR)
		//.failureForwardUrl(AppConstants.URL_LOGIN_ERROR)
		.usernameParameter(AppConstants.PARAM_USER_NAME).passwordParameter(AppConstants.PARAM_USER_PASSWORD)
		
		.and().rememberMe().rememberMeParameter(AppConstants.PARAM_REMEMBER_ME).tokenRepository(persistentTokenRepository)
		.tokenValiditySeconds(300).and().csrf().disable().exceptionHandling().accessDeniedPage(AppConstants.URL_COMMON_ERROR);
		
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder(){
		return new AppPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider(){
		final DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
		return daoAuthenticationProvider;
	}
	
	@Bean
	public AuthenticationSuccessHandler getAuthenticationSuccessHandler(){
		return new AppAuthSuccessHandler();
	}
	
	@Bean
	public PersistentTokenBasedRememberMeServices getPersistentTokenBasedRememberMeServices(){
		PersistentTokenBasedRememberMeServices services = new PersistentTokenBasedRememberMeServices(
								AppConstants.PARAM_REMEMBER_ME, userDetailsService, persistentTokenRepository);
		return services;
	}
	
}
