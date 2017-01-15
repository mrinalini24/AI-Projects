package com.nwgjb.pathfinder.tasks;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToggleButton;

public class TaskGroup{

	ArrayList<AbstractButton> allTasks=new ArrayList<>();
	Task current;
	Task defaultTask;
	
	public JToggleButton addToggle(Task t){
		JToggleButton b=new JToggleButton(t.getName());
		b.addChangeListener(new TaskChangeListener(t, this));
		allTasks.add(b);
		return b;
	}
	
	public JRadioButtonMenuItem addRadioMenu(Task t){
		JRadioButtonMenuItem b=new JRadioButtonMenuItem(t.getName());
		b.addChangeListener(new TaskChangeListener(t, this));
		allTasks.add(b);
		return b;
	}
	
	public void unselectExcept(AbstractButton t){
		for(AbstractButton tb:allTasks){
			if(tb!=t)tb.setSelected(false);
		}
	}
	
	public void setEnabledAll(boolean e){
		for(AbstractButton tb:allTasks){
			tb.setEnabled(e);
		}
	}
	
	public void setDefault(Task t){
		if(current==defaultTask)
			current=t;
		defaultTask=t;
	}

	public void onDragStart(MouseEvent e) {
		if(current!=null)current.onDragStart(e);
	}

	public void onDrag(MouseEvent e) {
		if(current!=null)current.onDrag(e);
	}

	public void onDragEnd(MouseEvent e) {
		if(current!=null)current.onDragEnd(e);
	} 
	
	
}
