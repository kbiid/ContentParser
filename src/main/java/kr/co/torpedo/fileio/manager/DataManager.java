package kr.co.torpedo.fileio.manager;

import java.util.ArrayList;

import kr.co.torpedo.fileio.model.Employee;
import kr.co.torpedo.fileio.model.Intern;

public class DataManager {
	private ArrayList<Employee> employeeList;

	public DataManager() {
		employeeList = new ArrayList<>();
	}

	public void addEmployeeToList() {
		employeeList.add(new Employee("김기주", 26, "010-2972-4701", "개발부", "kbiid@naver.com"));
		employeeList.add(new Employee("홍길동", 23, "010-1234-5678", "영업부", "hong@naver.com"));
		employeeList.add(new Employee("장발장", 25, "010-5678-1234", "인사부", "jang@naver.com"));
		employeeList.add(new Employee("김광현", 30, "010-4789-5131", "개발부", "kim@naver.com"));
		employeeList.add(new Employee("김성현", 28, "010-7465-1459", "인사부", "sung@naver.com"));
	}

	public void addInternToList() {
		employeeList.add(new Intern("김인턴", 20, "010-3475-1496", "개발부", "intern@naver.com", 6));
		employeeList.add(new Intern("이인턴", 27, "010-8754-7894", "인사부", "lee@naver.com", 12));
		employeeList.add(new Intern("심인턴", 32, "010-8522-0537", "영업부", "sim@naver.com", 8));
		employeeList.add(new Intern("박인턴", 24, "010-3694-7891", "인사부", "park@naver.com", 12));
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
