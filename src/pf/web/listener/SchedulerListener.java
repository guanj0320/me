package pf.web.listener;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import pf.scheduler.timer.Scheduler;



public class SchedulerListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent arg0) {
		Scheduler.init(arg0.getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "classes" + File.separator + "config" + File.separator + "timer" + File.separator + "schedules.xml");
		System.out.println("----------------------调度器已经启动！------------------");
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		Scheduler.stop();

	}

}
