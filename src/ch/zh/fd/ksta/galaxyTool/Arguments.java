package ch.zh.fd.ksta.galaxyTool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Arguments {
	private String sourceFileName;
	private String sourceFileEncoding = "ISO_8859_1";
	private ArrayList<String> sourceFileNames = new ArrayList<String>();

	public Arguments(String[] args) throws IllegalArgumentException {
		if(args.length < 1) {
			throw new IllegalArgumentException();
		}
		sourceFileName = args[0];

		for(int i = 1; i < args.length; i++) {
			String arg = args[i];
			if(arg.startsWith("/SE:")) {
				sourceFileEncoding = arg.substring(4);
			}
			else {
				throw new IllegalArgumentException();
			}
		}

		if(sourceFileName.startsWith("@")) {
			try {
				File file = new File(sourceFileName.substring(1));

				BufferedReader reader;
				reader = new BufferedReader(new FileReader(file));
				String line = reader.readLine();
				while(line != null) {
					sourceFileNames.add(file.getParentFile().getPath() + '\\' + line);
					line = reader.readLine();
				}
			} catch (IOException e) {
				System.out.println("Fehler beim Lesen der Datei : " + sourceFileName.substring(1));
				throw new IllegalArgumentException();
			}
		}
		else {
			sourceFileNames.add(sourceFileName);
		}
	}

	public String getSourceFileEncoding() {
		return sourceFileEncoding;
	}

	public ArrayList<String> getSourceFileNames() {
		return sourceFileNames;
	}

}
