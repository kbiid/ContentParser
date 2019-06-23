package kr.co.torpedo.fileio.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
	private Properties properties;

	public ConfigReader() {
		properties = new Properties();
		loadProp();
	}

	public Properties getProperties() {
		return properties;
	}

	public void loadProp() {
		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties")) {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getDir() {
		if (properties == null || !properties.containsKey("base.dir")) {
			throw new NullPointerException("properties가 null이거나 base.dir키가 없습니다.");
		}
		return properties.get("base.dir").toString();
	}

	public String getFileFormat() {
		if (properties == null || !properties.containsKey("file.format")) {
			throw new NullPointerException("properties가 null이거나 file.format키가 없습니다.");
		}
		return properties.get("file.format").toString();
	}
}
