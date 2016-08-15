package ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.esr;


public class TelefonLine extends ESRLine implements ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general.TelefonLine {

	private String sachbearbeiterTelefon;

	public TelefonLine(String line) {
		sachbearbeiterTelefon = (line.length() > 11) ? line.substring(11, 11+13).trim() : "";
	}

	public String getSachbearbeiterTelefon() {
		return sachbearbeiterTelefon;
	}

}
