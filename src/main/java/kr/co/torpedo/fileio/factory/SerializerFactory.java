package kr.co.torpedo.fileio.factory;

import kr.co.torpedo.fileio.parser.ByteParser;
import kr.co.torpedo.fileio.parser.CSVParser;
import kr.co.torpedo.fileio.parser.JsoNParser;
import kr.co.torpedo.fileio.parser.Parser;
import kr.co.torpedo.fileio.parser.XmlParser;

public class SerializerFactory {
	public static Parser makeSerializer(String str, String dir) {
		Parser parser = null;

		switch (str.toLowerCase()) {
		case "byte":
			parser = new ByteParser(dir);
			break;
		case "cvs":
			parser = new CSVParser(dir);
			break;
		case "xml":
			parser = new XmlParser(dir);
			break;
		case "json":
			parser = new JsoNParser(dir);
			break;
		default:
			parser = new ByteParser(dir);
			break;
		}

		return parser;
	}
}
