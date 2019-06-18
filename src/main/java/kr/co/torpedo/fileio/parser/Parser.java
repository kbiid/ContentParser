package kr.co.torpedo.fileio.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.torpedo.fileio.manager.DataManager;
import kr.co.torpedo.fileio.manager.FileManager;
import kr.co.torpedo.fileio.model.Employee;

public abstract class Parser {
	public static final Logger invalidFileLogger = LoggerFactory.getLogger("log.invalid");
	private FileManager fileManager;
	private DataManager dataManager;

	public Parser(String dir) {
		dataManager = new DataManager();
		fileManager = new FileManager();
		fileManager.setDir(dir);
		fileManager.makeDirFile();
	}

	protected FileManager getFileManager() {
		return fileManager;
	}

	public DataManager getDataManager() {
		return dataManager;
	}

	public void setSawonPath() {
		fileManager.setSawonFilePath();
	}
	
	public void setInternPath() {
		fileManager.setInterFilenPath();
	}
	
	public void serializeEmployee() {
		getDataManager().addEmployeeToList();
		selialize();
	}

	public void serializeWithIntern() {
		getDataManager().addEmployeeToList();
		getDataManager().addInternToList();
		selialize();
	}

	public void deSerializeEmployee() {
		deSelialize();
	}

	public void deSrializeWithIntern() {
		deSelialize();
	}

	/**
	 * 직렬화 후 파일에 저장하는 메소드
	 */
	protected abstract void selialize();

	/**
	 * 파일에 데이터를 입력하는 메소드
	 */
	protected abstract void writeEmployee(Object obj);

	/**
	 * 역직렬화 메소드
	 */
	protected abstract void deSelialize();

	/**
	 * 직렬화된 데이터를 읽어오는 메소드
	 * 
	 * @param emp
	 * @param obj
	 */
	protected abstract void readEmployee(Employee emp, Object obj);
}