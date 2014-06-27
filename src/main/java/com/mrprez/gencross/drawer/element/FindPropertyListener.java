package com.mrprez.gencross.drawer.element;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.mrprez.gencross.Personnage;
import com.mrprez.gencross.drawer.Drawer;
import com.mrprez.gencross.drawer.explorer.ExplorerDialog;

public class FindPropertyListener implements ActionListener {
	private DrawedElementDialog drawedElementDialog;
	

	public FindPropertyListener(DrawedElementDialog drawedElementDialog) {
		super();
		this.drawedElementDialog = drawedElementDialog;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String type = drawedElementDialog.getType();
		if(type.equals("Total de points") || type.equals("Points restant") || type.equals("Points dépensés")){
			Personnage personnage = Drawer.getInstance().getPersonnage();
			String pointPoolName = (String) JOptionPane.showInputDialog(drawedElementDialog, "Pool de points", "",  JOptionPane.PLAIN_MESSAGE, null, personnage.getPointPools().keySet().toArray(), personnage.getPointPools().keySet().iterator().next());
			if(pointPoolName!=null){
				drawedElementDialog.setPropertyName(pointPoolName);
			}
		}else{
			ExplorerDialog explorerDialog = new ExplorerDialog(drawedElementDialog);
			explorerDialog.setSelectedProperty(drawedElementDialog.getDrawedElement().getPropertyName());
			explorerDialog.setVisible(true);
		}
	}

}
