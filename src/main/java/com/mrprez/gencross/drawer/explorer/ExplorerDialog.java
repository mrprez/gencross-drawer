package com.mrprez.gencross.drawer.explorer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import com.mrprez.gencross.Personnage;
import com.mrprez.gencross.Property;
import com.mrprez.gencross.drawer.Drawer;
import com.mrprez.gencross.drawer.element.DrawedElementDialog;

public class ExplorerDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JScrollPane treeView = new JScrollPane();
	private PropertyTree propertyTree;
	private JButton validationButton;
	private DrawedElementDialog drawedElementDialog;
	
	
	public ExplorerDialog(DrawedElementDialog drawedElementDialog){
		super(drawedElementDialog, true);
		this.drawedElementDialog = drawedElementDialog;
		propertyTree = new PropertyTree(Drawer.getInstance().getPersonnage());
		treeView.setViewportView(propertyTree);
		validationButton = new JButton("Valider");
		validationButton.addActionListener(this);
		
		GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
			.addGroup(layout.createSequentialGroup()
				.addContainerGap()
				.addComponent(treeView)
				.addContainerGap()
			)
			.addGroup(layout.createSequentialGroup()
				.addComponent(validationButton)
			)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addContainerGap()
			.addComponent(treeView)
			.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
			.addComponent(validationButton)
			.addContainerGap()
		);
		
		pack();
	}
	
	public Property getSelectedProperty(){
		return (Property) ((DefaultMutableTreeNode) propertyTree.getSelectionPath().getLastPathComponent()).getUserObject();
	}
	
	public void setPersonnage(Personnage personnage){
		propertyTree = new PropertyTree(personnage);
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(getSelectedProperty()!=null){
			drawedElementDialog.setPropertyName(getPropertyPath(getSelectedProperty()));
			dispose();
		}else{
			JOptionPane.showMessageDialog(this, "Vous n'avez sélectionné aucune propriété");
		}
	}
	
	private String getPropertyPath(Property property){
		String names[] = property.getAbsoluteName().split("#");
		StringBuilder result = new StringBuilder(names[0]);
		Property current = property.getPersonnage().getProperty(result.toString());
		for(int i=1; i<names.length; i++){
			int index = getSubPropertyIndex(current, names[i]);
			result.append("#@").append(""+(1+index));
			current = current.getSubProperty(names[i]);
		}
		return result.toString();
	}
	
	private int getSubPropertyIndex(Property property, String subPropertyFullName){
		int index=0;
		for(Property subProperty : property.getSubProperties()){
			if(subProperty.getFullName().equals(subPropertyFullName)){
				return index;
			}
			index++;
		}
		return -1;
	}
	
	public void setSelectedProperty(String absoluteName){
		TreeModel treeModel = propertyTree.getModel();
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeModel.getRoot();
		String namesTab[] = absoluteName.split("#");
		DefaultMutableTreeNode tempNode = node;
		for(int i=0; i<namesTab.length && tempNode!=null; i++){
			String name=namesTab[i];
			tempNode = getNextNode(node, name);
			if(tempNode!=null){
				node = tempNode;
			}
		}
		propertyTree.setSelectionPath(new TreePath(node.getPath()));
	}
	
	private DefaultMutableTreeNode getNextNode(DefaultMutableTreeNode node,String name){
		if(name.startsWith("@")){
			if(!name.substring(1).matches("[0-9]+")){
				return null;
			}
			int childCount = Integer.parseInt(name.substring(1))-1;
			return (DefaultMutableTreeNode) node.getChildAt(childCount);
		}else{
			for(int childCount=0; childCount<node.getChildCount(); childCount++){
				DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)node.getChildAt(childCount);
				if(((Property)currentNode.getUserObject()).getFullName().equals(name)){
					return currentNode;
				}
			}
		}
		return null;
	}

}
