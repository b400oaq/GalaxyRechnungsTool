package ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.esr;


public class AGNummerLine extends ESRLine implements ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general.AGNummerLine {

	String agNummer;
	String adresszeile1;

	public AGNummerLine(String line) {
		agNummer = (line.length() > 16) ? line.substring(16, 16+13) : "";
		adresszeile1 = (line.length() > 48) ? line.substring(48) : "";
	}

	public String getAgNummer() {
		return agNummer;
	}

	public String getAdresszeile1() {
		return adresszeile1;
	}

}
