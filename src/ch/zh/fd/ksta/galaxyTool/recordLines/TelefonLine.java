package ch.zh.fd.ksta.galaxyTool.recordLines;

public class TelefonLine extends GalaxyRechnungsRecordLine {

	private String sachbearbeiterTelefon;

	public TelefonLine(String line) {
		sachbearbeiterTelefon = (line.length() > 11) ? line.substring(11, 11+13).trim() : "";
	}

	public String getSachbearbeiterTelefon() {
		return sachbearbeiterTelefon;
	}

}
