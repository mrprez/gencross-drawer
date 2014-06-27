package com.mrprez.gencross.drawer.popupmenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.mrprez.gencross.drawer.element.DrawedElementDialog;

public class AddDrawedElementDialogAppearListener implements ActionListener{
	private int xDrawedElement;
	private int yDrawedElement;

	@Override
	public void actionPerformed(ActionEvent arg0) {
		DrawedElementDialog.displayNewDrawedElement(xDrawedElement, yDrawedElement);
	}

	public int getxDrawedElement() {
		return xDrawedElement;
	}
	public void setxDrawedElement(int xDrawedElement) {
		this.xDrawedElement = xDrawedElement;
	}
	public int getyDrawedElement() {
		return yDrawedElement;
	}
	public void setyDrawedElement(int yDrawedElement) {
		this.yDrawedElement = yDrawedElement;
	}
	
	
	
	

}
