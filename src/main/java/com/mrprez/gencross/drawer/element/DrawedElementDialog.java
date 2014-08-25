package com.mrprez.gencross.drawer.element;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;

import com.mrprez.gencross.drawer.Drawer;
import com.mrprez.gencross.drawer.font.FontManager;

public class DrawedElementDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	
	private JLabel variableNameLabel = new JLabel("Nom");
	private JTextField variableNameField = new JTextField(20);
	private JLabel propertyNameLabel = new JLabel("Propriété");
	private JTextField propertyNameField = new JTextField(20);
	private JButton findPropertyButton = new JButton("Rechercher");
	private JLabel fieldLabel = new JLabel("Champs");
	private JComboBox fieldComboBox = new JComboBox();
	private JButton validationButton = new JButton("Valider");
	private JLabel fontSizeLabel = new JLabel("Taille de police");
	private JComboBox fontSizeComboBox = new JComboBox(new Integer[]{8,9,10,11,12,14,16,18,20,22,24,26,28,30,32,34,36,38,40,42,44,46,48,50,52,54,56,58,60,62,64,66,68,70,72});
	private JLabel fontLabel = new JLabel("Police");
	private JComboBox fontComboBox = new JComboBox(FontManager.getFonts().toArray());
	private JLabel colorLabel = new JLabel("Couleur");
	private JButton colorButton = new JButton();
	private JLabel boldLabel = new JLabel("Gras");
	private JCheckBox boldCheckBox = new JCheckBox();
	private JLabel italicLabel = new JLabel("Italique");
	private JCheckBox italicCheckBox = new JCheckBox();
	private JLabel angleLabel = new JLabel("Angle: 0°");
	private JSlider angleSlider = new JSlider(-180,180,0);
	private JLabel substringLabel = new JLabel("Substring");
	private JTextField substringField = new JTextField("");
	
	
	private DrawedElement drawedElement;
	
	
	public DrawedElementDialog(Frame owner){
		super(owner, true);
		build();
	}
	
	public static void displayDrawedElement(DrawedElement drawedElement){
		DrawedElementDialog drawedElementDialog = new DrawedElementDialog(Drawer.getInstance());
		drawedElementDialog.setDrawedElement(drawedElement);
		drawedElementDialog.setVisible(true);
	}
	
	public static void displayNewDrawedElement(int x, int y){
		DrawedElementDialog drawedElementDialog = new DrawedElementDialog(Drawer.getInstance());
		drawedElementDialog.drawedElement = new DrawedElement();
		drawedElementDialog.drawedElement.setX(x);
		drawedElementDialog.drawedElement.setY(y);
		drawedElementDialog.propertyNameField.setText("");
		drawedElementDialog.variableNameField.setText("");
		drawedElementDialog.findPropertyButton.setEnabled(Drawer.getInstance().getPersonnage()!=null);
		drawedElementDialog.setVisible(true);
	}
	
	private void initComponents(){
		angleSlider.setMajorTickSpacing(90);
		angleSlider.setMinorTickSpacing(10);
		angleSlider.setPaintLabels(true);
		angleSlider.setPaintTicks(true);
		angleSlider.setPaintTrack(true);
		angleSlider.addChangeListener(new ChangeAngleListener(angleLabel, angleSlider));
		
		fieldComboBox.addItem("Valeur");
		fieldComboBox.addItem("Nom");
		fieldComboBox.addItem("Valeur sans transformation");
		fieldComboBox.addItem("Nom sans transformation");
		fieldComboBox.addItem("Texte combiné");
		fieldComboBox.addItem("Total de points");
		fieldComboBox.addItem("Points restant");
		fieldComboBox.addItem("Points dépensés");
		
		findPropertyButton.addActionListener(new FindPropertyListener(this));
		
		colorButton.addActionListener(new ChooseColorListener(this));
	}
	
	private void build(){
		initComponents();
		setResizable(false);
		validationButton.addActionListener(this);
		
		GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup()
			.addGroup(layout.createSequentialGroup()
				.addContainerGap()
				.addGroup(layout.createParallelGroup()
					.addComponent(variableNameLabel)
					.addComponent(propertyNameLabel)
					.addComponent(fieldLabel)
					.addComponent(fontSizeLabel)
					.addComponent(fontLabel)
					.addComponent(colorLabel)
					.addComponent(boldLabel)
					.addComponent(italicLabel)
					.addComponent(angleLabel)
					.addComponent(substringLabel)
				)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup()
					.addComponent(variableNameField)
					.addComponent(propertyNameField)
					.addComponent(fieldComboBox)
					.addComponent(fontSizeComboBox)
					.addComponent(fontComboBox)
					.addComponent(colorButton)
					.addComponent(boldCheckBox)
					.addComponent(italicCheckBox)
					.addComponent(angleSlider)
					.addComponent(substringField)
				)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup()
					.addComponent(findPropertyButton)
				)
				.addContainerGap()
			)
			.addComponent(validationButton, GroupLayout.Alignment.CENTER)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addContainerGap()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(variableNameLabel)
				.addComponent(variableNameField)
			)
			.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(propertyNameLabel)
				.addComponent(propertyNameField,0,propertyNameField.getPreferredSize().height,propertyNameField.getPreferredSize().height)
				.addComponent(findPropertyButton)
			)
			.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(fieldLabel)
				.addComponent(fieldComboBox)
			)
			.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(fontSizeLabel)
				.addComponent(fontSizeComboBox)
			)
			.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(fontLabel)
				.addComponent(fontComboBox)
			)
			.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(colorLabel)
				.addComponent(colorButton)
			)
			.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(boldLabel)
				.addComponent(boldCheckBox)
			)
			.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(italicLabel)
				.addComponent(italicCheckBox)
			)
			.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(angleLabel)
				.addComponent(angleSlider)
			)
			.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(substringLabel)
				.addComponent(substringField)
			)
			.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
			.addComponent(validationButton)
			.addContainerGap()
		);
		
		pack();
	}

	public DrawedElement getDrawedElement() {
		drawedElement.setPropertyName(propertyNameField.getText());
		drawedElement.setVariableName(variableNameField.getText());
		drawedElement.setFont(FontManager.getFont(fontComboBox.getSelectedItem().toString()));
		drawedElement.setFontSize((Integer)fontSizeComboBox.getSelectedItem());
		drawedElement.setColor(getColor());
		drawedElement.setFontStyle((boldCheckBox.isSelected()?Font.BOLD:0)+(italicCheckBox.isSelected()?Font.ITALIC:0));
		drawedElement.setAngleInDegree(angleSlider.getValue());
		if(substringField.getText().matches("[0-9]+")){
			drawedElement.setSubstring(Integer.parseInt(substringField.getText()));
		}else{
			drawedElement.setSubstring(null);
		}
		if(fieldComboBox.getSelectedItem().equals("Valeur")){
			drawedElement.setType(DrawedElement.VALUE);
		}else if(fieldComboBox.getSelectedItem().equals("Nom")){
			drawedElement.setType(DrawedElement.NAME);
		}else if(fieldComboBox.getSelectedItem().equals("Valeur sans transformation")){
			drawedElement.setType(DrawedElement.BRUT_VALUE);
		}else if(fieldComboBox.getSelectedItem().equals("Nom sans transformation")){
			drawedElement.setType(DrawedElement.BRUT_NAME);
		}else if(fieldComboBox.getSelectedItem().equals("Texte combinée")){
			drawedElement.setType(DrawedElement.TEXT);
		}else if(fieldComboBox.getSelectedItem().equals("Total de points")){
			drawedElement.setType(DrawedElement.POINT_TOTAL);
		}else if(fieldComboBox.getSelectedItem().equals("Points restant")){
			drawedElement.setType(DrawedElement.REMAINING_POINTS);
		}else if(fieldComboBox.getSelectedItem().equals("Points dépensés")){
			drawedElement.setType(DrawedElement.SPEND_POINTS);
		}
		return drawedElement;
	}
	
	public void setDrawedElement(DrawedElement drawedElement) {
		this.drawedElement = drawedElement;
		variableNameField.setText(drawedElement.getVariableName());
		propertyNameField.setText(drawedElement.getPropertyName());
		fieldComboBox.setSelectedIndex(Arrays.asList(DrawedElement.VALUE, DrawedElement.NAME, DrawedElement.BRUT_VALUE,DrawedElement.BRUT_NAME,DrawedElement.TEXT,DrawedElement.POINT_TOTAL,DrawedElement.REMAINING_POINTS,DrawedElement.SPEND_POINTS).indexOf(drawedElement.getType()));
		
		fontComboBox.setSelectedItem(drawedElement.getFont().getFontName());
		fontSizeComboBox.setSelectedItem(drawedElement.getFontSize());
		boldCheckBox.setSelected(drawedElement.getFont().isBold());
		italicCheckBox.setSelected(drawedElement.getFont().isItalic());
		colorButton.setBackground(drawedElement.getColor());
		angleSlider.setValue(drawedElement.getAngleInDegree());
		substringField.setText(drawedElement.getSubstring()==null?"":drawedElement.getSubstring().toString());
		
		findPropertyButton.setEnabled(Drawer.getInstance().getPersonnage()!=null);
	}
	
	public void setPropertyName(String propertyName){
		propertyNameField.setText(propertyName);
	}
	
	public void setColor(Color color){
		colorButton.setBackground(color);
	}
	
	public Color getColor(){
		return colorButton.getBackground();
	}
	
	public String getFieldType(){
		return (String) fieldComboBox.getSelectedItem();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Drawer.getInstance().getDrawing().addDrawedElement(getDrawedElement());
		dispose();
		SwingUtilities.updateComponentTreeUI(Drawer.getInstance().getDrawing());
	}

	
	

}
