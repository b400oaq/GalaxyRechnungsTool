package ch.zh.fd.ksta.galaxyTool.recordLines;

public class AkontoZahlungsfristLine extends GalaxyRechnungsRecordLine {

	private String zahlbarBis;

	public AkontoZahlungsfristLine(String line) {
		zahlbarBis = line.trim();
	}

	public String getZahlbarBis() {
		return zahlbarBis;
	}

}
