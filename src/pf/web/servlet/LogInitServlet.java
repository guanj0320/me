package pf.web.servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * 
 * 类描述/Class Description：动态生成log文件的地址
 *
 */
public class LogInitServlet extends HttpServlet {
	
	private static Logger logger = Logger.getLogger(LogInitServlet.class); 

	public void init(ServletConfig config) throws ServletException {
		String prefix = config.getServletContext().getRealPath("/WEB-INF/logs"); 
		System.setProperty("loghome",prefix);//设置系统变量
		String filePath = config.getInitParameter("config"); 
		Properties props = new Properties(); 
		try { 
			FileInputStream istream = new FileInputStream(filePath); 
			props.load(istream); 
			istream.close(); 
			//errorfile
			String errorFile = prefix + props.getProperty("log4j.appender.errorfile.File");//设置路径 
			props.setProperty("log4j.appender.errorfile.File",errorFile); 
			//sqlfile
			String sqlFile = prefix + props.getProperty("log4j.appender.sqlfile.File");//设置路径 
			props.setProperty("log4j.appender.sqlfile.File",sqlFile); 
			//bizfile
			String bizFile = prefix + props.getProperty("log4j.appender.bizfile.File");//设置路径 
			props.setProperty("log4j.appender.bizfile.File",bizFile); 
			PropertyConfigurator.configure(props);//装入log4j配置信息 
			logger.info(errorFile);
			logger.info(sqlFile);
			logger.info(bizFile);
		} catch (IOException e) { 			
			logger.debug("Could not read configuration file [" + filePath + "]."); 
			logger.debug("Ignoring configuration file [" + filePath + "]."); 
			throw new ServletException("配置路径不存在！");
		} 

//		try {
//			System.setProperty("loghome",this.getServletContext().getRealPath("/logs"));
//			String path = this.getInitParameter("config");
//			try {
//				path = this.getServletContext().getRealPath(path);
//			} catch (Exception e) {
//				throw new ServletException("配置路径不存在！");
//			}
//		    PropertyConfigurator.configure(path);
//		} catch(Exception e) {
//			e.printStackTrace();
//			System.out.println(e.getMessage());
//		}
		System.out.println("----------------------Log4j初始化已完成！------------------");
	}
}
