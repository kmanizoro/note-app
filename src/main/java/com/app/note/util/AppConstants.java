package com.app.note.util;

import java.util.HashMap;
import java.util.Map;

public class AppConstants {
	
	//common constants
	public static final String START 	= "Start @";
	public static final String END 		= "End @";
	public static final String COLON 	= ":";
	public static final String EQUAL_TO = "=";
	public static final String AMBERSON	= "&";
	public static final String QUESTION_MARK	= "?";
	public static final String BACKSLASH		= "\\";
	public static final String FORWAREDSLASH	= "/";
	public static final String HTTP_PREFIX		= "http://";
	public static final String UTF_8			= "UTF-8";
	public static final String JDBC_MYSQL_PREFIX	= "jdbc:mysql://";
	public static final String JDBC_MYSQL_DRIVER	= "com.mysql.jdbc.Driver";
	public static final int INVALID_IND	= 0;
	public static final int VALID_IND	= 1;
	public static final String GRATER	= "GRATER";
	public static final String LESS		= "LESS";
	public static final String EQUAL	= "EQUAL";
	public static final String NOT_EQUAL= "NOT_EQUAL";
	
	//mail
	public static final String MAIL_SENT 		= "Sent";
	public static final String MAIL_RESPONSE 	= "MailResponse";
	public static final String MAIL_NOT_SENT 	= "NotSent";
	public static final String MAIL_SMTP_SERVER = "smpt.gmail.com";
	public static final String MAIL_FROM_USER 	= "username@gmail.com";
	public static final String MAIL_FROM_PASS 	= "gmainpassword";
	

	//file_names
	public static final String MAIL_TEMPLATE_COMMON_FILE = "email_template.html";
	public static final String MAIL_TEMPLATE_FORGOT_PASS_FILE = "password_template.html";
	public static final String MAIL_TEMPLATE_HELP_SUPPORT_FILE = "help_text.html";
	
	
	public static final String MAIL_MSG_ACCOUNT_CREATION 	= "mail.message.account.creation";
	public static final String MAIL_MSG_INVALID_ACCOUNT 	= "mail.message.account.invaild";
	public static final String MAIL_MSG_ACCOUNT_TIMEOUT 	= "mail.message.account.timeout";
	public static final String MAIL_MSG_ACCOUNT_VERIFY_LINK = "mail.message.account.verify.link.success";
	public static final String MAIL_MSG_FORGOT_PASS_LINK = "mail.message.account.password.link.success";
	
	public static final String COMMON_MESSAGE_CONTACT_SUPPORT 	= "common.message.contact.support";
	
	//messages
	public static final String RESPONSE_SUCCESS 	= "Success";
	public static final String RESPONSE_ERROR 		= "Error";
	public static final String REQUEST_ATTRIBUTE 	= "Rquest_Attr";
	
	
	public static final String TRANSACTION_SUCCESS 	= "Trans";
	public static final String TRANSACTION_ERROR 	= "Failed";
	
	public static final String MESSAGE_ERROR 		= "MSG_ERR";
	public static final String MESSAGE_EXCEPTION 	= "MSG_EXP";
	public static final String MESSAGE_MSG 			= "MSG_MSG";
	public static final String RESPONSE_INFO 		= "RES_INFO";
	public static final String MESSAGE_SIGNUP 		= "SignUP";
	public static final String MESSAGE_FORGOTPASS 	= "forgotPass";
	public static final String ACTION_URL_NOTE 		= "Note";
	public static final String REQUEST_METHOD_POST 	= "POST";
	public static final String REQUEST_METHOD_GET 	= "GET";
	
	//error_msgs
	public static final String ERROR_INVALID_USER	= "login.userName.passWord.Invalid";
	
