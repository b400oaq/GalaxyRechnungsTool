package ch.zh.fd.ksta.galaxyTool.recordLines;

public class ESRReferenzLine extends GalaxyRechnungsRecordLine {

	private String esrReferenz;

	public ESRReferenzLine(String line) {
		esrReferenz = (line.length() > 49) ? line.substring(49, 49+32) : "";
	}

	public String setESRReferenz() {
		return esrReferenz;
	}

}
