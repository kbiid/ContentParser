package kr.co.torpedo.fileio.parser;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InvalidClassException;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import kr.co.torpedo.fileio.model.Employee;
import kr.co.torpedo.fileio.model.Intern;

public class JsonParser extends Parser {

	public JsonParser(String dir) {
		super(dir);
		getFileManager().setFileName("sawon-v1.json");
		getFileManager().setFileNameIntern("sawon-v2.json");
	}

	@Override
	protected void selialize() {
		JsonObject jObj = null;
		JsonObject empObj = new JsonObject();
		JsonArray array = new JsonArray();
		for (Employee emp : getDataManager().getEmployeeList()) {
			jObj = new JsonObject();

			jObj.addProperty("name", emp.getName());
			jObj.addProperty("age", emp.getAge());
			jObj.addProperty("phoneNumber", emp.getPhoneNumber());
			jObj.addProperty("department", emp.getDepartMent());
			jObj.addProperty("email", emp.getEmail());
			if (emp instanceof Intern) {
				jObj.addProperty("term", ((Intern) emp).getTerm());
			} else {
				String str = "정직원";
				jObj.addProperty("term", str);
			}
			array.add(jObj);
			empObj.add("employee", array);
			writeEmployee(empObj.toString());
		}
		getDataManager().getEmployeeList().clear();
	}

	@Override
	protected void writeEmployee(Object obj) {
		String json = null;
		if (obj instanceof String) {
			json = (String) obj;
		} else {
			try {
				throw new InvalidClassException("String이 아님");
			} catch (InvalidClassException e) {
				Parser.invalidFileLogger.error("JSONSerializer InvalidClassException : " + e);
			}
		}

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(getFileManager().getMakefile()))) {
			bw.write(json);
			bw.write("\r\n");
		} catch (IOException e) {
			Parser.invalidFileLogger.error("JSONSerializer IOException : " + e);
		}
	}

	@Override
	public void deSelialize() {
		getDataManager().getEmployeeList().clear();
		com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
		try {
			Object obj = parser.parse(new FileReader(getFileManager().getMakefile()));
			Employee emp = null;
			JsonObject jsonObj = (JsonObject) obj;
			readEmployee(emp, jsonObj);
		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			Parser.invalidFileLogger.error("JSONDeSerializer Exception : " + e);
		}
	}

	@Override
	public void readEmployee(Employee emp, Object obj) {
		JsonObject jsonObj = null;

		if (obj instanceof JsonObject) {
			jsonObj = (JsonObject) obj;
		} else {
			try {
				throw new InvalidClassException("JsonObject가 아님");
			} catch (InvalidClassException e) {
				Parser.invalidFileLogger.error("JSONDeSerializer Exception : " + e);
			}
		}
		JsonArray array = ((JsonArray) jsonObj.get("employee"));
		for (int i = 0; i < array.size(); i++) {
			JsonObject jobj = (JsonObject) array.get(i);
			String name = jobj.get("name").toString();
			int age = Integer.parseInt(jobj.get("age").toString());
			String phoneNumber = jobj.get("phoneNumber").toString();
			String department = jobj.get("department").toString();
			String email = jobj.get("email").toString();
			String term = jobj.get("term").toString();

			if (term.equals("\"정직원\"")) {
				emp = new Employee(name, age, phoneNumber, department, email);
			} else {
				emp = new Intern(name, age, phoneNumber, department, email, Integer.parseInt(term));
			}
			getDataManager().addList(emp);
		}
	}
}
