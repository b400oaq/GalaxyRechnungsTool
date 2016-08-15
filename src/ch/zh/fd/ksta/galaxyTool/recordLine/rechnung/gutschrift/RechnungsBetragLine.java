package ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.gutschrift;


public class RechnungsBetragLine extends GutschriftsLine implements ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general.RechnungsBetragLine {

	private String zahlbarBis;
	private String rechnungsBetrag;

	public RechnungsBetragLine(String line) {
		zahlbarBis = "";

		rechnungsBetrag = (line.length() > 57) ? line.substring(57) : "";
		rechnungsBetrag = rechnungsBetrag.trim().replaceAll(",", "");
		if(rechnungsBetrag.endsWith("-")) {
			rechnungsBetrag = rechnungsBetrag.substring(0, rechnungsBetrag.length() - 1);
		}
		else {
			rechnungsBetrag = "-" + rechnungsBetrag;
		}
	}

	public String getZahlbarBis() {
		return zahlbarBis;
	}

	public String getRechnungsBetrag() {
		return rechnungsBetrag;
	}

}
