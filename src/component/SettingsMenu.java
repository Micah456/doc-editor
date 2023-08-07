package component;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.json.simple.JSONObject;

public class SettingsMenu extends JDialog implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ActionListener al;
	private JSONObject jsonSettings;
	private JTabbedPane tp;
	private final GridLayout tabSectionLayout = new GridLayout(4,1);
	
	private JTextField ddTextField;
	
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
		JPanel defaultDictPanel = new JPanel();
		defaultDictPanel.setLayout(new GridLayout(2,1));
		defaultDictPanel.setBorder(BorderFactory.createTitledBorder("Default Dictionary"));
		
		JLabel ddLabel = new JLabel("Select the default dictionary.");
		JPanel ddActionPanel = new JPanel();
		ddActionPanel.setLayout(new FlowLayout());
		
		ddTextField = new JTextField(30);
		ddTextField.setText((String)jsonSettings.get("defaultDictionary"));
		JButton ddBrowseBtn = new JButton("Browse");
		ddBrowseBtn.addActionListener(this);
		ddBrowseBtn.setActionCommand("ddBrowseBtn");
		ddActionPanel.add(ddTextField);
		ddActionPanel.add(ddBrowseBtn);
		
		defaultDictPanel.add(ddLabel);
		defaultDictPanel.add(ddActionPanel);
		
		languageTab.add(defaultDictPanel);		
		return languageTab;
	}
	
	public void buildTabs() throws Exception {
		if(this.al == null) {
			throw new Exception("Action Listener Required.");
		}
		else if(this.jsonSettings == null) {
			throw new Exception("JSON Settings Required.");
		}
		//tp.addTab("General", new JScrollPane(getGeneralTab()));
		tp.addTab("Language", new JScrollPane(getLanguageTab()));
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand() == "ddBrowseBtn") {
			System.out.println("To implement browse for dd");
		}
	}
}
