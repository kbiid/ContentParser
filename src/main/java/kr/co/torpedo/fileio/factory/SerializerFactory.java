package kr.co.torpedo.fileio.factory;

import kr.co.torpedo.fileio.parser.ByteParser;
import kr.co.torpedo.fileio.parser.CSVParser;
import kr.co.torpedo.fileio.parser.JsonParser;
import kr.co.torpedo.fileio.parser.Parser;
import kr.co.torpedo.fileio.parser.XmlParser;

public class SerializerFactory {
	public static Parser makeSerializer(String str, String dir) {
		Parser parser = null;

		switch (str.toLowerCase()) {
		case "byte":
			parser = new ByteParser(dir);
			break;
		case "csv":
			parser = new CSVParser(dir);
			break;
		case "xml":
			parser = new XmlParser(dir);
			break;
		case "json":
			parser = new JsonParser(dir);
			break;
		default:
			parser = new ByteParser(dir);
			break;
		}

		return parser;
	}
}
