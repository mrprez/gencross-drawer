package com.mrprez.gencross.drawer.menu;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import com.mrprez.gencross.drawer.Drawer;
import com.mrprez.gencross.drawer.framework.Action;
import com.mrprez.gencross.drawer.framework.FileChooser;

public class LoadImageListener extends Action {
	private FileChooser fileChooser = new FileChooser();
	
	
	


	@Override
	public void workInBackground() throws IOException, InterruptedException {
		int returnValue = fileChooser.showOpenDialog(Drawer.getInstance());
		if(returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			Drawer.getInstance().getDrawing().setImage(selectedFile);
		}
	}

}
