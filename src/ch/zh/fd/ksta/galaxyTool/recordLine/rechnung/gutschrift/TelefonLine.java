package ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.gutschrift;


public class TelefonLine extends GutschriftsLine implements ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general.TelefonLine {

	private String sachbearbeiterTelefon;

	public TelefonLine(String line) {
		sachbearbeiterTelefon = (line.length() > 15) ? line.substring(15, 15+13).trim() : "";
	}

	public String getSachbearbeiterTelefon() {
		return sachbearbeiterTelefon;
	}

}
