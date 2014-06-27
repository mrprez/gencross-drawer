package com.mrprez.gencross.drawer;


public class Format {
	private double width;
	private double height;
	
	public final static int resolution = 28;
	
	
	public Format(double width, double height) {
		super();
		this.width = width;
		this.height = height;
	}
	
	public static Format getA4(){
		return new Format(21, 29.7);
	}

	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	
}