	// page url 
	public static final String PAGE_ROOT 			= "/";
	public static final String URL_POST 			= "/post";
	public static final String URL_ERRORS 			= "/errors";
	public static final String URL_COMMON_ERROR 	= "/common_error";
	public static final String URL_WELCOME 			= "/welcome";
	public static final String URL_DASHBOARD 		= "/dashboard";
	public static final String URL_DELETE_USER 		= "/deluser";
	public static final String URL_LOGIN 			= "/login";
	public static final String URL_LOGIN_ERROR 		= "/login?customError";
	public static final String URL_SIGNUP 			= "/signup";
	public static final String URL_LOGOUT 			= "/logout";
	public static final String URL_USER_LIST 		= "/userlist";
	public static final String URL_PROFILE 			= "/profile";
	public static final String URL_API 				= "/api";
	public static final String URL_SUPPORT 			= "/support";
	public static final String URL_SEND_SUPPORT 	= "/sendSupport";
	public static final String URL_SAVE_PROFILE 	= "/saveProfile";
	public static final String URL_GET_PROFILE 		= "/getProfile";
	public static final String URL_GET_DASHBOARD 	= "/getDashboard";
	public static final String URL_SAVE_DASHBOARD 	= "/saveDashboard";
	public static final String URL_DELETE_DASHBOARD 			= "/deleteDashboard";
	public static final String URL_NEW_USER_VERIFICATION 		= "/userRegVerification";
	public static final String URL_REQUEST_EMAIL_VERIFICATION 	= "/requestemailverify";
	public static final String URL_REQUEST_FORGOT_PASSWORD 		= "/forgotpassword";
	
	public static final String NEW_USER_VERIFICATION_STRING = "userRegVerification";
	
	// colors
	public static final String COLOR_WHITE 	= ("#FAFAFA");
	public static final String COLOR_BLUE 	= ("#10ADF5");
	public static final String COLOR_GREEN 	= ("#CCFF90");
	public static final String COLOR_CYAN 	= ("#65B6AE");
	public static final String COLOR_RED 	= ("#EF9A9A");
	public static final String COLOR_GREY	= ("#CFD8DC");
	
	public static final Map<String,String> colors = new HashMap<String,String>();
	
	// page name
	public static final String PAGE_POST 			= "post";
	public static final String PAGE_PROFILE 		= "dashboard_profile";
	public static final String PAGE_SUPPORT 		= "dashboard_support";
	public static final String PAGE_ERRORS 			= "errors";
	public static final String PAGE_COMMON_ERROR 	= "common_error";
	public static final String PAGE_WELCOME 		= "welcome";
	public static final String PAGE_DELETE_USER 	= "delUser";
	public static final String PAGE_LOGIN 			= "login";
	public static final String PAGE_LOGOUT 			= "logout";
	public static final String PAGE_USER_LIST 		= "userList";
	public static final String PAGE_DASHBOARD 		= "dashboard";
	public static final String PAGE_REQUEST_LOGIN 	= "request_login";

	public static final String DASH_LIST_OF_NOTE_OBJECTS	= "ListOfNoteObjects";
	public static final String DASH_SINGLE_NOTE_OBJECT_SAVE	= "SingleNoteObject";
	public static final String DASH_SINGLE_NOTE_OBJECT_DELETE	= "DeleteNoteObjectSingle";
	public static final String DASH_LIST_OF_NOTE_OBJECTS_DELETE	= "DeleteNoteObjectsList";
	
	//parameters
	public static final String PARAM_REMEMBER_ME	= "rembr-me";
	public static final String PARAM_DASHBOARD 		= "dashboard";
	public static final String PARAM_USER_NAME		= "usrname";
	public static final String PARAM_USER_EMAIL		= "usremail";
	public static final String PARAM_USER_PASSWORD	= "passwrd";
	public static final String PARAM_LOGGED_USER	= "loggedInUser";
	
	public static final String SESSION_USER_NAME	= "ssnUserName";
	public static final String SESSION_USER_DISP_NAME	= "ssnUserDispName";
	public static final String SESSION_USER_ID		= "ssnUserId";
	public static final String SESSION_LOGIN_ID		= "ssnLoginId";
	public static final String SESSION_LOGIN_SESSION_ID	= "ssnLoginSsnId";
	public static final String SESSION_USER_ROLE	= "ssnUserRole";
	
