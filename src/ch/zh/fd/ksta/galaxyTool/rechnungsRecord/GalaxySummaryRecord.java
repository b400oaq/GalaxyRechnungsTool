package ch.zh.fd.ksta.galaxyTool.rechnungsRecord;

public class GalaxySummaryRecord extends GalaxyRecord {

	String anzahlESR;
	String gesamtbetrag;

	public GalaxySummaryRecord(GalaxyRecord record) {
	}

	public static String csvHeader() {
		String csv = "\"Anzahl ESR\";" +
		           "\"Gesamtbetrag\"";
		return csv;
	}

	public String toCSV() {
		String csv = "\"" + getAnzahlESR() + "\";" +
		           "\"" + getGesamtbetrag() + "\""; 
		return csv;
	}

	public String getAnzahlESR() {
		return (anzahlESR != null) ? anzahlESR : "";
	}
	public void setAnzahlESR(String anzahlESR) {
		this.anzahlESR = anzahlESR;
	}
	public String getGesamtbetrag() {
		return (gesamtbetrag != null) ? gesamtbetrag : "";
	}
	public void setGesamtbetrag(String gesamtbetrag) {
		this.gesamtbetrag = gesamtbetrag;
	}

}
