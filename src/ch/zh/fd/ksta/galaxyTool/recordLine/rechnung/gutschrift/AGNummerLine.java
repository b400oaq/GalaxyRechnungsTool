package ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.gutschrift;


public class AGNummerLine extends GutschriftsLine implements ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general.AGNummerLine {

	String agNummer;
	String adresszeile1;

	public AGNummerLine(String line) {
		agNummer = (line.length() > 23) ? line.substring(23, 23+13) : "";
		adresszeile1 = (line.length() > 48) ? line.substring(48) : "";
	}

	public String getAgNummer() {
		return agNummer;
	}

	public String getAdresszeile1() {
		return adresszeile1;
	}

}
