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
	private final GridLayout tabSectionLayout = new GridLayout(4,1,0,20);
	
	private JComboBox<String> ddCombo;
	
	public SettingsMenu(JFrame parent, ActionListener al, JSONObject settings) throws Exception {
		super(parent);
		this.al = al;
		this.jsonSettings = settings;
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
		//--------
		
		//Add Dictionary - AD - requires JFileChooser
		JPanel addDictPanel = getSettingPanel("Add Dictionary",2,1);
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
		
	}
	private JPanel getSettingPanel(String title, int rows, int cols) {
		JPanel settingPanel = new JPanel();
		settingPanel.setLayout(new GridLayout(rows,cols));
		LineBorder lb = new LineBorder(Color.black, 2, true);
		settingPanel.setBorder(new TitledBorder(lb, title));
		return settingPanel;
	}
}
