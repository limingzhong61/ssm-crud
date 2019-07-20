package com.atguigu.crud.bean;

import javax.validation.constraints.Pattern;

public class Employee {
	private Integer empId;
	@Pattern(regexp = "(^[a-zA-Z0-9_-]{4,16}$)|(^[\\\\u2E80-\\\\u9FFF]{2,5})", message = "用户名必须是4-16位英文数字或2-5位中文")
	private String empName;

	private String gender;
	//@Email
	@Pattern(regexp = "^([a-zA-Z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$", message = "jsr303邮箱格式错误")
	private String email;

	private Integer dId;

	private Department department;

	public Employee() {
		// TODO Auto-generated constructor stub
	}

	public Employee(Integer empId, String empName, String gender, String email, Integer dId) {
		super();
		this.empId = empId;
		this.empName = empName;
		this.gender = gender;
		this.email = email;
		this.dId = dId;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName == null ? null : empName.trim();
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender == null ? null : gender.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public Integer getdId() {
		return dId;
	}

	public void setdId(Integer dId) {
		this.dId = dId;
	}

	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", empName=" + empName + ", gender=" + gender + ", email=" + email
				+ ", dId=" + dId + ", department=" + department + "]";
	}
}