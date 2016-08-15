package ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general;

import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.GalaxyRechnungsLine;


public class KostenLine extends GalaxyRechnungsLine {

	private String kosten;

	public KostenLine(String line) {
		kosten = line.trim().replaceAll(",", "");
		if(kosten.endsWith("-")) {
			kosten = "-" + kosten.substring(0, kosten.length() - 1);
		}
	}

	public String getKosten() {
		return kosten;
	}

}
