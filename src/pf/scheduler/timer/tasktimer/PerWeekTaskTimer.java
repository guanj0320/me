package pf.scheduler.timer.tasktimer;

import java.util.Calendar;

import pf.tools.StringUtil;



public class PerWeekTaskTimer extends PerMonthTaskTimer {

	protected Calendar goToNextCalendar(Calendar cal, boolean includeToday) {
		int day = cal.get(Calendar.DAY_OF_WEEK);
		switch (day) {
		case Calendar.MONDAY:
			day = 1;
			break;
		case Calendar.TUESDAY:
			day = 2;
			break;
		case Calendar.WEDNESDAY:
			day = 3;
			break;
		case Calendar.THURSDAY:
			day = 4;
			break;
		case Calendar.FRIDAY:
			day = 5;
			break;
		case Calendar.SATURDAY:
			day = 6;
			break;
		default:
			day = 7;
		}
		String[] inDays = days.split(",");
		int period = includeToday ? 0 : 1;
		boolean setted = false;
		for (int i = day + period; i <= 7; i++) {
			if (StringUtil.exists("" + i, inDays)) {
				cal.add(Calendar.DATE, i - day);
				setted = true;
				break;
			}
		}
		if (!setted) {
			for (int i = 1; i <= day - period; i++) {
				if (StringUtil.exists("" + i, inDays)) {
					cal.add(Calendar.DATE, 7 - day + i);
					setted = true;
					break;
				}
			}
		}
		//启动服务时候includeToday=true,其他时候includeToday=false
		//这是一个特殊情况,当天启动服务,inDays只有当天一天时,需要设置成下周
		if (!setted) {
			if(StringUtil.exists("" + day,inDays)){
				cal.add(Calendar.DATE,7);
				setted = true;
			}
		}
		if (!setted) {
			this.setInvalid();
		}
		return cal;
	}
}
