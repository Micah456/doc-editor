package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DataHandler {
	protected File currFile; 
	//protected String currFileName;
	//protected String currDirectory;
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
}
