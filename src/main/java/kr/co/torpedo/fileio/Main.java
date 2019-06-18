package kr.co.torpedo.fileio;

import java.io.IOException;

import kr.co.torpedo.fileio.deserializer.DeSerializer;
import kr.co.torpedo.fileio.serializer.Serializer;

/**
 * 프로그램 실행시키기 위한 메인 클래스
 * 
 * @author user
 *
 */
public class Main {
	public static void main(String[] args) throws IOException {
		Serializer serializer;
		DeSerializer deserializer;

		serializer = SerializerMaker.makeSerializer(args[0]);
		serializer.serializeEmployee();

		deserializer = SerializerMaker.makeDeSerializer(args[0]);
		deserializer.deSerializeEmployee();
		deserializer.showEmployeeList();

		serializer.serializeWithIntern();
		deserializer.deSrializeWithIntern();
		deserializer.showEmployeeList();
	}
}
