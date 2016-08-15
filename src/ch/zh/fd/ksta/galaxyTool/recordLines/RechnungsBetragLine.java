package ch.zh.fd.ksta.galaxyTool.recordLines;

public class RechnungsBetragLine extends GalaxyRechnungsRecordLine {

	private String zahlbarBis;
	private String rechnungsBetrag;

	public RechnungsBetragLine(String line) {
		zahlbarBis = (line.length() > 37) ? line.substring(37, 37+8) : "";

		rechnungsBetrag = (line.length() > 57) ? line.substring(57) : "";
		rechnungsBetrag = rechnungsBetrag.trim().replaceAll(",", "");
		if(rechnungsBetrag.endsWith("-")) {
			rechnungsBetrag = "-" + rechnungsBetrag.substring(0, rechnungsBetrag.length() - 1);
		}
	}

	public String getZahlbarBis() {
		return zahlbarBis;
	}

	public String getRechnungsBetrag() {
		return rechnungsBetrag;
	}

}
