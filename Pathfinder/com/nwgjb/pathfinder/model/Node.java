package com.nwgjb.pathfinder.model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A representation of a graph node.
 * 
 * @author Lekan Wang (lekan@lekanwang.com), Modified by Gary
 *
 */
public class Node {
	private List<Edge> edges;
	public double x;
	public double y;
	
	/**
	 * Creates a node with no edges, and the given value.
	 * @param value
	 */
	public Node(double x, double y) {
		this.edges = new ArrayList<Edge>();
		this.x=x;
		this.y=y;
	}
	
	
	/**
	 * Adds the given edge to this node. The edge must be not null
	 * and valid.
	 * 
	 * @param edge
	 */
	public void addEdge(Edge edge) {
		assert (edge != null);
		edges.add(edge);
	}
	
	/**
	 * Gets the list of edges as an unmodifiable list.
	 */
	public List<Edge> getEdges() {
		return Collections.unmodifiableList(edges);
	}


	public double distanceTo(double x2, double y2) {
		double dx=x2-x;
		double dy=y2-y;
		return Math.sqrt(dx*dx+dy*dy);
	}
	
	public double distanceTo(Node n) {
		return distanceTo(n.x, n.y);
	}
	
	public boolean isReachable(Node n){
		for(Edge e:edges){
			if(e.getNode1()==n||e.getNode2()==n)return true;
		}
		return false;
	}
	
	public Edge getEdge(Node n){
		for(Edge e:edges){
			if(e.getNode(this)==n){
				return e;
			}
		}
		return null;
	}

	public void removeEdge(Edge e) {
		edges.remove(e);
	}
}
