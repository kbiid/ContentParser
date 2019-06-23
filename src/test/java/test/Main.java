package test;

import java.io.IOException;

import kr.co.torpedo.fileio.config.ConfigReader;
import kr.co.torpedo.fileio.factory.SerializerFactory;
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
		String format = configReader.getFileFormat();
		parser = SerializerFactory.makeSerializer(format);
		String dir = configReader.getDir();
		parser.getFileManager().setDir(dir);
		parser.getFileManager().getMakefile();
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
