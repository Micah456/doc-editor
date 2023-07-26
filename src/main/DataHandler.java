package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

public class DataHandler {
	protected File currFile; 
	protected boolean fileUpdated;
	//protected String currFileName;
	//protected String currDirectory;
	public DataHandler() {
		this.fileUpdated = false;
	}
	protected boolean saveFile(String text, String path ) {
		if(path == "") {
			//To stop file from saving if no file name given
			return false;
		}
		path = appendFileExt(path);
		System.out.println("Path: " + path);
		try {
            FileWriter fw = new FileWriter(path);
            for (int i = 0; i < text.length(); i++) {
            	fw.write(text.charAt(i));
            }
            fw.close();
            File f = new File(path);
            this.currFile = f;
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
	}
	protected String readFile(File file) {
		StringBuilder sb = new StringBuilder();
		try {
			FileReader fr = new FileReader(file);
			int i;
			while ((i = fr.read()) != -1) {
                sb.append((char)i);
            }
			fr.close();
			this.currFile = file;
			return sb.toString();
		} catch (FileNotFoundException fe) {
			// TODO Auto-generated catch block
			System.out.println("Error: File not found");
			return null;
		} catch(IOException ie) {
			System.out.println("Error: Error while reading file");
			return null;
		}
	}
	/**
	 * Returns String path with .txt appended if it doesn't already have this extension
	 * @param path
	 * @return path with .txt at the end.
	 */
	public static String appendFileExt(String path) {
		if(path.lastIndexOf(".txt") != path.length()-4) {
			path += ".txt";
		}
		return path;
	}
	protected String getCurrDirectory() throws Exception {
		try {
			return currFile.getParentFile().getAbsolutePath();
		}catch(Exception e) {
			throw new Exception("Cannot get current directory or doesn't exist");
		}
		
		
	}
	protected void updateFileUpdateStatus(boolean status) {
		this.fileUpdated = status;
		System.out.println("File update? " + this.fileUpdated);
	}
	/**
	 * Sets currFile as null and fileUpdated as false.
	 * Must be called after clearing docFrame or fileUpdated
	 * will be set to true afterwards
	 */
	protected void newFile() {
		this.currFile = null;
		updateFileUpdateStatus(false);
	}
	protected void copySelected(JTextPane textPane) {
		textPane.copy();
	}
	protected void cutSelected(JTextPane textPane) {
		textPane.cut();
	}
	protected void deleteSelected(JTextPane textPane) {
		try {
			int startIndex = textPane.getSelectionStart();
			int len = textPane.getSelectionEnd()- startIndex;
			textPane.getDocument().remove(startIndex, len);
			
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	protected void paste(JTextPane textPane) {
		textPane.paste();
	}
}
