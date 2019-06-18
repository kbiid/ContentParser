package kr.co.torpedo.fileio.serializer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InvalidClassException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import kr.co.torpedo.fileio.Serializable;
import kr.co.torpedo.fileio.model.Employee;
import kr.co.torpedo.fileio.model.Intern;

public class JSONSerializer extends Serializer {
	public JSONSerializer() {
		setFileName("sawon-v1.json");
		setFileNameIntern("sawon-v2.json");
	}

	@Override
	protected void selialize() {
		JsonObject jObj = null;
		JsonObject empObj = new JsonObject();
		JsonArray array = new JsonArray();
		for (Employee emp : getEmployeeList()) {
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
				Serializable.invalidFileLogger.error("JSONSerializer InvalidClassException : " + e);
			}
		}

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(getMakefile()))) {
			bw.write(json);
			bw.write("\r\n");
		} catch (IOException e) {
			Serializable.invalidFileLogger.error("JSONSerializer IOException : " + e);
		}
	}
}
