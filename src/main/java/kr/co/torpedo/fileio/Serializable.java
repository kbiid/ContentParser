package kr.co.torpedo.fileio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Serializable {
	public static final String DIR = "D:\\eclipse_workspace\\Fileio\\result\\";
	public static final Logger invalidFileLogger = LoggerFactory.getLogger("log.invalid");

	public void setFileSawonPath();

	public void setFilePathIntern();
}
