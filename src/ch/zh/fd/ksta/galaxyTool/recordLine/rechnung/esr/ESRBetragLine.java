package ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.esr;


public class ESRBetragLine extends ESRLine {

	private String esrBetrag;

	public ESRBetragLine(String line) {
		esrBetrag = (line.length() > 23) ? line.substring(0, 23) : "";
		esrBetrag = esrBetrag.trim().replaceAll(" ", "");
		if(esrBetrag.length() > 2) {
			esrBetrag = esrBetrag.substring(0, esrBetrag.length() - 2) + "." + esrBetrag.substring(esrBetrag.length() - 2, esrBetrag.length());
			if(esrBetrag.endsWith("-")) {
				esrBetrag = "-" + esrBetrag.substring(0, esrBetrag.length() - 1);
			}
		}
	}

	public String getESRBetrag() {
		return esrBetrag;
	}

}
