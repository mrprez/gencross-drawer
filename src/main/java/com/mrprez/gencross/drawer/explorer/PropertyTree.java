package com.mrprez.gencross.drawer.explorer;

import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.mrprez.gencross.Personnage;
import com.mrprez.gencross.Property;

public class PropertyTree extends JTree {
	private static final long serialVersionUID = 1L;
	private Personnage personnage;
	private DefaultMutableTreeNode top;


	public PropertyTree(Personnage personnage){
		super(new DefaultMutableTreeNode("Propriétés"));
		this.personnage = personnage;
		top = (DefaultMutableTreeNode) this.getModel().getRoot();
		setBorder(BorderFactory.createLoweredBevelBorder());
		Iterator<Property> it = this.personnage.iterator();
		while(it.hasNext()){
			top.add(buildNode(it.next()));
		}
		setRootVisible(false);
		expandPath(new TreePath(top.getPath()));
	}
	
	public DefaultMutableTreeNode buildNode(Property property){
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(property);
		if(property.getSubProperties()!=null){
			Iterator<Property> it = property.iterator();
			while(it.hasNext()){
				node.add(buildNode(it.next()));
			}
		}
		return node;
	}
	
	
	
	@Override
	public String convertValueToText(Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		if(value instanceof DefaultMutableTreeNode){
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
			if(node.getUserObject() instanceof Property){
				Property property = (Property)node.getUserObject();
				if(property.getValue()!=null){
					return property.getRenderer().displayName(property)+" : "+property.getRenderer().displayValue(property);	
				}else{
					return property.getRenderer().displayName(property);
				}
			}
		}
		return super.convertValueToText(value, selected, expanded, leaf, row, hasFocus);
	}
	
	
	public void expandAll(DefaultMutableTreeNode node){
		this.expandPath(new TreePath(node.getPath()));
		for(int i=0; i<node.getChildCount();i++){
			expandAll((DefaultMutableTreeNode)node.getChildAt(i));
		}
	}
	
	public void selectTreePath(String absoluteName){
		
		
		
	}

}
