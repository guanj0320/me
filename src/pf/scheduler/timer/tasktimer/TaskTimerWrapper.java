package pf.scheduler.timer.tasktimer;

import pf.scheduler.timer.TaskTimer;

public abstract class TaskTimerWrapper implements TaskTimer {

	/*
     * 
     */
    private boolean bValid = true;

    /*
     * 
     */
    protected long period = -1;


	protected void setInvalid() {
		bValid = false;
	}

	public boolean isValid() {
		return bValid;
	}

}
