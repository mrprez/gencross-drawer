package com.mrprez.gencross.drawer.framework;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import com.mrprez.gencross.drawer.Drawer;
import com.mrprez.gencross.drawer.error.ErrorFrame;

public class ActionSwingWorker extends SwingWorker<Boolean, Integer> {
	
	private Exception exception;
	private Action action;
	
	
	public ActionSwingWorker(Action action){
		super();
		this.action = action;
	}
	
	@Override
	protected Boolean doInBackground() {
		try {
			action.workInBackground();
		} catch (Exception e) {
			this.exception = e;
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	@Override
	protected void done() {
		try {
			if(!get()){
				ErrorFrame.displayError(exception);
			}
		} catch (Exception e) {
			ErrorFrame.displayError(exception);
		}
		SwingUtilities.updateComponentTreeUI(Drawer.getInstance());
	}

}
