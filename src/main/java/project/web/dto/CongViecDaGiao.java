package project.web.dto;

import java.util.ArrayList;
import java.util.List;

import project.entity.Employee;

public class CongViecDaGiao {
	List<Employee> employee= new ArrayList<Employee>();
	List<Employee> modifyBy= new ArrayList<Employee>();
	
	public CongViecDaGiao() {
		super();
	}

	public CongViecDaGiao(List<Employee> employee, List<Employee> modifyBy) {
		super();
		this.employee = employee;
		this.modifyBy = modifyBy;
	}

	public List<Employee> getEmployee() {
		return employee;
	}

	public void setEmployee(List<Employee> employee) {
		this.employee = employee;
	}

	public List<Employee> getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(List<Employee> modifyBy) {
		this.modifyBy = modifyBy;
	}
	
	
}
