package pf.scheduler.timer.tasktimer;

import java.util.Calendar;
import java.util.Date;

import pf.tools.xml.XMLNode;



/**
 * 
 * @author Administrator
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DelayTaskTimer extends TaskTimerWrapper {
    /*
     *  
     */
    private long delay = 0;

    /**
     *  
     */
    public void init(XMLNode config) {
        this.period = Long.valueOf(config.getAttributeValue("period"))
                .longValue();
        this.delay = Long.valueOf(config.getAttributeValue("delay"))
                .longValue();
    }

    /**
     *  
     */
    public Date getNextTime(boolean bFirst) {
        if (bFirst) {
        	Calendar cal = Calendar.getInstance();
            cal.add(Calendar.SECOND, (int) delay);
            return cal.getTime();
        } else {
            if (this.period != -1) {
            	Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, (int) this.period);
                return cal.getTime();
            } else {
                return null;
            }
        }
    }

}