package com.mrprez.gencross.drawer.extrapolation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TreeSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.mrprez.gencross.drawer.Drawer;
import com.mrprez.gencross.drawer.element.DrawedElement;
import com.mrprez.gencross.drawer.error.ErrorFrame;
import com.mrprez.gencross.drawer.utils.VariableNameComparator;
import com.mrprez.gencross.drawer.utils.VariableNameRenderer;
import com.mrprez.gencross.drawer.utils.VariableNameUtil;

public class ExtrapolationDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private static ExtrapolationDialog instance = buildInstance();
	
	private JLabel element1Label = new JLabel("Element 1");
	private JTextField element1TextField = new JTextField(20);
	private JLabel element2Label = new JLabel("Element 2");
	private JComboBox element2ComboBox = new JComboBox();
	private JLabel numberLabel = new JLabel("Nombre de répétitions");
	private JComboBox numberComboBox = new JComboBox();
	private JButton validateButton = new JButton("Valider");
	
	
	private DrawedElement drawedElement1;
	
	
	public ExtrapolationDialog(){
		super();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setModal(true);
		
		element1TextField.setEditable(false);
		element2ComboBox.setRenderer(new VariableNameRenderer());
		for(int i=1; i<=20; i++){
			numberComboBox.addItem(Integer.valueOf(i));
		}
		validateButton.addActionListener(this);
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addContainerGap()
			.addGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup()
						.addComponent(element1Label)
						.addComponent(element2Label)
						.addComponent(numberLabel)
					)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(layout.createParallelGroup()
						.addComponent(element1TextField)
						.addComponent(element2ComboBox)
						.addComponent(numberComboBox)
					)
				)
				.addComponent(validateButton, GroupLayout.Alignment.CENTER)
			)
			.addContainerGap()
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addContainerGap()
			.addGroup(layout.createParallelGroup()
				.addComponent(element1Label)
				.addComponent(element1TextField)
			)
			.addPreferredGap(ComponentPlacement.RELATED)
			.addGroup(layout.createParallelGroup()
				.addComponent(element2Label)
				.addComponent(element2ComboBox)
			)
			.addPreferredGap(ComponentPlacement.RELATED)
			.addGroup(layout.createParallelGroup()
				.addComponent(numberLabel)
				.addComponent(numberComboBox)
			)
			.addPreferredGap(ComponentPlacement.RELATED)
			.addComponent(validateButton)
			.addContainerGap()
		);
		pack();
	}
	
	public static synchronized ExtrapolationDialog buildInstance(){
		return new ExtrapolationDialog();
	}
	
	public static void display(DrawedElement drawedElement, DrawedElement secondDrawedElement){
		instance.setDrawedElement1(drawedElement);
		TreeSet<DrawedElement> set = new TreeSet<DrawedElement>(new VariableNameComparator());
		set.addAll(Drawer.getInstance().getDrawing().getDrawedElementSet());
		instance.element2ComboBox.setModel(new DefaultComboBoxModel(set.toArray()));
		if(secondDrawedElement!=null){
			instance.element2ComboBox.setSelectedItem(secondDrawedElement);
		}
		instance.setVisible(true);
	}
	

	public DrawedElement getDrawedElement1() {
		return drawedElement1;
	}
	public void setDrawedElement1(DrawedElement drawedElement1) {
		this.drawedElement1 = drawedElement1;
		this.element1TextField.setText(drawedElement1.getVariableName());
	}
	
	public static ExtrapolationDialog getInstance() {
		return instance;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try{
			DrawedElement drawedElement2 = (DrawedElement) element2ComboBox.getSelectedItem();
			int dx = drawedElement2.getX()-drawedElement1.getX();
			int dy = drawedElement2.getY()-drawedElement1.getY();
			
			String variableNameCommonBegin = VariableNameUtil.findCommonBegins(drawedElement1.getVariableName(), drawedElement2.getVariableName());
			String variableNameCommonEnd = VariableNameUtil.findCommonEnds(drawedElement1.getVariableName(), drawedElement2.getVariableName());
			String v1Str = drawedElement1.getVariableName().substring(variableNameCommonBegin.length(), drawedElement1.getVariableName().length()-variableNameCommonEnd.length());
			String v2Str = drawedElement2.getVariableName().substring(variableNameCommonBegin.length(), drawedElement2.getVariableName().length()-variableNameCommonEnd.length());
			int v1 = Integer.valueOf(v1Str);
			int v2 = Integer.valueOf(v2Str);
			int dv = v2-v1;
			
			String propertyNameCommonBegin = VariableNameUtil.findCommonBegins(drawedElement1.getPropertyName(), drawedElement2.getPropertyName());
			String propertyNameCommonEnd = VariableNameUtil.findCommonEnds(drawedElement1.getPropertyName(), drawedElement2.getPropertyName());
			String p1Str = drawedElement1.getPropertyName().substring(propertyNameCommonBegin.length(), drawedElement1.getPropertyName().length()-propertyNameCommonEnd.length());
			String p2Str = drawedElement2.getPropertyName().substring(propertyNameCommonBegin.length(), drawedElement2.getPropertyName().length()-propertyNameCommonEnd.length());
			int p1 = Integer.valueOf(p1Str);
			int p2 = Integer.valueOf(p2Str);
			int dp = p2-p1;
			
			for(int i=1; i<=((Integer)numberComboBox.getSelectedItem()).intValue(); i++){
				DrawedElement drawedElement = drawedElement1.clone();
				drawedElement.setX(drawedElement2.getX()+i*dx);
				drawedElement.setY(drawedElement2.getY()+i*dy);
				drawedElement.setPropertyName(propertyNameCommonBegin+(p2+i*dp)+propertyNameCommonEnd);
				drawedElement.setVariableName(variableNameCommonBegin+(v2+i*dv)+variableNameCommonEnd);
				Drawer.getInstance().getDrawing().addDrawedElement(drawedElement);
			}
			SwingUtilities.updateComponentTreeUI(Drawer.getInstance());
		}catch(Exception e){
			ErrorFrame.displayError(e);
		}
		
		
		
	}
	
	
	
	
	

}
