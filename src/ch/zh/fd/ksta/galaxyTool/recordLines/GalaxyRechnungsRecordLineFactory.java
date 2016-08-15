package ch.zh.fd.ksta.galaxyTool.recordLines;

import ch.zh.fd.ksta.galaxyTool.rechnungsRecords.GalaxyHeaderRecord;
import ch.zh.fd.ksta.galaxyTool.rechnungsRecords.GalaxyRechnungsRecord;
import ch.zh.fd.ksta.galaxyTool.rechnungsRecords.GalaxyRecord;
import ch.zh.fd.ksta.galaxyTool.rechnungsRecords.GalaxySummaryRecord;


public class GalaxyRechnungsRecordLineFactory {

	private static char ESC = '\u001B';

	private static String FORM_FEED = "\u000C";
	private static String CHANGE_FONT_ESR  = "\u001B(1O\u001B(s0p10h12v0s0b110T";
	private static String CHANGE_FONT_NORMAL  = "\u001B(10U\u001B(s0p10h12v0s3b4099T";
	private static String END_OF_FILE  = "\u001BE";

	private static String SEPERATOR_1 = " ----------------------------------------------";
	private static String SEPERATOR_2 = "      -----------------------------------------";

	private static String NCR_ABRECHNUNGS_BEARBEITUNG_1 = " NCR                    ABRECHNUNGS-BEARBEITUNG";
	private static String NCR_ABRECHNUNGS_BEARBEITUNG_2 = " NCR                    Abrechnungs-Bearbeitung";
	private static String NCR_VERBUCHUNG_ABRECHNUNGEN = " NCR                  Verbuchen Abrechnungen";

	private static String H_1_HERR = "                   Herr";
	private static String H_1_FRAU = "                   Frau";
	private static String H_1_SACHBEARBEITER = "                                                           Name Sachbearbeiter";
	private static String H_1_REGISTERFUEHRER = "                                                           Name Registerführer";
	private static String H_2_ESR = "                   *   E S R   *";
	private static String H_2_GUTSCHRIFT = "                   *GUTSCHRIFTEN*";
	private static String H_3 = " NCR P3QSEE5---";
	private static String H_4 = " Rechnungs-Art:";
	private static String H_5 = "                                  2 = Interne Abrech.";
	private static String H_6 = " Arbeitgeber von:";
	private static String H_7 = "             bis:";
	private static String H_8 = " Generation  1-8:";
	private static String H_9 = " Quittung      :";
	private static String H_10 = " Fortsetzung   :";
	private static String H_11 = " Printer Steuerungs-switches";
	private static String H_12 = " Rechnungsdatum: ";
	private static String H_13 = " Buchungstext  :";
	private static String H_14 = " Alte Verarbeitungen:";
	private static String H_15 = " Generation  1 :";
	private static String H_16 = " Generation  2 :";

	private static String R_RECHNUNGS_NR = "      Rechnungs-Nr.";
	private static String R_ARBEITGEBER_ABRECHNUNG = "                              Arbeitgeberabrechnung";
	private static String R_REVISIONS_ABRECHNUNG = "                              Revisions-Abrechnung";
	private static String R_EINSCHAETZUNGS_ABRECHNUNG = "                              Einschätzung";
	private static String R_AKONTO_ABRECHNUNG = "                              Akonto-Rechnung";
	private static String R_VERZUGSZINS_ABRECHNUNG = "                              Verzugszinsen/Kosten";
	private static String R_BRUTTO_BETRAG = "      Bruttobetrag                                    Fr.";
	private static String R_BEZUGSPROVISION = "      Bezugsprovision                                 Fr.";
	private static String R_VERRECHNUNG_GUTSCHRIFT_VOM = "      Verrechnung Gutschrift        vom ";
	private static String R_VERRECHNUNG_AKONTO_VOM = "      Verrechnung Akontozahlung     vom ";
	private static String R_VERRECHNUNG_AKONTO = "      Verrechnung Akontozahlung         ";
	private static String R_REVISION = "      Revision                                        Fr.";
	private static String R_EINSCHAETZUNG = "      Einschätzung                                    Fr.";
	private static String R_AKONTOZAHLUNG = "      Akontozahlung für Quellensteuer  ";
	private static String R_VERZUGSZINS = "      Verzugszins                                     Fr.";
	private static String R_KOSTEN = "      Kosten                                          Fr.";
	private static String R_AKONTO_ZAHLUNGSFRIST = "      Zahlungsfrist: ";
	private static String R_RECHNUNGS_BETRAG = "      Rechnungsbetrag:";
	private static String R_SEPERATOR_BETRAG = "                                                      ------------------";
	private static String R_TEXT_1 = "      Allfällige Verzugszinsen";
	private static String R_TEXT_2 = "      nungsfrist und laufen";
	private static String R_TEXT_3 = "      weiter.";
	private static String R_TEXT_4 = "      Beilagen:";
	private static String R_TEXT_5 = "                                                                                 H";

