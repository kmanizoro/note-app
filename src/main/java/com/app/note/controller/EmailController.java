package com.app.note.controller;

import java.util.Locale;

import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

import com.app.note.bean.TokenDetail;
import com.app.note.bean.UserDetail;
import com.app.note.service.INoteService;
import com.app.note.util.AppConstants;
import com.app.note.util.AppException;
import com.app.note.util.AppHelper;
import com.app.note.util.AppUtil;

@Controller
public class EmailController {

	private static final Logger logger = LogManager.getLogger(EmailController.class);
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	AppHelper appHelper;
	
	@Autowired
	INoteService noteService;
	
	public String sendEmail(String toMailId,String toUserName,String toSubject,String toContent, Locale locale){
		
		String response = null;
		
		if(AppUtil.isNotNull(toMailId, toUserName, toSubject)){
			
			//construct message based on message id
			if (AppConstants.SEND_EMAIL_USER_CREATION.equals(toSubject)) {
				String htmlMsgContent = getSuccessMessageUsingTemplate(toSubject, toUserName, toMailId, locale);
				String subjectContent = appHelper.getMessage(AppConstants.MAIL_MSG_ACCOUNT_CREATION, locale);
				response = send(toMailId, subjectContent, htmlMsgContent, AppUtil.getLocale(locale));
			}
			
			else if (AppConstants.SEND_EMAIL_USER_VERIFYED.equals(toSubject)) {
				String htmlMsgContent = getSuccessMessageUsingTemplate( toSubject, toUserName, toMailId, locale);
				String subjectContent = appHelper.getMessage(AppConstants.LOGIN_VERIFICATION_SUCCESS,locale);
				response = send(toMailId, subjectContent, htmlMsgContent, AppUtil.getLocale(locale));
			}
			
			else if (AppConstants.SEND_EMAIL_FORGOT_PASSWORD.equals(toSubject)) {
				String htmlMsgContent = getForgotPasswordMsgTemplate( toSubject, toUserName, toMailId, locale);
				String subjectContent = appHelper.getMessage(AppConstants.LOGIN_PASSWORD_REQUEST_SUCCESS,locale);
				response = send(toMailId, subjectContent, htmlMsgContent, AppUtil.getLocale(locale));
			}
			else if (AppConstants.SEND_EMAIL_HELP_SUPPORT.equals(toSubject)) {
				String htmlMsgContent = getSupportTextTemplate( toSubject, toUserName, toMailId, toContent, locale);
				String subjectContent = appHelper.getMessage(AppConstants.HELP_SUPPORT_MAIL_SUBJECT,locale);
				response = send(toMailId, subjectContent, htmlMsgContent, AppUtil.getLocale(locale));
			}
		}	
		return response;
	}
	
