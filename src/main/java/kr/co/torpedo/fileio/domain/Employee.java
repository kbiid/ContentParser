package kr.co.torpedo.fileio.domain;

import java.io.Serializable;

/**
 * 사원에 관한 클래스
 * 
 * @author user
 *
 */
public class Employee implements Serializable {
	private static final long serialVersionUID = -1908203776903222872L;
	private String name;
	private int age;
	private String phoneNumber;
	private String department;
	private String email;

	public Employee(String name, int age, String phoneNumber, String department, String email) {
		this.name = name;
		this.age = age;
		this.phoneNumber = phoneNumber;
		this.department = department;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getDepartMent() {
		return department;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return String.format("Employee{name='%s', age='%d', phoneNumber='%s', department='%s', email='%s'} \n", name,
				age, phoneNumber, department, email);
	}
}
