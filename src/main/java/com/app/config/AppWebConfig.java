package com.app.config;

import java.util.Locale;
import java.util.Properties;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.web.WebMvcProperties.LocaleResolver;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.AntPathMatcher;
//import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.app.dao.INoteDao;
import com.app.dao.NoteDaoImpl;
import com.app.util.AppConstants;
import com.app.util.AppException;

@EnableWebMvc
@Configuration
@ComponentScan("com.app")
@EnableTransactionManagement
public class AppWebConfig extends WebMvcConfigurerAdapter{

	public static String mySQLServerConfig = AppConstants.JDBC_MYSQL_PREFIX + AppConstants.DB_SERVER +
												AppConstants.COLON + AppConstants.DB_SERVER_PORT +
													AppConstants.FORWAREDSLASH + AppConstants.DB_NAME;
	
	@Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/views/");
        viewResolver.setSuffix(".jsp");
        registry.viewResolver(viewResolver);
    }
	
	@Bean(name="messageSource")
	public MessageSource messageSource(){
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.addBasenames("message");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
	
	@Bean
	public CookieLocaleResolver localeResolver(){
		CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
		cookieLocaleResolver.setDefaultLocale(new Locale(AppConstants.LANGUAE_ENGLISH));
		cookieLocaleResolver.setCookieName(AppConstants.PATHVARIABLE_LANGUAE);
		cookieLocaleResolver.setCookieMaxAge(4800);
		return cookieLocaleResolver;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry interceptorRegistry){
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName(AppConstants.PATHVARIABLE_LANGUAE);
		interceptorRegistry.addInterceptor(localeChangeInterceptor);
	}
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/res/**")
        .addResourceLocations("/res/");
    }
	
	@Bean(name="dataSource")
	public static DataSource getDataSource(){
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		basicDataSource.setUrl(mySQLServerConfig);
		basicDataSource.setUsername(AppConstants.DB_USER_NAME);
		basicDataSource.setPassword(AppConstants.DB_USER_PASS);
		return basicDataSource;
	}
	
	private static Properties getHibernateProperties(){
		Properties properties = new Properties();
		properties.put("connection.url", mySQLServerConfig);
		properties.put("connection.driver_class", AppConstants.JDBC_MYSQL_DRIVER);
		properties.put("connection.username", AppConstants.DB_USER_NAME);
		properties.put("connection.password", AppConstants.DB_USER_PASS);
        properties.put("connection.provider_class","org.hibernate.connection.C3P0ConnectionProvider");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        properties.put("hibernate.c3p0.acquire_increment","1");
        properties.put("hibernate.c3p0.idle_test_period","60");
        properties.put("hibernate.c3p0.min_size","1");
        properties.put("hibernate.c3p0.max_size","2");
        properties.put("hibernate.c3p0.max_statements","50");
        properties.put("hibernate.c3p0.timeout","0");
        properties.put("hibernate.c3p0.acquireRetryAttempts","1");
        properties.put("hibernate.c3p0.acquireRetryDelay","250");
        properties.put("hibernate.show_sql","true");
        properties.put("hibernate.use_sql_comments","true");
        properties.put("hibernate.transaction.factory_class","org.hibernate.transaction.JDBCTransactionFactory");
        //properties.put("hibernate.current_session_context_class","thread");
		
		return properties;
	}
	
	
	@Autowired
    @Qualifier("sessionFactory")
    private static SessionFactory sessionFactory = buildSessionFactory();
	
	@Bean
    private static SessionFactory buildSessionFactory() {
        try {
            if (sessionFactory == null) {
            	LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(getDataSource());
            	sessionBuilder.addProperties(getHibernateProperties());
            	sessionBuilder.scanPackages("com.app.model");
            	return sessionBuilder.buildSessionFactory();
            }
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new AppException(ex);
        }
        return sessionFactory;
    }
	
	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory){
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
		return transactionManager;
	}
	
	@Autowired
    @Bean(name = "noteDao")
    public INoteDao getNoteDao(SessionFactory sessionFactory) {
    	return new NoteDaoImpl(sessionFactory);
    }
	@Override
	public void configurePathMatch(PathMatchConfigurer pathMatchConfigurer){
		AntPathMatcher antPathMatcher = new AntPathMatcher();
		antPathMatcher.setCaseSensitive(false);
		pathMatchConfigurer.setPathMatcher(antPathMatcher);
	}
	
	@Bean
	public JavaMailSender getJavaMailSender(){
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(AppConstants.MAIL_SMTP_SERVER);
		mailSender.setPort(587);
		mailSender.setUsername(AppConstants.MAIL_FROM_USER);
		mailSender.setPassword(AppConstants.MAIL_FROM_PASS);
		
		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");
	    
		return mailSender;
	}
}
