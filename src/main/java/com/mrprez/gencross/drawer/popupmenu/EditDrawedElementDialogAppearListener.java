package com.mrprez.gencross.drawer.popupmenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.mrprez.gencross.drawer.Drawer;
import com.mrprez.gencross.drawer.element.DrawedElementDialog;

public class EditDrawedElementDialogAppearListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		DrawedElementDialog.displayDrawedElement(Drawer.getInstance().getDrawing().getSelectedDrawedElement());
	}

}