	public static final String PAGE					= "Page :";
	public static final String EXCEPTION			= "Exception";
	public static final String EXCEPTION_MESSAGE	= "Exp_Msg";
	public static final String EXCEPTION_DESCRIPTION= "Exp_Des";
	public static final String APP_EXCEPTION		= "AppException";
	
	//Path Variables
	public static final String PATHVARIABLE_LANGUAE		= "lang";
	public static final String PATHVARIABLE_TOKEN		= "userToken";
	public static final String PATHVARIABLE_USER_EMAIL 	= "userEmailId";
	public static final String PATHVARIABLE_TMP_TOKEN	= "tmpToken";
	
	//Db Configuration
	public static final String DB_SERVER		= "localhost";
	public static final String DB_SERVER_PORT	= "3306";
	public static final String DB_USER_NAME		= "root";
	public static final String DB_USER_PASS		= "";
	public static final String DB_NAME			= "note";
	
	//language
	public static final String LANGUAE_ENGLISH		= "en";
	public static final String LANGUAE_TAMIL		= "ta";
	public static final String LANGUAE_FRENCH		= "fr";
	public static final String COUNTRY_INDIA		= "IN";
	
	//Servlet Exceptions
	public static final String BINDEXCEPTION 							= "BindException";
	public static final String CONVERSIONNOTSUPPORTEDEXCEPTION 			= "ConversionNotSupportedException";
	public static final String HTTPMEDIATYPENOTACCEPTABLEEXCEPTION 		= "HttpMediaTypeNotAcceptableException";
	public static final String HTTPMEDIATYPENOTSUPPORTEDEXCEPTION 		= "HttpMediaTypeNotSupportedException";
	public static final String HTTPMESSAGENOTREADABLEEXCEPTION 			= "HttpMessageNotReadableException";
	public static final String HTTPMESSAGENOTWRITABLEEXCEPTION 			= "HttpMessageNotWritableException";
	public static final String HTTPREQUESTMETHODNOTSUPPORTEDEXCEPTION 	= "HttpRequestMethodNotSupportedException";
	public static final String METHODARGUMENTNOTVALIDEXCEPTION 			= "MethodArgumentNotValidException";
	public static final String MISSINGSERVLETREQUESTPARAMETEREXCEPTION 	= "MissingServletRequestParameterException";
	public static final String MISSINGSERVLETREQUESTPARTEXCEPTION 		= "MissingServletRequestPartException";
	public static final String NOHANDLERFOUNDEXCEPTION 					= "NoHandlerFoundException";
	public static final String NOSUCHREQUESTHANDLINGMETHODEXCEPTION 	= "NoSuchRequestHandlingMethodException";
	public static final String TYPEMISMATCHEXCEPTION 					= "TypeMismatchException";
	
	//email verification
	public static final String APP_SERVER_HOST = "localhost";
	public static final String APP_SERVER_PORT = "8080";
	public static final String APP_NAME 	   = "";
	
	//Sent Mail details
	public static final String SEND_EMAIL_USER_CREATION = "User_Creation";
	public static final String SEND_EMAIL_USER_VERIFYED = "User_Verified";
	public static final String SEND_EMAIL_FORGOT_PASSWORD = "Forgot_Password";
	public static final String SEND_EMAIL_ACCOUNT_DELETED = "Account_Deletion";
	public static final String SEND_EMAIL_HELP_SUPPORT = "Help_Support";
	
	//email verification messages
	public static final String EMAIL_CNF_MESSAGE = "mail.email.message.account.verify";
	public static final String EMAIL_CNF_MESSAGE2 = "mail.email.message.account.msg2";
	public static final String EMAIL_CNF_MESSAGE_TITLE = "mail.email.message.account.title";
	public static final String EMAIL_CNF_MESSAGE_USER = "mail.email.message.account.username";
	public static final String EMAIL_CNF_MESSAGE_URL = "mail.email.message.account.url";
	public static final String EMAIL_CNF_MESSAGE_BTN = "mail.email.message.account.btn";
	public static final String EMAIL_CNF_MESSAGE_APP_NAME = "mail.email.message.account.appname";
	
