package ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general;

import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.GalaxyRechnungsLine;


public class BezugsProvisionLine extends GalaxyRechnungsLine {

	private String bezugsProvision;

	public BezugsProvisionLine(String line) {
		bezugsProvision = line.trim().replaceAll(",", "");
		if(bezugsProvision.endsWith("-")) {
			bezugsProvision = "-" + bezugsProvision.substring(0, bezugsProvision.length() - 1);
		}
	}

	public String getBezugsProvisionBetrag() {
		return bezugsProvision;
	}

}
