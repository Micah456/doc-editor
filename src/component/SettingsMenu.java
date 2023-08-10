package component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.simple.JSONObject;

import main.DataHandler;

public class SettingsMenu extends JDialog implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ActionListener al;
	private JSONObject jsonSettings;
	private JTabbedPane tp;
	private DataHandler dh;
	private final GridLayout tabSectionLayout = new GridLayout(4,1,0,20);
	
	private JComboBox<String> ddCombo;
	private JComboBox<String> sdCombo;
	private JTextField adTextField;
	
	public SettingsMenu(JFrame parent, ActionListener al, DataHandler dh, JSONObject settings) throws Exception {
		super(parent);
		this.al = al;
		this.jsonSettings = settings;
		this.dh = dh;
		this.setTitle("Settings");
		this.setSize(450,300);
		tp = new JTabbedPane();
		buildTabs();
		JButton applySettingsBtn = new JButton("Apply");
		JButton cancelSettingsBtn = new JButton("Cancel");
		applySettingsBtn.addActionListener(al);
		applySettingsBtn.setActionCommand("SettingsMenu apply");
		cancelSettingsBtn.addActionListener(al);
		cancelSettingsBtn.setActionCommand("SettingsMenu cancel");

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(applySettingsBtn);
		buttonPanel.add(cancelSettingsBtn);
				
		this.setLayout(new BorderLayout());
		this.add(tp, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	private JPanel getLanguageTab() {
		JPanel languageTab = new JPanel();
		languageTab.setLayout(tabSectionLayout);
		
		//Default Dictionary Panel - DD
		JPanel defaultDictPanel = getSettingPanel("Default Dictionary",2,1);
		
		JLabel ddLabel = new JLabel("Select the default dictionary.");
		HashMap<String,ArrayList<String>> dictionaries = DataHandler.getDictionaries(DataHandler.dictDir);
		String[] dicts = (String[]) dictionaries.keySet().toArray(new String[dictionaries.size()]);
		String defaultDict = (String)jsonSettings.get("defaultDictionary");
		ddCombo = new JComboBox<>(dicts);
		int ddIndex = 0;
		for(int i = 0; i<dicts.length; i++) {
			if(dicts[i].equals(defaultDict)) {
				ddIndex = i;
				break;
			}
		}
		ddCombo.setSelectedIndex(ddIndex);
		
		defaultDictPanel.add(ddLabel);
		defaultDictPanel.add(ddCombo);
		
		//--------
		//Set Dictionary - SD
		JPanel setDictPanel = getSettingPanel("Set Current Dictionary",2,1);
		
		JLabel sdLabel = new JLabel("Select the current dictionary.");
		sdCombo = new JComboBox<>(dicts);
		String currDictName = this.dh.getCurrDictName();
		int sdIndex = 0;
		for(int i = 0; i<dicts.length; i++) {
			if(dicts[i].equals(currDictName)) {
				sdIndex = i;
				break;
			}
		}
		sdCombo.setSelectedIndex(sdIndex);
		
		setDictPanel.add(sdLabel);
		setDictPanel.add(sdCombo);
		
		//--------
		
		//Add Dictionary - AD - requires JFileChooser
		JPanel addDictPanel = getSettingPanel("Add Dictionary",2,1);
		
		JLabel adLabel = new JLabel("Add a new dictionary reference file.");
		JPanel adPanel = new JPanel();
		adPanel.setLayout(new FlowLayout());
		adTextField = new JTextField(25);
		adTextField.setEnabled(false);
		JButton adBrowseBtn = new JButton("Browse");
		adBrowseBtn.addActionListener(this);
		adBrowseBtn.setActionCommand("AD - Browse");
		JButton adClearBtn = new JButton("Clear");
		adClearBtn.addActionListener(this);
		adClearBtn.setActionCommand("AD - Clear");
		
		adPanel.add(adTextField);
		adPanel.add(adBrowseBtn);
		adPanel.add(adClearBtn);
		
		addDictPanel.add(adLabel);
		addDictPanel.add(adPanel);
		//--------
		
		//Ignored Words - IW - consider using JList, add and remove
		//btns and scrollpane
		JPanel ignoredWordsPanel = getSettingPanel("Ignored Words",2,1);
		//--------
		
		languageTab.add(defaultDictPanel);
		languageTab.add(setDictPanel);
		languageTab.add(addDictPanel);
		languageTab.add(ignoredWordsPanel);
		return languageTab;
	}
	
	public void buildTabs() throws Exception {
		if(this.al == null) {
			throw new Exception("Action Listener Required.");
		}
		else if(this.jsonSettings == null) {
			throw new Exception("JSON Settings Required.");
		}
		tp.addTab("Language", new JScrollPane(getLanguageTab()));
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("AD - Browse")) {
			adBrowse();
		}
		else if(e.getActionCommand().equals("AD - Clear")) {
			adTextField.setText("");
		}
		
	}
	private JPanel getSettingPanel(String title, int rows, int cols) {
		JPanel settingPanel = new JPanel();
		settingPanel.setLayout(new GridLayout(rows,cols));
		LineBorder lb = new LineBorder(Color.black, 2, true);
		settingPanel.setBorder(new TitledBorder(lb, title));
		return settingPanel;
	}
	private void adBrowse() {
		JFileChooser fc = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter("Text Documents", "txt");
		fc.setFileFilter(filter);
		int a = fc.showDialog(this, "Select");
		if(a == JFileChooser.APPROVE_OPTION) {
			adTextField.setText(fc.getSelectedFile().getName());
		}
	}
}
