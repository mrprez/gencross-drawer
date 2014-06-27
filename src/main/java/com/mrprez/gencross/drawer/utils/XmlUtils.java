package com.mrprez.gencross.drawer.utils;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.mrprez.gencross.drawer.drawing.Drawing;
import com.mrprez.gencross.drawer.element.DrawedElement;

public class XmlUtils {
	
	public static Element buildDrawedElementXml(DrawedElement drawedElement){
		Element element = DocumentHelper.createElement("drawedElement");
		element.addAttribute("x", ""+drawedElement.getX());
		element.addAttribute("y", ""+drawedElement.getY());
		element.addAttribute("fontName",drawedElement.getFontName());
		element.addAttribute("fontSize",""+drawedElement.getFontSize());
		element.addAttribute("fontStyle",""+drawedElement.getFontStyle());
		element.addAttribute("name", drawedElement.getVariableName());
		Color color = drawedElement.getColor();
		element.addAttribute("color", color.getRed()+","+color.getGreen()+","+color.getBlue());
		element.addAttribute("angle", ""+drawedElement.getAngle());
		if(drawedElement.getSubstring()!=null){
			element.addAttribute("substring", drawedElement.getSubstring().toString());
		}
		element.setText(drawedElement.getText());
		return element;
	}
	
	public static Document buildDrawingXml(Drawing drawing){
		Document document = DocumentHelper.createDocument();
		Element rootEl = document.addElement("drawing");
		Iterator<DrawedElement> it = drawing.drawedElementIterator();
		while(it.hasNext()){
			rootEl.add(buildDrawedElementXml(it.next()));
		}
		return document;
	}
	
	public static Drawing loadDescriptor(Document document, Drawing drawing) throws FileNotFoundException, IOException, DocumentException{
		Element root = document.getRootElement();
		for(Object element : root.elements("drawedElement")){
			drawing.addDrawedElement(loadElement((Element)element));
		}
		return drawing;
	}
	
	private static DrawedElement loadElement(Element element){
		DrawedElement drawedElement = new DrawedElement();
		String text = element.getText();
		String propertyName = text.substring(0, text.lastIndexOf("->"));
		String type = text.substring(text.lastIndexOf("->")+2);
		drawedElement.setPropertyName(propertyName);
		drawedElement.setType(type);
		String colors[] = element.attributeValue("color").split(",");
		drawedElement.setColor(new Color(Integer.parseInt(colors[0]),Integer.parseInt(colors[1]),Integer.parseInt(colors[2])));
		drawedElement.setFontName(element.attributeValue("fontName"));
		drawedElement.setFontSize(Integer.parseInt(element.attributeValue("fontSize")));
		drawedElement.setFontStyle(Integer.parseInt(element.attributeValue("fontStyle")));
		drawedElement.setVariableName(element.attributeValue("name"));
		drawedElement.setX(Integer.parseInt(element.attributeValue("x")));
		drawedElement.setY(Integer.parseInt(element.attributeValue("y")));
		if(element.attribute("angle")!=null){
			drawedElement.setAngle(Double.parseDouble(element.attributeValue("angle")));
		}
		if(element.attributeValue("substring")!=null){
			drawedElement.setSubstring(Integer.parseInt(element.attributeValue("substring")));
		}
		
		return drawedElement;
	}
	
	public static Document loadXml(InputStream inputStream) throws IOException, DocumentException{
		InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
		StringBuilder buffer = new StringBuilder();
		int i;
		while((i=reader.read())>=0){
			buffer.append((char)i);
		}
		Document document = DocumentHelper.parseText(buffer.toString());
		return document;
	}
	
	

}
