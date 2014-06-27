package com.mrprez.gencross.drawer.framework;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CloseListener implements ActionListener {
	private Window window;
	
	
	public CloseListener(Window window) {
		super();
		this.window = window;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		window.dispose();
	}

}
