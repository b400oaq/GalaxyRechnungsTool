package ch.zh.fd.ksta.galaxyTool.recordLines;

public class VerzugszinsLine extends GalaxyRechnungsRecordLine {

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
