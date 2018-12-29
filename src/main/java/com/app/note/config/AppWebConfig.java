package com.app.note.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.app.note.util.AppConstants;

@Configuration
@Component
public class AppWebConfig implements WebMvcConfigurer {

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.addBasenames("message");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public CookieLocaleResolver localeResolver() {
		CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
		cookieLocaleResolver.setDefaultLocale(new Locale(AppConstants.LANGUAE_ENGLISH));
		cookieLocaleResolver.setCookieName(AppConstants.PATHVARIABLE_LANGUAE);
		cookieLocaleResolver.setCookieMaxAge(4800);
		return cookieLocaleResolver;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName(AppConstants.PATHVARIABLE_LANGUAE);
		return localeChangeInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}

	@Override
	public void configurePathMatch(PathMatchConfigurer pathMatchConfigurer) {
		AntPathMatcher antPathMatcher = new AntPathMatcher();
		antPathMatcher.setCaseSensitive(false);
		pathMatchConfigurer.setPathMatcher(antPathMatcher);
	}

//	@Bean
//	public JavaMailSender getJavaMailSender() {
//		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//		mailSender.setHost(AppConstants.MAIL_SMTP_SERVER);
//		mailSender.setPort(465);
//		mailSender.setUsername(AppConstants.MAIL_FROM_USER);
//		mailSender.setPassword(AppConstants.MAIL_FROM_PASS);
//
//		Properties props = mailSender.getJavaMailProperties();
//		props.put("mail.transport.protocol", "smtp");
//		props.put("mail.smtp.auth", "true");
//		props.put("mail.smtp.ssl.trust", AppConstants.MAIL_SMTP_SERVER);
//		props.put("mail.smtp.starttls.enable", "true");
//		props.put("mail.debug", "true");
//
//		return mailSender;
//	}
}
