package ch.zh.fd.ksta.galaxyTool.rechnungsRecords;

import java.io.BufferedReader;
import java.io.IOException;

import ch.zh.fd.ksta.galaxyTool.recordLines.AGNummerLine;
import ch.zh.fd.ksta.galaxyTool.recordLines.AbrechnungsPeriodeLine;
import ch.zh.fd.ksta.galaxyTool.recordLines.Adresszeile3Line;
import ch.zh.fd.ksta.galaxyTool.recordLines.AkontoAbrechnungLine;
import ch.zh.fd.ksta.galaxyTool.recordLines.AkontoZahlungLine;
import ch.zh.fd.ksta.galaxyTool.recordLines.AkontoZahlungsfristLine;
import ch.zh.fd.ksta.galaxyTool.recordLines.ArbeitgeberAbrechnungLine;
import ch.zh.fd.ksta.galaxyTool.recordLines.BetrifftLine;
import ch.zh.fd.ksta.galaxyTool.recordLines.BezugsProvisionLine;
import ch.zh.fd.ksta.galaxyTool.recordLines.BruttoBetragLine;
import ch.zh.fd.ksta.galaxyTool.recordLines.ESRBetragLine;
import ch.zh.fd.ksta.galaxyTool.recordLines.ESRReferenzLine;
import ch.zh.fd.ksta.galaxyTool.recordLines.EinschaetzungLine;
import ch.zh.fd.ksta.galaxyTool.recordLines.EinschaetzungsAbrechnungLine;
import ch.zh.fd.ksta.galaxyTool.recordLines.EmptyLine;
import ch.zh.fd.ksta.galaxyTool.recordLines.EndOfFileLine;
import ch.zh.fd.ksta.galaxyTool.recordLines.EscapeLine;
import ch.zh.fd.ksta.galaxyTool.recordLines.FormFeedLine;
import ch.zh.fd.ksta.galaxyTool.recordLines.GalaxyRechnungsRecordLine;
import ch.zh.fd.ksta.galaxyTool.recordLines.GalaxyRechnungsRecordLineFactory;
import ch.zh.fd.ksta.galaxyTool.recordLines.GesamtbetragLine;
import ch.zh.fd.ksta.galaxyTool.recordLines.HeaderLine;
import ch.zh.fd.ksta.galaxyTool.recordLines.HeaderStartLine;
import ch.zh.fd.ksta.galaxyTool.recordLines.KostenLine;
import ch.zh.fd.ksta.galaxyTool.recordLines.LastRecordLine;
import ch.zh.fd.ksta.galaxyTool.recordLines.NewRecordLine;
import ch.zh.fd.ksta.galaxyTool.recordLines.NumberOfESRLine;
import ch.zh.fd.ksta.galaxyTool.recordLines.PLZOrtLine;
import ch.zh.fd.ksta.galaxyTool.recordLines.RechnungsBetragLine;
import ch.zh.fd.ksta.galaxyTool.recordLines.RechnungsNummerLine;
import ch.zh.fd.ksta.galaxyTool.recordLines.RevisionLine;
import ch.zh.fd.ksta.galaxyTool.recordLines.RevisionsAbrechnungLine;
import ch.zh.fd.ksta.galaxyTool.recordLines.SachbearbeiterLine;
import ch.zh.fd.ksta.galaxyTool.recordLines.SeperatorLine;
import ch.zh.fd.ksta.galaxyTool.recordLines.SummaryLine;
import ch.zh.fd.ksta.galaxyTool.recordLines.TelefonLine;
import ch.zh.fd.ksta.galaxyTool.recordLines.TextLine;
import ch.zh.fd.ksta.galaxyTool.recordLines.VerrechnungLine;
import ch.zh.fd.ksta.galaxyTool.recordLines.VerzugszinsAbrechnungLine;
import ch.zh.fd.ksta.galaxyTool.recordLines.VerzugszinsLine;

