package com.mrprez.gencross.drawer.utils;

import java.util.Comparator;

import com.mrprez.gencross.drawer.element.DrawedElement;

public class VariableNameComparator implements Comparator<DrawedElement> {

	@Override
	public int compare(DrawedElement arg0, DrawedElement arg1) {
		return arg0.getVariableName().compareTo(arg1.getVariableName());
	}

}
