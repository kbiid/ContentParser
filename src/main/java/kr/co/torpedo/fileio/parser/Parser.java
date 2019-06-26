package kr.co.torpedo.fileio.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.torpedo.fileio.domain.Employee;
import kr.co.torpedo.fileio.manager.DataManager;
import kr.co.torpedo.fileio.manager.FileManager;

public abstract class Parser {
	public static final Logger invalidFileLogger = LoggerFactory.getLogger(Parser.class);
	protected FileManager fileManager;
	protected DataManager dataManager;

	public FileManager getFileManager() {
		return fileManager;
	}

	public void setFileManager(FileManager fileManager) {
		this.fileManager = fileManager;
	}

	public void setDataManager(DataManager dataManager) {
		this.dataManager = dataManager;
	}

	public DataManager getDataManager() {
		return dataManager;
	}

	/**
	 * 직렬화 후 파일에 저장하는 메소드
	 */
	public abstract void selialize();

	/**
	 * 파일에 데이터를 입력하는 메소드
	 */
	protected abstract void writeEmployee(Object obj);

	/**
	 * 역직렬화 메소드
	 */
	public abstract void deSelialize();

	/**
	 * 직렬화된 데이터를 읽어오는 메소드
	 * 
	 * @param emp
	 * @param obj
	 */
	protected abstract void readEmployee(Employee emp, Object obj);
	
	protected abstract void setFileName(String fileName);
}
