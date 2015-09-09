package test.parse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import br.com.dfs.parse.ParseType;

public class CSV implements ParseType {

	@Override
	public String createContent(String[] fields, List<String[]> values) throws IOException, FileNotFoundException {
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(Arrays.toString(fields).replaceAll("\\[|\\]", ""));
		for (String[] value : values) {
			stringBuilder.append("\n");
			stringBuilder.append(Arrays.toString(value).replaceAll("\\[|\\]", ""));
		}
		return stringBuilder.toString();
	}

	@Override
	public String getExtensionFile() {
		return "csv";
	}

}
