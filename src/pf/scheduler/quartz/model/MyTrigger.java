package pf.scheduler.quartz.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyTrigger {
	public static final String DATE_FORMAT_PATTERN = "yy-MM-dd HH:mm:ss";
	SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT_PATTERN);
	
	public static final String START_TIME_PROP = "startTime";
	public static final String STOP_TIME_PROP = "stopTime";
	public static final String VOLATILITY_PROP = "volatility";
	public static final String MISFIRE_INSTRUCTION_PROP = "misFireInstruction";
	public static final String TRIGGER_NAME_PROP = "triggerName";
	public static final String TRIGGER_GROUP_PROP = "triggerGroup";
	public static final String DESCRIPTION_PROP = "description";
	public static final String JOB_NAME_PROP = "jobName";
	public static final String JOB_GROUP_PROP = "jobGroup";

	public static final String SCHEDULE_ACTION_PROP = "scheduleAction";
	public static final String CANCEL_ACTION_PROP = "cancelAction";

	protected String startTime;
	protected String stopTime;
	private boolean volatility;
	private int misFireInstruction;
	private String triggerName;
	private String triggerGroup;
	private String jobName;
	private String jobGroup;
	private String description;
	private String nextFireTime;
	private String previousFireTime;
	private String type;
	private String className;

	private String scheduleAction;
	private String cancelAction;
	
	public String getStartTime() {
		return startTime;
	}

	public Date getStartTimeAsDate() throws ParseException {
		return dateFormatter.parse(startTime);
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getStopTime() {
		return stopTime;
	}

	public Date getStopTimeAsDate() throws ParseException {
		return dateFormatter.parse(stopTime);
	}

	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}

	public String getScheduleAction() {
		return scheduleAction;
	}

	public void setScheduleAction(String scheduleAction) {
		this.scheduleAction = scheduleAction;
	}

	public String getCancelAction() {
		return cancelAction;
	}

	public void setCancelAction(String cancelAction) {
		this.cancelAction = cancelAction;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isVolatility() {
		return volatility;
	}

	public void setVolatility(boolean volatility) {
		this.volatility = volatility;
	}

	public int getMisFireInstruction() {
		return misFireInstruction;
	}

	public void setMisFireInstruction(int misFireInstruction) {
		this.misFireInstruction = misFireInstruction;
	}

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String name) {
		this.triggerName = name;
	}

	public String getTriggerGroup() {
		return triggerGroup;
	}

	public void setTriggerGroup(String group) {
		this.triggerGroup = group;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	
	
	/**
	 * Returns the nextFireTime.
	 * @return String
	 */
	public String getNextFireTime() {
		return nextFireTime;
	}

	/**
	 * Sets the nextFireTime.
	 * @param nextFireTime The nextFireTime to set
	 */
	public void setNextFireTime(String nextFireTime) {
		this.nextFireTime = nextFireTime;
	}

	/**
	 * Returns the previousFireTime.
	 * @return String
	 */
	public String getPreviousFireTime() {
		return previousFireTime;
	}

	/**
	 * Sets the previousFireTime.
	 * @param previousFireTime The previousFireTime to set
	 */
	public void setPreviousFireTime(String previousFireTime) {
		this.previousFireTime = previousFireTime;
	}

	/**
	 * Returns the type.
	 * @return String
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 * @param type The type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	
}