	private String send(String to,String subject,String msgCnt,Locale locale) throws AppException{
		if(logger.isDebugEnabled()){
			logger.info(AppConstants.START);
		}
		try{
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");
			
			helper.setFrom(AppConstants.MAIL_FROM_USER);
			helper.setReplyTo(AppConstants.MAIL_FROM_USER);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(msgCnt,true);
			
			mailSender.send(message);
			return AppConstants.MAIL_SENT;
		}catch(AppException ex){
			logger.error(ex);
			return AppConstants.MAIL_NOT_SENT;
		}catch(Exception ex){
			logger.error(ex);
			return AppConstants.MAIL_NOT_SENT;
		}finally{
			if(logger.isDebugEnabled()){
				logger.info(AppConstants.END);
			}
		}
	}
	
	
	private String getSuccessMessageUsingTemplate(
						String toSub, String appUser,String toMailId,Locale locale){

		String htmlTextContent	= null;
		try{			
			htmlTextContent 	  = AppUtil.getFileContents(AppConstants.MAIL_TEMPLATE_COMMON_FILE);
			
			if(AppConstants.SEND_EMAIL_USER_CREATION.equals(toSub)){
			
				String tokenString 	  = AppUtil.getTokenString();
				String tmpTokenNumber = AppUtil.getTempSixDigitNumber();
				TokenDetail tokenDetail = new TokenDetail(tokenString, AppConstants.INVALID_IND); 
				tokenDetail.setTokenEnddate(AppUtil.getNextDate(30));
				if(AppUtil.isNotNull(htmlTextContent) &&
							AppConstants.TRANSACTION_SUCCESS.equals(noteService.createTokenDetails(appUser, tokenDetail))) {
					
					String appConfirmUrl = AppUtil.getUserRegVerifyUrl(tokenString, toMailId, tmpTokenNumber);
					System.out.println("appConfirmUrl "+appConfirmUrl);
					htmlTextContent = htmlTextContent.replace(AppConstants.EMAIL_CNF_MESSAGE_URL, appConfirmUrl)
						.replace(AppConstants.EMAIL_CNF_MESSAGE, 
								appHelper.getMessage(AppConstants.EMAIL_CNF_MESSAGE,locale))
						.replace(AppConstants.EMAIL_CNF_MESSAGE_BTN, 
								appHelper.getMessage(AppConstants.EMAIL_CNF_MESSAGE_BTN, locale))
						.replace(AppConstants.EMAIL_CNF_MESSAGE2, 
								appHelper.getMessage(AppConstants.EMAIL_CNF_MESSAGE2,locale))
						.replace(AppConstants.EMAIL_CNF_MESSAGE_APP_NAME,
								appHelper.getMessage(AppConstants.EMAIL_CNF_MESSAGE_APP_NAME, locale))
						.replace(AppConstants.EMAIL_CNF_MESSAGE_USER, 
								appHelper.getMessage(AppConstants.EMAIL_CNF_MESSAGE_USER, locale).replace("<app-user>", appUser));
				}
				
			} else if (AppConstants.SEND_EMAIL_USER_VERIFYED.equals(toSub)) {
				
				if(AppUtil.isNotNull(htmlTextContent)
						&& AppConstants.TRANSACTION_SUCCESS.equals(noteService.activateAllTokenDetails((appUser)))) {
					
					String loginUrl = AppUtil.getUserLoginUrl();
					htmlTextContent = htmlTextContent.replace(AppConstants.EMAIL_CNF_MESSAGE_URL, loginUrl)
						.replace(AppConstants.EMAIL_CNF_MESSAGE, 
								appHelper.getMessage(AppConstants.EMAIL_VERIFY_MESSAGE, locale))
						.replace(AppConstants.EMAIL_CNF_MESSAGE_BTN, 
								appHelper.getMessage(AppConstants.EMAIL_VERIFY_MESSAGE_BTN, locale))
						.replace(AppConstants.EMAIL_CNF_MESSAGE2, 
								appHelper.getMessage(AppConstants.EMAIL_VERIFY_MESSAGE2, locale))
						.replace(AppConstants.EMAIL_CNF_MESSAGE_APP_NAME,
								appHelper.getMessage(AppConstants.EMAIL_VERIFY_MESSAGE_APP_NAME, locale))
						.replace(AppConstants.EMAIL_CNF_MESSAGE_USER, 
								appHelper.getMessage(AppConstants.EMAIL_VERIFY_MESSAGE_USER, locale).replace("<app-user>", appUser));
				}
			}
			
		}catch(Exception ex){
			logger.error(ex);
		}
		return htmlTextContent;
	}
	
