package ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.esr;


public class AbrechnungsPeriodeLine extends ESRLine implements ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general.AbrechnungsPeriodeLine {

	String abrechnungsPeriode;
	String adresszeile2;

	public AbrechnungsPeriodeLine(String line) {
		abrechnungsPeriode = (line.length() > 16) ? line.substring(16, 16+17) : "";
		adresszeile2 = (line.length() > 48) ? line.substring(48) : "";
	}

	public String getAbrechnungsPeriode() {
		return abrechnungsPeriode;
	}

	public String getAdresszeile2() {
		return adresszeile2;
	}

}
