package com.app.note.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.app.note.bean.NoteDetailsVO;
import com.app.note.bean.NoteInfo;
import com.app.note.bean.UserDetail;
import com.app.note.bean.UserType;
import com.app.note.service.INoteService;
import com.app.note.util.AppConstants;
import com.app.note.util.AppException;
import com.app.note.util.AppHelper;
import com.app.note.util.AppPasswordEncoder;
import com.app.note.util.AppUtil;
import com.app.note.util.JSONUtil;

@Controller
@RequestMapping(value=AppConstants.PAGE_ROOT)
public class AppUserController {

	private static final Logger logger = LogManager.getLogger(AppUserController.class);
	
	@Autowired
	INoteService noteService;

	@Autowired
	AppHelper appHelper;
	
	static AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();
	
	@Autowired
	PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices; 
	
	@Autowired
	MessageSource messageSource;

	@Autowired
	EmailController emailController; 
	
	@RequestMapping(value=AppConstants.PAGE_ROOT)
	public String welcomePage(){
		if(logger.isDebugEnabled()){
			logger.info("Welocme Page");
		}
		return AppUtil.rediredtedTo(AppConstants.URL_DASHBOARD);
	}
	
	@RequestMapping(value=AppConstants.URL_LOGIN,method={RequestMethod.GET,RequestMethod.POST})
	public String loginPage(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(name=AppConstants.PARAM_USER_NAME,required=false) String requestUserName,
			ModelMap modelMap, Locale lang){
		if(logger.isDebugEnabled()){
			logger.info(AppConstants.PAGE+AppConstants.PAGE_LOGIN);
		}
		try{
			AppUtil.setLocaleContextHolder(lang);
			if(AppConstants.REQUEST_METHOD_GET.equalsIgnoreCase(request.getMethod())){
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				if(authentication!=null){
					//if(isCurAuthenticationAnonymous() && AppUtil.isNotNull(requestUserName)){
					//}
					SecurityContextHolder.getContext().setAuthentication(null);
				}
				return AppConstants.PAGE_LOGIN;
			} else if(AppConstants.REQUEST_METHOD_POST.equalsIgnoreCase(request.getMethod())){
			
				if(isCurAuthenticationAnonymous()){
					modelMap.put(AppConstants.MESSAGE_ERROR, messageSource.
								getMessage(AppConstants.ERROR_INVALID_USER, null, lang));
					return AppConstants.PAGE_LOGIN;
				}else{
					return AppConstants.URL_USER_LIST;
				}
			}
			return AppConstants.PAGE_LOGIN;
		}catch(AppException exception){
			throw new AppException(exception);
		}finally{
			if(logger.isDebugEnabled()){
				logger.info("End :"+AppConstants.PAGE+AppConstants.PAGE_LOGIN);
			}
		}
	}
	
