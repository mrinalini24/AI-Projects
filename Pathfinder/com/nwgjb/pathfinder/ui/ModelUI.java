package com.nwgjb.pathfinder.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import com.nwgjb.pathfinder.model.Edge;
import com.nwgjb.pathfinder.model.Model;
import com.nwgjb.pathfinder.model.Node;
import com.nwgjb.pathfinder.model.Path;

public class ModelUI extends JComponent{
	
	public static enum EdgeDisplayMode{
		BLACK, SPEED_DEPEND
	}
	
	
	private static final long serialVersionUID = 134873833155444440L;
	
	private final static BasicStroke[] stroke={new BasicStroke(1), new BasicStroke(2), new BasicStroke(3), new BasicStroke(4), new BasicStroke(5),
			new BasicStroke(6), new BasicStroke(7), new BasicStroke(8), new BasicStroke(9), new BasicStroke(10)};
	
	Model model;
	LinkedList<Path> paths=new LinkedList<>();
	Image background;
	Painter painter;
	EdgeDisplayMode edgeDispMode=EdgeDisplayMode.BLACK;
	
	boolean modelVisible=false;
	
	{
		try {
			background=ImageIO.read(new File("Stanfordmap-1000x618.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ModelUI(Model m){
		model=m;
	}
	
	public void setModel(Model m){
		model=m;
		repaint();
	}
	
	public void addPath(Path p){
		paths.add(p);
		repaint();
	}
	
	public void setEdgeDisplayMode(EdgeDisplayMode m){
		edgeDispMode=m;
	}
	
	public void setPainter(Painter p){
		painter=p;
	}
	
	public void clearPath(){
		paths.clear();
		repaint();
	}

	public void setModelVisible(boolean v) {
		modelVisible=v;
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(1000, 618);
	}
	
	public void repaint(){
		Container c=getParent();
		if(c instanceof Scaler){
			c.repaint();
		}else{
			super.repaint();
		}
	}
	
	public void paintComponent(Graphics g){
		Graphics2D g2d=(Graphics2D)g;
		
		g.drawImage(background, 0, 0, null);
		
		if(painter!=null)painter.paint(g2d);
		
		g2d.setStroke(stroke[4]);
		for(Path path:paths){
			Node p=null;
			for(Node n:path){
				if(p!=null){
					Edge e=p.getEdge(n);
					if(e!=null){
						g2d.setColor(selectColor(e));
					}else{
						g2d.setColor(Color.BLACK);
					}
					g2d.drawLine((int)n.x, (int)n.y, (int)p.x, (int)p.y);
				}
				p=n;
			}
		}
		
		if(modelVisible){
			g2d.setStroke(stroke[0]);
			for(Node n:model){
				for(Edge e:n.getEdges()){
					if(e.getNode1()==n){
						g2d.setColor(selectColor(e));
						g2d.drawLine((int)n.x, (int)n.y, (int)e.getNode2().x, (int)e.getNode2().y);
					}
				}
			}
			
			g2d.setStroke(stroke[0]);
			g2d.setColor(Color.WHITE);	
			for(Node n:model){
				g2d.fillRect((int)n.x-5, (int)n.y-5, 10, 10);
			}
			
			g2d.setColor(Color.BLACK);	
			for(Node n:model){
				g2d.drawRect((int)n.x-5, (int)n.y-5, 10, 10);
			}
		}else{
			g2d.setStroke(stroke[0]);
			g2d.setColor(Color.WHITE);
			for(Path path:paths){
				for(Node n:path){
					g2d.fillRect((int)n.x-5, (int)n.y-5, 10, 10);
				}
			}
			
			g2d.setColor(Color.BLACK);
			for(Path path:paths){
				for(Node n:path){
					g2d.drawRect((int)n.x-5, (int)n.y-5, 10, 10);
				}
			}
		}
		
	}

	private Color selectColor(Edge e) {
		if(edgeDispMode==EdgeDisplayMode.BLACK)return Color.BLACK;
		double speed=e.speed();
		if(speed>=50){
			return Color.GREEN;
		}else if(speed>=30){
			return Color.YELLOW;
		}else{
			return Color.RED;
		}
	}

	public EdgeDisplayMode getEdgeDisplayMode() {
		return edgeDispMode;
	}
	
	
}
