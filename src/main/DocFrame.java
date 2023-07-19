package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class DocFrame extends JFrame{
	private final int width = 600;
	private final int height = 500;
	private JTextArea mainTA;
	private JMenuBar menuBar;
	private JPanel statusBar;
	private JLabel linesTF;
	private JLabel wordsTF;
	private JLabel spellingWarningTF;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DocFrame() {
		this.setTitle("Word Processor");
        //this.setIconImage();
        this.setSize(width, height);
        this.setLocation(0,0);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        buildLayout();
	}
	private void buildLayout() {
		mainTA = new JTextArea();
		this.add(mainTA, BorderLayout.CENTER);
		
		menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem newFileItem = new JMenuItem("New");
		JMenuItem saveFileItem = new JMenuItem("Save");
		JMenuItem openFileItem = new JMenuItem("Open");
		fileMenu.add(newFileItem);
		fileMenu.add(saveFileItem);
		fileMenu.add(openFileItem);
		menuBar.add(fileMenu);
		
		JMenu editMenu = new JMenu("Edit");
		JMenuItem undoEditItem = new JMenuItem("Undo");
		JMenuItem redoEditItem = new JMenuItem("Redo");
		JMenuItem copyEditItem = new JMenuItem("Copy");
		JMenuItem pasteEditItem = new JMenuItem("Paste");
		editMenu.add(undoEditItem);
		editMenu.add(redoEditItem);
		editMenu.add(copyEditItem);
		editMenu.add(pasteEditItem);
		menuBar.add(editMenu);

		this.setJMenuBar(menuBar);
		
		statusBar = new JPanel();
		statusBar.setLayout(new FlowLayout());
		linesTF = new JLabel("Lines: 0");
		wordsTF = new JLabel("Words: 0");
		spellingWarningTF = new JLabel("Spelling: OK!");
		statusBar.add(linesTF);
		statusBar.add(wordsTF);
		statusBar.add(spellingWarningTF);
		this.add(statusBar, BorderLayout.SOUTH);
	}
}
