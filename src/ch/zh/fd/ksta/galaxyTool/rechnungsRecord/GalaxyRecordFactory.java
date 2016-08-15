package ch.zh.fd.ksta.galaxyTool.rechnungsRecord;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

import ch.zh.fd.ksta.galaxyTool.RechnungsFilter;
import ch.zh.fd.ksta.galaxyTool.recordLine.GalaxyRechnungsFileLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.GalaxyRechnungsRecordLineFactory;
import ch.zh.fd.ksta.galaxyTool.recordLine.general.EmptyLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.general.EndOfFileLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.general.EscapeLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.general.FormFeedLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.general.SeperatorLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.general.TextLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.header.HeaderLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.esr.ESRBetragLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.esr.ESRLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.esr.ESRReferenzLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general.AGNummerLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general.AbrechnungsPeriodeLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general.Adresszeile3Line;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general.AkontoAbrechnungLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general.AkontoZahlungLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general.AkontoZahlungsfristLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general.ArbeitgeberAbrechnungLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general.BetrifftLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general.BezugsProvisionLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general.BruttoBetragLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general.EinschaetzungLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general.EinschaetzungsAbrechnungLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general.KostenLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general.PLZOrtLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general.RechnungsBetragLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general.RechnungsNummerLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general.RevisionLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general.RevisionsAbrechnungLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general.SachbearbeiterLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general.TelefonLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general.VerrechnungLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general.VerzugszinsAbrechnungLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general.VerzugszinsLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.gutschrift.AbsenderLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.gutschrift.GutschriftsDatumLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.gutschrift.GutschriftsLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.summary.GesamtbetragLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.summary.NumberOfESRLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.summary.SummaryLine;

public class GalaxyRecordFactory {

	private BufferedReader reader;
	private BufferedWriter includeWriter;
	private BufferedWriter excludeWriter;
	private ArrayList<RechnungsFilter> rechnungsFilterList;
	private GalaxyRechnungsFileLine recordLine = null;
	private GalaxyRechnungsFileLine prevRecordLine = null;
	private int globalLineCounter = 0;

	public GalaxyRecordFactory(BufferedReader reader, BufferedWriter includeWriter, BufferedWriter excludeWriter, ArrayList<RechnungsFilter> rechnungsFilterList) {
		this.reader = reader;
		this.includeWriter = includeWriter;
		this.excludeWriter = excludeWriter;
		this.rechnungsFilterList  = rechnungsFilterList;
		globalLineCounter = 0;
	}

