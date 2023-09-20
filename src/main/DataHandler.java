package main;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DataHandler {
	public static final File dictDir = new File("data/dictionaries");
	private String defaultDict;
	public HashMap<String,ArrayList<String>> dictionaries;
	private ArrayList<String> currDict;
	private String currDictName;
	protected File currFile; 
	protected boolean fileUpdated;
	protected final UndoManager um = new UndoManager();
	private ArrayList<String> ignoredWords;
	private static String appSettingsFileName = "data/appSettings.json";

	public DataHandler() {
		this.fileUpdated = false;
		this.dictionaries = getDictionaries(dictDir);
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
		um.discardAllEdits();
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
	protected boolean undo() {
		try {
			um.undo();
			if(!um.canUndo()) {
				return false;
			}
			else {
				return true;
			}
		}catch(CannotUndoException c){
			System.out.println("Can't undo: " + c.getMessage());
			Toolkit.getDefaultToolkit().beep();
			return false;
		}
	}
	protected boolean redo() {
		try {
			um.redo();
			if(!um.canRedo()) {
				return false;
			}
			else {
				return true;
			}
		}catch(CannotRedoException c){
			System.out.println("Can't redo: " + c.getMessage());
			Toolkit.getDefaultToolkit().beep();
			return false;
		}
	}
	public static HashMap<String,ArrayList<String>> getDictionaries(File dictDir){
		HashMap<String, ArrayList<String>> dictionaries = new HashMap<>();
		for(File f : dictDir.listFiles()) {
			System.out.println("Reading file: '" + f.getName() + "'.");
			String dictName = f.getName();
			//Remove .txt
			dictName = dictName.substring(0, dictName.length()-4);
			ArrayList<String> content = getFileContent(f);
			dictionaries.put(dictName, content);
		}
		return dictionaries;
	}
	private static ArrayList<String> getFileContent(File f){
		ArrayList<String> content = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		try {
			FileReader fr = new FileReader(f);
			int i;
			while ((i = fr.read()) != -1) {
                sb.append((char)i);
            }
			fr.close();
			String data = sb.toString();
			String[] dataArr = data.split("[\n\r]+");
			for(String s : dataArr) {
				content.add(s);
			}
			return content;
		} catch (FileNotFoundException fe) {
			// TODO Auto-generated catch block
			System.out.println("Error: File '" + f.getName() + "' not found.");
			return null;
		} catch(IOException ie) {
			System.out.println("Error: Error while reading file '" + f.getName() + "'.");
			return null;
		}
		
	}
	public void setDictionary(String dictionaryName) {
		this.currDict = this.dictionaries.get(dictionaryName);
		this.currDictName = dictionaryName;
	}
	protected ArrayList<String> getCurrDictionary(){
		if(this.currDict != null) {
			return this.currDict;
		}
		else {
			setDictionary(defaultDict);
			return this.dictionaries.get(defaultDict);
		}
	}
	public String getCurrDictName() {
		if(currDictName == null) {
			return defaultDict;
		}
		return currDictName;
	}
	protected JSONObject getAppSettings(String appSettingsFileName) {
		JSONParser parser = new JSONParser();
		Object obj = null;
		try {
			obj = parser.parse(new FileReader(appSettingsFileName));
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return (JSONObject) obj;	
	}
	protected void applyAppSettings(JSONObject appSettings) {
		this.defaultDict = (String) appSettings.get("defaultDictionary");
		//TODO use this to add ignore list 
		this.ignoredWords = (ArrayList<String>) appSettings.get("ignoredWords");
	}
	public void refreshAppSettings() {
		applyAppSettings(getAppSettings(appSettingsFileName));
	}
	public ArrayList<String> getIgnoredWords(){
		return this.ignoredWords;
	}
	public String[] getIgnoredWordsAsArray() {
		return convertToArray(this.ignoredWords);
	}
	public String[] convertToArray(ArrayList<String> stringList) {
		String[] result = new String[stringList.size()];
		for(int i = 0; i<stringList.size(); i++) {
			result[i] = stringList.get(i);
		}
		return result;
	}
	public boolean updateSettingsFile(String jsonString) {
		String path = appSettingsFileName;
		System.out.println("Updating file: " + path);
		try {
            FileWriter fw = new FileWriter(path);
            for (int i = 0; i < jsonString.length(); i++) {
            	fw.write(jsonString.charAt(i));
            }
            fw.close();
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
	}
	public boolean addDictionary(String dictPath) {
		File f = new File(dictPath);
		String dictName = f.getName();
		StringBuilder sb = new StringBuilder();
		String content;
		try {
			FileReader fr = new FileReader(f);
			int i;
			while ((i = fr.read()) != -1) {
                sb.append((char)i);
            }
			fr.close();
			content = sb.toString();
		} catch (FileNotFoundException fe) {
			// TODO Auto-generated catch block
			System.out.println("Error: File not found");
			return false;
		} catch(IOException ie) {
			System.out.println("Error: Error while reading file");
			return false;
		}
		String newPath = dictDir.getAbsolutePath() + "\\" + dictName;
		System.out.println("New file path: " + newPath);
		System.out.println("File content:\n" + content);
		try {
            FileWriter fw = new FileWriter(newPath);
            for (int i = 0; i < content.length(); i++) {
            	fw.write(content.charAt(i));
            }
            fw.close();
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
	}
	public boolean removeDictionary(String dictPath) {
		System.out.println("Implement remove dictionary");
		File f = new File(dictPath);
		return f.delete();
	}
}
