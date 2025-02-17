package model;


import java.util.Date;

public class Employee {
	private String code;
	private String name;
	private int age;
	private Date dateOfBirth;
	
	public Employee() {
	}
	
	public Employee(String code, String name, int age, Date dateOfBirth) {
		super();
		this.code = code;
		this.name = name;
		this.age = age;
		this.dateOfBirth = dateOfBirth;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	
	
}
