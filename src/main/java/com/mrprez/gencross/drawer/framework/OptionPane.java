package com.mrprez.gencross.drawer.framework;

import java.awt.Component;
import java.awt.HeadlessException;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.mrprez.gencross.drawer.error.ErrorFrame;

public class OptionPane extends JOptionPane implements Runnable {
	private static final long serialVersionUID = 1L;
	private static final int CONFIRM_DIALOG = 1;
	private static final int OPTION_DIALOG = 2;
	private static final int MESSAGE_DIALOG = 3;
	
	private int method;
	private Component parentComponent;
	private Object message;
	private String title;
	private int optionType;
	private int messageType;
	private Icon icon;
	private Object[] options;
	private Object initialValue;
	private int result;
	
	
	private OptionPane(int method, Component parentComponent, Object message, String title,
			int optionType, int messageType, Icon icon, Object[] options,
			Object initialValue) {
		super();
		this.method = method;
		this.parentComponent = parentComponent;
		this.message = message;
		this.title = title;
		this.optionType = optionType;
		this.messageType = messageType;
		this.icon = icon;
		this.options = options;
		this.initialValue = initialValue;
	}
	
	private OptionPane(int method, Component parentComponent, Object message, String title,
			int optionType, int messageType, Icon icon) {
		super();
		this.method = method;
		this.parentComponent = parentComponent;
		this.message = message;
		this.title = title;
		this.optionType = optionType;
		this.messageType = messageType;
		this.icon = icon;
	}
	
	private OptionPane(int method, Component parentComponent, Object message, String title,
			int messageType, Icon icon) {
		super();
		this.method = method;
		this.parentComponent = parentComponent;
		this.message = message;
		this.title = title;
		this.messageType = messageType;
		this.icon = icon;
	}

	public static int showOptionDialog(Component parentComponent,Object message,String title,int optionType,int messageType,Icon icon,Object[] options,Object initialValue) throws HeadlessException {
		OptionPane optionPane = new OptionPane(OPTION_DIALOG,parentComponent,message,title,optionType,messageType,icon,options,initialValue);
		try {
			SwingUtilities.invokeAndWait(optionPane);
		} catch (Exception e) {
			ErrorFrame.displayError(e);
		}
		return optionPane.result;
	}
	
	public static int showConfirmDialog(Component parentComponent,Object message,String title,int optionType) throws HeadlessException {
		OptionPane optionPane = new OptionPane(OPTION_DIALOG,parentComponent,message,title,optionType,JOptionPane.QUESTION_MESSAGE,null);
		try {
			SwingUtilities.invokeAndWait(optionPane);
		} catch (Exception e) {
			ErrorFrame.displayError(e);
		}
		return optionPane.result;
	}
	
	public static void showMessageDialog(Component parentComponent, Object message, String title, int messageType){
		OptionPane optionPane = new OptionPane(MESSAGE_DIALOG,parentComponent,message,title,messageType,null);
		try {
			SwingUtilities.invokeAndWait(optionPane);
		} catch (Exception e) {
			ErrorFrame.displayError(e);
		}
	}
	
	@Override
	public void run() {
		if(method==OPTION_DIALOG){
			result = super.showOptionDialog(parentComponent,message,title,optionType,messageType,icon,options,initialValue);
		}else if(method==CONFIRM_DIALOG){
			result = super.showConfirmDialog(parentComponent, message, title, optionType, messageType,icon);
		}else if(method==MESSAGE_DIALOG){
			super.showMessageDialog(parentComponent, message, title, messageType, icon);
		}
	}

}
