package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.nio.file.FileSystemNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import document.Document;

public class Controller {
	private ActionController ac;
	private KeyController kc;
	private DocumentController dc;
	private WindowController wc;
	private DocFrame docFrame;
	private DataHandler dataHandler;
	
	public Controller(DocFrame docFrame, DataHandler dataHandler) {
		this.kc = new KeyController();
		this.ac = new ActionController();
		this.dc = new DocumentController();
		this.wc = new WindowController();
		this.docFrame = docFrame;
		this.dataHandler = dataHandler;
		
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
			if(e.getSource() == docFrame.textPane) {
				updateCounts();
			}
		}
		
	}
	public class DocumentController implements DocumentListener{

		@Override
		public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			dataHandler.updateFileUpdateStatus(true);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			dataHandler.updateFileUpdateStatus(true);
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			dataHandler.updateFileUpdateStatus(true);
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
						saveAs();
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
	private void updateCounts() {
		int[] counts = calculateCounts(docFrame.textPane);
		docFrame.updateCounts(counts[0], counts[1]);
	}
	/**
	 * Calculates the line and word counts for a given JTextPane
	 * @param textPane JTextPane containing text to analyse
	 * @return int[] containing line and word count in that order.
	 */
	private static int[] calculateCounts(JTextPane textPane) {
		Document d = new Document(textPane.getText());
		int[] results = {d.getNumLines(), d.getNumWords()};
		return results;
	}
	private void saveAs() {
		String saveFilePath = getSaveFilePath();
		if(saveFilePath != null && dataHandler.saveFile(docFrame.textPane.getText(), saveFilePath)) {
			System.out.println("Save successful!");
			docFrame.updateTitle(dataHandler.currFile.getName(), true);
			dataHandler.updateFileUpdateStatus(false);
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
					saveAs();
					break;
				case JOptionPane.NO_OPTION:
					System.out.println("Don't save");
					break;
				default:
					System.out.println("Cancel");
					return;
				
			}
		}
		docFrame.textPane.setText("");
		dataHandler.newFile();
		updateCounts();
		
	}
	private void open() {
		//Note: currFile is set once dataHandler successfully reads the file
		if(dataHandler.fileUpdated) {
			switch (showUnsavedWarning()) {
				case JOptionPane.OK_OPTION:
					System.out.println("Save!");
					saveAs();
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
				updateCounts();
				dataHandler.updateFileUpdateStatus(false);
			}
			else{
				System.out.println("Open aborted: file could not be read.");
			}
		}
		else {
			System.out.println("Open aborted: no file selected/found.");
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
}
