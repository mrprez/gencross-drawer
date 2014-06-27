package com.mrprez.gencross.drawer.menu;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import org.dom4j.Document;

import com.mrprez.gencross.drawer.Drawer;
import com.mrprez.gencross.drawer.drawing.Drawing;
import com.mrprez.gencross.drawer.font.FontManager;
import com.mrprez.gencross.drawer.framework.Action;
import com.mrprez.gencross.drawer.framework.FileChooser;
import com.mrprez.gencross.drawer.utils.XmlUtils;

public class OpenListener extends Action {
	private FileChooser fileChooser;

	
	public OpenListener(FileChooser fileChooser) {
		super();
		this.fileChooser = fileChooser;
	}

	@Override
	public void workInBackground() throws Exception {
		int result = fileChooser.showOpenDialog(Drawer.getInstance());
		if(result==JFileChooser.APPROVE_OPTION){
			Document document = null;
			BufferedImage image = null;
			boolean reloadFonts = false;
			ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(fileChooser.getSelectedFile())));
			try{
				ZipEntry entry;
				while((entry = zis.getNextEntry()) != null){
					if(entry.getName().equals(SaveListener.XML_NAME)){
						document = XmlUtils.loadXml(zis);
					}else if(entry.getName().equals(SaveListener.BACKGROUND_IMAGE_NAME)){
						image = ImageIO.read(zis);
					}else if(entry.getName().endsWith(".ttf")){
						if(loadFont(entry.getName(), zis)){
							reloadFonts = true;
						}
					}
				}
			}finally{
				zis.close();
			}
			
			if(reloadFonts){
				FontManager.loadFonts(new File(Drawer.FONT_REPOSITORY_NAME));
			}
			
			Drawing drawing = new Drawing(image);
			XmlUtils.loadDescriptor(document, drawing);
			
			Drawer.getInstance().setDrawing(drawing);
			
		}
	}
	
	private boolean loadFont(String fileName, ZipInputStream zis) throws IOException, URISyntaxException{
		File fontFile = new File(new File(Drawer.getExecutionDirectory().getParentFile(), Drawer.FONT_REPOSITORY_NAME), fileName);
		if(!fontFile.exists()){
			OutputStream os = new BufferedOutputStream(new FileOutputStream(fontFile));
			try{
				int i;
				while((i=zis.read())>=0){
					os.write(i);
				}
			}finally{
				os.close();
			}
			return true;
		}
		return false;
	}
	
	

}
