package com.nwgjb.pathfinder.search;
import com.nwgjb.pathfinder.model.Model;
import com.nwgjb.pathfinder.model.Node;
import com.nwgjb.pathfinder.model.Path;


public interface Search {
	Path search(Model model, Node start, Node dest);
}	
