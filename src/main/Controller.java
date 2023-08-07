package main;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import document.Document;

public class Controller {
	private ActionController ac;
	private KeyController kc;
	private DocumentController dc;
	private WindowController wc;
	private CaretController cc;
	private MouseController mc;
	private UndoController uc;
	private DocFrame docFrame;
	private DataHandler dataHandler;
	private JSONObject appSettings;
	private String appSettingsFileName = "data/appSettings.json";
	
	public Controller(DocFrame docFrame, DataHandler dataHandler) {
		this.kc = new KeyController();
		this.ac = new ActionController();
		this.dc = new DocumentController();
		this.wc = new WindowController();
		this.cc = new CaretController();
		this.mc = new MouseController();
		this.uc = new UndoController();
		this.docFrame = docFrame;
		this.dataHandler = dataHandler;
		this.appSettings = dataHandler.getAppSettings(appSettingsFileName);
		dataHandler.applyAppSettings(this.appSettings);
		
	}
	public class ActionController implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == docFrame.saveAsBtn) {
				saveAs();
			}
			else if(e.getSource() == docFrame.openBtn) {
				open();
			}
			else if(e.getSource() == docFrame.newBtn) {
				newFile();
			}
			else if(e.getSource() == docFrame.saveBtn) {
				save();
			}
			else if(e.getSource() == docFrame.copyBtn ||
					e.getSource() == docFrame.copyPopBtn) {
				dataHandler.copySelected(docFrame.textPane);
			}
			else if(e.getSource() == docFrame.cutBtn || 
					e.getSource() == docFrame.cutPopBtn){
				dataHandler.cutSelected(docFrame.textPane);
			}
			else if(e.getSource() == docFrame.deleteBtn || 
					e.getSource() == docFrame.deletePopBtn){
				dataHandler.deleteSelected(docFrame.textPane);
			}
			else if(e.getSource() == docFrame.pasteBtn || 
					e.getSource() == docFrame.pastePopBtn){
				dataHandler.paste(docFrame.textPane);
			}
			else if(e.getSource() == docFrame.selectAllBtn || 
					e.getSource() == docFrame.selectAllPopBtn){
				selectAll(docFrame.textPane);
			}
			else if(e.getSource() == docFrame.undoBtn){
				undo();
			}
			else if(e.getSource() == docFrame.redoBtn){
				redo();
			}
			else if(e.getSource() == docFrame.findBtn) {
				openFind();
			}
			else if(e.getSource() == docFrame.findNextBtn) {
				find(true);
			}
			else if(e.getSource() == docFrame.findPrevBtn) {
				find(false);
			}
			else if(e.getSource() == docFrame.settingsBtn) {
				openSettings();
			}
		}
	}
	public class KeyController implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == docFrame.textPane &&
					e.getKeyCode() == 525) {//Popup menu key
				docFrame.popupMenu.show(docFrame , docFrame.getWidth()/2, docFrame.getHeight()/2 - 40); 
			}
			else if(e.getKeyCode() == 90 && e.isControlDown()){
				undo();
			}
			else if(e.getKeyCode() == 89 && e.isControlDown()){
				redo();
			}
			else if(e.getKeyCode() == 70 && e.isControlDown()) {
				openFind();
			}
		}
		
	}
	public class DocumentController implements DocumentListener{

		@Override
		public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			updateStats();
			dataHandler.updateFileUpdateStatus(true);
			enableSaveBtn();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			updateStats();
			dataHandler.updateFileUpdateStatus(true);
			enableSaveBtn();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			updateStats();
			dataHandler.updateFileUpdateStatus(true);
			enableSaveBtn();
		}
		
	}
	public class WindowController implements WindowListener{

		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
			if(dataHandler.fileUpdated) {
				switch (showUnsavedWarning()) {
					case JOptionPane.OK_OPTION:
						System.out.println("Save!");
						save();
						break;
					case JOptionPane.NO_OPTION:
						System.out.println("Don't save");
						break;
					default:
						System.out.println("Cancel");
						return;
				}
			}
			System.out.println("Closing...");
			docFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		}

		@Override
		public void windowClosed(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	public class CaretController implements CaretListener {

		@Override
		public void caretUpdate(CaretEvent e) {
			// TODO Auto-generated method stub
			if(e.getDot() != e.getMark()) {
				docFrame.copyBtn.setEnabled(true);
				docFrame.copyPopBtn.setEnabled(true);
				docFrame.cutBtn.setEnabled(true);
				docFrame.cutPopBtn.setEnabled(true);
				docFrame.deleteBtn.setEnabled(true);
				docFrame.deletePopBtn.setEnabled(true);
			}
			else {
				docFrame.copyBtn.setEnabled(false);
				docFrame.copyPopBtn.setEnabled(false);
				docFrame.cutBtn.setEnabled(false);
				docFrame.cutPopBtn.setEnabled(false);
				docFrame.deleteBtn.setEnabled(false);
				docFrame.deletePopBtn.setEnabled(false);
			}
		}
	}
	public class MouseController implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == docFrame.textPane &&
					e.getButton() == 3) {// Right click
				docFrame.popupMenu.show(docFrame , e.getX(), e.getY()+40);
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	public class UndoController implements UndoableEditListener{

		@Override
		public void undoableEditHappened(UndoableEditEvent e) {
			// TODO Auto-generated method stub
			System.out.println("Undoable edit happened");
			docFrame.undoBtn.setEnabled(true);
			dataHandler.um.addEdit(e.getEdit());
		}	
	}
	
	public ActionController getActionController() {
		return this.ac;
	}
	public KeyController getKeyController() {
		return this.kc;
	}
	public DocumentController getDocumentController() {
		return this.dc;
	}
	public WindowController getWindowController() {
		return this.wc;
	}
	public CaretController getCaretController() {
		return this.cc;
	}
	public MouseController getMouseController() {
		return this.mc;
	}
	public UndoController getUndoController() {
		return this.uc;
	}
	private void updateStats() {
		Document d = new Document(docFrame.textPane.getText());
		ArrayList<String> dictionary = dataHandler.getCurrDictionary();
		int[] counts = calculateCounts(d);
		boolean spellingOkay = isSpellingOkay(d, dictionary);
		docFrame.updateCounts(counts[0], counts[1]);
		docFrame.updateSpellingStatus(spellingOkay);
	}
	private static boolean isSpellingOkay(Document d, ArrayList<String> dict) {
		d.runSpellCheck(dict);
		return d.getSpellingErrors().size() == 0;
	}
	/**
	 * Calculates the line and word counts for a given JTextPane
	 * @param textPane JTextPane containing text to analyse
	 * @return int[] containing line and word count in that order.
	 */
	private static int[] calculateCounts(Document d) {
		int[] results = {d.getNumLines(), d.getNumWords()};
		return results;
	}
	private void selectAll(JTextPane textPane) {
		textPane.selectAll();
	}
	private void openSettings() {
		System.out.println("To implement open settings");
		docFrame.settingsDialog.setVisible(true);
	}
	private void openFind() {
		docFrame.findDialog.setVisible(true);
	}
	private void find(boolean findNext) {
		/**NOTE: the select method doesn't take into account
		 * the \n so I've introduced two offsets to help
		 * 'translate' between the the two systems.
		 */
		System.out.println("Finding next");
		String query = docFrame.findTextField.getText();
		Document d = new Document(docFrame.textPane.getText());
		int startIndex = docFrame.textPane.getCaretPosition();
		int initialOffset = getOffset(startIndex, d.getText());
		startIndex += initialOffset;
		if(findNext == false) {startIndex-=(query.length() + 1);}
		if(startIndex >= d.getText().length()) {
			Toolkit.getDefaultToolkit().beep();
			showWarning("End of document reached.");
			return;
		}
		if(startIndex < 0) {
			Toolkit.getDefaultToolkit().beep();
			showWarning("Beginning of document reached.");
			return;
		}
		int[] location = d.find(findNext, startIndex, query);
		if(location[0] == -1) {
			Toolkit.getDefaultToolkit().beep();
			showWarning("Cannot find anymore '" + query + "'.");
		}
		else {
			int offset = getOffset(location[0], d.getText());
			docFrame.textPane.select(location[0]- offset, location[1] + 1 - offset);
		}
		
	}
	private void undo() {
		if(!dataHandler.undo()) {
			docFrame.undoBtn.setEnabled(false);
			dataHandler.updateFileUpdateStatus(false);
		}
		docFrame.redoBtn.setEnabled(true);
	}
	private void redo() {
		if(!dataHandler.redo()) {
			docFrame.redoBtn.setEnabled(false);
		}
		docFrame.undoBtn.setEnabled(true);
		
	}
	private void saveAs() {
		String saveFilePath = getSaveFilePath();
		if(saveFilePath != null && dataHandler.saveFile(docFrame.textPane.getText(), saveFilePath)) {
			System.out.println("Save successful!");
			docFrame.updateTitle(dataHandler.currFile.getName(), true);
			dataHandler.updateFileUpdateStatus(false);
			docFrame.saveBtn.setEnabled(false);
		}
		else {
			System.out.println("Save unsuccessful");
		}
		
	}
	private void newFile() {
		if(dataHandler.fileUpdated) {
			switch (showUnsavedWarning()) {
				case JOptionPane.OK_OPTION:
					System.out.println("Save!");
					save();
					break;
				case JOptionPane.NO_OPTION:
					System.out.println("Don't save");
					break;
				default:
					System.out.println("Cancel");
					return;
				
			}
		}
		docFrame.newFile();
		dataHandler.newFile();
		updateStats();
		docFrame.saveBtn.setEnabled(false);
		docFrame.undoBtn.setEnabled(false);
		docFrame.redoBtn.setEnabled(false);
		
	}
	private void open() {
		//Note: currFile is set once dataHandler successfully reads the file
		if(dataHandler.fileUpdated) {
			switch (showUnsavedWarning()) {
				case JOptionPane.OK_OPTION:
					System.out.println("Save!");
					save();
					break;
				case JOptionPane.NO_OPTION:
					System.out.println("Don't save");
					break;
				default:
					System.out.println("Cancel");
					return;
				
			}
		}
		File fileToOpen = getFileToOpen();
		if(fileToOpen != null) {
			String text = dataHandler.readFile(fileToOpen);
			if(text != null) {
				docFrame.openFileInDocFrame(text, fileToOpen.getName());
				updateStats();
				dataHandler.updateFileUpdateStatus(false);
				docFrame.saveBtn.setEnabled(false);
				docFrame.undoBtn.setEnabled(false);
				docFrame.redoBtn.setEnabled(false);
				dataHandler.um.discardAllEdits();
			}
			else{
				System.out.println("Open aborted: file could not be read.");
			}
		}
		else {
			System.out.println("Open aborted: no file selected/found.");
		}
		
	}
	private void save() {
		if(dataHandler.currFile != null) {
			String path = dataHandler.currFile.getAbsolutePath();
			System.out.println("Saving to: " + path);
			if(dataHandler.saveFile(docFrame.textPane.getText(), path)) {
				System.out.println("File overwritten");
				docFrame.updateTitle(dataHandler.currFile.getName(), true);
				dataHandler.updateFileUpdateStatus(false);
				docFrame.saveBtn.setEnabled(false);
				return;
			}
			else {
				System.out.println("An error occurred while saving");
			}
		}
		else {
			System.out.println("currFile doesn't exist - switch to saveas");
			saveAs();
		}
	}
	private String getSaveFilePath() {
		JFileChooser fc = getJFileChooser();
		int r = fc.showSaveDialog(docFrame);
		if (r == JFileChooser.APPROVE_OPTION){  
	        return fc.getSelectedFile().getAbsolutePath();  
	    }
		return null;
	}
	private File getFileToOpen() {
		JFileChooser fc = getJFileChooser();
		int r = fc.showOpenDialog(docFrame);
		if (r == JFileChooser.APPROVE_OPTION){  
	        return fc.getSelectedFile();
	    }  
		return null;
	}
	private JFileChooser getJFileChooser() {
		JFileChooser fc;
		try {
			String directory = dataHandler.getCurrDirectory();
			System.out.println("Directory is: " + directory);
			fc = new JFileChooser(directory);
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			fc = new JFileChooser();
		}
		FileFilter filter = new FileNameExtensionFilter("Text Documents", "txt");
		fc.setFileFilter(filter);
		return fc;
	}
	protected int showUnsavedWarning() {
		int a = JOptionPane.showConfirmDialog(docFrame,"You have unsaved changes. Do you want to save?");  
		return a;
	}
	private void enableSaveBtn() {
		if(!docFrame.saveBtn.isEnabled()) {
			docFrame.saveBtn.setEnabled(true);
		}
	}
	/**
	 * When selecting found phrases, the newline character isn't counted
	 * This causes the selection to be shifted to the right based
	 * on how many newlines appear since the before the found phrase.
	 * The offset must be subtracted when selecting position to be
	 * selected.
	 * @param startIndex where the starting position is
	 * @param text the entire text being searched
	 * @return the offset to remove
	 */
	private int getOffset(int startIndex, String text) {
		String textUntilPos;
		try {
			textUntilPos = text.substring(0, startIndex + 1);
		}
		catch(IndexOutOfBoundsException ioe){
			System.out.println(ioe.getMessage());
			textUntilPos = text.substring(0, startIndex);
		}
		return textUntilPos.split("\n").length - 1;
	}
	private void showWarning(String message) {
		JOptionPane.showMessageDialog(docFrame, message);
	}

}
