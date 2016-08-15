package ch.zh.fd.ksta.galaxyTool.recordLines;

public class EinschaetzungLine extends GalaxyRechnungsRecordLine {

	private String einschaetzungBetrag;

	public EinschaetzungLine(String line) {
		einschaetzungBetrag = line.trim().replaceAll(",", "");
		if(einschaetzungBetrag.endsWith("-")) {
			einschaetzungBetrag = "-" + einschaetzungBetrag.substring(0, einschaetzungBetrag.length() - 1);
		}
	}

	public String getEinschaetzungBetrag() {
		return einschaetzungBetrag;
	}

}
