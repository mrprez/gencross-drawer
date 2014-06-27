package com.mrprez.gencross.drawer.error;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Rectangle;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;

public class ErrorFrame extends JDialog {
	private static final long serialVersionUID = 1L;
	private Exception exception;
	private JLabel message = new JLabel("L'application a rencontré un problème. Contactez votre administrateur.");
	private JButton detailButton = new JButton("Détails");
	private JPanel errorPanel = new JPanel();
	private JScrollPane scrollPane = new JScrollPane(errorPanel);
	
	
	public ErrorFrame(Exception exception) throws HeadlessException {
		super();
		exception.printStackTrace();
		this.exception = exception;
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		this.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		this.setTitle(exception.getClass().getSimpleName());
		scrollPane.setPreferredSize(new Dimension(200,200));
		populate();
		scrollPane.setVisible(true);
		detailButton.addActionListener(new ErrorDetailListener(this));
		
		GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createSequentialGroup()
        	.addContainerGap()
        	.addGroup(layout.createParallelGroup()
	    		.addComponent(message)
	    		.addComponent(detailButton, GroupLayout.Alignment.TRAILING)
	    		.addComponent(scrollPane)
	    	)
	    	.addContainerGap()
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
        	.addContainerGap()
        	.addGroup(layout.createSequentialGroup()
	        	.addComponent(message)
	        	.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
	        	.addComponent(detailButton)
	        	.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
	        	.addComponent(scrollPane)
	        )
        	.addContainerGap()
        );
        pack();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle desktop = ge.getMaximumWindowBounds();
        int x = (desktop.width-this.getSize().width)/2;
        int y = (desktop.height-this.getSize().height)/2;
        this.setLocation(x, y);
        
	}
	
	private void populate(){
		errorPanel.setLayout(new GridBagLayout());
		GridBagConstraints constraint = new GridBagConstraints();
		constraint.gridy = 1;
		constraint.anchor = GridBagConstraints.FIRST_LINE_START;
		Throwable currentException = exception;
		do{
			errorPanel.add(new JLabel(currentException.getClass().getName()+" : "+currentException.getMessage()),constraint);
			StackTraceElement stackTrace[] = currentException.getStackTrace();
			for(int i=0; i<stackTrace.length; i++){
				constraint.gridy = constraint.gridy+1;
				errorPanel.add(new JLabel(stackTrace[i].toString()),constraint);
			}
			currentException = currentException.getCause();
			if(currentException!=null){
				constraint.gridy = constraint.gridy+1;
				errorPanel.add(new JLabel("Caused by: "+currentException.getClass().getName()+" : "+currentException.getMessage()),constraint);
			}
		}while(currentException!=null);
		
	}
	
	public void hideShowDetails(){
		scrollPane.setVisible(!scrollPane.isVisible());
		pack();
	}
	
	public static void displayError(Exception e){
		(new ErrorFrame(e)).setVisible(true);
	}

}
