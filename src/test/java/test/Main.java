package test;

import java.io.IOException;

import kr.co.torpedo.fileio.config.ConfigReader;
import kr.co.torpedo.fileio.factory.SerializerFactory;
import kr.co.torpedo.fileio.manager.FileManager;
import kr.co.torpedo.fileio.parser.Parser;

/**
 * 프로그램 실행시키기 위한 메인 클래스
 * 
 * @author user
 *
 */
public class Main {
	public static void main(String[] args) throws IOException {
		InitiateData initiateData = new InitiateData();
		ConfigReader configReader = new ConfigReader();
		String dir = configReader.getDir();
		Parser parser;
		
		String format = configReader.getFileFormat();
		parser = SerializerFactory.makeSerializer(format);
		
		FileManager fileManager = new FileManager();
		fileManager.setDir(dir);
		fileManager.getMakefile();
		parser.setFileManager(fileManager);
		
		initiateData.addEmployeeToList();
		parser.setDataManager(initiateData.getDataManager());
		parser.setSawonPath();
		parser.serializeEmployee();
		parser.deSerializeEmployee();
		parser.getDataManager().showEmployeeList();

		initiateData.addEmployeeToList();
		initiateData.addInternToList();
		parser.setInternPath();
		parser.serializeWithIntern();
		parser.deSrializeWithIntern();
		parser.getDataManager().showEmployeeList();
	}
}
