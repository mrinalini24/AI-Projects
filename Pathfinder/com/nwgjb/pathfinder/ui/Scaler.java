package com.nwgjb.pathfinder.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.RepaintManager;

public class Scaler extends JRootPane{
	
	private static final long serialVersionUID = 6032290355377235975L;
	
	double scale=1.0;
	
	double lbound;
	double ubound;
	
	double xoffset;
	double yoffset;
	
	double rotation=0;
	
	JComponent comp;
	JPanel mask;
	BufferedImage image;
	Graphics g;
	
	static{
		RepaintManager.setCurrentManager(new RepaintManagerC());
	}
	
	public Scaler(JComponent c){
		this(c, 0.5, 10);
	}
	
	public Scaler(JComponent c, double l, double u){
		comp=c;
		lbound=l;
		ubound=u;
		
		mask=new JPanel();
		
		mask.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e){
				e=dealMouseEvent(e);
				for(MouseListener ml:comp.getMouseListeners()){
					ml.mousePressed(e);
				}
			};
			
			@Override
			public void mouseReleased(MouseEvent e){
				e=dealMouseEvent(e);
				for(MouseListener ml:comp.getMouseListeners()){
					ml.mouseReleased(e);
				}
			};
		});
		mask.addMouseMotionListener(new MouseMotionAdapter(){
			@Override
			public void mouseDragged(MouseEvent e) {
				e=dealMouseEvent(e);
				for(MouseMotionListener ml:comp.getMouseMotionListeners()){
					ml.mouseDragged(e);
				}
			}
		});
		mask.addMouseWheelListener(new MouseWheelListener(){
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(e.isShiftDown()){
					rotation=(rotation+(e.getScrollType()==MouseWheelEvent.WHEEL_UNIT_SCROLL?-e.getUnitsToScroll()*10:(e.getWheelRotation()>0?-30:30)))%360;
					repaint();
				}else{
					double pscale=scale;
					
					scale+=(e.getScrollType()==MouseWheelEvent.WHEEL_UNIT_SCROLL?-e.getUnitsToScroll()*0.1:(e.getWheelRotation()>0?-0.3:0.3));
					if(scale<lbound){
						scale=lbound;
					}else if(scale>ubound){
						scale=ubound;
					}
					
					double sRatio=1/scale-1/pscale; 
					Point p=deRotation(e.getPoint());
					move(p.getX()*sRatio, p.getY()*sRatio);
				}
			}
		});
		
		setContentPane(c);
		setGlassPane(mask);
		mask.setVisible(true);
		
		setSize(getPreferredSize());
	}
	
	public void setOffset(double x, double y){
		xoffset=x;
		yoffset=y;
	}
	
	public void move(double dx, double dy){
		xoffset+=dx;
		yoffset+=dy;
		
		if((xoffset+image.getWidth())*scale<getWidth()){
			xoffset=getWidth()/scale-image.getWidth();
		}
		
		if((yoffset+image.getHeight())*scale<getHeight()){
			yoffset=getHeight()/scale-image.getHeight();
		}
		
		if(xoffset>0)xoffset=0;
		if(yoffset>0)yoffset=0;
		
		repaint();
	}
	
	public void setSize(int w, int h){
		super.setSize(w, h);
		comp.setSize((int)(w/scale), (int)(h/scale));
		image=new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
		g=image.getGraphics();
	}
	
	public Dimension getPreferredSize(){
		Dimension ret=comp.getPreferredSize();
		ret.width*=scale;
		ret.height*=scale;
		return ret;
	}
	
	public void paint(Graphics g){
		Graphics2D g2d=(Graphics2D)g;
		if(rotation!=0)
			g2d.rotate(rotation*Math.PI/180, getWidth()/2, getHeight()/2);
		if(scale==1){
			comp.paint(g2d.create((int)xoffset, (int)yoffset, image.getWidth(), image.getHeight()));
		}else{
			comp.paint(this.g);
			g2d.drawImage(image, (int)(xoffset*scale), (int)(yoffset*scale), (int)(image.getWidth()*scale), (int)(image.getHeight()*scale), null);
		}
	}
	
	public static class RepaintManagerC extends RepaintManager{
		public void addDirtyRegion(JComponent c, int x, int y, int w, int h){
			if(c.getParent() instanceof Scaler){
				Scaler s=(Scaler)c.getParent();
				c=s;
				x=(int)(x*s.scale);
				y=(int)(y*s.scale);
				w=(int)(w*s.scale);
				h=(int)(h*s.scale);
			}
			super.addDirtyRegion(c, x, y, w, h);
	    }
	}
	
	private Point deRotation(Point p){
		double x=p.x-getWidth()/2.;
		double y=p.y-getHeight()/2.;
		
		double rot=-rotation*Math.PI/180;
		double cos=Math.cos(rot);
		double sin=Math.sin(rot);
		
		double nx=x*cos-y*sin;
		double ny=x*sin+y*cos;
		
		return new Point((int)nx+getWidth()/2, (int)ny+getHeight()/2);
	}
	
	private MouseEvent dealMouseEvent(MouseEvent e){
		Point np=deRotation(e.getPoint());
		return new MouseEvent(comp, e.getID(), e.getWhen(), e.getModifiers(), (int)(np.x/scale-xoffset), (int)(np.y/scale-yoffset), e.getClickCount(), e.isPopupTrigger());
	}

	public void setRotation(double d) {
		rotation=d;
	}
	
}
