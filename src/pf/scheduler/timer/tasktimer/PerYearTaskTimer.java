package pf.scheduler.timer.tasktimer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import pf.tools.xml.XMLNode;



public class PerYearTaskTimer extends TaskTimerWrapper {

	protected String[] day;

	protected String days = "";

	protected String time = "";
	
	private Calendar startCal = null;

	public Date getNextTime(boolean isFirst) {
		startCal = null;
		Calendar nowCal = Calendar.getInstance();
		int year = nowCal.get(Calendar.YEAR);
		goToNextCalendar(year,nowCal,isFirst);
		System.out.print(nowCal.getTime() + "*****" + startCal.getTime());
		return startCal.getTime();
	}
	
	private void goToNextCalendar(int year,Calendar nowCal,boolean isFirst){
		long value = -1;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		for (int i = 0; i < day.length; i++) {
			try {
				Date dt = sdf.parse(year + "-" + day[i]);
				Calendar currentCal = Calendar.getInstance();
				currentCal.setTime(dt);
				long val = currentCal.getTimeInMillis() - nowCal.getTimeInMillis();
				if(val == 0 && isFirst == true){
					value = val;
					startCal = nowCal;
					break;
				}else if(val > 0 && startCal == null){
					value = val;
					startCal = currentCal;
				}else if(val > 0 && val < value){
					value = val;
					startCal = currentCal;
				}
			} catch (ParseException e) {
				e.printStackTrace();
				this.setInvalid();
			}
		}
		if(value < 0 || startCal == null){
			goToNextCalendar(year+1,nowCal,isFirst);
		}
	}

	public void init(XMLNode config) {
		days = config.getAttributeValue("days");
		day = days.split(",");
		time = config.getAttributeValue("time");
		for (int i = 0; i < day.length; i++) {
			day[i] = day[i] + " " + time;
		}
	}

}
