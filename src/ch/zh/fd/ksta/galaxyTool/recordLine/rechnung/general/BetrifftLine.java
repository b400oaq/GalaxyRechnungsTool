package ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general;

import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.GalaxyRechnungsLine;


public class BetrifftLine extends GalaxyRechnungsLine {

	String betrifft;

	public BetrifftLine(String line) {
		betrifft = line.trim();
	}

	public String getBetrifft() {
		return betrifft;
	}

}
