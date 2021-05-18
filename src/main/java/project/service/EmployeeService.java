package project.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import project.entity.Employee;
import project.web.dto.DetailEmployeeDTO;

public interface EmployeeService extends UserDetailsService{
	public void saveEmployee(Employee emp);
	List<Employee> searchEmployee(String keyword, String role);
	
	List<DetailEmployeeDTO> detaiEmployee(Integer id);
	
	
	Page<Employee> pageAdminEmployee(String keyword, String role, Pageable pageable);
}
