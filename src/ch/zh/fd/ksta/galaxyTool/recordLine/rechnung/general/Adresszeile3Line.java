package ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general;

import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.GalaxyRechnungsLine;


public class Adresszeile3Line extends GalaxyRechnungsLine {

	String adresszeile3;

	public Adresszeile3Line(String line) {
		adresszeile3 = (line.length() > 48) ? line.substring(48) : "";
	}

	public String getAdresszeile3() {
		return adresszeile3;
	}

}
