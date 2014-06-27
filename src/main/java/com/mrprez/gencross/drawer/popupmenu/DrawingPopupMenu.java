package com.mrprez.gencross.drawer.popupmenu;

import java.awt.Component;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.mrprez.gencross.drawer.Drawer;


public class DrawingPopupMenu extends JPopupMenu {
	private static final long serialVersionUID = 1L;
	
	private JMenuItem addItem = new JMenuItem("Ajouter une variable");
	private JMenuItem removeItem = new JMenuItem("Supprimer");
	private JMenuItem editItem = new JMenuItem("Editer");
	private JMenuItem extrapolationItem = new JMenuItem("Extrapoler");
	private JMenuItem regressionItem = new JMenuItem("Regression");
	private JMenuItem duplicateItem = new JMenuItem("Dupliquer");
	private AddDrawedElementDialogAppearListener drawedElementDialogAppearListener = new AddDrawedElementDialogAppearListener();
	
	
	public DrawingPopupMenu(){
		super();
		addItem.addActionListener(drawedElementDialogAppearListener);
		add(addItem);
		
		removeItem.addActionListener(new RemoveDrawedElementListener());
		add(removeItem);
		
		editItem.addActionListener(new EditDrawedElementDialogAppearListener());
		add(editItem);
		
		duplicateItem.addActionListener(new DuplicateListener());
		add(duplicateItem);
		
		extrapolationItem.addActionListener(new ExtrapolationDialogAppearListener());
		add(extrapolationItem);
		
		regressionItem.addActionListener(new RegressionDialogAppearListener());
		add(regressionItem);
	}

	@Override
	public void show(Component arg0, int x, int y) {
		if(Drawer.getInstance().getDrawing().getSelectedDrawedElement()==null){
			removeItem.setEnabled(false);
			editItem.setEnabled(false);
		}else{
			removeItem.setEnabled(true);
			editItem.setEnabled(true);
		}
		drawedElementDialogAppearListener.setxDrawedElement(x);
		drawedElementDialogAppearListener.setyDrawedElement(y);
		super.show(arg0, x, y);
	}
	
	

}
