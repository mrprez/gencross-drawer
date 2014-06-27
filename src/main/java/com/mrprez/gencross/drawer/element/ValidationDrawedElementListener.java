package com.mrprez.gencross.drawer.element;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import com.mrprez.gencross.drawer.Drawer;

public class ValidationDrawedElementListener implements ActionListener {
	private DrawedElementDialog drawedElementDialog;

	
	public ValidationDrawedElementListener(DrawedElementDialog drawedElementDialog) {
		super();
		this.drawedElementDialog = drawedElementDialog;
	}


	@Override
	public void actionPerformed(ActionEvent event) {
		Drawer.getInstance().getDrawing().addDrawedElement(drawedElementDialog.getDrawedElement());
		drawedElementDialog.dispose();
		SwingUtilities.updateComponentTreeUI(Drawer.getInstance().getDrawing());
	}

}