	@RequestMapping(value=AppConstants.URL_PROFILE,method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView getProfilePage(
			HttpServletRequest request,ModelMap modelMap,Locale lang){
		String retrunTo = AppConstants.PAGE_PROFILE;
		if(logger.isDebugEnabled()){
			logger.info(AppConstants.START+AppConstants.URL_PROFILE);
		}
		try{
			AppUtil.setLocaleContextHolder(lang);
			if(AppConstants.REQUEST_METHOD_GET.equalsIgnoreCase(request.getMethod())){
				if(isCurAuthenticationAnonymous()){
					modelMap.put(AppConstants.MESSAGE_ERROR, messageSource.
								getMessage(AppConstants.ERROR_INVALID_USER, null, lang));
				}else{
					UserDetail userDetail = noteService.getUserDetails(getLoginUser());
					userDetail.setUserPass((new AppPasswordEncoder()).decode(userDetail.getUserPass()));
					modelMap.put(getLoginUser(), userDetail);
				}
			} else if(AppConstants.REQUEST_METHOD_POST.equalsIgnoreCase(request.getMethod())){
				
				return new ModelAndView(retrunTo, modelMap);
			}
			
		}catch(AppException exception){
			throw new AppException(exception);
		}finally{
			if(logger.isDebugEnabled()){
				logger.info(AppConstants.END+AppConstants.URL_PROFILE);
			}
		}
		return new ModelAndView(retrunTo, modelMap);
	}
	
	@RequestMapping(value=AppConstants.URL_SUPPORT,method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView getSupportPage(
			HttpServletRequest request,
			ModelMap modelMap,Locale lang){
		String retrunTo = AppConstants.PAGE_SUPPORT;
		if(logger.isDebugEnabled()){
			logger.info(AppConstants.START+AppConstants.URL_SUPPORT);
		}
		try{
			AppUtil.setLocaleContextHolder(lang);
			if(AppConstants.REQUEST_METHOD_GET.equalsIgnoreCase(request.getMethod())){
				if(isCurAuthenticationAnonymous()){
					modelMap.put(AppConstants.MESSAGE_ERROR, messageSource.
								getMessage(AppConstants.ERROR_INVALID_USER, null, lang));
				}else{
					UserDetail userDetail = noteService.getUserDetails(getLoginUser());
					modelMap.put(getLoginUser(), userDetail);
				}
			} else if(AppConstants.REQUEST_METHOD_POST.equalsIgnoreCase(request.getMethod())){
				
				return new ModelAndView(retrunTo, modelMap);
			}
			
		}catch(AppException exception){
			throw new AppException(exception);
		}finally{
			if(logger.isDebugEnabled()){
				logger.info(AppConstants.END+AppConstants.URL_SUPPORT);
			}
		}
		return new ModelAndView(retrunTo, modelMap);
	}
	
	@RequestMapping(value = AppConstants.URL_API+AppConstants.URL_SEND_SUPPORT, 
							method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendSupportMail(@RequestBody(required=false) ModelMap helpText,Locale locale) {
		String responseString = "MSG_ERR";
		ModelMap modelMap = null;
		AppUtil.setLocaleContextHolder(locale);
		try{
			if(AppUtil.isNotNull(helpText)){
				modelMap = appHelper.sendSupportMail(modelMap, helpText, locale);
				if(modelMap==null || modelMap.isEmpty()){
					String email = (String) helpText.get("email");
					String user = (String) helpText.get("name");
					String content = (String) helpText.get("content");
					responseString = emailController.sendEmail(
							email,user,AppConstants.SEND_EMAIL_HELP_SUPPORT, content, locale);
					modelMap = (modelMap==null)? new ModelMap():modelMap;
						modelMap.put(AppConstants.MAIL_RESPONSE, responseString);
				}
			}
	        if(AppConstants.MESSAGE_ERROR.equals(responseString)){
	            return new ResponseEntity<String>(JSONUtil.getJSONValueAsString(responseString),HttpStatus.NO_CONTENT);
	        }else{
	        	return new ResponseEntity<String>(JSONUtil.getJSONValueAsString(responseString), HttpStatus.OK);
	        }
		}catch(Exception ex){
			throw new AppException();
		}
		
    }
	
	@RequestMapping(value = AppConstants.URL_API+AppConstants.URL_SAVE_DASHBOARD, 
								method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> saveDashboardDetails(@RequestBody(required=false) ModelMap reqObj,Locale locale) {
		String responseString = "MSG_ERR";
		AppUtil.setLocaleContextHolder(locale);
		try{
			if(AppUtil.isNotNull(reqObj) && appHelper.validateApiRequest(reqObj)){
				List<NoteInfo> noteInfoList = new ArrayList<NoteInfo>();
				appHelper.convertNoteListUIToModelForSave(reqObj, noteInfoList);
				responseString = noteService.saveListOfNoteInfos(noteInfoList);
			}
			if(AppConstants.TRANSACTION_SUCCESS.equals(responseString)){
				return new ResponseEntity<String>(JSONUtil.getJSONValueAsString(responseString),HttpStatus.OK);
			}else{
				return new ResponseEntity<String>(JSONUtil.getJSONValueAsString(responseString), HttpStatus.NO_CONTENT);
			}
		}catch(Exception ex){
			throw new AppException();
		}
	}
	
	@RequestMapping(value = AppConstants.URL_API+AppConstants.URL_DELETE_DASHBOARD, 
								method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteDashboardDetails(@RequestBody(required=false) ModelMap reqObj,Locale locale) {
		String responseString = "MSG_ERR";
		AppUtil.setLocaleContextHolder(locale);
		try{
			List<Long> noteInfoList = null;
			if(AppUtil.isNotNull(reqObj) && appHelper.validateApiRequest(reqObj)){
				noteInfoList = new ArrayList<Long>();
				noteInfoList = appHelper.convertNoteUIToModelForDelete(reqObj, noteInfoList);
				responseString = noteService.deleteListOfNoteInfos(noteInfoList);
			}
			if(AppConstants.TRANSACTION_SUCCESS.equals(responseString)){
				return new ResponseEntity<String>(JSONUtil.getJSONValueAsString(responseString),HttpStatus.OK);
			}else{
				return new ResponseEntity<String>(JSONUtil.getJSONValueAsString(responseString), HttpStatus.NO_CONTENT);
			}
		}catch(Exception ex){
			throw new AppException();
		}
	}
	
	@RequestMapping(value = AppConstants.URL_API+AppConstants.URL_GET_DASHBOARD,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getDashboardDetails(@RequestBody(required=false) ModelMap reqObj,Locale locale) {
		String responseString = "";
		AppUtil.setLocaleContextHolder(locale);
		try{
			List<NoteDetailsVO> listOfNotes = null;
			if(appHelper.validateApiRequest(reqObj) && AppUtil.isNotNull(appHelper.getUserId(reqObj))){
				Long userId = new Long(appHelper.getUserId(reqObj));
				List<NoteInfo> noteList = noteService.getListOfNoteInfos(userId);
				listOfNotes = new ArrayList<NoteDetailsVO>();
				appHelper.convertNoteListModelToUI(noteList, listOfNotes);
				responseString = JSONUtil.getJSONValueAsString(listOfNotes);
			}
			if(AppUtil.isNotNull(responseString)){
				return new ResponseEntity<String>(responseString,HttpStatus.OK);
			}else{
				return new ResponseEntity<String>(responseString, HttpStatus.NO_CONTENT);
			}
		}catch(Exception ex){
			throw new AppException();
		}
	}

	@RequestMapping(value = AppConstants.URL_API+AppConstants.URL_GET_PROFILE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getUserProfile(@RequestBody(required=false) ModelMap modelMap) {
		UserDetail userDetail = noteService.getUserDetails(getLoginUser());
		String responseString = "";
        if(AppUtil.isNotNull(userDetail) && appHelper.validateApiRequest(modelMap)){
        	userDetail.setUserPass((new AppPasswordEncoder()).decode(userDetail.getUserPass()));
        	responseString = JSONUtil.getJSONValueAsString(userDetail);
        	return new ResponseEntity<String>(responseString, HttpStatus.OK);
        }else{
        	responseString = JSONUtil.getJSONValueAsString(new UserDetail());
        	return new ResponseEntity<String>(responseString,HttpStatus.NO_CONTENT);
        }
    }
	
	@RequestMapping(value = AppConstants.URL_API+AppConstants.URL_SAVE_PROFILE, method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> putUserProfile(@RequestBody(required=false) UserDetail userDetail ) {
		String responseString = "";
		try{
			if(AppUtil.isNotNull(userDetail) && AppUtil.isNotNull(userDetail.getUserMail())){
				userDetail.setUserPass((new AppPasswordEncoder()).encode(userDetail.getUserPass()));
				responseString =noteService.updateUserDetails(userDetail); 
			}
	        if(AppUtil.isNull(responseString)){
	        	responseString = JSONUtil.getJSONValueAsString(new UserDetail());
	            return new ResponseEntity<String>(responseString,HttpStatus.NO_CONTENT);
	        }else{
	        	responseString = JSONUtil.getJSONValueAsString(userDetail);
	        }
	        return new ResponseEntity<String>(responseString, HttpStatus.OK);
		}catch(Exception ex){
			throw new AppException(ex);
		}
		
    }
	
	@RequestMapping(value=AppConstants.URL_REQUEST_EMAIL_VERIFICATION,method={RequestMethod.GET,RequestMethod.POST})
	public String requestEmailVerification(
			HttpServletRequest request,
			ModelMap modelMap, Locale lang,
			@RequestParam(value=AppConstants.PARAM_USER_EMAIL,required=false) String userMailId){
		if(logger.isDebugEnabled()){
			logger.info(AppConstants.PAGE+AppConstants.PAGE_REQUEST_LOGIN);
		}
		try{
			AppUtil.setLocaleContextHolder(lang);
			if(AppConstants.REQUEST_METHOD_GET.equalsIgnoreCase(request.getMethod())){
				
				return AppConstants.PAGE_REQUEST_LOGIN;
			} else if(AppConstants.REQUEST_METHOD_POST.equalsIgnoreCase(request.getMethod())){
			
				modelMap = appHelper.verifyRequestEmailVerification(modelMap, userMailId, lang); 
				
				if (modelMap.isEmpty()) {
					UserDetail userDetail = noteService.getUserDetailsByEmail(userMailId);
					
					if (AppUtil.isNotNull(userDetail) && 
							AppConstants.INVALID_IND == userDetail.getUserInd()) {
						
						String emailResponse = emailController.sendEmail(userMailId, userDetail.getUserName(), 
																		AppConstants.SEND_EMAIL_USER_CREATION, null, lang);
						modelMap.put(AppConstants.MAIL_RESPONSE, emailResponse);
						modelMap.put(AppConstants.RESPONSE_SUCCESS, 
								appHelper.getMessage(AppConstants.MAIL_MSG_ACCOUNT_VERIFY_LINK, lang)); 
					}else{
						modelMap.put(AppConstants.MESSAGE_ERROR,
								appHelper.getMessage(AppConstants.COMMON_MESSAGE_CONTACT_SUPPORT, lang));
					}
				}
				
			}
			return AppConstants.PAGE_REQUEST_LOGIN;
		}catch(AppException exception){
			throw new AppException(exception);
		}finally{
			if(logger.isDebugEnabled()){
				logger.info(AppConstants.PAGE+AppConstants.PAGE_REQUEST_LOGIN);
			}
		}
	}
	
	@RequestMapping(value=AppConstants.URL_REQUEST_FORGOT_PASSWORD,method={RequestMethod.GET,RequestMethod.POST})
	public String requestForgotPassword(
			HttpServletRequest request,
			ModelMap modelMap, Locale lang,
			@RequestParam(value=AppConstants.PARAM_USER_EMAIL,required=false) String userMailId){
		if(logger.isDebugEnabled()){
			logger.info(AppConstants.PAGE+AppConstants.PAGE_REQUEST_LOGIN);
		}
		try{
			AppUtil.setLocaleContextHolder(lang);
			if(AppConstants.REQUEST_METHOD_GET.equalsIgnoreCase(request.getMethod())){
				
				modelMap.put(AppConstants.MESSAGE_FORGOTPASS,AppConstants.MESSAGE_FORGOTPASS);
				return AppConstants.PAGE_REQUEST_LOGIN;
				
			} else if(AppConstants.REQUEST_METHOD_POST.equalsIgnoreCase(request.getMethod())){
				
				modelMap = appHelper.verifyRequestPasswordVerification(modelMap, userMailId, lang);
				
				if(modelMap.isEmpty()){
					
					String emailResponse = emailController.sendEmail(userMailId, userMailId, 
													AppConstants.SEND_EMAIL_FORGOT_PASSWORD, null, lang);

							modelMap.put(AppConstants.MAIL_RESPONSE, emailResponse);
							modelMap.put(AppConstants.RESPONSE_SUCCESS, 
									appHelper.getMessage(AppConstants.MAIL_MSG_FORGOT_PASS_LINK, lang));
				}
			}
			return AppConstants.PAGE_REQUEST_LOGIN;
		}catch(AppException exception){
			throw new AppException(exception);
		}finally{
			if(logger.isDebugEnabled()){
				logger.info(AppConstants.PAGE+AppConstants.PAGE_REQUEST_LOGIN);
			}
		}
	}
	
	@RequestMapping(value=AppConstants.URL_SIGNUP,method={RequestMethod.GET,RequestMethod.POST})
	public String signUpPage(
			HttpServletRequest request,
			ModelMap modelMap,Locale lang,
			@RequestParam(value=AppConstants.PARAM_USER_NAME,required=false) String userName,
			@RequestParam(value=AppConstants.PARAM_USER_EMAIL,required=false) String emailId,
			@RequestParam(value=AppConstants.PARAM_USER_PASSWORD,required=false) String password){
		if(logger.isDebugEnabled()){
			logger.info(AppConstants.PAGE+AppConstants.PAGE_LOGIN);
		}
		try{
			AppUtil.setLocaleContextHolder(lang);
			if(AppConstants.REQUEST_METHOD_GET.equalsIgnoreCase(request.getMethod())){
				modelMap.put(AppConstants.MESSAGE_SIGNUP,AppConstants.MESSAGE_SIGNUP);
				return AppConstants.PAGE_LOGIN;
			}
			
			else if(AppConstants.REQUEST_METHOD_POST.equalsIgnoreCase(request.getMethod())){
			
				modelMap = appHelper.validateSignUpUser(modelMap, userName, password, emailId, lang); 
				
				if (modelMap.isEmpty()) {
					password = (new AppPasswordEncoder()).encode(password);
					UserDetail userDetail = new UserDetail(userName, password, 
								emailId,AppConstants.INVALID_IND,UserType.ENDUSER.name());
					String isUserCreated = noteService.createUserDetails(userDetail);
					
					if (AppConstants.TRANSACTION_SUCCESS.equals(isUserCreated)) {
						
						String emailResponse = emailController.sendEmail(emailId, userName, 
															AppConstants.SEND_EMAIL_USER_CREATION, null, lang);
						
						modelMap.put(AppConstants.MAIL_RESPONSE, emailResponse);
						modelMap.put(AppConstants.RESPONSE_SUCCESS, 
								appHelper.getMessage(AppConstants.MAIL_MSG_ACCOUNT_CREATION, lang)); 
					}else{
						modelMap.put(AppConstants.MESSAGE_ERROR,
								appHelper.getMessage(AppConstants.COMMON_MESSAGE_CONTACT_SUPPORT, lang));
						modelMap.put(AppConstants.MESSAGE_SIGNUP,AppConstants.MESSAGE_SIGNUP);
					}
					
				}else{
					modelMap.put(AppConstants.MESSAGE_SIGNUP,AppConstants.MESSAGE_SIGNUP);
				}
			}
			return AppConstants.PAGE_LOGIN;
		}catch(AppException exception){
			throw new AppException(exception);
		}finally{
			if(logger.isDebugEnabled()){
				logger.info("End :"+AppConstants.PAGE+AppConstants.PAGE_LOGIN);
			}
		}
	}
	
	@RequestMapping(value=AppConstants.URL_NEW_USER_VERIFICATION,method=RequestMethod.GET)
	public String newUserRegisterConfirm(
			HttpServletRequest request, ModelMap modelMap, Locale lang,
			@RequestParam(value=AppConstants.PATHVARIABLE_TOKEN,required=false) String userToken,
			@RequestParam(value=AppConstants.PATHVARIABLE_TMP_TOKEN,required=false) String tempToken,
			@RequestParam(value=AppConstants.PATHVARIABLE_USER_EMAIL,required=false) String userEmail){
		if(logger.isDebugEnabled()){
			logger.info(AppConstants.START+AppConstants.URL_NEW_USER_VERIFICATION);
		}
		try{
			AppUtil.setLocaleContextHolder(lang);
			modelMap = appHelper.validateNewUserHash(modelMap, userToken, userEmail, AppUtil.getCurrentLocale());
			
			if (modelMap.isEmpty()) {
				UserDetail userDetail = noteService.getUserDetailsByEmail(userEmail);
				if (AppUtil.isNotNull(userDetail)) {
					
					userDetail.setUserInd(AppConstants.VALID_IND);
					String trans = noteService.updateUserDetails(userDetail);
					
					if(AppConstants.TRANSACTION_SUCCESS.equals(trans)){
						String emailResponse = emailController.sendEmail(userDetail.getUserMail(), 
											userDetail.getUserName(), AppConstants.SEND_EMAIL_USER_VERIFYED, null, lang);
							modelMap.put(AppConstants.MAIL_RESPONSE, emailResponse);
							modelMap.put(AppConstants.RESPONSE_SUCCESS, 
							appHelper.getMessage(AppConstants.LOGIN_VERIFICATION_SUCCESS, AppUtil.getCurrentLocale()));
							request.setAttribute(AppConstants.REQUEST_ATTRIBUTE, modelMap);
					}
				}
			}else{
				request.setAttribute(AppConstants.REQUEST_ATTRIBUTE, modelMap);
				return AppConstants.PAGE_REQUEST_LOGIN;
			}
			
			return AppConstants.PAGE_LOGIN;
		}catch(AppException exception){
			throw new AppException(exception);
		}finally{
			if(logger.isDebugEnabled()){
				logger.info(AppConstants.END+AppConstants.URL_NEW_USER_VERIFICATION);
			}
		}
	}
	
	@RequestMapping(value=AppConstants.URL_LOGOUT)
	public String loginOutPage(HttpServletRequest request,HttpServletResponse response){
		if(logger.isDebugEnabled()){
			logger.info(AppConstants.PAGE+AppConstants.PAGE_LOGOUT);
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication!=null){
			SecurityContextHolder.getContext().setAuthentication(null);
			//persistentTokenBasedRememberMeServices.logout(request, response, authentication);
		}
		//ModelAndView andView = new ModelAndView("redirect:"+AppConstants.URL_LOGIN+"?"+AppConstants.REQUEST_ATTRIBUTE+"=Mani K Lougout");
		//andView.addObject(AppConstants.REQUEST_ATTRIBUTE, "TEST");
		return AppUtil.rediredtedTo(AppConstants.URL_LOGIN);
	}
	
	@RequestMapping(value=AppConstants.URL_USER_LIST)
	public String getUserListPage(ModelMap modelMap){
		if(logger.isDebugEnabled()){
			logger.info(AppConstants.PAGE+AppConstants.PAGE_USER_LIST);
		}
		List<UserDetail> userList = null;
		try{
			userList = noteService.getUserDetailList();
			if(logger.isDebugEnabled()){
				logger.info(JSONUtil.getJSONValueAsString(userList));
			}
			modelMap.put(AppConstants.PAGE_USER_LIST, JSONUtil.getJSONValueAsString(userList));
			modelMap.put(AppConstants.PARAM_LOGGED_USER, getLoginUser());
		}catch(Exception exception){
			throw new AppException(exception);
		}
		return AppConstants.PAGE_USER_LIST;
	}
	
	@RequestMapping(value=AppConstants.URL_DASHBOARD)
	public String getUserDashboardPage(ModelMap modelMap){
		if(logger.isDebugEnabled()){
			logger.info(AppConstants.PAGE+AppConstants.PAGE_DASHBOARD);
		}
		modelMap.put(AppConstants.PARAM_DASHBOARD,AppConstants.PARAM_DASHBOARD);
		return AppConstants.PAGE_DASHBOARD;
	}
	
	private static String getLoginUser(){
		String userName	 = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof UserDetails){
			userName	 = ((UserDetails) principal).getUsername();
		}else{
			userName	 = (AppUtil.isNotNull(userName))? principal.toString() : null;
		}
		return userName;
	}

	private boolean isCurAuthenticationAnonymous(){
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		try{
			return authenticationTrustResolver.isAnonymous(authentication);
		}catch(Exception ex){
			throw new AppException(ex);
		}
	}
	
	
	
	}
