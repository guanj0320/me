package pf.scheduler.timer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pf.tools.interfaces.PropertiesSetter;
import pf.tools.reflect.ReflectSupport;
import pf.tools.xml.XMLBuilder;
import pf.tools.xml.XMLDocument;
import pf.tools.xml.XMLNode;


public class Scheduler {

	private static Map taskTimerRegs;
	private static Map tasks = new HashMap();
	private static final Timer tmr = new Timer(true);

	static {
		taskTimerRegs = new HashMap();
		taskTimerRegs.put("delay",
				"base.scheduler.timer.tasktimer.DelayTaskTimer");
		taskTimerRegs.put("perday",
				"base.scheduler.timer.tasktimer.PerDayTaskTimer");
		taskTimerRegs.put("perweek",
				"base.scheduler.timer.tasktimer.PerWeekTaskTimer");
		taskTimerRegs.put("permonth",
				"base.scheduler.timer.tasktimer.PerMonthTaskTimer");
		taskTimerRegs.put("peryear",
				"base.scheduler.timer.tasktimer.PerYearTaskTimer");
	}

	public Scheduler() {
	}

	private static TaskTimer getTaskTimer(String taskTimer) {
		String ttClz = (String) taskTimerRegs.get(taskTimer);
		if (ttClz == null)
			ttClz = taskTimer;
		return (TaskTimer) ReflectSupport.newObject(ttClz);
	}

	static void addJob(TimerTaskWrapper task, Date nextTime) {
		tmr.schedule(task, nextTime);
		tasks.put(task.getTaskName(), nextTime);
	}

	private static void addJob(String taskName, Task task, TaskTimer taskTimer) {
		Date firstTime = taskTimer.getNextTime(true);
		if (taskTimer.isValid() && firstTime != null)
			tmr.schedule(new TimerTaskWrapper(taskName, task, taskTimer),
					firstTime);
	}

	public static void stop() {
		tmr.cancel();
		tasks.clear();
	}

	public static void init(String xmlpath) {
		Log log = LogFactory.getLog("FILE");
		XMLDocument document = XMLBuilder.buildDocument();
		try {
			document.loadFromFile(xmlpath);
		} catch (Exception e) {
			log.error("未定位到任务闹钟配置信息！");
		}
		if (document != null) {
			XMLNode root = document.getRoot();
			int count = root.getChildNodesCount();
			for (int i = 0; i < count; i++) {
				XMLNode item = root.getChildNode(i);
				XMLNode timerNode = item.getChildNode("timer");
				if (timerNode == null) {
					log.debug("未定位到任务闹钟配置信息！");
					continue;
				} else {
					String taskTimerClass = timerNode.getAttributeValue("type");
					if ("".equals(taskTimerClass)) {
						log.debug("任务闹钟类为空！");
						continue;
					} else {
						XMLNode taskNode = item.getChildNode("task");
						if (taskNode == null) {
							log.debug("未定位到任务类！");
							continue;
						} else {
							String taskClass = taskNode
									.getAttributeValue("class");
							if ("".equals(taskClass))
								taskClass = taskNode.getNodeValue();
							if ("".equals(taskClass)) {
								log.debug("任务类为空！");
								continue;
							} else
								try {
									Task task = (Task) ReflectSupport
											.newObject(taskClass);
									TaskTimer tt = getTaskTimer(taskTimerClass);
									if (task != null && tt != null) {
										tt.init(timerNode);
										Object obj = taskNode.getNodeValueObject();
										if (obj != null && obj instanceof Properties) {
											if (task instanceof PropertiesSetter) {
												PropertiesSetter ps = (PropertiesSetter) task;
												ps.setProperties((Properties) obj);
											}
										}
										addJob(item.getNodeName(), task, tt);
									}
								} catch (Throwable thr) {
									thr.printStackTrace();
									log.debug("定义任务初始化出错, 任务类:" + taskClass
											+ " timer类: " + taskTimerClass
											+ "！");
								}
						}
					}
				}
			}

			System.out.println("\t调度器已经启动！");
		} else {
			log.debug("未定位到调度文件！");
		}
	}
}
