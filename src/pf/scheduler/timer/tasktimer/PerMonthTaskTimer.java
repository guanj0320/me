package pf.scheduler.timer.tasktimer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import pf.tools.StringUtil;
import pf.tools.xml.XMLNode;



public class PerMonthTaskTimer extends TaskTimerWrapper {

	protected String days = "";

	protected String time = "";

	public Date getNextTime(boolean isFirst) {
		if (isFirst) {
			Calendar cal = Calendar.getInstance();
			try {
				resetTime(cal);
				goToNextCalendar(cal, true);
				if (cal.getTimeInMillis() <= Calendar.getInstance()
						.getTimeInMillis() + 1000) {
					cal = goToNextCalendar(cal, false);
				}
			} catch (Exception e) {
				e.printStackTrace();
				this.setInvalid();
			}
			return cal.getTime();
		} else {
			Calendar cal = Calendar.getInstance();
			try {
				this.resetTime(cal);
				goToNextCalendar(cal, false);
			} catch (Exception e) {
				e.printStackTrace();
				this.setInvalid();
			}
			return cal.getTime();
		}
	}

	private void resetTime(Calendar cal) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("kk:mm:ss");
		Date dt = sdf.parse(time);
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(dt);
		int hours = cal.get(Calendar.HOUR_OF_DAY);
		int minutes = cal.get(Calendar.MINUTE);
		int seconds = cal.get(Calendar.SECOND);

		int dHours = startCal.get(Calendar.HOUR_OF_DAY);
		int dMinutes = startCal.get(Calendar.MINUTE);
		int dSeconds = startCal.get(Calendar.SECOND);

		int currentSeconds = seconds + minutes * 60 + hours * 3600;
		int dCurrentSeconds = dSeconds + dMinutes * 60 + dHours * 3600;

		cal.add(Calendar.SECOND, -1 * currentSeconds);
		cal.add(Calendar.SECOND, dCurrentSeconds);
	}

	public void init(XMLNode config) {
		days = config.getAttributeValue("days");
		time = config.getAttributeValue("time");
		SimpleDateFormat sdf = new SimpleDateFormat("kk:mm:ss");
		try {
			sdf.parse(time);
		} catch (Exception e) {
			e.printStackTrace();
			this.setInvalid();
		}
	}

	protected Calendar goToNextCalendar(Calendar cal, boolean includeToday) {
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String[] inDays = days.split(",");
		boolean setted = false;
		// /�����죬��ӵ��쿪ʼ; ���򣬴���һ�쿪ʼ
		int period = includeToday ? 0 : 1;
		// /�ӵ����ڶ��쵽�µ�
		for (int i = day + period; i <= 31; i++) {
			if (StringUtil.exists("" + i, inDays)) {
				cal.set(Calendar.DAY_OF_MONTH, i);
				setted = true;
				break;
			}
		}
		// //���³�ʼ
		if (!setted) {
			cal.add(Calendar.MONTH, 1);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			for (int i = 1; i <= 31; i++) {
				if (StringUtil.exists("" + i, inDays)) {
					cal.set(Calendar.DAY_OF_MONTH, i);
					setted = true;
					break;
				}
			}
		}
		if (!setted) {
			this.setInvalid();
		}
		return cal;
	}

}
