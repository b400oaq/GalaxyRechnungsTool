package ch.zh.fd.ksta.galaxyTool.recordLines;

public class BruttoBetragLine extends GalaxyRechnungsRecordLine {

	String bruttoBetrag;

	public BruttoBetragLine(String line) {
		bruttoBetrag = line.trim().replaceAll(",", "");
		if(bruttoBetrag.endsWith("-")) {
			bruttoBetrag = "-" + bruttoBetrag.substring(0, bruttoBetrag.length() - 1);
		}
	}

	public String getBruttoBetrag() {
		return bruttoBetrag;
	}

}
