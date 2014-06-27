package com.mrprez.gencross.drawer.menu;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.mrprez.gencross.drawer.Drawer;
import com.mrprez.gencross.drawer.font.FontManager;
import com.mrprez.gencross.drawer.framework.Action;
import com.mrprez.gencross.drawer.framework.FileChooser;
import com.mrprez.gencross.drawer.framework.OptionPane;
import com.mrprez.gencross.drawer.utils.XmlUtils;

public class SaveListener extends Action {
	public static final String BACKGROUND_IMAGE_NAME = "background.jpg";
	public static final String XML_NAME = "descriptor.xml";
	
	private FileChooser fileChooser;
	
	
	public SaveListener(FileChooser fileChooser) {
		super();
		this.fileChooser = fileChooser;
	}

	@Override
	public void workInBackground() throws Exception {
		int result = fileChooser.showSaveDialog(Drawer.getInstance());
		if(result==FileChooser.APPROVE_OPTION){
			File file = fileChooser.getSelectedFile();
			String fileName = file.getName();
			if(fileName.contains(".")){
				String extension = fileName.substring(file.getName().lastIndexOf(".")+1);
				if(extension.equals("gcd")){
					fileName = fileName.substring(0, fileName.length()-4);
				}
			}
			
			File gcdFile = new File(file.getParentFile(), fileName+".gcd");
			createGcd(gcdFile);
		}
	}
	
	private void createGcd(File gcdFile) throws IOException{
		if(gcdFile.exists()){
			int result = OptionPane.showConfirmDialog(Drawer.getInstance(), "Ce fichier existe déjà. Ecraser?","Ecraser?",JOptionPane.YES_NO_OPTION);
			if(result==OptionPane.OK_OPTION){
				gcdFile.delete();
			}else{
				return;
			}
		}
		if(!gcdFile.createNewFile()){
			throw new IOException("Impossible de créer le fichier: "+gcdFile.getAbsolutePath());
		}
		ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(gcdFile),2048));
		
		XMLWriter writer = new XMLWriter(zos, new OutputFormat("\t", true, "UTF-8"));
		
		try{
			zos.putNextEntry(new ZipEntry(XML_NAME));
			writer.write(XmlUtils.buildDrawingXml(Drawer.getInstance().getDrawing()));
			zos.closeEntry();
			zos.putNextEntry(new ZipEntry(BACKGROUND_IMAGE_NAME));
			ImageIO.write(Drawer.getInstance().getDrawing().getImage(), "jpeg", zos);
			zos.closeEntry();
			for(File fontFile : FontManager.getUsedFontFiles(Drawer.getInstance().getDrawing())){
				zos.putNextEntry(new ZipEntry(fontFile.getName()));
				InputStream fontInputStream = new BufferedInputStream(new FileInputStream(fontFile));
				try{
					int i;
					while((i=fontInputStream.read())>=0){
						zos.write(i);
					}
				}finally{
					fontInputStream.close();
				}
				zos.closeEntry();
			}
		}finally{
			writer.close();
			zos.close();
		}
	}
	

}
