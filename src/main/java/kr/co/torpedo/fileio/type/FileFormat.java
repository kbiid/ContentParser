package kr.co.torpedo.fileio.type;

public enum FileFormat {
	CSV("csv"), BYTE("byte"), XML("xml"), JSON("json");
	
	final private String name;

	private FileFormat(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