	private static String S_TOTALREKAPITULATION = "                              Totalrekapitulation";
	private static String S_ANZAHL_ESR = "                              Anzahl ESR";
	private static String S_GESAMTBETRAG = "                              Gesamtbetrag            Fr.";

	
	public static GalaxyRechnungsRecordLine readLine(String line, GalaxyRecord record, int globalLineCounter, int localLineCounter) {
		if(record == null) {
			return readRecordStartLine(line);
		}
		else if(record instanceof GalaxyHeaderRecord) {
			return readHeaderLine(line);
		}
		else if(record instanceof GalaxyRechnungsRecord) {
			return readRechnungsLine(line, globalLineCounter, localLineCounter);
		}
		else if(record instanceof GalaxySummaryRecord) {
			return readSummaryLine(line, globalLineCounter, localLineCounter);
		}
		else {
			throw new IllegalArgumentException("Unknown record type:" + record.getClass().getName());
		}

	}

	private static GalaxyRechnungsRecordLine readRecordStartLine(String line) {
		if(line.startsWith(FORM_FEED)) {
			return new HeaderStartLine();
		}
		else if(line.trim().length() == 0) {
			return new EmptyLine();
		}
		else {
			throw new IllegalArgumentException("Unknown line in record start:" + line);
		}
	}

	private static GalaxyRechnungsRecordLine readHeaderLine(String line) {
		if(line.trim().length() == 0) {
			return new EmptyLine();
		}
		else if(line.contains(FORM_FEED)) {
			return new FormFeedLine();
		}
		else if(line.charAt(0) == ESC) {
			if(line.substring(1).startsWith("&l55Z")) {
				return new NewRecordLine();
			}
			else {
				return new EscapeLine();
			}
		}
		else if(line.startsWith(SEPERATOR_1)) {
			return new SeperatorLine();
		}
		else if(line.startsWith(SEPERATOR_2)) {
			return new SeperatorLine();
		}
		else if(line.startsWith(NCR_ABRECHNUNGS_BEARBEITUNG_1)) {
			return new HeaderLine();
		}
		else if(line.startsWith(NCR_ABRECHNUNGS_BEARBEITUNG_2)) {
			return new HeaderLine();
		}
		else if(line.startsWith(NCR_VERBUCHUNG_ABRECHNUNGEN)) {
			return new HeaderLine();
		}
		else if(line.startsWith(H_1_HERR)) {
			return new HeaderLine();
		}
		else if(line.startsWith(H_1_FRAU)) {
			return new HeaderLine();
		}
		else if(line.startsWith(H_1_SACHBEARBEITER)) {
			return new HeaderLine();
		}
		else if(line.startsWith(H_1_REGISTERFUEHRER)) {
			return new HeaderLine();
		}
		else if(line.startsWith(H_2_ESR)) {
			return new HeaderLine();
		}
		else if(line.startsWith(H_2_GUTSCHRIFT)) {
			return new HeaderLine();
		}
		else if(line.startsWith(H_3)) {
			return new HeaderLine();
		}
		else if(line.startsWith(H_4)) {
			return new HeaderLine();
		}
		else if(line.startsWith(H_5)) {
			return new HeaderLine();
		}
		else if(line.startsWith(H_6)) {
			return new HeaderLine();
		}
		else if(line.startsWith(H_7)) {
			return new HeaderLine();
		}
		else if(line.startsWith(H_8)) {
			return new HeaderLine();
		}
		else if(line.startsWith(H_9)) {
			return new HeaderLine();
		}
		else if(line.startsWith(H_10)) {
			return new HeaderLine();
		}
		else if(line.startsWith(H_11)) {
			return new HeaderLine();
		}
		else if(line.startsWith(H_12)) {
			return new HeaderLine();
		}
		else if(line.startsWith(H_13)) {
			return new HeaderLine();
		}
		else if(line.startsWith(H_14)) {
			return new HeaderLine();
		}
		else if(line.startsWith(H_15)) {
			return new HeaderLine();
		}
		else if(line.startsWith(H_16)) {
			return new HeaderLine();
		}
		else {
			throw new IllegalArgumentException("unknown line in header record: " + line);
		}
	}

