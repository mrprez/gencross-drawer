package com.mrprez.gencross.drawer.drawing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import com.mrprez.gencross.drawer.element.DrawedElement;
import com.mrprez.gencross.drawer.popupmenu.DrawingPopupMenu;

public class Drawing extends JPanel {
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	private JPopupMenu popupMenu = new DrawingPopupMenu();
	private Set<DrawedElement> drawedElementSet = new HashSet<DrawedElement>();
	private DrawedElement selectedDrawedElement;
	private DrawedElement secondSelectedDrawedElement;
	
	
	public Drawing(int width, int height) throws IOException {
		super();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D gr = image.createGraphics();
		gr.setColor(Color.white);
		gr.fillRect(0, 0, width, height);
		gr.dispose();
		setImage(image);
		init();
	}
	
	public Drawing(String imagePath) throws IOException {
		super();
		setImage(imagePath);
		init();
	}
	
	public Drawing(BufferedImage image) throws IOException {
		super();
		setImage(image);
		init();
	}
	
	private void init(){
		addMouseListener(new DrawingMouseListener(popupMenu));
		getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "up");
		getActionMap().put("up", new DrawingKeyAction(0, -1));
		getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "down");
		getActionMap().put("down", new DrawingKeyAction(0, 1));
		getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "left");
		getActionMap().put("left", new DrawingKeyAction(-1, 0));
		getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "right");
		getActionMap().put("right", new DrawingKeyAction(1, 0));
		
		getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, KeyEvent.SHIFT_MASK, false), "up*10");
		getActionMap().put("up*10", new DrawingKeyAction(0, -10));
		getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, KeyEvent.SHIFT_MASK, false), "down*10");
		getActionMap().put("down*10", new DrawingKeyAction(0, 10));
		getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, KeyEvent.SHIFT_MASK, false), "left*10");
		getActionMap().put("left*10", new DrawingKeyAction(-10, 0));
		getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, KeyEvent.SHIFT_MASK, false), "right*10");
		getActionMap().put("right*10", new DrawingKeyAction(10, 0));
	}


	@Override
	protected void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		gr.drawImage(image, 0, 0,image.getWidth(),image.getHeight(), null);
		for(DrawedElement drawedElement : drawedElementSet){
			if(drawedElement==secondSelectedDrawedElement){
				Rectangle2D rectangle = drawedElement.getFont().getStringBounds(drawedElement.getVariableName(), ((Graphics2D)gr).getFontRenderContext());
				gr.setColor(new Color(255,0,0,100));
				gr.drawRect(drawedElement.getX()-3, drawedElement.getY()-(int)rectangle.getHeight()+2, (int)rectangle.getWidth()+6, (int)rectangle.getHeight());
			}
			if(drawedElement==selectedDrawedElement){
				Rectangle2D rectangle = drawedElement.getFont().getStringBounds(drawedElement.getVariableName(), ((Graphics2D)gr).getFontRenderContext());
				gr.setColor(Color.red);
				gr.drawRect(drawedElement.getX()-3, drawedElement.getY()-(int)rectangle.getHeight()+2, (int)rectangle.getWidth()+6, (int)rectangle.getHeight());
			}
			gr.setColor(drawedElement.getColor());
			gr.setFont(drawedElement.getFont());
			gr.drawString(drawedElement.getVariableName(), drawedElement.getX(), drawedElement.getY());
		}
	}
	
	public void setImage(String imagePath) throws IOException{
		setImage(new File(imagePath));
	}
	public void setImage(File imageFile) throws IOException{
		setImage(ImageIO.read(imageFile));
	}
	public void setImage(InputStream is) throws IOException{
		setImage(ImageIO.read(is));
	}
	public void setImage(BufferedImage image){
		this.image = image;
		super.setPreferredSize(new Dimension(this.image.getWidth(),this.image.getHeight()));
	}
	public BufferedImage getImage(){
		return image;
	}
	
	public void addDrawedElement(DrawedElement drawedElement){
		drawedElementSet.add(drawedElement);
	}
	
	public void selectDrawedElement(DrawedElement selectedDrawedElement){
		this.selectedDrawedElement = selectedDrawedElement;
	}
	public DrawedElement getSelectedDrawedElement(){
		return selectedDrawedElement;
	}
	public DrawedElement getSecondSelectedDrawedElement(){
		return secondSelectedDrawedElement;
	}
	public void setSecondSelectedDrawedElement(DrawedElement drawedElement){
		this.secondSelectedDrawedElement = drawedElement;
	}
	
	public DrawedElement getDrawedElement(int x, int y){
		int minDistance = Integer.MAX_VALUE;
		DrawedElement result = null;
		for(DrawedElement drawedElement : drawedElementSet){
			int d = (drawedElement.getX()-x)*(drawedElement.getX()-x)+(drawedElement.getY()-y)*(drawedElement.getY()-y);
			if(d<minDistance){
				result = drawedElement;
				minDistance = d;
			}
		}
		return result;
	}
	
	public void removeSelectedDrawedElement(){
		drawedElementSet.remove(selectedDrawedElement);
		selectedDrawedElement = null;
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	public Iterator<DrawedElement> drawedElementIterator(){
		return drawedElementSet.iterator();
	}
	public Set<DrawedElement> getDrawedElementSet(){
		return drawedElementSet;
	}
	
	public int getImageWidth(){
		return image.getWidth(null);
	}
	public int getImageHeight(){
		return image.getHeight(null);
	}

	
}
