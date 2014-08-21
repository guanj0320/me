package pf.scheduler.quartz.model;

import java.util.ArrayList;
import java.util.List;

public class SchedulerDTO {
	private String schedulerName;
	private String runningSince;
	private String numJobsExecuted;
	private String persistenceType;
	private String threadPoolSize;
	private String version;
	private String state;
	private String summary;
	
	private List globalJobListeners = new ArrayList();
	private List globalTriggerListeners = new ArrayList();
	private List schedulerListeners = new ArrayList();
	private List registeredJobListeners = new ArrayList();
	private List registeredTriggerListeners = new ArrayList();
	public String getSchedulerName() {
		return schedulerName;
	}
	public void setSchedulerName(String schedulerName) {
		this.schedulerName = schedulerName;
	}
	public String getRunningSince() {
		return runningSince;
	}
	public void setRunningSince(String runningSince) {
		this.runningSince = runningSince;
	}
	public String getNumJobsExecuted() {
		return numJobsExecuted;
	}
	public void setNumJobsExecuted(String numJobsExecuted) {
		this.numJobsExecuted = numJobsExecuted;
	}
	public String getPersistenceType() {
		return persistenceType;
	}
	public void setPersistenceType(String persistenceType) {
		this.persistenceType = persistenceType;
	}
	public String getThreadPoolSize() {
		return threadPoolSize;
	}
	public void setThreadPoolSize(String threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public List getGlobalJobListeners() {
		return globalJobListeners;
	}
	public void setGlobalJobListeners(List globalJobListeners) {
		this.globalJobListeners = globalJobListeners;
	}
	public List getGlobalTriggerListeners() {
		return globalTriggerListeners;
	}
	public void setGlobalTriggerListeners(List globalTriggerListeners) {
		this.globalTriggerListeners = globalTriggerListeners;
	}
	public List getSchedulerListeners() {
		return schedulerListeners;
	}
	public void setSchedulerListeners(List schedulerListeners) {
		this.schedulerListeners = schedulerListeners;
	}
	public List getRegisteredJobListeners() {
		return registeredJobListeners;
	}
	public void setRegisteredJobListeners(List registeredJobListeners) {
		this.registeredJobListeners = registeredJobListeners;
	}
	public List getRegisteredTriggerListeners() {
		return registeredTriggerListeners;
	}
	public void setRegisteredTriggerListeners(List registeredTriggerListeners) {
		this.registeredTriggerListeners = registeredTriggerListeners;
	}
	
	
}
