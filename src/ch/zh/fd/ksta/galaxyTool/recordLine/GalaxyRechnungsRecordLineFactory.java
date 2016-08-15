package ch.zh.fd.ksta.galaxyTool.recordLine;

import ch.zh.fd.ksta.galaxyTool.rechnungsRecord.GalaxyESRRechnungsRecord;
import ch.zh.fd.ksta.galaxyTool.rechnungsRecord.GalaxyGutschriftsRechnungsRecord;
import ch.zh.fd.ksta.galaxyTool.rechnungsRecord.GalaxyHeaderRecord;
import ch.zh.fd.ksta.galaxyTool.rechnungsRecord.GalaxyRecord;
import ch.zh.fd.ksta.galaxyTool.rechnungsRecord.GalaxySummaryRecord;
import ch.zh.fd.ksta.galaxyTool.recordLine.general.EmptyLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.general.EndOfFileLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.general.FormFeedLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.general.SeperatorLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.general.TextLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.header.HeaderLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.esr.ESRBetragLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.esr.ESRReferenzLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.esr.EscapeLine;
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
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general.RechnungsNummerLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general.RevisionLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general.RevisionsAbrechnungLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general.VerrechnungLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general.VerzugszinsAbrechnungLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general.VerzugszinsLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.gutschrift.AbsenderLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.gutschrift.GutschriftsDatumLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.summary.GesamtbetragLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.summary.NumberOfESRLine;
import ch.zh.fd.ksta.galaxyTool.recordLine.summary.SummaryLine;


public class GalaxyRechnungsRecordLineFactory {

	private static String ESC_1  = "\u001B&l20H";
	private static String ESC_2  = "\u001B&l55Z";
	private static String ESC_3  = "\u001BE";
	private static String FORM_FEED = "\u000C";
	private static String CHANGE_FONT_1  = "\u001B(10U\u001B(s0p10h12v0s3b4099T";
	private static String CHANGE_FONT_2  = "\u001B(10U\u001B(s0p10h10v0s0b4099T";

	private static String SEPERATOR_1 = " ----------------------------------------------";
	private static String SEPERATOR_2 = "      -----------------------------------------";

	private static String NCR_ABRECHNUNGS_BEARBEITUNG_1 = " NCR                    ABRECHNUNGS-BEARBEITUNG";
	private static String NCR_ABRECHNUNGS_BEARBEITUNG_2 = " NCR                    Abrechnungs-Bearbeitung";
	private static String NCR_VERBUCHUNG_ABRECHNUNGEN = " NCR                  Verbuchen Abrechnungen";

	private static String H_1_SACHBEARBEITER = "                                                           Name Sachbearbeiter";
	private static String H_1_REGISTERFUEHRER = "                                                           Name Registerführer";
	private static String H_1_FRAU = "                   Frau ";
	private static String H_1_HERR = "                   Herr ";
	private static String H_ESR = "                   *   E S R   *";
	private static String H_GUTSCHRIFT = "                   *GUTSCHRIFTEN*";
	private static String H_3 = " NCR P3QSEE5---";
	private static String H_3a = " NCR P3QSEG5---";
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
	private static String R_SEPERATOR_BETRAG = "                                                      ------------------";

	private static String ESR_RECHNUNGS_BETRAG = "      Rechnungsbetrag:";
	private static String ESR_TEXT_1 = "      Allfällige Verzugszinsen";
	private static String ESR_TEXT_2 = "      nungsfrist und laufen";
	private static String ESR_TEXT_3 = "      weiter.";
	private static String ESR_TEXT_4 = "      Beilagen:";
	private static String ESR_TEXT_5 = "                                                                                 H";

	private static String G_ABSENDER_1 = "      Kantonales Steueramt Zürich";
	private static String G_ABSENDER_2 = "      Dienstabteilung Quellensteuer";
	private static String G_ABSENDER_3 = "      8090 Zürich";
	private static String G_TELEFON = "      Telefon:";
	private static String G_SACHBEARBEITER = "      Sachbearbeiter: ";
	private static String G_ARBEITGEBER_NR = "      Arbeitgeber-Nr.:";
	private static String G_ABRECHNUNGSPERIODE = "      Abr.-Periode: ";
	private static String G_GUTHABEN = "      Ihr Guthaben";
	private static String G_TEXT_1 = "      Der Betrag wird Ihnen mit Ihrer nächsten Abrechnung verrechnet.";
	private static String G_TEXT_2 = "      Abrechnung zu Ihrer Information.";
	private static String G_TEXT_3 = "      Beilagen/Erläuterungen";
	private static String G_TEXT_4 = "      Anfrage-Formular fehlende Arbeitnehmer";

