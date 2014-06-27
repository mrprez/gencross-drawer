package com.mrprez.gencross.drawer.utils;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.mrprez.gencross.drawer.element.DrawedElement;

public class VariableNameRenderer extends JLabel implements ListCellRenderer {
	private static final long serialVersionUID = 1L;

	
	@Override
	public Component getListCellRendererComponent(JList arg0, Object object, int arg2, boolean arg3, boolean arg4) {
		if(object==null){
			this.setText("null");
		}else{
			this.setText(((DrawedElement)object).getVariableName());
		}
		return this;
	}

}
