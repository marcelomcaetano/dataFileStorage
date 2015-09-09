package br.com.dfs.parse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface ParseType {
	
	public String createContent(String[] fields, List<String[]> values) throws FileNotFoundException, IOException ;
	
	public String getExtensionFile();
	
}
