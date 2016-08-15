package ch.zh.fd.ksta.galaxyTool.recordLines;

public class VerrechnungLine extends GalaxyRechnungsRecordLine {

	private String verrechnungGutschriftDatum;
	private String verrechnungGutschriftBetrag;

	public VerrechnungLine(String line) {
		verrechnungGutschriftDatum = line.substring(40, 40+8);

		verrechnungGutschriftBetrag = line.substring(57).trim().replaceAll(",", "");
		if(verrechnungGutschriftBetrag.endsWith("-")) {
			verrechnungGutschriftBetrag = "-" + verrechnungGutschriftBetrag.substring(0, verrechnungGutschriftBetrag.length() - 1);
		}
	}

	public String getVerrechnungGutschriftDatum() {
		return verrechnungGutschriftDatum;
	}

	public String getVerrechnungGutschriftBetrag() {
		return verrechnungGutschriftBetrag;
	}

}
