package ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general;

import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.GalaxyRechnungsLine;


public class PLZOrtLine extends GalaxyRechnungsLine {

	String plzOrt;

	public PLZOrtLine(String line) {
		plzOrt = (line.length() > 48) ? line.substring(48) : "";
	}

	public String getPlzOrt() {
		return plzOrt;
	}

}
