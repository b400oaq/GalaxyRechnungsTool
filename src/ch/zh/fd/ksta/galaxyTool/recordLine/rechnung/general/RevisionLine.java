package ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general;

import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.GalaxyRechnungsLine;


public class RevisionLine extends GalaxyRechnungsLine {

	private String revisionBetrag;

	public RevisionLine(String line) {
		revisionBetrag = line.trim().replaceAll(",", "");
		if(revisionBetrag.endsWith("-")) {
			revisionBetrag = "-" + revisionBetrag.substring(0, revisionBetrag.length() - 1);
		}
	}

	public String getRevisionBetrag() {
		return revisionBetrag;
	}

}
