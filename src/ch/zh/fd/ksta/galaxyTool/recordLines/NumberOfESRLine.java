package ch.zh.fd.ksta.galaxyTool.recordLines;

public class NumberOfESRLine extends GalaxyRechnungsRecordLine {

	private String anzahlESR;

	public NumberOfESRLine(String line) {
		anzahlESR = line.trim().replaceAll(",", "");
	}

	public String getAnzahlESR() {
		return anzahlESR;
	}

}
