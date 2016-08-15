package ch.zh.fd.ksta.galaxyTool.recordLines;

public class KostenLine extends GalaxyRechnungsRecordLine {

	private String kosten;

	public KostenLine(String line) {
		kosten = line.trim().replaceAll(",", "");
		if(kosten.endsWith("-")) {
			kosten = "-" + kosten.substring(0, kosten.length() - 1);
		}
	}

	public String getKosten() {
		return kosten;
	}

}
