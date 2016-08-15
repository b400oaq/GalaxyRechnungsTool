package ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general;

import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.GalaxyRechnungsLine;


public class RechnungsNummerLine extends GalaxyRechnungsLine {

	private String rechnungsNummer;
	private String datum;

	public RechnungsNummerLine(String line) {
		rechnungsNummer = (line.length() > 21) ? line.substring(21, 21+4) : "";
		datum = (line.length() > 60) ? line.substring(60) : "";
	}

	public String getRechnungsNummer() {
		return rechnungsNummer;
	}

	public String getDatum() {
		return datum;
	}

}
