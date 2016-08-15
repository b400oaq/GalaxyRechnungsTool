package ch.zh.fd.ksta.galaxyTool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import ch.zh.fd.ksta.galaxyTool.rechnungsRecord.GalaxyHeaderRecord;
import ch.zh.fd.ksta.galaxyTool.rechnungsRecord.GalaxyRechnungsRecord;
import ch.zh.fd.ksta.galaxyTool.rechnungsRecord.GalaxyRecord;
import ch.zh.fd.ksta.galaxyTool.rechnungsRecord.GalaxyRecordFactory;
import ch.zh.fd.ksta.galaxyTool.rechnungsRecord.GalaxySummaryRecord;

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

			if(arguments.getRechnungsFilterList().size() > 0) {
				System.out.println("Gefilterte Rechnungen:");
				for(RechnungsFilter filter : arguments.getRechnungsFilterList()) {
					System.out.println(filter.getArbeitgeberNummer() + "/" + filter.getRechnungsNummer() + ": " + filter.getCounter());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processFile(String sourceFileName, BufferedWriter allWriter) {
		File sourceFile = new File(sourceFileName);
		File destinationFile = new File(sourceFileName + ".csv");
		File summaryFile = new File(sourceFileName + ".total.csv");
		File includeFile = new File(sourceFileName + ".incl");
		File excludeFile = new File(sourceFileName + ".excl");

		System.out.println("Processing " + sourceFile.getPath());

		BufferedReader sourceReader = null;
		BufferedWriter destinationWriter = null;
		BufferedWriter includeWriter = null;
		BufferedWriter excludeWriter = null;
		try {
			sourceReader = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFile), arguments.getSourceFileEncoding()));
			destinationWriter = new BufferedWriter(new FileWriter(destinationFile));
			includeWriter = (arguments.getRechnungsFilterList().size() == 0) ? null : new BufferedWriter(new FileWriter(includeFile));
			excludeWriter = (arguments.getRechnungsFilterList().size() == 0) ? null : new BufferedWriter(new FileWriter(excludeFile));

			destinationWriter.write(GalaxyRechnungsRecord.csvHeader());
			allWriter.write(GalaxyRechnungsRecord.csvHeader());
			destinationWriter.newLine();
			allWriter.newLine();

			GalaxyRecord record;
			GalaxyRecordFactory recordFactory = new GalaxyRecordFactory(sourceReader, includeWriter, excludeWriter, arguments.getRechnungsFilterList()); 
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
			if(includeWriter != null) includeWriter.close();
			if(excludeWriter != null) excludeWriter.close();

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