	public GalaxyRecord nextRecord() {
		try {
			String line;
			int localLineCounter = 0;
			GalaxyRecord record = new GalaxyRecord();
			String[] lineBuffer = new String[100];
			int lineBufferSize = 0;
			while((line = reader.readLine()) != null) {
				lineBuffer[lineBufferSize++] = line;
				localLineCounter++;
				globalLineCounter++;
				prevRecordLine = recordLine;
				recordLine = GalaxyRechnungsRecordLineFactory.readLine(line, record, globalLineCounter, localLineCounter);

				if(record instanceof GalaxyHeaderRecord) {
					if(recordLine instanceof SummaryLine) {
						record = new GalaxySummaryRecord(record);
						if(processSummaryLine((GalaxySummaryRecord)record, recordLine, prevRecordLine)) {
							writeLineBuffer(lineBuffer, lineBufferSize, null, null);
							return record;
						}
					}
					else if(processHeaderLine((GalaxyHeaderRecord)record, recordLine, prevRecordLine)) {
						writeLineBuffer(lineBuffer, lineBufferSize, null, null);
						return record;
					}
				}
				else if(record instanceof GalaxyESRRechnungsRecord) {
					if(processESRRechnungsLine((GalaxyESRRechnungsRecord)record, recordLine, prevRecordLine)) {
						writeLineBuffer(lineBuffer, lineBufferSize, ((GalaxyESRRechnungsRecord)record).getAgNummer(), ((GalaxyESRRechnungsRecord)record).getRechnungsNummer());
						return record;
					}
				}
				else if(record instanceof GalaxyGutschriftsRechnungsRecord) {
					if(processGutschriftsRechnungsLine((GalaxyGutschriftsRechnungsRecord)record, recordLine, prevRecordLine)) {
						writeLineBuffer(lineBuffer, lineBufferSize, ((GalaxyGutschriftsRechnungsRecord)record).getAgNummer(), ((GalaxyGutschriftsRechnungsRecord)record).getRechnungsNummer());
						return record;
					}
				}
				else if(record instanceof GalaxySummaryRecord) {
					if(processSummaryLine((GalaxySummaryRecord)record, recordLine, prevRecordLine)) {
						writeLineBuffer(lineBuffer, lineBufferSize, null, null);
						return record;
					}
				}
				else {
					if(recordLine instanceof HeaderLine) {
						record = new GalaxyHeaderRecord(record);
						if(processHeaderLine((GalaxyHeaderRecord)record, recordLine, prevRecordLine)) {
							writeLineBuffer(lineBuffer, lineBufferSize, null, null);
							return record;
						}
					}
					else if(recordLine instanceof ESRLine) {
						record = new GalaxyESRRechnungsRecord(record);
						if(processESRRechnungsLine((GalaxyESRRechnungsRecord)record, recordLine, prevRecordLine)) {
							writeLineBuffer(lineBuffer, lineBufferSize, null, null);
							return record;
						}
					}
					else if(recordLine instanceof GutschriftsLine) {
						record = new GalaxyGutschriftsRechnungsRecord(record);
						if(processGutschriftsRechnungsLine((GalaxyGutschriftsRechnungsRecord)record, recordLine, prevRecordLine)) {
							writeLineBuffer(lineBuffer, lineBufferSize, null, null);
							return record;
						}
					}
					else if(recordLine instanceof SummaryLine) {
						record = new GalaxySummaryRecord(record);
						if(processSummaryLine((GalaxySummaryRecord)record, recordLine, prevRecordLine)) {
							writeLineBuffer(lineBuffer, lineBufferSize, null, null);
							return record;
						}
					}
					else {
						if(processGeneralLine(record, recordLine, prevRecordLine)) {
							writeLineBuffer(lineBuffer, lineBufferSize, null, null);
							return record;
						}
					}
				}
			}
			if(record instanceof GalaxySummaryRecord) {
				return record;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private boolean processGeneralLine(GalaxyRecord record, GalaxyRechnungsFileLine recordLine, GalaxyRechnungsFileLine prevRecordLine) {
		if(recordLine instanceof FormFeedLine) {
			// ignore
		}
		else if(recordLine instanceof EmptyLine) {
			// ignore
		}
		else if(recordLine instanceof EscapeLine) {
			// ignore
		}
		else {
			throw new IllegalArgumentException("Header must not contain " + recordLine.getClass().getName());
		}
		return false;
	}

	private boolean processHeaderLine(GalaxyHeaderRecord record, GalaxyRechnungsFileLine recordLine, GalaxyRechnungsFileLine prevRecordLine) {
		if(recordLine instanceof HeaderLine) {
			// ignore
		}
		else if(recordLine instanceof EmptyLine) {
			// ignore
		}
		else if(recordLine instanceof EscapeLine) {
			// ignore
		}
		else if(recordLine instanceof SeperatorLine) {
			// ignore
		}
		else if(recordLine instanceof FormFeedLine) {
			return true;
		}
		else {
			throw new IllegalArgumentException("Header must not contain " + recordLine.getClass().getName());
		}
		return false;
	}

	private boolean processGutschriftsRechnungsLine(GalaxyRechnungsRecord record, GalaxyRechnungsFileLine recordLine, GalaxyRechnungsFileLine prevRecordLine) {
		if(recordLine instanceof EmptyLine) {
			// ignore
		}
		else if(recordLine instanceof SeperatorLine) {
			// ignore
		}
		else if(recordLine instanceof TextLine) {
			// ignore
		}
		else if(recordLine instanceof AbsenderLine) {
			// ignore
		}
		else if(recordLine instanceof TelefonLine) {
			record.setSachbearbeiterTelefon(((TelefonLine)recordLine).getSachbearbeiterTelefon());
		}
		else if(recordLine instanceof SachbearbeiterLine) {
			record.setSachbearbeiter(((SachbearbeiterLine)recordLine).getSachbearbeiter());
			record.setAnrede(((SachbearbeiterLine)recordLine).getAnrede());
		}
		else if(recordLine instanceof AGNummerLine) {
			record.setAgNummer(((AGNummerLine)recordLine).getAgNummer());
			record.setAdresszeile1(((AGNummerLine)recordLine).getAdresszeile1());
		}
		else if(recordLine instanceof AbrechnungsPeriodeLine) {
			record.setAbrechnungsPeriode(((AbrechnungsPeriodeLine)recordLine).getAbrechnungsPeriode());
			record.setAdresszeile2(((AbrechnungsPeriodeLine)recordLine).getAdresszeile2());
		}
		else if(recordLine instanceof Adresszeile3Line) {
			record.setAdresszeile3(((Adresszeile3Line)recordLine).getAdresszeile3());
		}
		else if(recordLine instanceof PLZOrtLine) {
			record.setPlzOrt(((PLZOrtLine)recordLine).getPlzOrt());
		}
		else if(recordLine instanceof BetrifftLine) {
			record.setBetrifft(((BetrifftLine)recordLine).getBetrifft());
		}
		else if(recordLine instanceof GutschriftsDatumLine) {
			record.setDatum(((GutschriftsDatumLine)recordLine).getDatum());
		}
		else if(recordLine instanceof RechnungsNummerLine) {
			record.setRechnungsNummer(((RechnungsNummerLine)recordLine).getRechnungsNummer());
			if(record.getDatum() == null) {
				record.setDatum(((RechnungsNummerLine)recordLine).getDatum());
			}
		}
		else if(recordLine instanceof ArbeitgeberAbrechnungLine) {
			record.setTyp(((ArbeitgeberAbrechnungLine)recordLine).getTyp());
		}
		else if(recordLine instanceof RevisionsAbrechnungLine) {
			record.setTyp(((RevisionsAbrechnungLine)recordLine).getTyp());
		}
		else if(recordLine instanceof EinschaetzungsAbrechnungLine) {
			record.setTyp(((EinschaetzungsAbrechnungLine)recordLine).getTyp());
		}
		else if(recordLine instanceof AkontoAbrechnungLine) {
			record.setTyp(((AkontoAbrechnungLine)recordLine).getTyp());
		}
		else if(recordLine instanceof VerzugszinsAbrechnungLine) {
			record.setTyp(((VerzugszinsAbrechnungLine)recordLine).getTyp());
		}
		else if(recordLine instanceof BruttoBetragLine) {
			record.setBruttoBetrag(((BruttoBetragLine)recordLine).getBruttoBetrag());
		}
		else if(recordLine instanceof BezugsProvisionLine) {
			record.setBezugsProvisionBetrag(((BezugsProvisionLine)recordLine).getBezugsProvisionBetrag());
		}
		else if(recordLine instanceof VerrechnungLine) {
			record.addVerrechnungGutschriftBetrag(((VerrechnungLine)recordLine).getVerrechnungGutschriftBetrag());
			record.addVerrechnungGutschriftDatum(((VerrechnungLine)recordLine).getVerrechnungGutschriftDatum());
		}
		else if(recordLine instanceof RevisionLine) {
			record.setRevisionBetrag(((RevisionLine)recordLine).getRevisionBetrag());
		}
		else if(recordLine instanceof EinschaetzungLine) {
			record.setEinschaetzungBetrag(((EinschaetzungLine)recordLine).getEinschaetzungBetrag());
		}
		else if(recordLine instanceof AkontoZahlungLine) {
			record.setAbrechnungsPeriode(((AkontoZahlungLine)recordLine).getAbrechnungsPeriode());
		}
		else if(recordLine instanceof AkontoZahlungsfristLine) {
			record.setZahlbarBis(((AkontoZahlungsfristLine)recordLine).getZahlbarBis());
		}
		else if(recordLine instanceof VerzugszinsLine) {
			record.setVerzugszinsBetrag(((VerzugszinsLine)recordLine).getVerzugszins());
		}
		else if(recordLine instanceof KostenLine) {
			record.setKostenBetrag(((KostenLine)recordLine).getKosten());
		}
		else if(recordLine instanceof RechnungsBetragLine) {
			record.setZahlbarBis(((RechnungsBetragLine)recordLine).getZahlbarBis());
			record.setRechnungsBetrag(((RechnungsBetragLine)recordLine).getRechnungsBetrag());
		}
		else if(recordLine instanceof FormFeedLine) {
			return true;
		}
		else {
			throw new IllegalArgumentException("Record must not contain " + recordLine.getClass().getName());
		}
		return false;
	}

	private boolean processESRRechnungsLine(GalaxyRechnungsRecord record, GalaxyRechnungsFileLine recordLine, GalaxyRechnungsFileLine prevRecordLine) {
		if(recordLine instanceof EmptyLine) {
			// ignore
		}
		else if(recordLine instanceof EscapeLine) {
			// ignore
		}
		else if(recordLine instanceof SeperatorLine) {
			// ignore
		}
		else if(recordLine instanceof TextLine) {
			// ignore
		}
		else if(recordLine instanceof TelefonLine) {
			record.setSachbearbeiterTelefon(((TelefonLine)recordLine).getSachbearbeiterTelefon());
		}
		else if(recordLine instanceof SachbearbeiterLine) {
			record.setSachbearbeiter(((SachbearbeiterLine)recordLine).getSachbearbeiter());
			record.setAnrede(((SachbearbeiterLine)recordLine).getAnrede());
		}
		else if(recordLine instanceof AGNummerLine) {
			record.setAgNummer(((AGNummerLine)recordLine).getAgNummer());
			record.setAdresszeile1(((AGNummerLine)recordLine).getAdresszeile1());
		}
		else if(recordLine instanceof AbrechnungsPeriodeLine) {
			record.setAbrechnungsPeriode(((AbrechnungsPeriodeLine)recordLine).getAbrechnungsPeriode());
			record.setAdresszeile2(((AbrechnungsPeriodeLine)recordLine).getAdresszeile2());
		}
		else if(recordLine instanceof Adresszeile3Line) {
			record.setAdresszeile3(((Adresszeile3Line)recordLine).getAdresszeile3());
		}
		else if(recordLine instanceof PLZOrtLine) {
			record.setPlzOrt(((PLZOrtLine)recordLine).getPlzOrt());
		}
		else if(recordLine instanceof BetrifftLine) {
			record.setBetrifft(((BetrifftLine)recordLine).getBetrifft());
		}
		else if(recordLine instanceof RechnungsNummerLine) {
			record.setRechnungsNummer(((RechnungsNummerLine)recordLine).getRechnungsNummer());
			record.setDatum(((RechnungsNummerLine)recordLine).getDatum());
		}
		else if(recordLine instanceof ArbeitgeberAbrechnungLine) {
			record.setTyp(((ArbeitgeberAbrechnungLine)recordLine).getTyp());
		}
		else if(recordLine instanceof RevisionsAbrechnungLine) {
			record.setTyp(((RevisionsAbrechnungLine)recordLine).getTyp());
		}
		else if(recordLine instanceof EinschaetzungsAbrechnungLine) {
			record.setTyp(((EinschaetzungsAbrechnungLine)recordLine).getTyp());
		}
		else if(recordLine instanceof AkontoAbrechnungLine) {
			record.setTyp(((AkontoAbrechnungLine)recordLine).getTyp());
		}
		else if(recordLine instanceof VerzugszinsAbrechnungLine) {
			record.setTyp(((VerzugszinsAbrechnungLine)recordLine).getTyp());
		}
		else if(recordLine instanceof BruttoBetragLine) {
			record.setBruttoBetrag(((BruttoBetragLine)recordLine).getBruttoBetrag());
		}
		else if(recordLine instanceof BezugsProvisionLine) {
			record.setBezugsProvisionBetrag(((BezugsProvisionLine)recordLine).getBezugsProvisionBetrag());
		}
		else if(recordLine instanceof VerrechnungLine) {
			record.addVerrechnungGutschriftBetrag(((VerrechnungLine)recordLine).getVerrechnungGutschriftBetrag());
			record.addVerrechnungGutschriftDatum(((VerrechnungLine)recordLine).getVerrechnungGutschriftDatum());
		}
		else if(recordLine instanceof RevisionLine) {
			record.setRevisionBetrag(((RevisionLine)recordLine).getRevisionBetrag());
		}
		else if(recordLine instanceof EinschaetzungLine) {
			record.setEinschaetzungBetrag(((EinschaetzungLine)recordLine).getEinschaetzungBetrag());
		}
		else if(recordLine instanceof AkontoZahlungLine) {
			record.setAbrechnungsPeriode(((AkontoZahlungLine)recordLine).getAbrechnungsPeriode());
		}
		else if(recordLine instanceof AkontoZahlungsfristLine) {
			record.setZahlbarBis(((AkontoZahlungsfristLine)recordLine).getZahlbarBis());
		}
		else if(recordLine instanceof VerzugszinsLine) {
			record.setVerzugszinsBetrag(((VerzugszinsLine)recordLine).getVerzugszins());
		}
		else if(recordLine instanceof KostenLine) {
			record.setKostenBetrag(((KostenLine)recordLine).getKosten());
		}
		else if(recordLine instanceof RechnungsBetragLine) {
			record.setZahlbarBis(((RechnungsBetragLine)recordLine).getZahlbarBis());
			record.setRechnungsBetrag(((RechnungsBetragLine)recordLine).getRechnungsBetrag());
		}
		else if(recordLine instanceof ESRReferenzLine) {
			record.setESRReferenz(((ESRReferenzLine)recordLine).setESRReferenz());
		}
		else if(recordLine instanceof ESRBetragLine) {
			record.setESRBetrag(((ESRBetragLine)recordLine).getESRBetrag());
		}
		else if(recordLine instanceof FormFeedLine) {
			return true;
		}
		else {
			throw new IllegalArgumentException("Record must not contain " + recordLine.getClass().getName());
		}
		return false;
	}

	private boolean processSummaryLine(GalaxySummaryRecord record, GalaxyRechnungsFileLine recordLine, GalaxyRechnungsFileLine prevRecordLine) {
		if(recordLine instanceof EmptyLine) {
			// ignore
		}
		else if(recordLine instanceof SeperatorLine) {
			// ignore
		}
		else if(recordLine instanceof NumberOfESRLine) {
			record.setAnzahlESR(((NumberOfESRLine)recordLine).getAnzahlESR());
		}
		else if(recordLine instanceof GesamtbetragLine) {
			record.setGesamtbetrag(((GesamtbetragLine)recordLine).getGesamtbetrag());
		}
		else if(recordLine instanceof SummaryLine) {
			// ignore
		}
		else if(recordLine instanceof EndOfFileLine) {
			return true;
		}
		else {
			throw new IllegalArgumentException("Record must not contain " + recordLine.getClass().getName());
		}
		return false;
	}

	private void writeLineBuffer(String[] lineBuffer, int lineBufferSize, String agNr, String rechnungsNr) throws IOException {
		if((includeWriter != null) && (excludeWriter != null)) {
			boolean isFiltered = false;
			for(RechnungsFilter filter : rechnungsFilterList) {
				if(filter.getArbeitgeberNummer().equals(agNr)) {
					if((filter.getRechnungsNummer().equals(rechnungsNr)) || (filter.getRechnungsNummer().equals("*"))) {
						isFiltered = true;
						filter.setCounter(filter.getCounter() + 1);
					}
				}
			}

			for(int i = 0; i < lineBufferSize; i++) {
				if(agNr == null) {
					includeWriter.write(lineBuffer[i] + "\n");
					excludeWriter.write(lineBuffer[i] + "\n");
				}
				else {
					if(isFiltered) {
						includeWriter.write(lineBuffer[i] + "\n");
					}
					else {
						excludeWriter.write(lineBuffer[i] + "\n");					
					}
				}
			}

			includeWriter.flush();
			excludeWriter.flush();
		}
	}

}
