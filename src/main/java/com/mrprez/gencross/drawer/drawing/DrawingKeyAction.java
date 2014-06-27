package com.mrprez.gencross.drawer.drawing;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.mrprez.gencross.drawer.Drawer;
import com.mrprez.gencross.drawer.element.DrawedElement;

public class DrawingKeyAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	private int dx;
	private int dy;
	
	
	public DrawingKeyAction(int dx, int dy) {
		super();
		this.dx = dx;
		this.dy = dy;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DrawedElement drawedElement = Drawer.getInstance().getDrawing().getSelectedDrawedElement();
		drawedElement.setX(drawedElement.getX()+dx);
		drawedElement.setY(drawedElement.getY()+dy);
		Drawer.getInstance().reinitToolBar();
		Drawer.getInstance().getDrawing().repaint();
	}

	

}
