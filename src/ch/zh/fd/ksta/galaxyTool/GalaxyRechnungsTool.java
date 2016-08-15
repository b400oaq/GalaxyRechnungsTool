package ch.zh.fd.ksta.galaxyTool;

public class GalaxyRechnungsTool {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Arguments arguments = new Arguments(args);
			GalaxyRechnungsProcessor processor = new GalaxyRechnungsProcessor(arguments);
			processor.process();
		}
		catch(IllegalArgumentException ex) {
			printUsage();
		}
	}

	private static void printUsage() {
		System.out.println("Liest ein Galaxy Rechnungs-File und erzeugt daraus ein CSV mit den einzelnen");
		System.out.println("Rechnungsdaten und ein CSV mit der Statistik.");
		System.out.println();
		System.out.println("java -jar GalaxyRechnungsTool.jar [@][Laufwerk:][Pfad][Dateiname]");
		System.out.println("  [/SE:encoding]");
		System.out.println();
		System.out.println("  [Laufwerk:][Pfad][Dateiname]");
		System.out.println("             Bezeichnet Laufwerk, Verzeichnis und Datei.");
		System.out.println("             Alternativ kann mit '@' eine Datei mit einer");
		System.out.println("             Liste von Files angegeben werden");
		System.out.println();
		System.out.println("  /SE         Source Encoding (z.B. ISO_8859_1, CP850, ...)");
		System.out.println();
	}
}
