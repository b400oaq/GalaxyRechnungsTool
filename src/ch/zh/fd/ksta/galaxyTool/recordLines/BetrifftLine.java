package ch.zh.fd.ksta.galaxyTool.recordLines;

public class BetrifftLine extends GalaxyRechnungsRecordLine {

	String betrifft;

	public BetrifftLine(String line) {
		betrifft = line.trim();
	}

	public String getBetrifft() {
		return betrifft;
	}

}
