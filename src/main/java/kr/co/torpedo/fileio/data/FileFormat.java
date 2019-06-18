package kr.co.torpedo.fileio.data;

public enum FileFormat {
	CVS("cvs"), BYTE("byte"), XML("xml"), JSON("json");
	
	final private String name;

	private FileFormat(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
