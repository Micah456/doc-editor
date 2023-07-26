package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextPane;
import javax.swing.event.CaretListener;
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
	
	protected JPopupMenu popupMenu;
	protected JMenuItem copyPopBtn;
	protected JMenuItem cutPopBtn;
	protected JMenuItem deletePopBtn;
	
	protected JMenuItem saveAsBtn;
	protected JMenuItem openBtn;
	protected JMenuItem newBtn;
	protected JMenuItem saveBtn;
	protected JMenuItem copyBtn;
	protected JMenuItem cutBtn;
	protected JMenuItem deleteBtn;
	
	private ActionListener al;
	private KeyListener kl;
	private DocumentListener dl;
	private WindowListener wl;
	private CaretListener cl;
	private MouseListener ml;
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
		copyBtn = new JMenuItem("Copy");
		cutBtn = new JMenuItem("Cut");
		JMenuItem pasteEditItem = new JMenuItem("Paste");
		deleteBtn = new JMenuItem("Delete");
		copyBtn.setEnabled(false);
		cutBtn.setEnabled(false);
		deleteBtn.setEnabled(false);
		editMenu.add(undoEditItem);
		editMenu.add(redoEditItem);
		editMenu.add(copyBtn);
		editMenu.add(cutBtn);
		editMenu.add(pasteEditItem);
		editMenu.add(deleteBtn);
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
		popupMenu = createPopupMenu();
	}
	public void setListeners(Controller controller) {
		this.al = controller.getActionController();
		this.kl = controller.getKeyController();
		this.dl = controller.getDocumentController();
		this.wl = controller.getWindowController();
		this.cl = controller.getCaretController();
		this.ml = controller.getMouseController();
		
		textPane.addKeyListener(kl);
		textPane.getDocument().addDocumentListener(dl);
		textPane.addCaretListener(cl);
		textPane.addMouseListener(ml);
		saveBtn.addActionListener(al);
		saveAsBtn.addActionListener(al);
		openBtn.addActionListener(al);
		newBtn.addActionListener(al);
		copyBtn.addActionListener(al);
		cutBtn.addActionListener(al);
		deleteBtn.addActionListener(al);
		
		copyPopBtn.addActionListener(al);
		cutPopBtn.addActionListener(al);
		deletePopBtn.addActionListener(al);
		
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
	private JPopupMenu createPopupMenu() {
		JPopupMenu pm = new JPopupMenu();
		cutPopBtn = new JMenuItem("Cut");
		copyPopBtn = new JMenuItem("Copy");
		JMenuItem pastePopBtn = new JMenuItem("Paste");
		deletePopBtn = new JMenuItem("Delete");
		JMenuItem selectAllPopBtn = new JMenuItem("Select All");
		cutPopBtn.setEnabled(false);
		copyPopBtn.setEnabled(false);
		deletePopBtn.setEnabled(false);
		pm.add(cutPopBtn);
		pm.add(copyPopBtn);
		pm.add(pastePopBtn);
		pm.add(deletePopBtn);
		pm.add(selectAllPopBtn);
		return pm;
	}
}
