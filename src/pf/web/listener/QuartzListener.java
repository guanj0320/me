package pf.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzListener implements ServletContextListener {
	private Log log = LogFactory.getLog("ERROR");
	public void contextDestroyed(ServletContextEvent arg0) {
		try {
			StdSchedulerFactory.getDefaultScheduler().shutdown();
			System.out.println("----------------------调度器已经停止！------------------");
	    } catch (SchedulerException e) {
	           log.error(e);
	    }

	}

	public void contextInitialized(ServletContextEvent arg0) {
		try {
			StdSchedulerFactory.getDefaultScheduler().start();	
			System.out.println("----------------------调度器已经启动！------------------");
		} catch(SchedulerException e) {
			log.error(e);
		}
	}

}
