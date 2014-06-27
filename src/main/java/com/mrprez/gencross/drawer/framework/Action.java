package com.mrprez.gencross.drawer.framework;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.mrprez.gencross.drawer.error.ErrorFrame;

public abstract class Action implements ActionListener {
	private ActionSwingWorker actionSwingWorker;
	
	
	@Override
	public final void actionPerformed(ActionEvent arg0) {
		try {
			actionSwingWorker = new ActionSwingWorker(this);
			actionSwingWorker.execute();
		} catch (Exception e) {
			ErrorFrame.displayError(e);
		}
	}

	protected abstract void workInBackground() throws Exception;

	
	
}