public class GalaxyRecordFactory {

	private BufferedReader reader;
	private GalaxyRechnungsRecordLine recordLine = null;
	private GalaxyRechnungsRecordLine prevRecordLine = null;
	int globalLineCounter = 0;
	

	public GalaxyRecordFactory(BufferedReader reader) {
		this.reader = reader;
		globalLineCounter = 0;
	}

	public GalaxyRecord nextRecord() {
		try {
			String line;
			int localLineCounter = 0;
			GalaxyRecord record = null;
			while((line = reader.readLine()) != null) {
				localLineCounter++;
				globalLineCounter++;
				prevRecordLine = recordLine;
				recordLine = GalaxyRechnungsRecordLineFactory.readLine(line, record, globalLineCounter, localLineCounter);
				if(record == null) {
					if(recordLine instanceof HeaderStartLine) {
						record = new GalaxyHeaderRecord();
					}
					else if(recordLine instanceof EmptyLine) {
						if(prevRecordLine instanceof LastRecordLine) {
							record = new GalaxySummaryRecord();
						}
						else {
							record = new GalaxyRechnungsRecord();
						}
					}
					else {
						throw new IllegalArgumentException("Record must not start with " + recordLine.getClass().getName());
					}
				}
				else if(record instanceof GalaxyHeaderRecord) {
					if(processHeaderLine((GalaxyHeaderRecord)record, recordLine, prevRecordLine)) {
						return record;
					}
				}
				else if(record instanceof GalaxyRechnungsRecord) {
					if(processRechnungsLine((GalaxyRechnungsRecord)record, recordLine, prevRecordLine)) {
						return record;
					}
				}
				else if(record instanceof GalaxySummaryRecord) {
					if(processSummaryLine((GalaxySummaryRecord)record, recordLine, prevRecordLine)) {
						return record;
					}
				}
				else {
					throw new IllegalArgumentException("Unknown record:" + record.getClass().getName());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private boolean processHeaderLine(GalaxyHeaderRecord record, GalaxyRechnungsRecordLine recordLine, GalaxyRechnungsRecordLine prevRecordLine) {
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
			// ignore
		}
		else if(recordLine instanceof NewRecordLine) {
			if(prevRecordLine instanceof FormFeedLine) {
				return true;
			}
			else {
				// ignore
			}
		}
		else {
			throw new IllegalArgumentException("Header must not contain " + recordLine.getClass().getName());
		}
		return false;
	}

	private boolean processRechnungsLine(GalaxyRechnungsRecord record, GalaxyRechnungsRecordLine recordLine, GalaxyRechnungsRecordLine prevRecordLine) {
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
		else if(recordLine instanceof NewRecordLine) {
			return true;
		}
		else if(recordLine instanceof LastRecordLine) {
			return true;
		}
		else {
			throw new IllegalArgumentException("Record must not contain " + recordLine.getClass().getName());
		}
		return false;
	}

	private boolean processSummaryLine(GalaxySummaryRecord record, GalaxyRechnungsRecordLine recordLine, GalaxyRechnungsRecordLine prevRecordLine) {
		if(recordLine instanceof EmptyLine) {
			// ignore
		}
		else if(recordLine instanceof SeperatorLine) {
			// ignore
		}
		else if(recordLine instanceof SummaryLine) {
			// ignore
		}
		else if(recordLine instanceof NumberOfESRLine) {
			record.setAnzahlESR(((NumberOfESRLine)recordLine).getAnzahlESR());
		}
		else if(recordLine instanceof GesamtbetragLine) {
			record.setGesamtbetrag(((GesamtbetragLine)recordLine).getGesamtbetrag());
		}
		else if(recordLine instanceof EndOfFileLine) {
			return true;
		}
		else {
			throw new IllegalArgumentException("Record must not contain " + recordLine.getClass().getName());
		}
		return false;
	}

}
