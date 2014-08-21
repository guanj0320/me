package pf.scheduler.timer;

import java.util.Date;

import pf.tools.xml.XMLNode;



public interface TaskTimer {
	public abstract Date getNextTime(boolean flag);

    public abstract void init(XMLNode xmlnode);

    public abstract boolean isValid();
}
