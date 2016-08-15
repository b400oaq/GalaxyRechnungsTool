package ch.zh.fd.ksta.galaxyTool.recordLines;

public class BezugsProvisionLine extends GalaxyRechnungsRecordLine {

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
