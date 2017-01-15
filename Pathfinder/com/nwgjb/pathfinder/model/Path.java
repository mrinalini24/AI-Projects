package com.nwgjb.pathfinder.model;
import java.util.ArrayList;
import java.util.Iterator;


public class Path implements Iterable<Node>, Comparable<Path>{
	ArrayList<Node> path=new ArrayList<>();
	double cost;
	
	public Path(Node n, double cost){
		path.add(n);
		this.cost=cost;
	}
	
	public Path(Path p, Node n, double cost){
		path.addAll(p.path);
		path.add(n);
		this.cost=p.cost+cost;
	}
	
	public int hashCode(){
		return path.hashCode();
	}
	
	public boolean equals(Object obj){
		if(obj instanceof Path){
			return path.equals(((Path)obj).path);
		}
		return false;
	}

	public Node last() {
		return path.get(path.size()-1);
	}

	public boolean contains(Node node) {
		return path.contains(node);
	}

	@Override
	public Iterator<Node> iterator() {
		return path.iterator();
	}

	public double getCost() {
		return cost;
	}

	@Override
	public int compareTo(Path p) {
		return Double.compare(cost, p.cost);
	}

	public Node get(int i) {
		return path.get(i);
	}
	
}
