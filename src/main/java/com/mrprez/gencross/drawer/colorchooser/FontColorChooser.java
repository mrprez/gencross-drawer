package com.mrprez.gencross.drawer.colorchooser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;

import com.mrprez.gencross.drawer.element.DrawedElementDialog;

public class FontColorChooser extends JColorChooser implements ActionListener {
	private static final long serialVersionUID = 1L;
	private DrawedElementDialog drawedElementDialog;
	

	public void show(DrawedElementDialog parent){
		this.drawedElementDialog = parent;
		setColor(parent.getColor());
		JColorChooser.createDialog(parent, "Couleur de Police", true, this, this, null).setVisible(true);
	}



	@Override
	public void actionPerformed(ActionEvent arg0) {
		drawedElementDialog.setColor(getColor());
	}

}
