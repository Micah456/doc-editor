package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.nio.file.FileSystemNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import document.Document;

public class Controller {
	private ActionController ac;
	private KeyController kc;
	private DocFrame docFrame;
	private DataHandler dataHandler;
	
	public Controller(DocFrame docFrame, DataHandler dataHandler) {
		this.kc = new KeyController();
		this.ac = new ActionController();
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
	public ActionController getActionController() {
		return this.ac;
	}
	public KeyController getKeyController() {
		return this.kc;
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
		}
		else {
			System.out.println("Save unsuccessful");
		}
		
	}
	private void open() {
		//Note: currFile is set once dataHandler successfully reads the file
		File fileToOpen = getFileToOpen();
		if(fileToOpen != null) {
			String text = dataHandler.readFile(fileToOpen);
			if(text != null) {
				docFrame.openFileInDocFrame(text, fileToOpen.getName());
				updateCounts();
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
}
