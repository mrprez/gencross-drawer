package com.mrprez.gencross.drawer.framework;

import java.awt.Component;
import java.awt.HeadlessException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

public class FileChooser extends JFileChooser implements Runnable {
	private static final long serialVersionUID = 1L;
	private Component parent;
	private String approveButtonText;
	private int result;
	
	
	@Override
	public void run() {
		result = super.showDialog(parent, approveButtonText);
	}

	@Override
	public int showDialog(Component parent, String approveButtonText) throws HeadlessException {
		try {
			this.parent = parent;
			this.approveButtonText = approveButtonText;
			SwingUtilities.invokeAndWait(this);
			return result;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return JFileChooser.CANCEL_OPTION;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return JFileChooser.CANCEL_OPTION;
		}
	}
	
	

}
