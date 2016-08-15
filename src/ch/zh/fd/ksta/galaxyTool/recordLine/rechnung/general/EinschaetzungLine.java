package ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general;

import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.GalaxyRechnungsLine;


public class EinschaetzungLine extends GalaxyRechnungsLine {

	private String einschaetzungBetrag;

	public EinschaetzungLine(String line) {
		einschaetzungBetrag = line.trim().replaceAll(",", "");
		if(einschaetzungBetrag.endsWith("-")) {
			einschaetzungBetrag = "-" + einschaetzungBetrag.substring(0, einschaetzungBetrag.length() - 1);
		}
	}

	public String getEinschaetzungBetrag() {
		return einschaetzungBetrag;
	}

}
