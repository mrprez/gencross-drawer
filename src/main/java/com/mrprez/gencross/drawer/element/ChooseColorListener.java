package com.mrprez.gencross.drawer.element;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.mrprez.gencross.drawer.colorchooser.FontColorChooser;

public class ChooseColorListener implements ActionListener {
	private FontColorChooser fontColorChooser = new FontColorChooser();
	private DrawedElementDialog drawedElementDialog;
	
	public ChooseColorListener(DrawedElementDialog drawedElementDialog){
		super();
		this.drawedElementDialog = drawedElementDialog;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		fontColorChooser.show(drawedElementDialog);
	}

}
