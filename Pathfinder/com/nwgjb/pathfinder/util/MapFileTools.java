package com.nwgjb.pathfinder.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import com.nwgjb.pathfinder.model.Edge;
import com.nwgjb.pathfinder.model.Model;
import com.nwgjb.pathfinder.model.Node;

public class MapFileTools {
	public static Model readMap(File confFile) throws IOException{
		BufferedReader bfr=new BufferedReader(new FileReader(confFile));
		
		Model m=new Model();
		
		String line;
		while((line=bfr.readLine())!=null){
			String[] args=line.split(" ");
			if(args.length==0){
				continue;
			}
			switch(args[0]){
				case "P":{
					double x=Double.parseDouble(args[1]);
					double y=Double.parseDouble(args[2]);
					m.addNode(x, y);
					break;
				}
				case "E":{
					Node n1=m.get(Integer.parseInt(args[1]));
					Node n2=m.get(Integer.parseInt(args[2]));
					Edge e=new Edge(n1, n2);
					n1.addEdge(e);
					n2.addEdge(e);
					if(args.length>3){
						e.speed(Double.parseDouble(args[3]));
					}
					break;
				}
			}
		}
		
		bfr.close();
		
		return m;
	}
	
	public static void writeMap(File confFile, Model map) throws IOException{
		PrintWriter bfr=new PrintWriter(new FileWriter(confFile));
		HashMap<Node, Integer> nodeMap=new HashMap<>();
		for(Node n:map){
			bfr.println("P "+n.x+" "+n.y);
			nodeMap.put(n, nodeMap.size());
		}
		int id=0;
		for(Node n:map){
			for(Edge e:n.getEdges()){
				if(e.getNode1()==n){
					bfr.println("E "+id+" "+nodeMap.get(e.getNode2())+" "+e.speed());
				}
			}
			id++;
		}
		bfr.close();
	}
	
	
}
