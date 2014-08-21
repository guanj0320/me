/**
 * @(#)DateUtil.java			
 * @update		10/03/09
 */
package pf.tools;

import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 类作用：该类为日期类，用于对日期方面的操作
 * 
 * @author 殷云龙
 * @version 4.0.0 
 * */
public class DateUtil
{
	private static final Log log = LogFactory.getLog(DateUtil.class);
	public static final String MTH_JAN = "Jan";
	public static final String MTH_FEB = "Feb";
	public static final String MTH_MAR = "Mar";
	public static final String MTH_APR = "Apr";
	public static final String MTH_MAY = "May";
	public static final String MTH_JUN = "Jun";
	public static final String MTH_JUL = "Jul";
	public static final String MTH_AUG = "Aug";
	public static final String MTH_SEP = "Sep";
	public static final String MTH_OCT = "Oct";
	public static final String MTH_NOV = "Nov";
	public static final String MTH_DEC = "Dec";
	
	/**
	* 作用：获取指定的日期
	* 
	* @param	year		传入 年
	* @param	month		传入 月
	* @param	day			传入 日
	* @return	Date		返回日期
	*/
	public static Date getDate(int year, int month, int day)
	{
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day, 0, 0, 0);
		return cal.getTime();
	}
	/**
	 * 作用：返回获取的日期
	 * 
	 * @param	Date		返回日期
	 * */
	public static Date getDate() {
		return new Date();
	}

	/**
	 * 作用：从已存在的日期与时间中获取一个日期格式
	 * 
	 * @param	date			输入需要得到的日期
	 * @param	time			输入需要得到的时间
	 * @return	Date			返回获取的日期
	 */
	public static Date getDateTime(Date date, Date time)
	{
		if (null == date && null == time)
		{
			return null;
		}
		if (null == date && null != time)
		{
			date = DateUtil.getSystemDate();
		}
		if (null != date && null == time)
		{
			time = DateUtil.getSystemTimestamp();
		}
		Calendar cal = Calendar.getInstance();
		int year = Integer.parseInt(DateUtil.parseDate(date, "yyyy"));
		int month = Integer.parseInt(DateUtil.parseDate(date, "M"));
		int day = Integer.parseInt(DateUtil.parseDate(date, "dd"));
		int hour = Integer.parseInt(DateUtil.parseDate(time, "h"));
		int minute = Integer.parseInt(DateUtil.parseDate(time, "m"));
		cal.set(year, month, day, hour, minute);
		return cal.getTime();
	}
	/**
	 * 作用：判断两个日期是否相等
	 * 
	 * @param	date1				传入需要对比的日期1
	 * @param	date2				传入需要对比的日期2
	 * @return	boolean				当两个日期相等时返回true，贩子返回false
	 * */
	public static boolean isDateEqual(Date date1, Date date2)
	{
		if ((date1 == null) || (date2 == null))
		{
			return false;
		}

		return resetTime(date1).compareTo(resetTime(date2)) == 0;
	}
	/**
	 * 作用：设置给定 ID 的 TimeZone
	 * 
	 * @param	timeZoneID			指定时间域
	 * */
	public static void setDefaultTimeZone(String timeZoneID)
	{
		TimeZone.setDefault(TimeZone.getTimeZone(timeZoneID));
	}
	/**
	 * 作用：
	 * 
	 * @param	type
	 * @param	startDate
	 * @param	endDate
	 * @return	int
	 * */
	public static final int getElapsedTime(int type, Date startDate, Date endDate)
	{
		int elapsed = 0;

		if ((startDate == null) || (endDate == null))
		{
			return -1;
		}

		if (startDate.after(endDate))
		{
			return -1;
		}

		GregorianCalendar gc1 = (GregorianCalendar) GregorianCalendar.getInstance();
		GregorianCalendar gc2 = (GregorianCalendar) gc1.clone();
		gc1.setTime(startDate);
		gc2.setTime(endDate);

		gc1.clear(Calendar.MILLISECOND);
		gc1.clear(Calendar.SECOND);
		gc1.clear(Calendar.MINUTE);
		gc1.clear(Calendar.HOUR_OF_DAY);
		gc2.clear(Calendar.MILLISECOND);
		gc2.clear(Calendar.SECOND);
		gc2.clear(Calendar.MINUTE);
		gc2.clear(Calendar.HOUR_OF_DAY);

		if ((type != Calendar.MONTH) && (type != Calendar.YEAR))
		{
			type = Calendar.DATE;
		}

		if (type == Calendar.MONTH)
		{
			gc1.clear(Calendar.DATE);
			gc2.clear(Calendar.DATE);
		}

		if (type == Calendar.YEAR)
		{
			gc1.clear(Calendar.DATE);
			gc2.clear(Calendar.DATE);
			gc1.clear(Calendar.MONTH);
			gc2.clear(Calendar.MONTH);
		}

		while (gc1.before(gc2))
		{
			gc1.add(type, 1);
			elapsed++;
		}

		return elapsed;
	}
	/**
	 * 作用：判断传入的日期是否为某月的最后一天
	 * 
	 * @param	date			传入需要判断的日期
	 * @return	boolean			当日期为某月的最后一天时返回true，反之返回false
	 * */
	public static final boolean isEndOfTheMonth(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		if (cal.get(Calendar.DATE) == maxDay)
		{
			return true;
		}

		return false;
	}
	/**
	 * 作用：判断传入的日期是否为某年的最后一天
	 * 
	 * @param	date			传入需要判断的日期
	 * @return	boolean			当日期为某年的最后一天时返回true，反之返回false
	 * */
	public static final boolean isEndOfTheYear(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		if ((cal.get(Calendar.MONTH) == 11) && (cal.get(Calendar.DATE) == 31))
		{
			return true;
		}

		return false;
	}
	/**
	 * 作用：传入一个日期，获取该日期中，该月最后一天
	 * 
	 * @param	date			传入需要判断的日期
	 * @return	int				返回传入日期中对应该月的最后一天
	 * */
	public static final int getLastDayOfTheMonth(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	/**
	 * 作用：传入英文简写的月份返回阿拉伯数字的月份
	 * 
	 * @param	month			传入一个月英文简写
	 * @return	int				返回这个简写英文对应的月份
	 * */
	public static int getMthInInt(String month)
	{
		if (month.equalsIgnoreCase(MTH_JAN))
		{
			return 1;
		}
		else if (month.equalsIgnoreCase(MTH_FEB))
		{
			return 2;
		}
		else if (month.equalsIgnoreCase(MTH_MAR))
		{
			return 3;
		}
		else if (month.equalsIgnoreCase(MTH_APR))
		{
			return 4;
		}
		else if (month.equalsIgnoreCase(MTH_MAY))
		{
			return 5;
		}
		else if (month.equalsIgnoreCase(MTH_JUN))
		{
			return 6;
		}
		else if (month.equalsIgnoreCase(MTH_JUL))
		{
			return 7;
		}
		else if (month.equalsIgnoreCase(MTH_AUG))
		{
			return 8;
		}
		else if (month.equalsIgnoreCase(MTH_SEP))
		{
			return 9;
		}
		else if (month.equalsIgnoreCase(MTH_OCT))
		{
			return 10;
		}
		else if (month.equalsIgnoreCase(MTH_NOV))
		{
			return 11;
		}
		else if (month.equalsIgnoreCase(MTH_DEC))
		{
			return 12;
		}
		else
		{
			return 0;
		}
	}
	/**
	 * 作用：返回下一个工作日期
	 * 
	 * @return	Date			返回下一个工作日期
	 * */
	public static Date getNextWorkingDay()
	{
		Date nextWorkingDay = DateUtil.addDaysToDate(DateUtil.getSystemDate(), 1);
		Calendar c = Calendar.getInstance();
		c.setTime(nextWorkingDay);

		int day = c.get(Calendar.DAY_OF_WEEK);
		
		//翟文浩修改，周六也要排除
		if (day == Calendar.SATURDAY)
		{
			nextWorkingDay = DateUtil.addDaysToDate(nextWorkingDay, 2);
		}
		//翟文浩修改，周六也要排除
		
		if (day == Calendar.SUNDAY)
		{
			nextWorkingDay = DateUtil.addDaysToDate(nextWorkingDay, 1);
		}

		return nextWorkingDay;
	}
	
	/**
	 * 几个工作日后的日期 翟文浩
	 * 
	 * @param	numDay			传入的日期数
	 * @return	Date			返回工作日后的工作日期
	 */
	public static Date getNextNWorkingDay(int numDay) {
		Date nextNWorkingDay = DateUtil.getSystemDate();
		
		for(int i = 0; i < numDay; i++) {
			nextNWorkingDay = DateUtil.addDaysToDate(nextNWorkingDay, 1);
			
			Calendar c = Calendar.getInstance();
			c.setTime(nextNWorkingDay);

			int day = c.get(Calendar.DAY_OF_WEEK);
			
			if (day == Calendar.SATURDAY)
			{
				nextNWorkingDay = DateUtil.addDaysToDate(nextNWorkingDay, 2);
			}
			if (day == Calendar.SUNDAY)
			{
				nextNWorkingDay = DateUtil.addDaysToDate(nextNWorkingDay, 1);
			}
		}
		
		return nextNWorkingDay;
	}
	/**
	 * 作用：判断两个日期，前一个参数的日期，是否比后一个参数的日期靠前
	 * 
	 * @param	startDate				传入需要判断的开始日期	
	 * @param	endDate					传入需要判断的结束日期
	 * @return	boolean					当开始日期在结束日期之前时返回true，反之返回false
	 * */
	public static boolean isStartBeforeEndDate(Date startDate, Date endDate)
	{
		if ((startDate == null) || (endDate == null))
		{
			return false;
		}

		return resetTime(startDate).compareTo(resetTime(endDate)) < 0;
	}
	/**
	 * 作用：判断两个日期，前一个参数的日期，是否比后一个参数的日期靠前
	 * 
	 * @param	startDate				传入需要判断的开始日期（包含小时，分，秒）	
	 * @param	endDate					传入需要判断的结束日期（包含小时，分，秒）	
	 * @return	boolean					当开始日期在结束日期之前时返回true，反之返回false
	 * */
	public static boolean isStartBeforeEndTime(Date startTime, Date endTime)
	{
		if ((startTime == null) || (endTime == null))
		{
			return true;
		}
		log.debug("Start Time is " + startTime.getTime());
		log.debug("End Time is " + endTime.getTime());
		if (startTime.getTime() > endTime.getTime())
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	/**
	 * 作用：判断传入的日期是否为该月的第一天
	 * 
	 * @param	date		传入需要判断的日期
	 * @return	boolean		当该日期为该月的第一天时，返回true，反之返回false
	 * */
	public static final boolean isStartOfTheMonth(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		if (cal.get(Calendar.DATE) == 1)
		{
			return true;
		}

		return false;
	}
	/**
	 * 作用：获取传入日期中的月份
	 * 
	 * @param	date			传入日期
	 * @return	int				返回获取的该日期中的月份
	 * */
	public static final int getMonth(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		return cal.get(Calendar.MONTH);

	}
	/**
	 * 作用：获取传入日期中的年
	 * 
	 * @param	date			传入日期
	 * @return	int				返回获取的该日期中的年
	 * */
	public static final int getYear(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		return cal.get(Calendar.YEAR);

	}
	/**
	 * 作用：判断传入的日期是否是一年的第一天
	 * 
	 * @param	date			传入日期
	 * @return	boolean			若该日期为一年的第一天则返回true，反之返回false
	 * */
	public static final boolean isStartOfTheYear(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		if ((cal.get(Calendar.MONTH) == 1) && (cal.get(Calendar.DATE) == 1))
		{
			return true;
		}

		return false;
	}
	/**
	 * 作用：输入数字的月份，返回英文的月份信息
	 * 
	 * @param	month			输入数字的月份
	 * @return	String			返回英文的月份信息
	 * */
	public static String getStrMth(int month)
	{
		if (month == 1)
		{
			return MTH_JAN;
		}
		else if (month == 2)
		{
			return MTH_FEB;
		}
		else if (month == 3)
		{
			return MTH_MAR;
		}
		else if (month == 4)
		{
			return MTH_APR;
		}
		else if (month == 5)
		{
			return MTH_MAY;
		}
		else if (month == 6)
		{
			return MTH_JUN;
		}
		else if (month == 7)
		{
			return MTH_JUL;
		}
		else if (month == 8)
		{
			return MTH_AUG;
		}
		else if (month == 9)
		{
			return MTH_SEP;
		}
		else if (month == 10)
		{
			return MTH_OCT;
		}
		else if (month == 11)
		{
			return MTH_NOV;
		}
		else if (month == 12)
		{
			return MTH_DEC;
		}
		else
		{
			return "";
		}
	}
	/**
	 * 作用：计算结束日期到开始日期之间的时间差
	 * 
	 * @param	startDate				传入 开始的日期
	 * @param	endDate					传入 结束的日期
	 * */
	public static final int[] computeDuration(Date startDate, Date endDate)
	{
		Calendar from = Calendar.getInstance();
		Calendar to = Calendar.getInstance();
		from.setTime(startDate);
		to.setTime(endDate);

		int birthYYYY = from.get(Calendar.YEAR);
		int birthMM = from.get(Calendar.MONTH);
		int birthDD = from.get(Calendar.DAY_OF_MONTH);
		int asofYYYY = to.get(Calendar.YEAR);
		int asofMM = to.get(Calendar.MONTH);
		int asofDD = to.get(Calendar.DAY_OF_MONTH);
		int ageInYears = asofYYYY - birthYYYY;
		int ageInMonths = asofMM - birthMM;
		int ageInDays = asofDD - birthDD + 1;

		if (ageInDays < 0)
		{
			ageInDays += from.getActualMaximum(Calendar.DAY_OF_MONTH);
			ageInMonths--;
		}

		if (ageInDays == to.getActualMaximum(Calendar.DAY_OF_MONTH))
		{
			ageInDays = 0;
			ageInMonths++;
		}

		if (ageInMonths < 0)
		{
			ageInMonths += 12;
			ageInYears--;
		}

		if ((birthYYYY < 0) && (asofYYYY > 0))
		{
			ageInYears--;
		}

		if (ageInYears < 0)
		{
			ageInYears = 0;
			ageInMonths = 0;
			ageInDays = 0;
		}

		int[] result = new int[3];
		result[0] = ageInYears;
		result[1] = ageInMonths;
		result[2] = ageInDays;

		return result;
	}
	/**
	 *  作用：获取系统日期
	 * 
	 * */
	public static java.sql.Date getSystemDate()
	{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		java.sql.Date sysDate = new java.sql.Date(cal.getTime().getTime());

		return sysDate;
	}
	/**
	 * 作用：获取系统时间
	 * */
	public static Timestamp getSystemTimestamp()
	{
		Calendar cal = Calendar.getInstance();
		return new Timestamp(cal.getTime().getTime());
	}
	/**
	 * 作用：验证传入的字符串是否是年的格式
	 * 
	 * @param	s				传入 s 字符串
	 * @return	boolean			如果是年的格式则返回true，如果不是则返回false
	 * */
	public static boolean isValidYearFormat(String s)
	{
		if (s == null)
		{
			return false;
		}
		else if (s.trim().length() == 4)
		{
			return true;
		}

		return false;
	}
	/**
	 * 作用：将日期格式转换为自己需要的格式
	 * 
	 * @param	date					传入 需要格式化的日期
	 * @param	strFormat				传入 需要格式化的日期型式
	 * @return	String					返回格式化后的日期
	 * */
	public static String getDate(Date date, String strFormat)
	{
		return DateUtil.parseDate(date, strFormat);
	}
	/**
	 * 作用：获取当前日期格式为“yyyy-MM-dd”
	 * 
	 * */
	public static String getCurrentDate() {
		return DateUtil.parseDate(new Date(), "yyyy-MM-dd");
	}
	/**
	 * 作用：获取当前日期格式为“yyyy-MM-dd HH:mm:ss”
	 * 
	 * */
	public static String getCurrentDateTime() {
		return DateUtil.parseDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * 作用：判断字符串格式是否是日期类型格式为ddMMyyyy
	 * 
	 * @param	strDate				需要判断的字符串
	 * @return	boolean				当字符串是日期格式的，则返回true，反之返回false
	 * */
	public static boolean isValidDate(String strDate)
	{
		return DateUtil.toDate(strDate, "ddMMyyyy") != null;
	}
	/**
	 * 作用：判断字符串格式是否是指定的日期类型格式
	 * 
	 * @param	strDate				需要判断的字符串
	 * @param	dateStrFormat		输入指定判断的格式
	 * @return	boolean				当字符串是日期格式的，则返回true，反之返回false
	 * */
	public static boolean isValidDate(String strDate, String dateStrFormat)
	{
		return DateUtil.toDate(strDate, dateStrFormat) != null;
	}
	/**
	 * 作用：为指定的日期增加或减少天数
	 * 
	 * @param	type				日历字段，指定是 DATE ，DAY_OF_MONTH ，DAY_OF_WEEK，DAY_OF_WEEK_IN_MONTH ，DAY_OF_YEAR 哪种类型的增加
	 * @param	date				需要添加或减少天数的日期
	 * @param	num					添加的天数为整数，减少的天数为负数
	 * @param	Date				返回最终的日期
	 * */
	public static final Date addDate(int type, Date date, int num)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.add(type, num);

		return new Date(cal.getTime().getTime());
	}
	/**
	 * 作用：为指定的日期增加或减少天数
	 * 
	 * @param	date				需要添加或减少天数的日期
	 * @param	numDays				添加的天数为整数，减少的天数为负数
	 * @param	Date				返回最终的日期
	 * */
	public static Date addDaysToDate(Date date, int numDays)
	{
		if (date == null)
		{
			return null;
		}

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, numDays);

		return c.getTime();
	}
	/**
	 * 作用：为指定的日期增加或减少小时
	 * 
	 * @param	date				需要添加或减少小时的日期
	 * @param	numHours			添加的小时为整数，减少的小时为负数
	 * @param	Date				返回最终的日期
	 * */
	public static Date addHoursToDate(Date date, int numHours)
	{
		if (date == null)
		{
			return null;
		}

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR_OF_DAY, numHours);

		return c.getTime();
	}
	/**
	 * 作用：为指定的日期增加或减少分钟
	 * 
	 * @param	date				需要添加或减少分钟的日期
	 * @param	numMins				添加的分钟为整数，减少的分钟为负数
	 * @param	Date				返回最终的日期
	 * */
	public static Date addMinutesToDate(Date date, int numMins)
	{
		if (date == null)
		{
			return null;
		}

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, numMins);

		return c.getTime();
	}
	/**
	 * 作用：为指定的日期增加或减少月数
	 * 
	 * @param	date				需要添加或减少月数的日期
	 * @param	numMonths			添加的月数为整数，减少的月数为负数
	 * @param	Date				返回最终的日期
	 * */
	public static Date addMonthsToDate(Date date, int numMonths)
	{
		if (date == null)
		{
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, numMonths);
		return c.getTime();
	}
	/**
	 * 作用：为指定的日期增加或减少年数
	 * 
	 * @param	date				需要添加或减少年数的日期
	 * @param	numYears			添加的年数为整数，减少的年数为负数
	 * @param	Date				返回最终的日期
	 * */
	public static Date addYearsToDate(Date date, int numYears)
	{
		if (date == null)
		{
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, numYears);
		return c.getTime();
	}
	/**
	 * 作用：日期的比较
	 * 
	 * @param	date1				需要比较的日期1
	 * @param	date2				需要比较的日期2
	 * @return	int					当返回0时表示两个日期相同，1表示date1大，－1表示date2大
	 * */
	public static int compareDates(Date date1, Date date2)
	{
		if ((date1 == null) && (date2 == null))
		{
			return 0;
		}

		if (date1 == null)
		{
			return -1;
		}

		if (date2 == null)
		{
			return 1;
		}

		String strFormat = "yyyyMMdd";
		SimpleDateFormat dateFormat = new SimpleDateFormat(strFormat);

		int intDate1 = Integer.parseInt(dateFormat.format(date1));
		int intDate2 = Integer.parseInt(dateFormat.format(date2));

		if (intDate1 == intDate2)
		{
			return 0;
		}

		if (intDate1 > intDate2)
		{
			return 1;
		}

		return -1;
	}
	/**
	 * 作用：将日期格式转换为String类型
	 * 
	 * @param	date					需要转换的日期	
	 * @param	formatStr				转换的日期格式
	 * @return	String					转换为字符串格式的日期
	 * */
	public static String parseDate(Date date, String formatStr)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);

		if (date == null)
		{
			return null;
		}
		else
		{
			return dateFormat.format(date);
		}
	}
	
	/**
	 * 作用：重置时间
	 * 
	 * @param	date				需要重置的日期
	 * @return	Date				返回小时，分钟，秒重置为0的日期
	 * */
	public static Date resetTime(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}
	/**
	 * 作用：将日期格式的字符串（包含时，分，秒）转换为日期格式
	 * 
	 * @param	strDateTime					需要转换的字符串日期		
	 * @param	dateTimeFormat				转换成的日期格式
	 * @return	Date						转换成的日期
	 * */
	public static Date toDate(String strDateTime, String dateTimeFormat)
	{
		if ((strDateTime == null) || (strDateTime.length() == 0) || (dateTimeFormat == null)
				|| (dateTimeFormat.length() == 0))
		{
			return null;
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat(dateTimeFormat);
		Date date = dateFormat.parse(strDateTime, new ParsePosition(0));

		if (date == null)
		{
			return null;
		}

		String dateStr = parseDate(date, dateTimeFormat);

		if (!strDateTime.equals(dateStr))
		{
			return null;
		}

		return date;
	}
	/**
	 * 作用：月份的写法转化，英文式转换成阿拉伯数字式
	 * 
	 * @param	mthMM				传入英文的月份简写
	 * @return	String				返回字符串式阿拉伯数字的月份
	 * */
	public static String toMMFormat(String mthMMM)
	{
		if (mthMMM.equalsIgnoreCase(MTH_JAN))
		{
			return "01";
		}
		else if (mthMMM.equalsIgnoreCase(MTH_FEB))
		{
			return "02";
		}
		else if (mthMMM.equalsIgnoreCase(MTH_MAR))
		{
			return "03";
		}
		else if (mthMMM.equalsIgnoreCase(MTH_APR))
		{
			return "04";
		}
		else if (mthMMM.equalsIgnoreCase(MTH_MAY))
		{
			return "05";
		}
		else if (mthMMM.equalsIgnoreCase(MTH_JUN))
		{
			return "06";
		}
		else if (mthMMM.equalsIgnoreCase(MTH_JUL))
		{
			return "07";
		}
		else if (mthMMM.equalsIgnoreCase(MTH_AUG))
		{
			return "08";
		}
		else if (mthMMM.equalsIgnoreCase(MTH_SEP))
		{
			return "09";
		}
		else if (mthMMM.equalsIgnoreCase(MTH_OCT))
		{
			return "10";
		}
		else if (mthMMM.equalsIgnoreCase(MTH_NOV))
		{
			return "11";
		}
		else if (mthMMM.equalsIgnoreCase(MTH_DEC))
		{
			return "12";
		}

		return null;
	}
	/**
	 * 作用：月份的写法转化，转换成英文式
	 * 
	 * @param	mthMM				传入字符串式阿拉伯数字的月份
	 * @return	String				返回英文的月份简写
	 * */
	public static String toMMMFormat(String mthMM)
	{
		if ("01".equals(mthMM))
		{
			return MTH_JAN;
		}
		else if ("02".equals(mthMM))
		{
			return MTH_FEB;
		}
		else if ("03".equals(mthMM))
		{
			return MTH_MAR;
		}
		else if ("04".equals(mthMM))
		{
			return MTH_APR;
		}
		else if ("05".equals(mthMM))
		{
			return MTH_MAY;
		}
		else if ("06".equals(mthMM))
		{
			return MTH_JUN;
		}
		else if ("07".equals(mthMM))
		{
			return MTH_JUL;
		}
		else if ("08".equals(mthMM))
		{
			return MTH_AUG;
		}
		else if ("09".equals(mthMM))
		{
			return MTH_SEP;
		}
		else if ("10".equals(mthMM))
		{
			return MTH_OCT;
		}
		else if ("11".equals(mthMM))
		{
			return MTH_NOV;
		}
		else if ("12".equals(mthMM))
		{
			return MTH_DEC;
		}

		return null;
	}
	/**
	 * 作用：将java.sql.Date类型日期转换成 java.util.Date格式
	 * */
	public static java.sql.Date toSQLDate(String strDateTime, String dateTimeFormat)
	{
		Date date = toDate(strDateTime, dateTimeFormat);

		if (date == null)
		{
			return null;
		}

		return new java.sql.Date(date.getTime());
	}
	/**
	 * 作用：将java.util.Date类型日期转换成 java.sql.Date格式
	 * */
	public static java.sql.Date toSQLDate(Date date)
	{
		if (date == null)
		{
			return null;
		}

		return new java.sql.Date(date.getTime());
	}
	/**
	 * 作用：将字符串式日期转换成Timestamp日期类型
	 * 
	 * @param	dateTimeStr			传入的 记录日期的字符串
	 * @param	dateTimeFormat		需要转换成的格式
	 * */
	public static Timestamp toTimestamp(String dateTimeStr, String dateTimeFormat)
	{
		return toTimestamp(toDate(dateTimeStr, dateTimeFormat));
	}
	/**
	 * 作用：将Date格式转换为Timestamp类型
	 * 
	 * @param	date		传入的 Date 对象
	 * */
	public static Timestamp toTimestamp(Date date)
	{
		if (date == null)
		{
			return null;
		}

		return new Timestamp(date.getTime());
	}
	/**
	 * 作用：将Timestamp格式转换为Date类型
	 * 
	 * @param	timeStamp		传入的 Timestamp 对象
	 * @return	Date			最后获取的日期
	 * */
	public static Date toDate(Timestamp timeStamp)
	{
		if (timeStamp == null)
		{
			return null;
		}

		return new Date(timeStamp.getTime());
	}
	/**
	 * 作用：限定返回的string为2位的数字，若传入的数字是1位则，在其前面加0，若是两位，则原数返回
	 * 
	 * @param	number				需要添加0的数字
	 * @param	String				返回调整后的数字
	 * */
	public static String toTwoDigits(String number)
	{
		return StringUtils.leftPad(number, 2, "0");
	}
	/**
	 * 作用：
	 * 
	 * @param	originalJobDate	
	 * @param	months	
	 * @param	days
	 * @return	Date[]
	 * */
	public static Date[] getDurationDates(Date originalJobDate, float months, int days)
	{
		if (originalJobDate == null)
		{
			return null;
		}

		Date[] expireDates;
		Calendar markedDate = Calendar.getInstance();
		markedDate.setTime(originalJobDate);

		int iMonths = (int) months;
		int iDays = days + Math.round(30 * (months - iMonths));

		if (months < 0)
		{
			markedDate.add(Calendar.DATE, iDays);
			Calendar tempDate = (Calendar) markedDate.clone();
			markedDate.add(Calendar.MONTH, iMonths);

			int dayTmp = tempDate.get(Calendar.DATE);
			int maxDaysTmp = tempDate.getActualMaximum(Calendar.DATE);
			int day = markedDate.get(Calendar.DATE);
			int maxDays = markedDate.getActualMaximum(Calendar.DATE);

			if (dayTmp > day)
			{
				expireDates = null;
			}
			else if (dayTmp == maxDaysTmp && day < maxDays)
			{
				expireDates = new Date[maxDays - day + 1];
				for (int i = 0; i < expireDates.length; i++)
				{
					expireDates[i] = markedDate.getTime();
					markedDate.set(Calendar.DATE, ++day);
				}
			}
			else
			{
				expireDates = new Date[1];
				expireDates[0] = markedDate.getTime();
			}
		}
		else
		{
			expireDates = new Date[1];
			markedDate.add(Calendar.MONTH, iMonths);
			markedDate.add(Calendar.DATE, iDays);
			expireDates[0] = markedDate.getTime();
		}

		return expireDates;
	}
	/**
	 * 作用：判断传入的日期是否是无穷大日期
	 * 
	 * @param	dateToCheck				需要判断的日期
	 * @return	boolean					当是无穷大日期时，返回true，反之，返回false
	 * */
	public static boolean isInfinity(Date dateToCheck)
	{
		if (dateToCheck == null)
		{
			return false;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(dateToCheck);
		int year = cal.get(Calendar.YEAR);
		log.info("The date is infinity. The given year :" + year);
		if (year >= 9999)
		{
			log.info("The date is infinity");
			return true;
		}

		log.info("The date is not infinity");
		return false;

	}
	/**
	 * 作用：获取传入日期那一周的第一天
	 * 
	 * @param	date			传入 日期
	 * @return	Date			获取那一周的第一天
	 * */
	public static final Date firstDayOfWeek(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setFirstDayOfWeek(0);
		int year = DateUtil.getYear(date);
		int month = DateUtil.getMonth(date) + 1;
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 2;
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		if (dayOfWeek < 0)
		{
			dayOfWeek += 7;
		}
		return DateUtil.getDate(year, month, dayOfMonth - dayOfWeek);
	}
	/**
	 * 作用：获取传入日期那一月的第一天
	 * 
	 * @param	date			传入 日期
	 * @return	Date			获取那一月的第一天
	 * */
	public static final Date firstDayOfMonth(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = DateUtil.getYear(date);
		int month = DateUtil.getMonth(date) + 1;
		int dayOfMonth = 1;
		return DateUtil.getDate(year, month, dayOfMonth);
	}
	/**
	 * 作用：获取传入日期那一年的第一天
	 * 
	 * @param	date			传入 日期
	 * @return	Date			获取那一年的第一天
	 * */
	public static final Date firstDayOfYear(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = DateUtil.getYear(date);
		int month = 1;
		int dayOfMonth = 1;
		return DateUtil.getDate(year, month, dayOfMonth);
	}
	/**
	 * 作用：获取传入日期那一周的最后一天
	 * 
	 * @param	date			传入 日期
	 * @return	Date			获取那一周的最后一天
	 * */
	public static final Date lastDayOfWeek(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setFirstDayOfWeek(0);
		int year = DateUtil.getYear(date);
		int month = DateUtil.getMonth(date) + 1;
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 2;
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		return DateUtil.getDate(year, month, dayOfMonth - dayOfWeek + 6);
	}
	/**
	 * 作用：获取传入月份中，那一月的最后一天
	 * 
	 * @param	date			传入 日期
	 * @return	Date			返回传入日期，中月份的最后一天
	 * */
	public static final Date lastDayOfMonth(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = DateUtil.getYear(date);
		int month = DateUtil.getMonth(date) + 2;
		int dayOfMonth = 1;
		Date nextDate = DateUtil.getDate(year, month, dayOfMonth);
		Calendar next = Calendar.getInstance();
		cal.setTime(nextDate);
		year = DateUtil.getYear(nextDate);
		month = DateUtil.getMonth(nextDate) + 1;
		dayOfMonth = cal.get(Calendar.DAY_OF_MONTH) -1;
		return DateUtil.getDate(year, month, dayOfMonth);
	}
	/**
	 * 作用：获取传入日期，那年的最后一天日期
	 * 
	 * @param	date			传入日期
	 * @return	Date			返回获取的该年最后一天日期
	 * */
	public static final Date lastDayOfYear(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = DateUtil.getYear(date);
		int month = 12;
		int dayOfMonth = 31;
		return DateUtil.getDate(year, month, dayOfMonth);
	}

	/**
	 * 得到本月本周的第一天
	 * @param date
	 * @return
	 */
	public static final int getFirstDayOfWeekInMonth(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 2;
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		return dayOfMonth - dayOfWeek;
	}
	
	/**
	 * 得到本日是本年的第几周
	 * @param date
	 * @return
	 */
	public static final int getWeekOfYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);   
		return c.get(Calendar.WEEK_OF_YEAR);
	}
	
	/**
	 * 得到两天之间相差的天数
	 * @param date
	 * @return
	 */
	public static final int getTwoDatesDispersion(Date date1,Date date2) {
		return (int) ((date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000));
	}
	
	public static void main(String[] args) {
//		Date currentDate = DateUtil.toDate("20070113", "yyyyMMdd");
//		Calendar c = Calendar.getInstance();
//		c.setTime(currentDate);  
//		String year = String.valueOf(c.get(Calendar.YEAR));
//		c.add(Calendar.DAY_OF_MONTH, -7);
//		
//		String weekOfYear = String.valueOf(c.get(Calendar.WEEK_OF_YEAR));
//		if("01".equals(weekOfYear)) year = String.valueOf(Integer.parseInt(year)-1);
//		if(weekOfYear.length() < 2) {
//			weekOfYear = "0"+weekOfYear;
//		} 
//		System.out.println( year + weekOfYear);
//		System.out.println(DateUtil.getDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		System.out.println(DateUtil.firstDayOfYear(new Date()));
		System.out.println(DateUtil.firstDayOfMonth(new Date()));
		System.out.println(DateUtil.firstDayOfWeek(new Date()));
		System.out.println(DateUtil.lastDayOfYear(new Date()));
		System.out.println(DateUtil.lastDayOfMonth(new Date()));
		System.out.println(DateUtil.lastDayOfWeek(new Date()));
	}
}