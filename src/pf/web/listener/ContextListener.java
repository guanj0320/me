package pf.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ContextListener extends ContextLoaderListener {
	/**
	 * Logger for this class
	 */
	private Log log = LogFactory.getLog(ContextListener.class);

	private static ApplicationContext context;
	
	public void contextInitialized(ServletContextEvent event) {
		log.debug("ContextListener contextInitialized method running...");
		
		super.contextInitialized(event);

		ServletContext context = event.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(context);
		ContextListener.setContext(ctx);
		
		log.debug("ContextListener context initialized ok.");
	}

	public static ApplicationContext getContext() {
		return context;
	}

	public static void setContext(ApplicationContext context) {
		ContextListener.context = context;
	}

}
