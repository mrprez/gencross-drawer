package com.mrprez.gencross.drawer.element;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ChangeAngleListener implements ChangeListener {
	private JLabel angleLabel;
	private JSlider angleSlider;
	
	
	public ChangeAngleListener(JLabel angleLabel, JSlider angleSlider) {
		super();
		this.angleLabel = angleLabel;
		this.angleSlider = angleSlider;
	}
	

	@Override
	public void stateChanged(ChangeEvent event) {
		angleLabel.setText("Angle: "+angleSlider.getValue()+"°");
	}

}