	private String getForgotPasswordMsgTemplate(
						String toSub, String appUser,String toMailId,Locale locale){

		String htmlTextContent	= null;
		try{			
			htmlTextContent 	  = AppUtil.getFileContents(AppConstants.MAIL_TEMPLATE_FORGOT_PASS_FILE);
			
			if(AppConstants.SEND_EMAIL_FORGOT_PASSWORD.equals(toSub)){

				UserDetail userDetail = noteService.getUserDetailsByEmail(toMailId);
				
				if(AppUtil.isNotNull(userDetail) &&
						AppUtil.isNotNull(userDetail.getUserMail()) ){
					
					String tempPassword = AppUtil.getTempPasswordString();
					userDetail.setUserPass((new BCryptPasswordEncoder()).encode(tempPassword));
					
					if(AppUtil.isNotNull(htmlTextContent) &&
							AppConstants.TRANSACTION_SUCCESS.equals(noteService.updateUserDetails(userDetail))) {

						htmlTextContent = htmlTextContent
								.replace(AppConstants.EMAIL_FORGOT_PASSWORD_TITLE, 
										appHelper.getMessage(AppConstants.EMAIL_FORGOT_PASSWORD_TITLE,locale))
								.replace(AppConstants.EMAIL_FORGOT_PASSWORD_QUESTION, 
										appHelper.getMessage(AppConstants.EMAIL_FORGOT_PASSWORD_QUESTION,locale))
								.replace(AppConstants.EMAIL_FORGOT_PASSWORD_QUESTION_MSG, 
										appHelper.getMessage(AppConstants.EMAIL_FORGOT_PASSWORD_QUESTION_MSG,locale))
								.replace(AppConstants.EMAIL_FORGOT_PASSWORD_USERNAME, 
										appHelper.getMessage(AppConstants.EMAIL_FORGOT_PASSWORD_USERNAME,locale))
								.replace(AppConstants.EMAIL_FORGOT_PASSWORD_PASSWORD, 
										appHelper.getMessage(AppConstants.EMAIL_FORGOT_PASSWORD_PASSWORD,locale))
								.replace(AppConstants.EMAIL_FORGOT_PASSWORD_USERNAME_TXT,userDetail.getUserName())
								.replace(AppConstants.EMAIL_FORGOT_PASSWORD_PASSWORD_TXT,tempPassword)
								.replace(AppConstants.EMAIL_FORGOT_PASSWORD_APP_NAME,
										appHelper.getMessage(AppConstants.EMAIL_FORGOT_PASSWORD_APP_NAME, locale))
								.replace(AppConstants.EMAIL_FORGOT_PASSWORD_MESSAGE2, 
										appHelper.getMessage(AppConstants.EMAIL_FORGOT_PASSWORD_MESSAGE2,locale));
					}

				}
				
			} 			
		}catch(Exception ex){
			logger.error(ex);
		}
		return htmlTextContent;
	}
	
	private String getSupportTextTemplate(String toSub, String appUser,String toMailId,String mailContent,Locale locale){

		String htmlTextContent	= null;
		try{			
		htmlTextContent 	  = AppUtil.getFileContents(AppConstants.MAIL_TEMPLATE_HELP_SUPPORT_FILE);
		if (AppConstants.SEND_EMAIL_HELP_SUPPORT.equals(toSub)) {
			
			if(AppUtil.isNotNull(htmlTextContent)) {
				htmlTextContent = htmlTextContent
					.replace(AppConstants.HELP_SUPPORT_MAIL_SUBJECT, 
							appHelper.getMessage(AppConstants.HELP_SUPPORT_MAIL_SUBJECT, locale))
					.replace(AppConstants.HELP_SUPPORT_MAIL_THANKS, 
							appHelper.getMessage(AppConstants.HELP_SUPPORT_MAIL_THANKS, locale))
					.replace(AppConstants.HELP_SUPPORT_MAIL_AUTO_GEN, 
							appHelper.getMessage(AppConstants.HELP_SUPPORT_MAIL_AUTO_GEN, locale))
					.replace(AppConstants.HELP_SUPPORT_MAIL_REQ_CONTENT,
							appHelper.getMessage(AppConstants.HELP_SUPPORT_MAIL_REQ_CONTENT, locale))
					.replace(AppConstants.HELP_SUPPORT_COMMON_APP_EMPTY,mailContent)
					.replace(AppConstants.HELP_SUPPORT_MAIL_THANKS_COMMA,
							appHelper.getMessage(AppConstants.HELP_SUPPORT_MAIL_THANKS_COMMA, locale))
					.replace(AppConstants.HELP_SUPPORT_COMMON_APP_TEAM,
							appHelper.getMessage(AppConstants.HELP_SUPPORT_COMMON_APP_TEAM, locale))
					.replace(AppConstants.EMAIL_CNF_MESSAGE_APP_NAME,
							appHelper.getMessage(AppConstants.EMAIL_CNF_MESSAGE_APP_NAME, locale))
					.replace(AppConstants.EMAIL_CNF_MESSAGE_USER, 
							appHelper.getMessage(AppConstants.EMAIL_VERIFY_MESSAGE_USER, locale).replace("<app-user>", appUser));
			}
		}
		
		}catch(Exception ex){
		logger.error(ex);
		}
		return htmlTextContent;
		}

	
}
