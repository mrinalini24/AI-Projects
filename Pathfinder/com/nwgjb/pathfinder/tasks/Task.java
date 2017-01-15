package com.nwgjb.pathfinder.tasks;
import java.awt.event.MouseEvent;

/**
 * A representation of a task on the ACM canvas.
 * 
 * @author Gary
 *
 */

public interface Task {
	String getName();
	void onActivation();
	void onDeactivation();
	void onDragStart(MouseEvent e);		
	void onDrag(MouseEvent e);
	void onDragEnd(MouseEvent e);
}
