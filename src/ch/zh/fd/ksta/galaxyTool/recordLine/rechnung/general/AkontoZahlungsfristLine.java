package ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general;

import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.GalaxyRechnungsLine;


public class AkontoZahlungsfristLine extends GalaxyRechnungsLine {

	private String zahlbarBis;

	public AkontoZahlungsfristLine(String line) {
		zahlbarBis = line.trim();
	}

	public String getZahlbarBis() {
		return zahlbarBis;
	}

}
