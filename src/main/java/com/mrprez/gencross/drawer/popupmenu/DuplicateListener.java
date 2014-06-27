package com.mrprez.gencross.drawer.popupmenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.mrprez.gencross.drawer.Drawer;
import com.mrprez.gencross.drawer.element.DrawedElement;
import com.mrprez.gencross.drawer.element.DrawedElementDialog;
import com.mrprez.gencross.drawer.error.ErrorFrame;

public class DuplicateListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent event) {
		try{
			DrawedElement drawedElement = Drawer.getInstance().getDrawing().getSelectedDrawedElement();
			DrawedElement newDrawedElement = drawedElement.clone();
			newDrawedElement.setVariableName("CopyOf"+drawedElement.getVariableName());
			newDrawedElement.setX(drawedElement.getX()+10);
			newDrawedElement.setY(drawedElement.getY()+10);
			Drawer.getInstance().getDrawing().addDrawedElement(newDrawedElement);
			DrawedElementDialog.displayDrawedElement(newDrawedElement);
		}catch(Exception e){
			ErrorFrame.displayError(e);
		}
	}

}
