package kr.co.torpedo.fileio.model;

public class Intern extends Employee {
	private static final long serialVersionUID = 4229400149178229867L;
	private int term;

	public Intern(String name, int age, String phoneNumber, String department, String email, int term) {
		super(name, age, phoneNumber, department, email);
		this.term = term;
	}

	public int getTerm() {
		return term;
	}

	@Override
	public String toString() {
		return String.format("Intern [term=%d] ", term) + super.toString();
	}
}
