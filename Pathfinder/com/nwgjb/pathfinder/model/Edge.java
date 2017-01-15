package com.nwgjb.pathfinder.model;
import com.nwgjb.pathfinder.util.Tuple;

/**
 * A representation of a graph node.
 * 
 * @author Lekan Wang (lekan@lekanwang.com), Modified by Gary
 *
 */
public class Edge {
	
	private Node n1, n2;
	private double cost;
	
	double speed=60;
	
	//private double speed=60;
	
	/**
	 * Creates an edge that connects nodes n1 and n2.
	 */
	public Edge(Node n1, Node n2) {
		this.n1=n1;
		this.n2=n2;
		cost=n1.distanceTo(n2);
	}
	
	/**
	 * Gets both nodes as a Tuple._2<Node, Node> object. You can access
	 * the individual Nodes by saying pair.left, and pair.right
	 * once you have the pair.
	 * 
	 * This method does not guarantee any ordering on the nodes.
	 * Hence, this could be useful when working with undirected
	 * graphs. (*hint hint*)
	 * 
	 * @return
	 */
	public Tuple._2<Node, Node> getNodes() {
		return Tuple.as(n1, n2);
	}
	
	/**
	 * Returns the first node. Because this guarantees an order,
	 * it could be useful when working with directed graphs.
	 * 
	 * @return
	 */
	public Node getNode1() {
		return this.n1;
	}
	
	/**
	 * Returns the second node. Because this guarantees an order,
	 * it could be useful when working with directed graphs.
	 * 
	 * @return
	 */
	public Node getNode2() {
		return this.n2;
	}
	
	public Node getNode(Node n){
		return n==n1?n2:n1;
	}
	
	public double getCost(){
		return cost;
	}
	
	public double getNormalDistanceTo(double x, double y){
		double a=n2.y-n1.y;
		double b=n1.x-n2.x;
		double c=n1.y*n2.x-n1.x*n2.y;
		return Math.abs(a*x+b*y+c)/Math.sqrt(a*a+b*b);
	}
	
	public double distanceTo(double x, double y){
		//segA:Point, segB:Point, p:Point;
		
		double p2x=n2.x-n1.x;
		double p2y=n2.y-n1.y;
		
		double tmp=p2x*p2x+p2y*p2y;
		double u=((x-n1.x)*p2x+(y-n1.y)*p2y)/tmp;
		
		if(u>1){
			u=1;
		}else if(u<0){
			u=0;
		}
		
		double dx=n1.x+u*p2x-x;
		double dy=n1.y+u*p2y-y;
		return Math.sqrt(dx*dx+dy*dy);
		
		/*
	    var p2:Point = new Point(segB.x - segA.x, segB.y - segA.y);
	    var something:Number = p2.x*p2.x + p2.y*p2.y;
	    var u:Number = ((p.x - segA.x) * p2.x + (p.y - segA.y) * p2.y) / something;

	    if (u > 1)
	        u = 1;
	    else if (u < 0)
	        u = 0;

	    var x:Number = segA.x + u * p2.x;
	    var y:Number = segA.y + u * p2.y;

	    var dx:Number = x - p.x;
	    var dy:Number = y - p.y;

	    var dist:Number = Math.sqrt(dx*dx + dy*dy);

	    return dist;*/
	}
	
	public void speed(double x){
		speed=x;
	}
	
	public double speed(){
		return speed;
	}

	public void updateCost() {
		cost=n1.distanceTo(n2);
	}

}