	private static GalaxyRechnungsRecordLine readRechnungsLine(String line, int globalLineCounter, int localLineCounter) {
		if(line.trim().length() == 0) {
			return new EmptyLine();
		}
		else if(line.charAt(0) == ESC) {
			if(line.substring(1).startsWith("&l55Z")) {
				return new NewRecordLine();
			}
			else if(line.startsWith(CHANGE_FONT_ESR)) {
				return new EscapeLine();
			}
			else if(line.startsWith(CHANGE_FONT_NORMAL)) {
				if(localLineCounter == 76) {
					return new LastRecordLine();
				}
				else {
					return new EscapeLine();
				}
			}
			else {
				return new EscapeLine();
			}
		}
		else if(line.startsWith(SEPERATOR_2)) {
			return new SeperatorLine();
		}
		else if(line.startsWith(R_SEPERATOR_BETRAG)) {
			return new SeperatorLine();
		}
		else if(line.startsWith(R_TEXT_1)) {
			return new TextLine();
		}
		else if(line.startsWith(R_TEXT_2)) {
			return new TextLine();
		}
		else if(line.startsWith(R_TEXT_3)) {
			return new TextLine();
		}
		else if(line.startsWith(R_TEXT_4)) {
			return new TextLine();
		}
		else if(line.startsWith(R_TEXT_5)) {
			return new TextLine();
		}
		else if(line.startsWith(R_RECHNUNGS_NR)) {
			return new RechnungsNummerLine(line);
		}
		else if(line.startsWith(R_ARBEITGEBER_ABRECHNUNG)) {
			return new ArbeitgeberAbrechnungLine();
		}
		else if(line.startsWith(R_REVISIONS_ABRECHNUNG)) {
			return new RevisionsAbrechnungLine();
		}
		else if(line.startsWith(R_EINSCHAETZUNGS_ABRECHNUNG)) {
			return new EinschaetzungsAbrechnungLine();
		}
		else if(line.startsWith(R_AKONTO_ABRECHNUNG)) {
			return new AkontoAbrechnungLine();
		}
		else if(line.startsWith(R_VERZUGSZINS_ABRECHNUNG)) {
			return new VerzugszinsAbrechnungLine();
		}
		else if(line.startsWith(R_BRUTTO_BETRAG)) {
			return new BruttoBetragLine(line.substring(R_BRUTTO_BETRAG.length()));
		}
		else if(line.startsWith(R_BEZUGSPROVISION)) {
			return new BezugsProvisionLine(line.substring(R_BEZUGSPROVISION.length()));
		}
		else if(line.startsWith(R_VERRECHNUNG_GUTSCHRIFT_VOM)) {
			return new VerrechnungLine(line);
		}
		else if(line.startsWith(R_VERRECHNUNG_AKONTO_VOM)) {
			return new VerrechnungLine(line);
		}
		else if(line.startsWith(R_VERRECHNUNG_AKONTO)) {
			return new VerrechnungLine(line);
		}
		else if(line.startsWith(R_REVISION)) {
			return new RevisionLine(line.substring(R_REVISION.length()));
		}
		else if(line.startsWith(R_EINSCHAETZUNG)) {
			return new EinschaetzungLine(line.substring(R_EINSCHAETZUNG.length()));
		}
		else if(line.startsWith(R_AKONTOZAHLUNG)) {
			return new AkontoZahlungLine(line.substring(R_AKONTOZAHLUNG.length()));
		}
		else if(line.startsWith(R_AKONTO_ZAHLUNGSFRIST)) {
			return new AkontoZahlungsfristLine(line.substring(R_AKONTO_ZAHLUNGSFRIST.length()));
		}
		else if(line.startsWith(R_VERZUGSZINS)) {
			return new VerzugszinsLine(line.substring(R_VERZUGSZINS.length()));
		}
		else if(line.startsWith(R_KOSTEN)) {
			return new KostenLine(line.substring(R_KOSTEN.length()));
		}
		else if(line.startsWith(R_RECHNUNGS_BETRAG)) {
			return new RechnungsBetragLine(line);
		}
		else if(localLineCounter == 11) {
			return new TelefonLine(line);
		}
		else if(localLineCounter == 13) {
			return new SachbearbeiterLine(line);
		}
		else if(localLineCounter == 14) {
			return new AGNummerLine(line);
		}
		else if(localLineCounter == 15) {
			return new AbrechnungsPeriodeLine(line);
		}
		else if(localLineCounter == 16) {
			return new Adresszeile3Line(line);
		}
		else if(localLineCounter == 17) {
			return new PLZOrtLine(line);
		}
		else if(localLineCounter == 18) {
			return new BetrifftLine(line);
		}
		else if(localLineCounter == 54) {
			return new ESRReferenzLine(line);
		}
		else if(localLineCounter == 58) {
			return new ESRBetragLine(line);
		}
		else if(localLineCounter == 59) {
			return new TextLine();
		}
		else if(localLineCounter == 60) {
			return new TextLine();
		}
		else if(localLineCounter == 60) {
			return new TextLine();
		}
		else if(localLineCounter == 61) {
			return new TextLine();
		}
		else if(localLineCounter == 62) {
			return new TextLine();
		}
		else if(localLineCounter == 63) {
			return new TextLine();
		}
		else if(localLineCounter == 64) {
			return new TextLine();
		}
		else if(localLineCounter == 68) {
			return new TextLine();
		}
		else {
			throw new IllegalArgumentException("unknown line " + localLineCounter + "/" + globalLineCounter + " in rechnungs record: " + line);
		}
	}

	private static GalaxyRechnungsRecordLine readSummaryLine(String line, int globalLineCounter, int localLineCounter) {
		if(line.trim().length() == 0) {
			return new EmptyLine();
		}
		else if(line.startsWith(SEPERATOR_1)) {
			return new SeperatorLine();
		}
		else if(line.startsWith(NCR_ABRECHNUNGS_BEARBEITUNG_1)) {
			return new SummaryLine();
		}
		else if(line.startsWith(NCR_ABRECHNUNGS_BEARBEITUNG_2)) {
			return new SummaryLine();
		}
		else if(line.startsWith(S_TOTALREKAPITULATION)) {
			return new SummaryLine();
		}
		else if(line.startsWith(S_ANZAHL_ESR)) {
			return new NumberOfESRLine(line.substring(S_ANZAHL_ESR.length()));
		}
		else if(line.startsWith(S_GESAMTBETRAG)) {
			return new GesamtbetragLine(line.substring(S_GESAMTBETRAG.length()));
		}
		else if(line.startsWith(END_OF_FILE)) {
			return new EndOfFileLine();
		}
		else {
			throw new IllegalArgumentException("unknown line " + localLineCounter + "/" + globalLineCounter + " in summary record: " + line);
		}
	}

}
