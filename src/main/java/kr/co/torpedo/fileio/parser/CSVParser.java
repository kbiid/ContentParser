package kr.co.torpedo.fileio.parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InvalidClassException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import kr.co.torpedo.fileio.model.Employee;
import kr.co.torpedo.fileio.model.Intern;

public class CSVParser extends Parser {
	private List<String[]> data;
	private String[] strList;

	public CSVParser(String dir) {
		super(dir);
		getFileManager().setFileName("sawon-v1.csv");
		getFileManager().setFileNameIntern("sawon-v2.csv");
		data = new ArrayList<String[]>();
		strList = new String[6];
	}

	@Override
	protected void selialize() {
		getFileManager().checkAndMakeDir();
		getFileManager().checkAndMakeFile();
		data.clear();
		setEmployeeData();

		try (CSVWriter cw = new CSVWriter(
				new OutputStreamWriter(new FileOutputStream(getFileManager().getMakefile()), "EUC-KR"))) {
			writeEmployee(cw);
			getDataManager().getEmployeeList().clear();
		} catch (IOException e) {
			Parser.invalidFileLogger.error("CSVSerializer IOException : " + e);
		}
	}

	@Override
	protected void writeEmployee(Object obj) {
		if ((obj instanceof CSVWriter)) {
			CSVWriter cw = (CSVWriter) obj;
			Iterator<String[]> it = data.iterator();

			while (it.hasNext()) {
				String[] str = (String[]) it.next();
				cw.writeNext(str);
			}
		} else {
			try {
				throw new InvalidClassException("Not CSVWriter Class");
			} catch (InvalidClassException e) {
				Parser.invalidFileLogger.error("CSVSerializer InvalidClassException : " + e);
			}
		}
	}

	public void setEmployeeData() {
		for (Employee emp : getDataManager().getEmployeeList()) {
			strList = new String[6];
			if (!isIntern(emp)) {
				strList[0] = emp.getName();
				strList[1] = Integer.toString(emp.getAge());
				strList[2] = emp.getPhoneNumber();
				strList[3] = emp.getDepartMent();
				strList[4] = emp.getEmail();
				strList[5] = "정직원";
			} else {
				strList[0] = emp.getName();
				strList[1] = Integer.toString(emp.getAge());
				strList[2] = emp.getPhoneNumber();
				strList[3] = emp.getDepartMent();
				strList[4] = emp.getEmail();
				strList[5] = Integer.toString(((Intern) emp).getTerm());
			}
			data.add(strList);
		}
	}

	private boolean isIntern(Employee emp) {
		if (emp instanceof Intern) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void deSelialize() {
		getDataManager().getEmployeeList().clear();
		Employee emp = null;
		data.clear();

		try (CSVReader reader = new CSVReader(
				new InputStreamReader(new FileInputStream(getFileManager().getMakefile()), "EUC-KR"))) {
			readEmployee(emp, reader);
			setDataToEmployee();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			Parser.invalidFileLogger.error("CSVDeSerializer Exception : " + e);
		} catch (IOException e) {
			Parser.invalidFileLogger.error("CSVDeSerializer Exception : " + e);
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
				Parser.invalidFileLogger.error("CSVDeSerializer Exception : " + e);
			}
		}
		try {
			while ((s = reader.readNext()) != null) {
				data.add(s);
			}
		} catch (IOException e) {
			Parser.invalidFileLogger.error("CSVDeSerializer Exception : " + e);
		}
	}

	public void setDataToEmployee() {
		Employee emp = null;

		for (String[] str : data) {
			if (!isReadDataIntern(str)) {
				emp = new Employee(str[0], Integer.parseInt(str[1]), str[2], str[3], str[4]);
				getDataManager().getEmployeeList().add(emp);
			} else {
				emp = new Intern(str[0], Integer.parseInt(str[1]), str[2], str[3], str[4], Integer.parseInt(str[5]));
				getDataManager().getEmployeeList().add(emp);
			}
		}
	}

	public boolean isReadDataIntern(String[] str) {
		if (str.length > 5 && !str[5].equals("정직원")) {
			return true;
		} else {
			return false;
		}
	}
}
