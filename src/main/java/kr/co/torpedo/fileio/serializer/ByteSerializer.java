package kr.co.torpedo.fileio.serializer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectOutputStream;

import kr.co.torpedo.fileio.Serializable;
import kr.co.torpedo.fileio.model.Employee;

public class ByteSerializer extends Serializer {
	public ByteSerializer() {
		setFileName("sawon-v1.txt");
		setFileNameIntern("sawon-v2.txt");
	}

	@Override
	public void selialize() {
		checkAndMakeDir();
		makeFile();

		try (FileOutputStream fout = new FileOutputStream(super.getMakefile());
				ObjectOutputStream oout = new ObjectOutputStream(fout)) {
			writeEmployee(oout);
		} catch (Exception e) {
			Serializable.invalidFileLogger.error("ByteSerializer Exception : " + e);
		}
	}

	@Override
	protected void writeEmployee(Object obj) {
		if ((obj instanceof ObjectOutputStream)) {
			ObjectOutputStream oout = (ObjectOutputStream) obj;
			for (Employee employee : super.getEmployeeList()) {
				try {
					oout.writeObject(employee);
				} catch (IOException e) {
					Serializable.invalidFileLogger.error("ByteSerializer IOException : " + e);
				}
			}
		} else {
			try {
				throw new InvalidClassException("ObjectOutputStream 클래스가 아님");
			} catch (InvalidClassException e) {
				Serializable.invalidFileLogger.error("ByteSerializer InvalidClassException : " + e);
			}
		}
	}
}
