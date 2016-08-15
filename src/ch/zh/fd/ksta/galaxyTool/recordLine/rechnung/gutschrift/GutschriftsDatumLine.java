package ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.gutschrift;


public class GutschriftsDatumLine extends GutschriftsLine {

	private String datum;

	public GutschriftsDatumLine(String line) {
		datum = (line.length() > 60) ? line.substring(60) : "";
	}

	public String getDatum() {
		return datum;
	}

}
