package ch.zh.fd.ksta.galaxyTool.recordLines;

public class PLZOrtLine extends GalaxyRechnungsRecordLine {

	String plzOrt;

	public PLZOrtLine(String line) {
		plzOrt = (line.length() > 48) ? line.substring(48) : "";
	}

	public String getPlzOrt() {
		return plzOrt;
	}

}
