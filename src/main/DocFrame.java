package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.ScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;

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
	protected JMenuItem pastePopBtn;
	protected JMenuItem selectAllPopBtn;
	
	protected JMenuItem saveAsBtn;
	protected JMenuItem openBtn;
	protected JMenuItem newBtn;
	protected JMenuItem saveBtn;
	protected JMenuItem copyBtn;
	protected JMenuItem cutBtn;
	protected JMenuItem deleteBtn;
	protected JMenuItem pasteBtn;
	protected JMenuItem selectAllBtn;
	protected JMenuItem undoBtn;
	protected JMenuItem redoBtn;
	protected JMenuItem findBtn;
	protected JMenuItem settingsBtn;
	
	protected JDialog findDialog;
	protected JTextField findTextField;
	protected JButton findNextBtn;
	protected JButton findPrevBtn;
	
	protected JDialog settingsDialog;
	protected JButton applySettingsBtn;
	protected JButton cancelSettingsBtn;
	
	private ActionListener al;
	private KeyListener kl;
	private DocumentListener dl;
	private WindowListener wl;
	private CaretListener cl;
	private MouseListener ml;
	private UndoableEditListener uel;
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
		settingsBtn = new JMenuItem("Settings");
		saveBtn.setEnabled(false);
		fileMenu.add(newBtn);
		fileMenu.add(saveBtn);
		fileMenu.add(saveAsBtn);
		fileMenu.add(openBtn);
		fileMenu.add(settingsBtn);
		menuBar.add(fileMenu);
		
		JMenu editMenu = new JMenu("Edit");
		undoBtn = new JMenuItem("Undo");
		redoBtn = new JMenuItem("Redo");
		copyBtn = new JMenuItem("Copy");
		cutBtn = new JMenuItem("Cut");
		pasteBtn = new JMenuItem("Paste");
		deleteBtn = new JMenuItem("Delete");
		selectAllBtn = new JMenuItem("Select All");
		findBtn = new JMenuItem("Find");
		copyBtn.setEnabled(false);
		cutBtn.setEnabled(false);
		deleteBtn.setEnabled(false);
		undoBtn.setEnabled(false);
		redoBtn.setEnabled(false);
		editMenu.add(undoBtn);
		editMenu.add(redoBtn);
		editMenu.add(copyBtn);
		editMenu.add(cutBtn);
		editMenu.add(pasteBtn);
		editMenu.add(deleteBtn);
		editMenu.add(selectAllBtn);
		editMenu.add(findBtn);
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
		findDialog = createFindDialog();
		settingsDialog = createSettingsDialog();
	}
	public void setListeners(Controller controller) {
		this.al = controller.getActionController();
		this.kl = controller.getKeyController();
		this.dl = controller.getDocumentController();
		this.wl = controller.getWindowController();
		this.cl = controller.getCaretController();
		this.ml = controller.getMouseController();
		this.uel = controller.getUndoController();
		
		textPane.addKeyListener(kl);
		textPane.getDocument().addDocumentListener(dl);
		textPane.addCaretListener(cl);
		textPane.addMouseListener(ml);
		textPane.getDocument().addUndoableEditListener(uel);
		saveBtn.addActionListener(al);
		saveAsBtn.addActionListener(al);
		openBtn.addActionListener(al);
		newBtn.addActionListener(al);
		copyBtn.addActionListener(al);
		cutBtn.addActionListener(al);
		deleteBtn.addActionListener(al);
		pasteBtn.addActionListener(al);
		selectAllBtn.addActionListener(al);
		undoBtn.addActionListener(al);
		redoBtn.addActionListener(al);
		findBtn.addActionListener(al);
		settingsBtn.addActionListener(al);
		
		applySettingsBtn.addActionListener(al);
		cancelSettingsBtn.addActionListener(al);
		
		copyPopBtn.addActionListener(al);
		cutPopBtn.addActionListener(al);
		deletePopBtn.addActionListener(al);
		pastePopBtn.addActionListener(al);
		selectAllPopBtn.addActionListener(al);
		
		findPrevBtn.addActionListener(al);
		findNextBtn.addActionListener(al);
		
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
		pastePopBtn = new JMenuItem("Paste");
		deletePopBtn = new JMenuItem("Delete");
		selectAllPopBtn = new JMenuItem("Select All");
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
	private JDialog createFindDialog() {
		JDialog jd = new JDialog(this);
		findTextField = new JTextField(20);
		findPrevBtn = new JButton("<");
		findNextBtn = new JButton(">");
		jd.setLayout(new FlowLayout());
		jd.setTitle("Find");
		jd.setLocation(200,50);
		jd.add(findTextField);
		jd.add(findPrevBtn);
		jd.add(findNextBtn);
		jd.pack();
		return jd;
	}
	protected void updateSpellingStatus(boolean spellingOkay) {
		String spellingStatus;
		if(spellingOkay) {
			spellingStatus = "Spelling: OK!";
		}
		else {
			spellingStatus = "Spelling: errors";
		}
		spellingWarningTF.setText(spellingStatus);
	}
	private JDialog createSettingsDialog() {
		GridLayout tabSectionLayout = new GridLayout(4,1);
		JDialog jd = new JDialog(this);
		jd.setTitle("Settings");
		jd.setSize(450,300);
		JTabbedPane tp = new JTabbedPane();
		
		//General tab
		JPanel generalTab = getGeneralTab(tabSectionLayout);
		
		JPanel languageTab = new JPanel();
		languageTab.setLayout(tabSectionLayout);
		
		applySettingsBtn = new JButton("Apply");
		cancelSettingsBtn = new JButton("Cancel");
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(applySettingsBtn);
		buttonPanel.add(cancelSettingsBtn);
		
		tp.addTab("General", new JScrollPane(generalTab));
		tp.addTab("Language", new JScrollPane(languageTab));
		jd.setLayout(new BorderLayout());
		jd.add(tp, BorderLayout.CENTER);
		jd.add(buttonPanel, BorderLayout.SOUTH);
		
		return jd;
	}
	private JPanel getGeneralTab(GridLayout tabSectionLayout) {
		JPanel generalTab = new JPanel();
		generalTab.setLayout(tabSectionLayout);
		
		//Default Dict Panel - DDS
		JPanel defaultDictPanel = new JPanel();
		defaultDictPanel.setLayout(new GridLayout(2,1));
		defaultDictPanel.setBorder(BorderFactory.createTitledBorder("Default Dictionary"));
		
		JLabel ddsLab1 = new JLabel("Please set the default directory "
				+ "to save to and open files from.");
		JPanel ddsActionPanel = new JPanel();
		ddsActionPanel.setLayout(new FlowLayout());
		
		JTextField ddsTextField = new JTextField(30);
		JButton ddsBrowseBtn = new JButton("Browse");
		ddsActionPanel.add(ddsTextField);
		ddsActionPanel.add(ddsBrowseBtn);
		
		defaultDictPanel.add(ddsLab1);
		defaultDictPanel.add(ddsActionPanel);
		
		generalTab.add(defaultDictPanel);
		return generalTab;
	}
}
