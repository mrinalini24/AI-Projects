package com.nwgjb.pathfinder.tasks;

import java.awt.event.MouseEvent;

public abstract class TaskAdapter implements Task {

	String name;
	
	public TaskAdapter(String name){
		this.name=name;
	}
	
	@Override
	public String getName(){
		return name;
	}
	
	@Override
	public void onDragStart(MouseEvent e) {
	}

	@Override
	public void onDrag(MouseEvent e) {
	}

	@Override
	public void onDragEnd(MouseEvent e) {
	}

	@Override
	public void onActivation() {
	}
	
	@Override
	public void onDeactivation() {
	}

}
