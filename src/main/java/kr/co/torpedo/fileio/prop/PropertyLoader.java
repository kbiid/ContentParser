package kr.co.torpedo.fileio.prop;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyLoader {
	private Properties properties;

	public PropertyLoader() {
		properties = new Properties();
	}

	public Properties getProperties() {
		return properties;
	}

	public void loadProp(String path) {
		try (FileInputStream fis = new FileInputStream(path)) {
			properties.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
