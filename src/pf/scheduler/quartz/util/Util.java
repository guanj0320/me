package pf.scheduler.quartz.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class Util {
		public static String JOB_DEFINITIONS_PROP="definitionManager";

		private static transient final Log logger = LogFactory.getLog( Util.class );	
		
	    public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
	    static SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT_PATTERN);

	    public static final String CURRENT_SCHEDULER_PROP = "currentScheduler";

	    /** The field's value is $Id: Util.java,v 1.4 2004/01/13 19:44:34 jhouse Exp $ */
	    public static final String CVS_INFO = "$Id: Util.java,v 1.4 2004/01/13 19:44:34 jhouse Exp $";

	    public static Scheduler getCurrentScheduler(HttpServletRequest request)
	    {
	        Scheduler currentScheduler = (Scheduler) request.getSession(true).getAttribute(CURRENT_SCHEDULER_PROP);
	        if (currentScheduler == null)
	        {
//	            currentScheduler = SchedulerFactoryRepository.getDefaultScheduler();
				try {
					currentScheduler = StdSchedulerFactory.getDefaultScheduler();
				} catch (SchedulerException e) {
				}
	            request.getSession().setAttribute(CURRENT_SCHEDULER_PROP, currentScheduler);
	        }
	        return currentScheduler;
	    }

	    /**
	     *
	     * @param date
	     * @return
	     */
	    public static String getDateAsString(Date date)
	    {
	        if (date==null)
	        {
	            return null;
	        }
	        return dateFormatter.format(date);
	    }

	    /**
	     *
	     * @param dateStr
	     * @return
	     * @throws ParseException
	     */
	    public static Date parseStringToDate(String dateStr) throws ParseException
	    {
	        if (dateStr==null)
	        {
	            return null;
	        }
	        return dateFormatter.parse(dateStr);
	    }

	    /**
	     *
	     * @param trigger
	     * @return
	     */
	    public static String getTriggerType(Trigger trigger)
	    {
	        String type = null;
	        if (trigger instanceof SimpleTrigger)
	        {
	            type = "simple";
	        }
	        else if (trigger instanceof CronTrigger)
	        {
	            type = "cron";
	        }
	        else
	        {
	            type = trigger.getClass().getName();
	        }
	        return type;
	    }

	    /**
	     * the method scheduler.getTriggersForJob not implemented
	     * @param scheduler
	     * @param jobName
	     * @param jobGroup
	     * @return
	     * @throws ServletException
	     */
	    public static Trigger[] getTriggersFromJob(Scheduler scheduler, String jobName, String jobGroup) throws ServletException
	    {
	        List triggerList = new ArrayList();
	        String[] groups = null;
	        try
	        {
	            groups = scheduler.getTriggerGroupNames();
	        }
	        catch (SchedulerException e)
	        {
	            logger.error("When getting all trigger groups", e);
	            throw new ServletException("When getting all trigger groups", e);
	        }

	        for (int i = 0; i < groups.length; i++)
	        {
	            String group = groups[i];
	            String[] names = null;
	            try
	            {
	                names = scheduler.getTriggerNames(group);
	            }
	            catch (SchedulerException e)
	            {
	                logger.error("When getting all trigger in group groups " + group, e);
	                throw new ServletException("When getting all trigger in group groups " + group, e);
	            }
	            for (int j = 0; j < names.length; j++)
	            {
	                String name = names[j];
	                Trigger trigger = null;
	                try
	                {
	                    trigger = scheduler.getTrigger(name, group);
	                }
	                catch (SchedulerException e)
	                {
	                    logger.error("When getting trigger " + name + " in group " + group, e);
	                    throw new ServletException("When getting trigger " + name + " in group " + group, e);
	                }

	                if (trigger==null)
	                {
	                    logger.warn("The trigger "+name+" in group "+group+" was null");
	                    continue;
	                }

	                if (trigger.getJobName().equals(jobName) && trigger.getJobGroup().equals(jobGroup))
	                {
	                    triggerList.add(trigger);
	                }
	            }
	        }
	        Trigger[] retArr = new Trigger[triggerList.size()];
	        triggerList.toArray(retArr);
	        return retArr;
	    }
}
