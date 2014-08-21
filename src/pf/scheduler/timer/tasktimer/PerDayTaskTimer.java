package pf.scheduler.timer.tasktimer;

import java.text.SimpleDateFormat;
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
public class PerDayTaskTimer extends TaskTimerWrapper {
	/*
	 * 
	 */
	private Calendar cal = null;

	/**
	 * 
	 */
	public void init(XMLNode config) {
		String parse = config.getAttributeValue("time");
		SimpleDateFormat sdf = new SimpleDateFormat("kk:mm:ss");
		try {
			Date dt = sdf.parse(parse);
			Calendar startCal = Calendar.getInstance();
			startCal.setTime(dt);
			cal = Calendar.getInstance();

			int hours = cal.get(Calendar.HOUR_OF_DAY);
			int minutes = cal.get(Calendar.MINUTE);
			int seconds = cal.get(Calendar.SECOND);

			int dHours = startCal.get(Calendar.HOUR_OF_DAY);
			int dMinutes = startCal.get(Calendar.MINUTE);
			int dSeconds = startCal.get(Calendar.SECOND);

			int currentSeconds = seconds + minutes * 60 + hours * 3600;
			int dCurrentSeconds = dSeconds + dMinutes * 60 + dHours * 3600;

			cal.add(Calendar.SECOND, -1 * currentSeconds);
			if (currentSeconds > dCurrentSeconds) {
				cal.add(Calendar.DATE, 1);
			}
			cal.add(Calendar.SECOND, dCurrentSeconds);
		} catch (Exception e) {
			e.printStackTrace();
			this.setInvalid();
		}
	}

	/**
	 * 
	 */
	public Date getNextTime(boolean bFirst) {
		if (bFirst) {
			return cal.getTime();
		} else {
			cal.add(Calendar.DATE, 1);
			return cal.getTime();
		}
	}

}