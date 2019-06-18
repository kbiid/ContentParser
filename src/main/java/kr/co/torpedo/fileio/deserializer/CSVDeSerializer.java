package kr.co.torpedo.fileio.deserializer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InvalidClassException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

import kr.co.torpedo.fileio.Serializable;
import kr.co.torpedo.fileio.model.Employee;
import kr.co.torpedo.fileio.model.Intern;

public class CSVDeSerializer extends DeSerializer {
	private List<String[]> data;

	public CSVDeSerializer() {
		data = new ArrayList<String[]>();
		setFileName("sawon-v1.csv");
		setFileNameIntern("sawon-v2.csv");
	}

	@Override
	public void deSelialize() {
		Employee emp = null;
		data.clear();

		try (CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(getMakefile()), "EUC-KR"))) {
			readEmployee(emp, reader);
			setDataToEmployee();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			Serializable.invalidFileLogger.error("CSVDeSerializer Exception : " + e);
		} catch (IOException e) {
			Serializable.invalidFileLogger.error("CSVDeSerializer Exception : " + e);
		}
	}

	@Override
	public void readEmployee(Employee emp, Object obj) {
		CSVReader reader = null;
		String[] s;

		if ((obj instanceof CSVReader)) {
			reader = (CSVReader) obj;
		} else {
			try {
				throw new InvalidClassException("CSVReader 클래스가 아님");
			} catch (InvalidClassException e) {
				Serializable.invalidFileLogger.error("CSVDeSerializer Exception : " + e);
			}
		}
		try {
			while ((s = reader.readNext()) != null) {
				data.add(s);
			}
		} catch (IOException e) {
			Serializable.invalidFileLogger.error("CSVDeSerializer Exception : " + e);
		}
	}

	public void setDataToEmployee() {
		Employee emp = null;

		for (String[] str : data) {
			if (!IsIntern(str)) {
				emp = new Employee(str[0], Integer.parseInt(str[1]), str[2], str[3], str[4]);
				getEmployeeList().add(emp);
			} else {
				emp = new Intern(str[0], Integer.parseInt(str[1]), str[2], str[3], str[4], Integer.parseInt(str[5]));
				getEmployeeList().add(emp);
			}
		}
	}

	public boolean IsIntern(String[] str) {
		if (str.length > 5 && !str[5].equals("정직원")) {
			return true;
		} else {
			return false;
		}
	}
}
