package kr.co.torpedo.fileio;

import kr.co.torpedo.fileio.parser.ByteParser;
import kr.co.torpedo.fileio.parser.CSVParser;
import kr.co.torpedo.fileio.parser.JsoNParser;
import kr.co.torpedo.fileio.parser.Parser;
import kr.co.torpedo.fileio.parser.XmlParser;

public class SerializerMaker {
	public static Parser makeSerializer(String str, String dir) {
		switch (str.toLowerCase()) {
		case "byte":
			return new ByteParser(dir);
		case "csv":
			return new CSVParser(dir);
		case "xml":
			return new XmlParser(dir);
		case "json":
			return new JsoNParser(dir);
		default:
			return new ByteParser(dir);
		}
	}
}
