package com.mrprez.gencross.drawer.element;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.AffineTransform;

import com.mrprez.gencross.drawer.font.FontManager;

public class DrawedElement implements Cloneable {
	public static final String VALUE = "value";
	public static final String NAME = "name";
	public static final String BRUT_VALUE = "brutValue";
	public static final String BRUT_NAME = "brutName";
	public static final String TEXT = "text";
	public static final String COMMENT = "comment";
	public static final String POINT_TOTAL = "pointsTotal";
	public static final String REMAINING_POINTS = "remainingPoints";
	public static final String SPEND_POINTS = "spendPoints";
	
	private int x = 0;
	private int y = 0;
	private String variableName;
	private String propertyName;
	private String type = VALUE;
	private Font font = FontManager.getFont("Arial");
	private Color color = Color.BLACK;
	private double angle = 0.0;
	private Integer substring;
	

	@Override
	public DrawedElement clone() throws CloneNotSupportedException {
		DrawedElement clone = new DrawedElement();
		clone.setAngle(angle);
		clone.setColor(new Color(color.getRGB()));
		clone.setFont(font);
		clone.setPropertyName(propertyName);
		clone.setSubstring(substring);
		clone.setType(type);
		clone.setVariableName(variableName);
		clone.setX(x);
		clone.setY(y);
		
		return clone;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public String getVariableName() {
		return variableName;
	}
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getText(){
		return propertyName+"->"+type;
	}
	public Font getFont() {
		return font;
	}
	public void setFont(Font font) {
		this.font = font;
	}
	public String getFontName() {
		return font.getFontName();
	}
	public void setFontName(String fontName) {
		if(FontManager.getFont(fontName)!=null){
			font = FontManager.getFont(fontName);
		}
	}
	public int getFontSize() {
		return font.getSize();
	}
	public void setFontSize(int fontSize) {
		font = font.deriveFont((float) fontSize);
	}
	public int getFontStyle() {
		return font.getStyle();
	}
	public void setFontStyle(int fontStyle) {
		font = font.deriveFont(fontStyle);
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public double getAngle() {
		return angle;
	}
	public int getAngleInDegree() {
		return (int) (angle/Math.PI*180.0);
	}
	public void setAngleInDegree(int angleInDegree) {
		font = font.deriveFont(AffineTransform.getRotateInstance(-this.angle));
		this.angle = ((double)angleInDegree)/180.0*Math.PI;
		font = font.deriveFont(AffineTransform.getRotateInstance(angle));
	}
	public void setAngle(double angle){
		font = font.deriveFont(AffineTransform.getRotateInstance(-this.angle));
		this.angle = angle;
		font = font.deriveFont(AffineTransform.getRotateInstance(angle));
	}
	public void setSubstring(Integer substring){
		this.substring = substring;
	}
	public Integer getSubstring(){
		return substring;
	}
	


}
