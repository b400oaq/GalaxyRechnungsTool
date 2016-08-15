package ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.general;

import ch.zh.fd.ksta.galaxyTool.recordLine.rechnung.GalaxyRechnungsLine;


public class VerzugszinsLine extends GalaxyRechnungsLine {

	private String verzugszins;

	public VerzugszinsLine(String line) {
		verzugszins = line.trim().replaceAll(",", "");
		if(verzugszins.endsWith("-")) {
			verzugszins = "-" + verzugszins.substring(0, verzugszins.length() - 1);
		}
	}

	public String getVerzugszins() {
		return verzugszins;
	}

}
