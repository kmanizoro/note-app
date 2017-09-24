package com.app.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitiallizer extends AbstractAnnotationConfigDispatcherServletInitializer implements WebApplicationInitializer  {
	
	private static final Map<String,String> initParameters = new HashMap<String,String>();
	static {
		//initParameters.put("loadOnStartup", "1");
		initParameters.put("throwExceptionIfNoHandlerFound", "true");
	}
	
	@Override
    public void onStartup(ServletContext container) throws ServletException {
      // Create the 'root' Spring application context
      AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
      rootContext.register(AppWebConfig.class);

      // Manage the lifecycle of the root application context
      container.addListener(new ContextLoaderListener(rootContext));

      // Register and map the dispatcher servlet
      ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", new DispatcherServlet(rootContext));
      dispatcher.setLoadOnStartup(1);
      dispatcher.addMapping("/");
      dispatcher.setInitParameters(initParameters);
    }

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		
		return new String[] {"/"};
	}
	@Override
    protected Filter[] getServletFilters() {
        Filter [] singleton = { new AppCROSFilter() };
        return singleton;
    }

}