	private static String S_TOTALREKAPITULATION = "                              Totalrekapitulation";
	private static String S_ANZAHL_ESR = "                              Anzahl ESR";
	private static String S_ANZAHL_GUTSCHRIFT = "                              Anzahl Gutschrift";
	private static String S_GESAMTBETRAG = "                              Gesamtbetrag            Fr.";

	
	public static GalaxyRechnungsFileLine readLine(String line, GalaxyRecord record, int globalLineCounter, int localLineCounter) {
		if(record instanceof GalaxyHeaderRecord) {
			return readHeaderLine(line, globalLineCounter, localLineCounter);
		}
		else if(record instanceof GalaxyESRRechnungsRecord) {
			return readESRRechnungsLine(line, globalLineCounter, localLineCounter);
		}
		else if(record instanceof GalaxyGutschriftsRechnungsRecord) {
			return readGutschriftsRechnungsLine(line, globalLineCounter, localLineCounter);
		}
		else if(record instanceof GalaxySummaryRecord) {
			return readSummaryLine(line, globalLineCounter, localLineCounter);
		}
		else {
			return readRecordLine(line, globalLineCounter, localLineCounter);
		}

	}

	private static GalaxyRechnungsFileLine readRecordLine(String line, int globalLineCounter, int localLineCounter) {
		if(line.startsWith(FORM_FEED)) {
			return new FormFeedLine();
		}
		else if(line.trim().length() == 0) {
			return new EmptyLine();
		}
		else if(line.startsWith(ESC_1)) {
			return new HeaderLine();
		}
		else if(line.startsWith(NCR_ABRECHNUNGS_BEARBEITUNG_1)) {
			return new HeaderLine();
		}
		else if(line.startsWith(NCR_ABRECHNUNGS_BEARBEITUNG_2)) {
			return new HeaderLine();
		}
		else if(line.startsWith(ESC_2)) {
			return new EscapeLine();
		}
		else if(line.startsWith(G_ABSENDER_1)) {
			return new AbsenderLine();
		}
		else if(line.startsWith(CHANGE_FONT_1)) {
			return new SummaryLine();
		}
		else {
			throw new IllegalArgumentException("Unknown line " + localLineCounter + "/" + globalLineCounter + " in record start:" + line);
		}
	}

