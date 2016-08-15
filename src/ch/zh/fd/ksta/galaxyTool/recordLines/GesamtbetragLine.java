package ch.zh.fd.ksta.galaxyTool.recordLines;

public class GesamtbetragLine extends GalaxyRechnungsRecordLine {

	private String gesamtbetrag;

	public GesamtbetragLine(String line) {
		gesamtbetrag = line.trim().replaceAll(",", "");
		if(gesamtbetrag.endsWith("-")) {
			gesamtbetrag = "-" + gesamtbetrag.substring(0, gesamtbetrag.length() - 1);
		}
	}

	public String getGesamtbetrag() {
		return gesamtbetrag;
	}

}
