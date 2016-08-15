package ch.zh.fd.ksta.galaxyTool.recordLines;

public class SachbearbeiterLine extends GalaxyRechnungsRecordLine {

	String sachbearbeiter;
	String anrede;

	public SachbearbeiterLine(String line) {
		sachbearbeiter = (line.length() > 16) ? line.substring(16, 16+32).trim() : "";
		anrede = (line.length() > 48) ? line.substring(48) : "";
	}

	public String getSachbearbeiter() {
		return sachbearbeiter;
	}

	public String getAnrede() {
		return anrede;
	}

}
