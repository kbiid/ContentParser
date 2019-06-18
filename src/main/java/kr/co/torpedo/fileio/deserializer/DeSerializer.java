package kr.co.torpedo.fileio.deserializer;

import java.io.File;
import java.util.ArrayList;

import kr.co.torpedo.fileio.CommonSerializer;
import kr.co.torpedo.fileio.Serializable;
import kr.co.torpedo.fileio.model.Employee;

public abstract class DeSerializer extends CommonSerializer implements Serializable {
	private ArrayList<Employee> employeeList;

	public DeSerializer() {
		dirfile = new File(DIR);
		setFileSawonPath();
		employeeList = new ArrayList<>();
	}

	public ArrayList<Employee> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(ArrayList<Employee> employeeList) {
		this.employeeList = employeeList;
	}

	@Override
	public void setFileSawonPath() {
		makefile = new File(DIR + fileName);
	}

	@Override
	public void setFilePathIntern() {
		makefile = new File(DIR + fileNameIntern);
	}

	public boolean checkDir() {
		if (dirfile == null || !dirfile.exists()) {
			return false;
		}
		return true;
	}

	public boolean checkFile() {
		if (makefile == null || !makefile.exists()) {
			return false;
		}
		return true;
	}

	public abstract void deSelialize();

	public abstract void readEmployee(Employee emp, Object oin);

	public void addList(Employee employee) {
		employeeList.add(employee);
	}

	public void showEmployeeList() {
		for (Employee employee : employeeList) {
			System.out.print(employee);
		}
		System.out.println("----------------------------------------------------------------------");
		employeeList.clear();
	}
	
	public void deSerializeEmployee() {
		setFileSawonPath();
		deSelialize();
	}

	public void deSrializeWithIntern() {
		setFilePathIntern();
		deSelialize();
	}
}