	public static final String EMAIL_VERIFY_MESSAGE = "mail.email.message.verification.success";
	public static final String EMAIL_VERIFY_MESSAGE2 = "mail.email.message.account.msg2";
	public static final String EMAIL_VERIFY_MESSAGE_TITLE = "mail.email.message.account.title";
	public static final String EMAIL_VERIFY_MESSAGE_USER = "mail.email.message.account.username";
	public static final String EMAIL_VERIFY_MESSAGE_URL = "mail.email.message.account.url";
	public static final String EMAIL_VERIFY_MESSAGE_BTN = "ui.login.button.login";
	public static final String EMAIL_VERIFY_MESSAGE_APP_NAME = "mail.email.message.account.appname";
	
	public static final String EMAIL_FORGOT_PASSWORD_QUESTION = "mail.email.message.forgot.password.question";
	public static final String EMAIL_FORGOT_PASSWORD_QUESTION_MSG = "mail.email.message.forgot.password.msg";
	public static final String EMAIL_FORGOT_PASSWORD_TITLE = "mail.email.message.account.title";
	public static final String EMAIL_FORGOT_PASSWORD_USERNAME = "ui.login.label.username";
	public static final String EMAIL_FORGOT_PASSWORD_PASSWORD = "ui.login.label.password";
	public static final String EMAIL_FORGOT_PASSWORD_USERNAME_TXT = "mail.email.message.forgot.username.txt";
	public static final String EMAIL_FORGOT_PASSWORD_PASSWORD_TXT = "mail.email.message.forgot.password.txt";
	public static final String EMAIL_FORGOT_PASSWORD_APP_NAME = "mail.email.message.account.appname";
	public static final String EMAIL_FORGOT_PASSWORD_MESSAGE2 = "mail.email.message.account.msg2";
	
	
	//signup validation message
	public static final String LOGIN_USERNAME_EMPTY = "login.userName.empty";
	public static final String LOGIN_PASSWORD_EMPTY = "login.passWord.empty";
	public static final String LOGIN_EMAIL_EMPTY = "login.email.empty";
	public static final String LOGIN_EMAIL_ID_INVALID = "login.email.invalid";
	public static final String LOGIN_USERNAME_EMAIL_ALREADY_EXISTS = "login.userName.email.already.exists";
	
	public static final String LOGIN_VERIFICATION_EMAIL_NOT_FOUND = "login.verify.email.notfound";
	public static final String LOGIN_VERIFICATION_INVALID = "login.verify.verification.notvalid";
	public static final String LOGIN_VERIFICATION_TIMEOUT = "login.verify.verification.timeout";
	public static final String LOGIN_VERIFICATION_SUCCESS = "login.verify.verification.success";
	public static final String LOGIN_USER_VERIFIED_ALREADY = "login.verify.verification.already";
	public static final String LOGIN_USER_PLZ_VERIFY_FIRST = "login.verify.verification.email.verify.first";
	public static final String LOGIN_PASSWORD_REQUEST_SUCCESS = "ui.login.request.label.title.password";
	
	public static final String HELP_SUPPORT_MAIL_SUBJECT = "mail.message.support.help.subject";
	public static final String HELP_SUPPORT_MAIL_TITLE = "mail.message.support.help.subject";
	public static final String HELP_SUPPORT_MAIL_REQ_CONTENT = "mail.message.support.request.content";
	public static final String HELP_SUPPORT_MAIL_AUTO_GEN = "mail.message.support.autogen.mail";
	public static final String HELP_SUPPORT_MAIL_THANKS = "mail.message.support.thanks";
	public static final String HELP_SUPPORT_MAIL_THANKS_COMMA = "common.message.thanks.comma";
	public static final String HELP_SUPPORT_COMMON_APP_TEAM = "common.message.app.name.team";
	public static final String HELP_SUPPORT_COMMON_APP_EMPTY = "common.message.app.empty";
	
	static{
		colors.put("GREY", COLOR_GREY);
		colors.put("BLUE", COLOR_BLUE);
		colors.put("CYAN", COLOR_CYAN);
		colors.put("GREEN", COLOR_GREEN);
		colors.put("RED", COLOR_RED);
		colors.put("WHITE", COLOR_WHITE);
	}
}
