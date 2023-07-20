package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
				//System.out.println("Key released on text pane");
				//System.out.println("Updating counts");
				int[] counts = calculateCounts(docFrame.textPane);
				docFrame.updateCounts(counts[0], counts[1]);
			}
		}
		
	}
	public ActionController getActionController() {
		return this.ac;
	}
	public KeyController getKeyController() {
		return this.kc;
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
		if(dataHandler.saveFile(docFrame.textPane.getText(), getSaveFilePath())) {
			System.out.println("Save successful!");
		}
		else {
			System.out.println("Save unsuccessful");
		}
		
	}
	private String getSaveFilePath() {
		JFileChooser fc = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter("Text Documents", "txt");
		fc.setFileFilter(filter);
		int r = fc.showSaveDialog(docFrame);
		if (r == JFileChooser.APPROVE_OPTION){  
	        return fc.getSelectedFile().getAbsolutePath();  
	    }  
		return "";
	}
}
