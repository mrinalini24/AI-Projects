package com.nwgjb.pathfinder.search;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

import com.nwgjb.pathfinder.model.Edge;
import com.nwgjb.pathfinder.model.Model;
import com.nwgjb.pathfinder.model.Node;
import com.nwgjb.pathfinder.model.Path;


public class AStarSearch implements Search {

	public static interface Function{
		double estimateCost(Node n, Node dest);
		double cost(Edge e);
	}
	
	Function func;
	
	public static final Function EUCLID_DISTANCE=new Function(){
		@Override
		public double estimateCost(Node n, Node dest) {
			return n.distanceTo(dest);
		}
		
		@Override
		public double cost(Edge e){
			return e.getCost();
		}
	};
	
	public static final Function SPEED_WEIGHTED=new Function(){
		
		static final double MAX_SPEED=60;
		
		@Override
		public double estimateCost(Node n, Node dest) {
			return n.distanceTo(dest)/MAX_SPEED;
		}
		
		@Override
		public double cost(Edge e){
			return e.getCost()/e.speed();
		}
	};
	
	public static final Function DIJKSTRA=new Function(){
		@Override
		public double estimateCost(Node n, Node dest) {
			return 0;
		}
		
		@Override
		public double cost(Edge e){
			return e.getCost();
		}
	};
	
	public static final Function GREEDY=new Function(){
		@Override
		public double estimateCost(Node n, Node dest) {
			return n.distanceTo(dest);
		}
		
		@Override
		public double cost(Edge e){
			return 0;
		}
	};
	
	public AStarSearch(Function func){
		this.func=func;
	}
	
	public AStarSearch(){
		this(EUCLID_DISTANCE);
	}
	
	@Override
	public Path search(Model model, Node start, final Node dest) {
		HashSet<Node> visited=new HashSet<>();
		PriorityQueue<Path> pathQueue=new PriorityQueue<>(11, new Comparator<Path>(){
			@Override
			public int compare(Path p1, Path p2) {
				return Double.compare(p1.getCost()+func.estimateCost(p1.last(), dest), p2.getCost()+func.estimateCost(p2.last(), dest));
			}
		});
		pathQueue.add(new Path(start, 0));
		
		while(!pathQueue.isEmpty()){
			Path path=pathQueue.poll();
			Node lastNode=path.last();
			
			if(lastNode==dest){
				return path;
			}
			
			if(visited.contains(lastNode)){
				continue;
			}
			
			visited.add(lastNode);
			for(Edge e:lastNode.getEdges()){
				Node connNode=e.getNode(lastNode);
				Path p=new Path(path, connNode, func.cost(e));
				if(visited.contains(connNode)){
					continue;
				}
				pathQueue.add(p);
			}
		}
		
		return null;
	}

}