	private static GalaxyRechnungsFileLine readHeaderLine(String line, int globalLineCounter, int localLineCounter) {
		if(line.contains(FORM_FEED)) {
			return new FormFeedLine();
		}
		else if(line.trim().length() == 0) {
			return new EmptyLine();
		}
		else if(line.startsWith(SEPERATOR_1)) {
			return new SeperatorLine();
		}
		else if(line.startsWith(NCR_VERBUCHUNG_ABRECHNUNGEN)) {
			return new HeaderLine();
		}
		else if(line.startsWith(H_ESR)) {
			return new HeaderLine();
		}
		else if(line.startsWith(H_GUTSCHRIFT)) {
			return new HeaderLine();
		}
		else if(line.startsWith(H_1_FRAU)) {
			return new HeaderLine();
		}
		else if(line.startsWith(H_1_HERR)) {
			return new HeaderLine();
		}
		else if(line.startsWith(H_1_SACHBEARBEITER)) {
			return new HeaderLine();
		}
		else if(line.startsWith(H_1_REGISTERFUEHRER)) {
			return new HeaderLine();
		}
		else if(line.startsWith(H_3)) {
			return new HeaderLine();
		}
		else if(line.startsWith(H_3a)) {
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
		else if(line.startsWith(ESC_2)) {
			return new ch.zh.fd.ksta.galaxyTool.recordLine.header.HeaderLine();
		}
		else if(line.startsWith(CHANGE_FONT_1)) {
			return new ch.zh.fd.ksta.galaxyTool.recordLine.header.HeaderLine();
		}
		else if(line.startsWith(CHANGE_FONT_2)) {
			return new ch.zh.fd.ksta.galaxyTool.recordLine.header.HeaderLine();
		}
		else if(line.startsWith(SEPERATOR_2)) {
			return new SeperatorLine();
		}
		else if(line.startsWith(S_TOTALREKAPITULATION)) {
			return new SummaryLine();
		}
		else {
			throw new IllegalArgumentException("unknown line " + localLineCounter + "/" + globalLineCounter + " in Header record: " + line);
		}
	}

	private static GalaxyRechnungsFileLine readESRRechnungsLine(String line, int globalLineCounter, int localLineCounter) {
		if(localLineCounter == 71) {
			return new FormFeedLine();
		}

		GalaxyRechnungsFileLine recordLine = readGeneralRechnungsLine(line, globalLineCounter, localLineCounter);
		if(recordLine != null) {
			return recordLine;
		}
		else if(line.startsWith(ESR_TEXT_1)) {
			return new TextLine();
		}
		else if(line.startsWith(ESR_TEXT_2)) {
			return new TextLine();
		}
		else if(line.startsWith(ESR_TEXT_3)) {
			return new TextLine();
		}
		else if(line.startsWith(ESR_TEXT_4)) {
			return new TextLine();
		}
		else if(line.startsWith(ESR_TEXT_5)) {
			return new TextLine();
		}
		else if(line.startsWith(ESR_RECHNUNGS_BETRAG)) {
			return new ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.esr.RechnungsBetragLine(line);
		}
		else if(line.startsWith(CHANGE_FONT_1)) {
			return new ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.esr.EscapeLine();
		}
		else if(localLineCounter == 12) {
			return new ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.esr.TelefonLine(line);
		}
		else if(localLineCounter == 14) {
			return new ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.esr.SachbearbeiterLine(line);
		}
		else if(localLineCounter == 15) {
			return new ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.esr.AGNummerLine(line);
		}
		else if(localLineCounter == 16) {
			return new ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.esr.AbrechnungsPeriodeLine(line);
		}
		else if(localLineCounter == 17) {
			return new Adresszeile3Line(line);
		}
		else if(localLineCounter == 18) {
			return new PLZOrtLine(line);
		}
		else if(localLineCounter == 19) {
			return new BetrifftLine(line);
		}
		else if(localLineCounter == 55) {
			return new ESRReferenzLine(line);
		}
		else if(localLineCounter == 59) {
			return new ESRBetragLine(line);
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
		else if(localLineCounter == 65) {
			return new TextLine();
		}
		else if(localLineCounter == 66) {
			return new TextLine();
		}
		else if(localLineCounter == 69) {
			return new TextLine();
		}
		else {
			throw new IllegalArgumentException("unknown line " + localLineCounter + "/" + globalLineCounter + " in ESR-Rechnungs record: " + line);
		}
	}

	private static GalaxyRechnungsFileLine readGutschriftsRechnungsLine(String line, int globalLineCounter, int localLineCounter) {
		GalaxyRechnungsFileLine recordLine = readGeneralRechnungsLine(line, globalLineCounter, localLineCounter);
		if(recordLine != null) {
			return recordLine;
		}
		else if(line.startsWith(G_ABSENDER_2)) {
			return new TextLine();
		}
		else if(line.startsWith(G_ABSENDER_3)) {
			return new TextLine();
		}
		else if(line.startsWith(G_TELEFON)) {
			return new ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.gutschrift.TelefonLine(line);
		}
		else if(line.startsWith(G_SACHBEARBEITER)) {
			return new ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.gutschrift.SachbearbeiterLine(line);
		}
		else if(line.startsWith(G_ARBEITGEBER_NR)) {
			return new ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.gutschrift.AGNummerLine(line);
		}
		else if(line.startsWith(G_ABRECHNUNGSPERIODE)) {
			return new ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.gutschrift.AbrechnungsPeriodeLine(line);
		}
		else if(line.startsWith(G_GUTHABEN)) {
			return new ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.gutschrift.RechnungsBetragLine(line);
		}
		else if(line.startsWith(G_TEXT_1)) {
			return new TextLine();
		}
		else if(line.startsWith(G_TEXT_2)) {
			return new TextLine();
		}
		else if(line.startsWith(G_TEXT_3)) {
			return new TextLine();
		}
		else if(line.startsWith(G_TEXT_4)) {
			return new TextLine();
		}
		else if(localLineCounter == 15) {
			return new Adresszeile3Line(line);
		}
		else if(localLineCounter == 16) {
			return new PLZOrtLine(line);
		}
		else if(localLineCounter == 17) {
			return new BetrifftLine(line);
		}
		else if(localLineCounter == 22) {
			return new GutschriftsDatumLine(line);
		}
		else {
			throw new IllegalArgumentException("unknown line " + localLineCounter + "/" + globalLineCounter + " in Gutschrifts-Rechnungs record: " + line);
		}
	}

	private static GalaxyRechnungsFileLine readGeneralRechnungsLine(String line, int globalLineCounter, int localLineCounter) {
		if(line.contains(FORM_FEED)) {
			return new FormFeedLine();
		}
		else if(line.trim().length() == 0) {
			return new EmptyLine();
		}
		else if(line.startsWith(SEPERATOR_2)) {
			return new SeperatorLine();
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
		else if(line.startsWith(R_SEPERATOR_BETRAG)) {
			return new SeperatorLine();
		}
		else {
			return null;
		}
	}

	private static GalaxyRechnungsFileLine readSummaryLine(String line, int globalLineCounter, int localLineCounter) {
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
		else if(line.startsWith(S_ANZAHL_GUTSCHRIFT)) {
			return new NumberOfESRLine(line.substring(S_ANZAHL_GUTSCHRIFT.length()));
		}
		else if(line.startsWith(S_GESAMTBETRAG)) {
			return new GesamtbetragLine(line.substring(S_GESAMTBETRAG.length()));
		}
		else if(line.startsWith(ESC_3)) {
			return new EndOfFileLine();
		}
		else {
			throw new IllegalArgumentException("unknown line " + localLineCounter + "/" + globalLineCounter + " in summary record: " + line);
		}
	}

}
