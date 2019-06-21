package kr.co.torpedo.fileio.manager;

import java.util.ArrayList;

import kr.co.torpedo.fileio.domain.Employee;

public class DataManager {
	private ArrayList<Employee> employeeList;

	public DataManager() {
		employeeList = new ArrayList<>();
	}

	public ArrayList<Employee> getEmployeeList() {
		return employeeList;
	}

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
}
