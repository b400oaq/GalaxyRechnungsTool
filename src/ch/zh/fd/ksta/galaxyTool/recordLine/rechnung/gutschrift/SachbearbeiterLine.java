package ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.gutschrift;


public class SachbearbeiterLine extends GutschriftsLine implements ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general.SachbearbeiterLine {

	String sachbearbeiter;
	String anrede;

	public SachbearbeiterLine(String line) {
		sachbearbeiter = (line.length() > 22) ? line.substring(22, 22+26).trim() : "";
		anrede = (line.length() > 2) ? line.substring(48) : "";
	}

	public String getSachbearbeiter() {
		return sachbearbeiter;
	}

	public String getAnrede() {
		return anrede;
	}

}
