package com.mrprez.gencross.drawer;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.GroupLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mrprez.gencross.Personnage;
import com.mrprez.gencross.drawer.drawing.Drawing;
import com.mrprez.gencross.drawer.element.DrawedElement;
import com.mrprez.gencross.drawer.error.ErrorFrame;
import com.mrprez.gencross.drawer.font.FontManager;
import com.mrprez.gencross.drawer.framework.FileChooser;
import com.mrprez.gencross.drawer.menu.LoadFontListener;
import com.mrprez.gencross.drawer.menu.LoadImageListener;
import com.mrprez.gencross.drawer.menu.LoadPersonnageListener;
import com.mrprez.gencross.drawer.menu.OpenListener;
import com.mrprez.gencross.drawer.menu.SaveListener;

public class Drawer extends JFrame implements Runnable {
	private static final long serialVersionUID = 1L;
	public final static String PERSONNAGE_REPOSITORY_NAME = "repository";
	public final static String FONT_REPOSITORY_NAME = "polices";
	private static Drawer instance;
	
	private JScrollPane scrollPane;
	private JMenuBar menuBar;
	private Drawing drawing;
	private Personnage personnage;
	private JToolBar toolBar;
	private JLabel xLabel;
	private JLabel yLabel;
	private FileChooser fileChooser = new FileChooser();
	
	
	public static void main(String[] args) {
		instance = new Drawer();
		SwingUtilities.invokeLater(instance);
	}

	@Override
	public void run() {
		try {
			FontManager.loadFonts(new File(getExecutionDirectory().getParentFile(),FONT_REPOSITORY_NAME));
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			setTitle("GenCross Drawer");
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			Rectangle desktop = ge.getMaximumWindowBounds();
			drawing = new Drawing(desktop.width, desktop.height);
			scrollPane = new JScrollPane(drawing);
			scrollPane.getVerticalScrollBar().setUnitIncrement(20);
			initToolBar();
			initFrame();
			initMenu();
			setVisible(true);
			setExtendedState(MAXIMIZED_BOTH);
		} catch (Exception e) {
			ErrorFrame.displayError(e);
		}
		
	}
	
	public static File getExecutionDirectory() throws URISyntaxException{
		URL jarUrl = Drawer.class.getProtectionDomain().getCodeSource().getLocation();
		File jarFile = new File(jarUrl.toURI());
		return jarFile.getParentFile();
	}
	
	public void initFrame(){
		GroupLayout layout = new GroupLayout(getContentPane());
		this.getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup()
			.addComponent(scrollPane)
			.addComponent(toolBar)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addComponent(scrollPane)
			.addComponent(toolBar)
		);
		pack();	
	}
	
	public void initMenu(){
		menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("Fichier");
		menuBar.add(fileMenu);
		
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("GenCross - Editor", "gcd"));
		
		JMenuItem openMenuItem = new JMenuItem("Ouvrir");
		openMenuItem.addActionListener(new OpenListener(fileChooser));
		fileMenu.add(openMenuItem);
		
		JMenuItem exportItem = new JMenuItem("Enregistrer");
		exportItem.addActionListener(new SaveListener(fileChooser));
		fileMenu.add(exportItem);
		
		JMenuItem loadImageMenuItem = new JMenuItem("Charger une image de fond");
		loadImageMenuItem.addActionListener(new LoadImageListener());
		fileMenu.add(loadImageMenuItem);
		
		JMenuItem loadPersonnageItem = new JMenuItem("Charger un personnage");
		loadPersonnageItem.addActionListener(new LoadPersonnageListener());
		fileMenu.add(loadPersonnageItem);
		
		JMenuItem loadFontItem = new JMenuItem("Charger une police");
		loadFontItem.addActionListener(new LoadFontListener());
		fileMenu.add(loadFontItem);
		
		this.setJMenuBar(menuBar);
	}
	
	public void initToolBar(){
		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		xLabel = new JLabel();
		yLabel = new JLabel();
		toolBar.add(xLabel);
		toolBar.addSeparator();
		toolBar.add(yLabel);
	}
	
	public void reinitToolBar(){
		DrawedElement drawedElement = drawing.getSelectedDrawedElement();
		if(drawedElement!=null){
			xLabel.setText("x: "+drawedElement.getX());
			yLabel.setText("y: "+drawedElement.getY());
		}
	}

	public static Drawer getInstance() {
		return instance;
	}
	public static void setInstance(Drawer instance) {
		Drawer.instance = instance;
	}

	public Drawing getDrawing() {
		return drawing;
	}
	public void setDrawing(Drawing drawing) {
		this.drawing = drawing;
		scrollPane.setViewportView(drawing);
	}

	public Personnage getPersonnage() {
		return personnage;
	}
	public void setPersonnage(Personnage personnage) {
		this.personnage = personnage;
	}

	public JFileChooser getFileChooser() {
		return fileChooser;
	}
	

}
