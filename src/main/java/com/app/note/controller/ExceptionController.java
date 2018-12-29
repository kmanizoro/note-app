package com.app.note.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.app.note.util.AppConstants;
import com.app.note.util.AppException;
import com.app.note.util.AppHelper;
import com.app.note.util.AppUtil;

@Controller
@ControllerAdvice
public class ExceptionController extends RuntimeException {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3940845850302168652L;
	
	private static final Logger logger = LogManager.getLogger(ExceptionController.class);

	private static final Map<String,String> exceptionMsg = new HashMap<String,String>();
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	AppHelper appHelper;
	
	static{
		exceptionMsg.put(AppConstants.BINDEXCEPTION," 400 (Bad Request)");
		exceptionMsg.put(AppConstants.CONVERSIONNOTSUPPORTEDEXCEPTION," 500 (Internal Server Error)");
		exceptionMsg.put(AppConstants.HTTPMEDIATYPENOTACCEPTABLEEXCEPTION," 406 (Not Acceptable)");
		exceptionMsg.put(AppConstants.HTTPMEDIATYPENOTSUPPORTEDEXCEPTION," 415 (Unsupported Media Type)");
		exceptionMsg.put(AppConstants.HTTPMESSAGENOTREADABLEEXCEPTION," 400 (Bad Request)");
		exceptionMsg.put(AppConstants.HTTPMESSAGENOTWRITABLEEXCEPTION," 500 (Internal Server Error)");
		exceptionMsg.put(AppConstants.HTTPREQUESTMETHODNOTSUPPORTEDEXCEPTION," 405 (Method Not Allowed)");
		exceptionMsg.put(AppConstants.METHODARGUMENTNOTVALIDEXCEPTION," 400 (Bad Request)");
		exceptionMsg.put(AppConstants.MISSINGSERVLETREQUESTPARAMETEREXCEPTION," 400 (Bad Request)");
		exceptionMsg.put(AppConstants.MISSINGSERVLETREQUESTPARTEXCEPTION," 400 (Bad Request)");
		exceptionMsg.put(AppConstants.NOHANDLERFOUNDEXCEPTION," 404 (Not Found)");
		exceptionMsg.put(AppConstants.NOSUCHREQUESTHANDLINGMETHODEXCEPTION," 404 (Not Found)");
		exceptionMsg.put(AppConstants.TYPEMISMATCHEXCEPTION," 400 (Bad Request)");
	}
	
	@ExceptionHandler(AppException.class)
	public ModelAndView putMessage(AppException appException){
		ModelAndView modelAndView = new ModelAndView(AppConstants.PAGE_ERRORS);
		modelAndView.addObject("msg", appException.getMsg());
		modelAndView.addObject(AppConstants.MESSAGE_ERROR,
				appHelper.getMessage(AppConstants.COMMON_MESSAGE_CONTACT_SUPPORT, AppUtil.getCurrentLocale()));
		return modelAndView;
	}
	
	@ExceptionHandler(Throwable.class)
	public ModelAndView putMessage(Exception exception){
		AppException appException = new AppException(exception);
		ModelAndView modelAndView = new ModelAndView(AppConstants.PAGE_ERRORS);
		modelAndView.addObject("msg", appException.getMsg());
		modelAndView.addObject(AppConstants.MESSAGE_ERROR,
				appHelper.getMessage(AppConstants.COMMON_MESSAGE_CONTACT_SUPPORT, AppUtil.getCurrentLocale()));
		return modelAndView;
	}
	
	@ExceptionHandler(ServletException.class)
	public ModelAndView putMessage(HttpServletRequest request, ServletException exception,
			@RequestParam(value=AppConstants.PATHVARIABLE_LANGUAE ,required = false) Locale locale) throws AppException{
		ModelAndView modelAndView = new ModelAndView(AppConstants.PAGE_COMMON_ERROR);
		locale = AppUtil.getLocale(locale, null); 
		if(AppUtil.isNotNull(exception)){
			String exName = AppUtil.getExceptionName(exception);
			if(logger.isInfoEnabled()){
				logger.info("exName : "+exName, exception);
			}
			modelAndView.addObject(AppConstants.EXCEPTION_MESSAGE, exceptionMsg.get(exName));
			modelAndView.addObject(AppConstants.EXCEPTION_DESCRIPTION, appHelper.getMessage(exName,locale));
		}
		return modelAndView;
	}
}
