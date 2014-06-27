package com.mrprez.gencross.drawer.menu;

import java.io.File;
import java.io.FileInputStream;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.dom4j.Document;

import com.mrprez.gencross.Personnage;
import com.mrprez.gencross.disk.PersonnageFactory;
import com.mrprez.gencross.drawer.Drawer;
import com.mrprez.gencross.drawer.framework.Action;
import com.mrprez.gencross.drawer.framework.FileChooser;

public class LoadPersonnageListener extends Action {
	private FileChooser fileChooser = new FileChooser();

	
	public LoadPersonnageListener(){
		super();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Fichiers Gen-Cross", "gcr"));
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setAcceptAllFileFilterUsed(false);
	}
	
	@Override
	public void workInBackground() throws Exception {
		int fileChooserResult = fileChooser.showOpenDialog(Drawer.getInstance());
		if(fileChooserResult==JFileChooser.APPROVE_OPTION){
			File file = fileChooser.getSelectedFile();
			Document document = PersonnageFactory.loadGcr(new FileInputStream(file));
			Personnage personnage = new Personnage();
			personnage.setXMLWithoutListener(document.getRootElement());
			Drawer.getInstance().setPersonnage(personnage);
		}
	}
	


}
