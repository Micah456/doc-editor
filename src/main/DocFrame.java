package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.event.DocumentListener;

public class DocFrame extends JFrame{
	private final int width = 600;
	private final int height = 500;
	private final String title = "Word Processor";
	protected JTextPane textPane;
	private JMenuBar menuBar;
	private JPanel statusBar;
	private JLabel linesTF;
	private JLabel wordsTF;
	private JLabel spellingWarningTF;
	
	protected JMenuItem saveAsBtn;
	protected JMenuItem openBtn;
	protected JMenuItem newBtn;
	protected JMenuItem saveBtn;
	
	private ActionListener al;
	private KeyListener kl;
	private DocumentListener dl;
	private WindowListener wl;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DocFrame() {
		this.setTitle(this.title);
        //this.setIconImage();
        this.setSize(width, height);
        this.setLocation(0,0);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLayout(new BorderLayout());
        buildLayout();
	}
	private void buildLayout() {
		textPane = new JTextPane();
		this.add(textPane, BorderLayout.CENTER);
		
		menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		newBtn = new JMenuItem("New");
		saveAsBtn = new JMenuItem("Save As");
		openBtn = new JMenuItem("Open");
		saveBtn = new JMenuItem("Save");
		saveBtn.setEnabled(false);
		fileMenu.add(newBtn);
		fileMenu.add(saveBtn);
		fileMenu.add(saveAsBtn);
		fileMenu.add(openBtn);
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
	public void setListeners(Controller controller) {
		this.al = controller.getActionController();
		this.kl = controller.getKeyController();
		this.dl = controller.getDocumentController();
		this.wl = controller.getWindowController();
		
		textPane.addKeyListener(kl);
		textPane.getDocument().addDocumentListener(dl);
		saveBtn.addActionListener(al);
		saveAsBtn.addActionListener(al);
		openBtn.addActionListener(al);
		newBtn.addActionListener(al);
		this.addWindowListener(wl);
	}
	protected void updateCounts(int lineCount, int wordCount) {
		linesTF.setText("Lines: " + lineCount);
		wordsTF.setText("Words: " + wordCount);
		textPane.invalidate();
	}
	protected void openFileInDocFrame(String text, String fileName) {
		textPane.setText(text);
		updateTitle(fileName, false);
		this.invalidate();
	}
	protected void updateTitle(String fileName, boolean invalidate) {
		this.setTitle(this.title + " - " + fileName);
		if(invalidate) {
			this.invalidate();
		}
	}
	protected void newFile() {
		this.textPane.setText("");
		this.setTitle(title);
	}
}
