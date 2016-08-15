package ch.zh.fd.ksta.galaxyTool;

public class RechnungsFilter {

	private String arbeitgeberNummer;
	private String rechnungsNummer;
	private int counter = 0;

	public RechnungsFilter(String arbeitgeberNummer, String rechnungsNummer) {
		this.arbeitgeberNummer = arbeitgeberNummer;
		this.rechnungsNummer = rechnungsNummer;
	}

	public String getArbeitgeberNummer() {
		return arbeitgeberNummer;
	}

	public void setArbeitgeberNummer(String arbeitgeberNummer) {
		this.arbeitgeberNummer = arbeitgeberNummer;
	}

	public String getRechnungsNummer() {
		return rechnungsNummer;
	}

	public void setRechnungsNummer(String rechnungsNummer) {
		this.rechnungsNummer = rechnungsNummer;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

}
