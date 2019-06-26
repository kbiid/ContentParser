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
		Parser parser;
		parser = SerializerFactory.makeSerializer(configReader.getFileFormat());

		FileManager fileManager = new FileManager();
		parser.setFileManager(fileManager);
		parser.setFileName("sawon-v1");

		initiateData.addEmployeeToList();
		parser.setDataManager(initiateData.getDataManager());
		parser.getFileManager().makeBaseDirFile(configReader.getDir());
		parser.getFileManager().makeResultFile();
		parser.selialize();
		parser.deSelialize();
		parser.getDataManager().showEmployeeList();

		initiateData.addEmployeeToList();
		initiateData.addInternToList();
		parser.setFileName("sawon-v2");
		parser.getFileManager().makeResultFile();
		parser.selialize();
		parser.deSelialize();
		parser.getDataManager().showEmployeeList();
	}
}
