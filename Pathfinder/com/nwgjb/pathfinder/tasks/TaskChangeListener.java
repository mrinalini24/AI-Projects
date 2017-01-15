package com.nwgjb.pathfinder.tasks;

import javax.swing.AbstractButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TaskChangeListener implements ChangeListener{
	
	Task task;
	TaskGroup group;
	
	public TaskChangeListener(Task t, TaskGroup taskGroup){
		task=t;
		group=taskGroup;
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		AbstractButton b=(AbstractButton)e.getSource();
		if(b.isSelected()){
			task.onActivation();
			group.current=task;
			group.unselectExcept(b);
		}else{
			task.onDeactivation();
			if(group.current==task)
				group.current=group.defaultTask;
		}
	}
	
	
}
