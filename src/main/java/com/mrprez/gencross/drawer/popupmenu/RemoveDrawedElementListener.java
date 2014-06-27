package com.mrprez.gencross.drawer.popupmenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.mrprez.gencross.drawer.Drawer;

public class RemoveDrawedElementListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Drawer.getInstance().getDrawing().removeSelectedDrawedElement();
	}

}
