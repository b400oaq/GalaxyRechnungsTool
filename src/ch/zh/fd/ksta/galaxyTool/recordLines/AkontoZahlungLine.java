package ch.zh.fd.ksta.galaxyTool.recordLines;

public class AkontoZahlungLine extends GalaxyRechnungsRecordLine {

	private String abrechnungsPeriode;

	public AkontoZahlungLine(String line) {
		abrechnungsPeriode = line.trim();
	}

	public String getAbrechnungsPeriode() {
		return abrechnungsPeriode;
	}

}
