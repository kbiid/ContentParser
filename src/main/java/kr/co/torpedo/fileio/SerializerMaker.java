package kr.co.torpedo.fileio;

import kr.co.torpedo.fileio.deserializer.ByteDeSerializer;
import kr.co.torpedo.fileio.deserializer.CSVDeSerializer;
import kr.co.torpedo.fileio.deserializer.DeSerializer;
import kr.co.torpedo.fileio.deserializer.JSONDeSerializer;
import kr.co.torpedo.fileio.deserializer.XMLDeSerializer;
import kr.co.torpedo.fileio.serializer.ByteSerializer;
import kr.co.torpedo.fileio.serializer.CSVSerializer;
import kr.co.torpedo.fileio.serializer.JSONSerializer;
import kr.co.torpedo.fileio.serializer.Serializer;
import kr.co.torpedo.fileio.serializer.XMLSerializer;

public class SerializerMaker {
	public static Serializer makeSerializer(String str) {
		switch (str.toLowerCase()) {
		case "byte":
			return new ByteSerializer();
		case "csv":
			return new CSVSerializer();
		case "xml":
			return new XMLSerializer();
		case "json":
			return new JSONSerializer();
		default:
			return new ByteSerializer();
		}
	}

	public static DeSerializer makeDeSerializer(String str) {
		switch (str.toLowerCase()) {
		case "byte":
			return new ByteDeSerializer();
		case "csv":
			return new CSVDeSerializer();
		case "xml":
			return new XMLDeSerializer();
		case "json":
			return new JSONDeSerializer();
		default:
			return new ByteDeSerializer();
		}
	}
}
