package com.mrprez.gencross.drawer.popupmenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.mrprez.gencross.drawer.Drawer;
import com.mrprez.gencross.drawer.element.DrawedElement;
import com.mrprez.gencross.drawer.regression.RegressionDialog;

public class RegressionDialogAppearListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		DrawedElement drawedElement = Drawer.getInstance().getDrawing().getSelectedDrawedElement();
		DrawedElement secondDrawedElement = Drawer.getInstance().getDrawing().getSecondSelectedDrawedElement();
		RegressionDialog.display(drawedElement, secondDrawedElement);
	}

}
