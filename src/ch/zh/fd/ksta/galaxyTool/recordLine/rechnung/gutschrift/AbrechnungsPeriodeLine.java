package ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.gutschrift;


public class AbrechnungsPeriodeLine extends GutschriftsLine implements ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general.AbrechnungsPeriodeLine {

	String abrechnungsPeriode;
	String adresszeile2;

	public AbrechnungsPeriodeLine(String line) {
		abrechnungsPeriode = (line.length() > 23) ? line.substring(23, 23+17) : "";
		adresszeile2 = (line.length() > 48) ? line.substring(48) : "";
	}

	public String getAbrechnungsPeriode() {
		return abrechnungsPeriode;
	}

	public String getAdresszeile2() {
		return adresszeile2;
	}

}
