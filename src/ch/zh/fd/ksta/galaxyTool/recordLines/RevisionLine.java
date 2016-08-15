package ch.zh.fd.ksta.galaxyTool.recordLines;

public class RevisionLine extends GalaxyRechnungsRecordLine {

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
