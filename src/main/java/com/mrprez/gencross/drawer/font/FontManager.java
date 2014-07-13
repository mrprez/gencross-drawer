package com.mrprez.gencross.drawer.font;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.mrprez.gencross.drawer.drawing.Drawing;
import com.mrprez.gencross.drawer.element.DrawedElement;

public class FontManager {
	private static Map<String, Font> fonts = new HashMap<String, Font>();
	private static Map<String, File> files = new HashMap<String, File>();
	
	
	public static void loadFonts(File policeRepository) throws FontFormatException, IOException{
		File fontFiles[] = policeRepository.listFiles();
		fonts.clear();
		for(int i=0; i<fontFiles.length; i++){
			File fontFile = fontFiles[i];
			if(fontFile.getName().endsWith(".ttf")){
				Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
				fonts.put(font.getFontName(), Font.createFont(Font.TRUETYPE_FONT, fontFile));
				files.put(font.getFontName(), fontFile);
			}
		}
	}
	
	public static SortedSet<String> getFonts(){
		TreeSet<String> result = new TreeSet<String>();
		result.addAll(fonts.keySet());
		return result;
	}
	
	public static Font getFont(String fontName){
		return fonts.get(fontName);
	}
	
	public static Set<File> getUsedFontFiles(Drawing drawing){
		Set<File> usedFontFiles = new HashSet<File>();
		Iterator<DrawedElement> it = drawing.drawedElementIterator();
		while(it.hasNext()){
			DrawedElement drawedElement = it.next();
			usedFontFiles.add(files.get(drawedElement.getFont().getFontName()));
		}
		return usedFontFiles;
	}
	
	
	

}
