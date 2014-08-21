package pf.scheduler.timer;

import java.util.Date;
import java.util.TimerTask;

public class TimerTaskWrapper extends TimerTask {
	
	private Task task;
    private TaskTimer tt = null;
    private String taskName = "";
    
    public TimerTaskWrapper(String taskName, Task task, TaskTimer taskTimer)
    {
        this.task = task;
        this.tt = taskTimer;
        this.taskName = taskName;
    }

    public String getTaskName() {
		return this.taskName;
	}
	
	public Task getTask(){
		return this.task;
	}
	
	public TaskTimer getTaskTimer(){
		return this.tt;
	}

	public void run() {
		try
        {
            if(task != null)
                task.run();
            Date nextTimer = this.tt.getNextTime(false);
            if(nextTimer != null)
                Scheduler.addJob(new TimerTaskWrapper(taskName, task, tt), nextTimer);
        }
        catch(Throwable thr)
        {
            thr.printStackTrace();
        }
	}

}
