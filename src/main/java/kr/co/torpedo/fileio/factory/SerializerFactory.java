package kr.co.torpedo.fileio.factory;

import kr.co.torpedo.fileio.parser.BYTEParser;
import kr.co.torpedo.fileio.parser.CSVParser;
import kr.co.torpedo.fileio.parser.JSONParser;
import kr.co.torpedo.fileio.parser.Parser;
import kr.co.torpedo.fileio.parser.XMLParser;

public class SerializerFactory {
	public static Parser makeSerializer(String str) {
		Parser parser = null;

		switch (str.toLowerCase()) {
		case "byte":
			parser = new BYTEParser();
			break;
		case "csv":
			parser = new CSVParser();
			break;
		case "xml":
			parser = new XMLParser();
			break;
		case "json":
			parser = new JSONParser();
			break;
		default:
			parser = new BYTEParser();
			break;
		}
		return parser;
	}
}
