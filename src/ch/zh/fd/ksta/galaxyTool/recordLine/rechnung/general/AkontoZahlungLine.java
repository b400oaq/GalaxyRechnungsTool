package ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general;

import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.GalaxyRechnungsLine;


public class AkontoZahlungLine extends GalaxyRechnungsLine {

	private String abrechnungsPeriode;

	public AkontoZahlungLine(String line) {
		abrechnungsPeriode = line.trim();
	}

	public String getAbrechnungsPeriode() {
		return abrechnungsPeriode;
	}

}
