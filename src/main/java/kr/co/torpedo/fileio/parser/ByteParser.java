package kr.co.torpedo.fileio.parser;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import kr.co.torpedo.fileio.model.Employee;

public class ByteParser extends Parser {

	public ByteParser(String dir) {
		super(dir);
		getFileManager().setFileName("sawon-v1.txt");
		getFileManager().setFileNameIntern("sawon-v2.txt");
	}

	@Override
	protected void selialize() {
		getFileManager().checkAndMakeDir();
		getFileManager().checkAndMakeFile();

		try (FileOutputStream fout = new FileOutputStream(getFileManager().getMakefile());
				ObjectOutputStream oout = new ObjectOutputStream(fout)) {
			writeEmployee(oout);
			getDataManager().getEmployeeList().clear();
		} catch (Exception e) {
			Parser.invalidFileLogger.error("ByteSerializer Exception : " + e);
		}
	}

	@Override
	protected void writeEmployee(Object obj) {
		if ((obj instanceof ObjectOutputStream)) {
			ObjectOutputStream oout = (ObjectOutputStream) obj;
			for (Employee employee : getDataManager().getEmployeeList()) {
				try {
					oout.writeObject(employee);
				} catch (IOException e) {
					Parser.invalidFileLogger.error("ByteSerializer IOException : " + e);
				}
			}
		} else {
			try {
				throw new InvalidClassException("Not ObjectOutputStream Class");
			} catch (InvalidClassException e) {
				Parser.invalidFileLogger.error("ByteSerializer InvalidClassException : " + e);
			}
		}
	}

	@Override
	public void deSelialize() {
		getFileManager().checkAndMakeDir();
		getFileManager().checkAndMakeFile();
		getDataManager().getEmployeeList().clear();
		Employee emp = null;

		try (FileInputStream fin = new FileInputStream(getFileManager().getMakefile());
				ObjectInputStream oin = new ObjectInputStream(fin)) {
			readEmployee(emp, oin);
		} catch (IOException e) {
			Parser.invalidFileLogger.error("ByteDeSerializer Exception : " + e);
		}
	}

	@Override
	public void readEmployee(Employee emp, Object obj) {
		ObjectInputStream oin = null;

		if ((obj instanceof ObjectInputStream)) {
			oin = (ObjectInputStream) obj;
		} else {
			try {
				throw new InvalidClassException("Not ObjectOutputStream Class");
			} catch (InvalidClassException e) {
				Parser.invalidFileLogger.error("ByteDeSerializer Exception : " + e);
			}
		}
		try {
			while (true) {
				emp = (Employee) oin.readObject();
				getDataManager().addList(emp);
			}
		} catch (EOFException e) {
			Parser.invalidFileLogger.error("ByteDeSerializer Exception : " + e);
		} catch (IOException | ClassNotFoundException e) {
			Parser.invalidFileLogger.error("ByteDeSerializer Exception : " + e);
		}
	}
}
