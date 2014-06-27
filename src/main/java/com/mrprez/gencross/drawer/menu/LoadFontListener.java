package com.mrprez.gencross.drawer.menu;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mrprez.gencross.drawer.Drawer;
import com.mrprez.gencross.drawer.font.FontManager;
import com.mrprez.gencross.drawer.framework.Action;
import com.mrprez.gencross.drawer.framework.FileChooser;
import com.mrprez.gencross.drawer.framework.OptionPane;

public class LoadFontListener extends Action {
	private FileChooser fileChooser = new FileChooser();
	
	
	public LoadFontListener(){
		super();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Fichiers True Type Fonts", "ttf"));
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setAcceptAllFileFilterUsed(false);
	}

	@Override
	public void workInBackground() throws Exception {
		int fileChooserResult = fileChooser.showOpenDialog(Drawer.getInstance());
		if(fileChooserResult==JFileChooser.APPROVE_OPTION){
			File file = fileChooser.getSelectedFile();
			File fontRepository = new File(Drawer.getExecutionDirectory().getParentFile(), Drawer.FONT_REPOSITORY_NAME);
			File destination = new File(fontRepository, file.getName());
			if(destination.exists()){
				int confirmErase = OptionPane.showConfirmDialog(Drawer.getInstance(), "La police "+destination.getName()+" existe déjà. Ecraser?", "Ecraser?", JOptionPane.OK_CANCEL_OPTION);
				if(confirmErase==JOptionPane.CANCEL_OPTION){
					return;
				}
				destination.delete();
			}
			destination.createNewFile();
			copyFile(file, destination);
			FontManager.loadFonts(fontRepository);
		}
	}
	
	private void copyFile(File origin, File destination) throws IOException{
		InputStream is = new BufferedInputStream(new FileInputStream(origin));
		OutputStream os = new BufferedOutputStream(new FileOutputStream(destination));
		try{
			int i;
			while((i=is.read())>=0){
				os.write(i);
			}
		}finally{
			is.close();
			os.close();
		}
		
	}

}
