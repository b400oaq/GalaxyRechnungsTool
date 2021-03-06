package ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general;

import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.GalaxyRechnungsLine;


public class BruttoBetragLine extends GalaxyRechnungsLine {

	String bruttoBetrag;

	public BruttoBetragLine(String line) {
		bruttoBetrag = line.trim().replaceAll(",", "");
		if(bruttoBetrag.endsWith("-")) {
			bruttoBetrag = "-" + bruttoBetrag.substring(0, bruttoBetrag.length() - 1);
		}
	}

	public String getBruttoBetrag() {
		return bruttoBetrag;
	}

}
