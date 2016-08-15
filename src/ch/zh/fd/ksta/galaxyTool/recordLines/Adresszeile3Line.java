package ch.zh.fd.ksta.galaxyTool.recordLines;

public class Adresszeile3Line extends GalaxyRechnungsRecordLine {

	String adresszeile3;

	public Adresszeile3Line(String line) {
		adresszeile3 = (line.length() > 48) ? line.substring(48) : "";
	}

	public String getAdresszeile3() {
		return adresszeile3;
	}

}
