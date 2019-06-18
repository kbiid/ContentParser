package kr.co.torpedo.fileio.prop;

import java.util.Properties;

public class PropertyReader {
	private Properties properties;

	public PropertyReader(Properties properties) {
		this.properties = properties;
	}

	public String getDir() {
		if (properties == null || !properties.containsKey("file.dir")) {
			throw new NullPointerException("properties가 null이거나 file.dir키가 없습니다.");
		}
		return properties.get("file.dir").toString();
	}

	public String getFileFormat() {
		if (properties == null || !properties.containsKey("file.format")) {
			throw new NullPointerException("properties가 null이거나 file.format키가 없습니다.");
		}
		return properties.get("file.format").toString();
	}
}
