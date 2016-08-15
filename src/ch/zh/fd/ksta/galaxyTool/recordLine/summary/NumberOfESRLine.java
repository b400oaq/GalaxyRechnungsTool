package ch.zh.fd.ksta.galaxyTool.recordLine.summary;


public class NumberOfESRLine extends SummaryLine {

	private String anzahlESR;

	public NumberOfESRLine(String line) {
		anzahlESR = line.trim().replaceAll(",", "");
	}

	public String getAnzahlESR() {
		return anzahlESR;
	}

}
