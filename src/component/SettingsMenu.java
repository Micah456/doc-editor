package component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import main.Controller;
import main.DataHandler;

public class SettingsMenu extends JDialog implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller c;
	private JSONObject jsonSettings;
	private JTabbedPane tp;
	private DataHandler dh;
	private final GridLayout tabSectionLayout = new GridLayout(4,1,0,20);
	
	private JComboBox<String> ddCombo;
	private JComboBox<String> sdCombo;
	private JTextField adTextField;
	
	private JList<String> iwWordList;
	private JTextField iwTextField;
	private DefaultListModel<String> iwListModel;
	
	public SettingsMenu(JFrame parent, Controller c, DataHandler dh, JSONObject settings) throws Exception {
		super(parent);
		this.c = c;
		this.jsonSettings = settings;
		this.dh = dh;
		this.setTitle("Settings");
		this.setSize(450,300);
		tp = new JTabbedPane();
		buildTabs();
		JButton applySettingsBtn = new JButton("Apply");
		JButton cancelSettingsBtn = new JButton("Cancel");
		applySettingsBtn.addActionListener(this);
		applySettingsBtn.setActionCommand("SettingsMenu - apply");
		cancelSettingsBtn.addActionListener(this);
		cancelSettingsBtn.setActionCommand("SettingsMenu - cancel");

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
		//JPanel ignoredWordsPanel = getSettingPanel("Ignored Words",3,1);
		JPanel ignoredWordsPanel = new JPanel();
		ignoredWordsPanel.setLayout(new BorderLayout());
		LineBorder lb = new LineBorder(Color.black, 2, true);
		ignoredWordsPanel.setBorder(new TitledBorder(lb, "Ignored Words"));
		//TODO make sure to load existing ignored words
		//Structure:JTextfield + add button
		//			JList of words inside scrollpane
		//			Remove button (click word in JList and press delete
		JPanel iwPanel1 = new JPanel();
		iwPanel1.setLayout(new FlowLayout());
		
		
		iwTextField = new JTextField(25);
		JButton iwAddBtn = new JButton("Add Word");
		iwAddBtn.addActionListener(this);
		iwAddBtn.setActionCommand("IW - Add");
		iwPanel1.add(iwTextField);
		iwPanel1.add(iwAddBtn);
				
		iwListModel = new DefaultListModel<>();
		ArrayList<String> ignoredWords = this.dh.getIgnoredWords();
		for(int i = 0; i < ignoredWords.size(); i++) {
			iwListModel.addElement(ignoredWords.get(i));
		}
		iwWordList = new JList<String>(iwListModel);
		
		JButton iwRemoveBtn = new JButton("Remove Word");
		iwRemoveBtn.addActionListener(this);
		iwRemoveBtn.setActionCommand("IW - Remove");
				
		ignoredWordsPanel.add(iwPanel1, BorderLayout.NORTH);
		JScrollPane sp = new JScrollPane(iwWordList);
		sp.setPreferredSize(new Dimension(400,40));
		ignoredWordsPanel.add(sp, BorderLayout.CENTER);
		ignoredWordsPanel.add(iwRemoveBtn, BorderLayout.SOUTH);
		
		//--------
		
		languageTab.add(defaultDictPanel);
		languageTab.add(setDictPanel);
		languageTab.add(addDictPanel);
		languageTab.add(ignoredWordsPanel);
		return languageTab;
	}
	
	public void buildTabs() throws Exception {
		if(this.c == null) {
			throw new Exception("Controller Required.");
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
		else if(e.getActionCommand().equals("IW - Add")) {
			addIgnoredWord();
		}
		else if(e.getActionCommand().equals("IW - Remove")) {
			removeIgnoredWord();
		}
		else if(e.getActionCommand().equals("SettingsMenu - apply")) {
			makeChanges();
			this.dispose();
		}
		else if(e.getActionCommand().equals("SettingsMenu - cancel")) {
			this.dispose();
		}
	}
	private void addIgnoredWord() {
		System.out.println("Adding ignored word.");
		String wordToAdd = iwTextField.getText();
		if(wordToAdd.isEmpty() || wordToAdd.isBlank()) {
			System.out.println("No word to add");
			return;
		}
		wordToAdd = wordToAdd.replaceAll("\\s+","");
		if(!existsInModel(iwListModel, wordToAdd)) {
			iwListModel.addElement(wordToAdd);
			iwTextField.setText("");
		}
		else {
			System.out.println("Word already in ignore list");
		}
		
	}
	private void removeIgnoredWord() {
		System.out.println("Removing ignored word.");
		int index = iwWordList.getSelectedIndex();
		if(index == -1) {
			System.out.println("No word selected");
			return;
		}
		iwListModel.remove(index);
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
			adTextField.setText(fc.getSelectedFile().getAbsolutePath());
		}
	}
	private boolean existsInModel(DefaultListModel<String> model, String word) {
		for(int i = 0; i < model.getSize(); i++) {
			if(model.get(i).equals(word)) {
				return true;
			}
		}
		return false;
	}
	private void makeChanges() {
		//TODO this method will call other methods to make
		//The appropriate changes
		//For now, link to makeLanguageChanges
		makeLanguageChanges();
		System.out.println(jsonSettings.toJSONString());
		if(dh.updateSettingsFile(jsonSettings.toJSONString())) {
			dh.refreshAppSettings();
			this.c.showWarning("Settings Updated Successfully");
		}
		else {
			this.c.showWarning("Settings Not Updated: an error has occurred.");
		}
		
	}
	
	private void makeLanguageChanges() {
		//Make changes to setting file
		jsonSettings.replace("defaultDictionary", ddCombo.getSelectedItem().toString());
		JSONArray ja = new JSONArray();
		for(int i = 0; i < iwListModel.getSize(); i++) {
			ja.add(iwListModel.get(i));
		}
		jsonSettings.replace("ignoredWords", ja);
		//Change current dictionary
		dh.setDictionary((String)sdCombo.getSelectedItem());
		//Add dictionary
		String dictToAdd = adTextField.getText();
		if(!dictToAdd.isBlank() && !dictToAdd.isEmpty()) {
			dh.addDictionary(dictToAdd);
		}
	}
}
