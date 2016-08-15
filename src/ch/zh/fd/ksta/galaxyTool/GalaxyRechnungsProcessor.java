package ch.zh.fd.ksta.galaxyTool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import ch.zh.fd.ksta.galaxyTool.rechnungsRecords.GalaxyHeaderRecord;
import ch.zh.fd.ksta.galaxyTool.rechnungsRecords.GalaxyRechnungsRecord;
import ch.zh.fd.ksta.galaxyTool.rechnungsRecords.GalaxyRecord;
import ch.zh.fd.ksta.galaxyTool.rechnungsRecords.GalaxyRecordFactory;
import ch.zh.fd.ksta.galaxyTool.rechnungsRecords.GalaxySummaryRecord;

public class GalaxyRechnungsProcessor {

	private Arguments arguments;

	public GalaxyRechnungsProcessor(Arguments arguments) {
		this.arguments = arguments;
	}

	public void process() {
		try {
			File destinationAllFile = new File((new File(arguments.getSourceFileNames().get(0))).getParentFile() + "\\Rechnungen.csv");
			BufferedWriter allWriter = new BufferedWriter(new FileWriter(destinationAllFile));
			for(String sourceFileName : arguments.getSourceFileNames()) {
				processFile(sourceFileName, allWriter);
			}
			allWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processFile(String sourceFileName, BufferedWriter allWriter) {
		File sourceFile = new File(sourceFileName);
		File destinationFile = new File(sourceFileName + ".csv");
		File summaryFile = new File(sourceFileName + ".total.csv");

		System.out.println("Processing " + sourceFile.getPath());

		BufferedReader sourceReader = null;
		BufferedWriter destinationWriter = null;
		try {
			sourceReader = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFile), arguments.getSourceFileEncoding()));
			destinationWriter = new BufferedWriter(new FileWriter(destinationFile));

			destinationWriter.write(GalaxyRechnungsRecord.csvHeader());
			allWriter.write(GalaxyRechnungsRecord.csvHeader());
			destinationWriter.newLine();
			allWriter.newLine();

			GalaxyRecord record;
			GalaxyRecordFactory recordFactory = new GalaxyRecordFactory(sourceReader); 
			GalaxySummaryRecord summaryRecord = null;
			while((record = recordFactory.nextRecord()) != null) {
				if(record instanceof GalaxyHeaderRecord) {
					// ignore
				}
				else if(record instanceof GalaxyRechnungsRecord) {
					destinationWriter.write(((GalaxyRechnungsRecord)record).toCSV(sourceFile.getName()));
					allWriter.write(((GalaxyRechnungsRecord)record).toCSV(sourceFile.getName()));
					destinationWriter.newLine();
					allWriter.newLine();
				}
				else if(record instanceof GalaxySummaryRecord) {
					summaryRecord = (GalaxySummaryRecord)record;
				}
			}
			destinationWriter.close();

			if(summaryRecord == null) {
				throw new IllegalArgumentException("Kein Total Record vorhanden");
			}
			else {
				destinationWriter = new BufferedWriter(new FileWriter(summaryFile));
				destinationWriter.write(GalaxySummaryRecord.csvHeader());
				destinationWriter.newLine();
				destinationWriter.write(summaryRecord.toCSV());
				destinationWriter.newLine();
				destinationWriter.close();
			}

		} catch (FileNotFoundException e) {
			System.out.println("File konnte nicht geöffnet werden (" + e.getMessage() + ")");
		} catch (IOException e) {
			System.out.println("Fehler beim Filezugriff (" + e.getMessage() + ")");
		} catch (Exception e) {
			System.out.println("Fehler beim formatieren");
			e.printStackTrace();
		}
		finally {
			try {
				destinationWriter.close();
			} catch (IOException e) {}
		}
	}

}
