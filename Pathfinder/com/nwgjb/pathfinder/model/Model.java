package com.nwgjb.pathfinder.model;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;


public class Model implements Iterable<Node>{
	
	ArrayList<Node> nodes=new ArrayList<>();
	LinkedList<Color> clr=new LinkedList<>(Arrays.asList(
			Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY,
			Color.GRAY, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA,
			Color.ORANGE, Color.PINK, Color.RED, Color.WHITE,
			Color.YELLOW
			));
	Iterator<Color> itr=clr.iterator();
	
	public void addNode(double x, double y) {
		Node n=new Node(x, y);
		nodes.add(n);
	}

	public Color generateColor() {
		if(!itr.hasNext()){
			itr=clr.iterator();
		}
		return itr.next();
	}
	
	public boolean isEmpty(){
		return nodes.isEmpty();
	}
	
	public Node findNode(final double x, final double y, double lim){
		Comparator<Node> c=new Comparator<Node>(){
			@Override
			public int compare(Node arg0, Node arg1) {
				return Double.compare(Math.abs(arg0.x-x)+Math.abs(arg0.y-y), Math.abs(arg1.x-x)+Math.abs(arg1.y-y));
			}
		};
		if(nodes.isEmpty()){
			return null;
		}
		Node n=Collections.min(nodes, c);
		if(n.distanceTo(x, y)>lim){
			return null;
		}
		return n;
	}
	
	public Node findNodePrecise(final double x, final double y, double lim){
		Comparator<Node> c=new Comparator<Node>(){
			@Override
			public int compare(Node arg0, Node arg1) {
				return Double.compare(arg0.distanceTo(x, y), arg1.distanceTo(x,  y));
			}
		};
		if(nodes.isEmpty()){
			return null;
		}
		Node n=Collections.min(nodes, c);
		if(n.distanceTo(x, y)>lim){
			return null;
		}
		return n;
	}

	public Node get(int i) {
		return nodes.get(i);
	}
	
	public int size(){
		return nodes.size();
	}

	public Node last() {
		return nodes.get(nodes.size()-1);
	}

	@Override
	public Iterator<Node> iterator() {
		return nodes.iterator();
	}

	public void removeNode(Node node) {
		nodes.remove(node);
	}

	public Edge findEdge(final double x, final double y, double maxValue) {
		Edge e=null;
		double d=maxValue;
		for(Node n:nodes){
			for(Edge ed:n.getEdges()){
				double di=ed.distanceTo(x, y);
				if(di<d){
					e=ed;
					d=di;
				}
			}
		}
		return e;
	}

	
	
}
