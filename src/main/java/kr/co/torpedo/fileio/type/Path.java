package kr.co.torpedo.fileio.type;

public enum Path {
	PROPERTY("D:/eclipse_workspace/ContentParser/src/main/resources/application.properties");
	
	final private String name;

	private Path(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
