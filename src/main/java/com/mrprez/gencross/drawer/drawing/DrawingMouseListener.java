package com.mrprez.gencross.drawer.drawing;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import com.mrprez.gencross.drawer.Drawer;
import com.mrprez.gencross.drawer.element.DrawedElement;

public class DrawingMouseListener implements MouseListener {
	private JPopupMenu popupMenu;
	
	
	public DrawingMouseListener(JPopupMenu popupMenu) {
		super();
		this.popupMenu = popupMenu;
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		Drawer.getInstance().getDrawing().grabFocus();
		if(event.getButton()==MouseEvent.BUTTON1){
			DrawedElement selected = Drawer.getInstance().getDrawing().getDrawedElement(event.getX(), event.getY());
			if(event.isShiftDown()){
				Drawer.getInstance().getDrawing().setSecondSelectedDrawedElement(selected);
			}else{
				Drawer.getInstance().getDrawing().selectDrawedElement(selected);
			}
			Drawer.getInstance().reinitToolBar();
			SwingUtilities.updateComponentTreeUI(Drawer.getInstance());
		}
		if(event.getButton()==MouseEvent.BUTTON3){
			popupMenu.show(Drawer.getInstance().getDrawing(), event.getX(), event.getY());
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		;
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		;
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		;
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		;
	}

}
