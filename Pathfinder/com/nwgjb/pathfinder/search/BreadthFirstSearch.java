package com.nwgjb.pathfinder.search;
import java.util.HashSet;
import java.util.LinkedList;

import com.nwgjb.pathfinder.model.Edge;
import com.nwgjb.pathfinder.model.Model;
import com.nwgjb.pathfinder.model.Node;
import com.nwgjb.pathfinder.model.Path;


public class BreadthFirstSearch implements Search {

	@Override
	public Path search(Model model, Node start, Node dest) {
		HashSet<Path> visitedPath=new HashSet<>();
		LinkedList<Path> pathQueue=new LinkedList<>();
		Path result=null;
		pathQueue.add(new Path(start, 0));
		while(!pathQueue.isEmpty()){
			Path path=pathQueue.removeFirst();
			if(result!=null&&path.getCost()>result.getCost()){
				continue;
			}
			Node lastNode=path.last();
			visitedPath.add(path);
			if(lastNode==dest){
				if(result==null||path.getCost()<result.getCost()){
					result=path;
				}
			}
			for(Edge e:lastNode.getEdges()){
				Node connNode=e.getNode(lastNode);
				if(path.contains(connNode))continue;
				Path newPath=new Path(path, connNode, e.getCost());
				if(visitedPath.contains(newPath)||pathQueue.contains(newPath)){
					continue;
				}
				pathQueue.addLast(newPath);
			}
		}
		return result;
	}

}
