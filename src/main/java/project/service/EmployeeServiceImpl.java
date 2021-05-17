package project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import project.entity.Employee;
import project.entity.TaskToEmployee;
import project.repository.EmployeeRepository;
import project.repository.TaskToEmployeeRepository;
import project.web.dto.DetailEmployeeDTO;
import project.web.dto.ProjectDTO;
import project.web.dto.TaskDTO;
import project.web.dto.UserProjectDetailDTO;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository empRepo;
	
	@Autowired
	TaskToEmployeeRepository taskToEmpRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Employee emp = empRepo.findByEmail(email);
		System.out.println("Email " + email + " !!!");
		System.out.println("Employee= " + emp);

		if (emp == null) {
			throw new UsernameNotFoundException("Email " //
					+ email + " was not found in the database");
		}

		// USER, ADMIN
		String role = emp.getRole();

		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();

		// ROLE_EMPLOYEE, ROLE_MANAGER
		GrantedAuthority authority = new SimpleGrantedAuthority(role);

		grantList.add(authority);

		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		UserDetails userDetails = (UserDetails) new User(emp.getEmail(), //
				emp.getPassword(), true, accountNonExpired, //
				credentialsNonExpired, accountNonLocked, grantList);
		return userDetails;
	}

	@Override
	public void saveEmployee(Employee emp) {
		// TODO Auto-generated method stub
		
	}

	public List<Employee> lsearchEmployee(String keyword) {
		
		return empRepo.findAll();
	}

	@Override
	public List<Employee> searchEmployee(String keyword, String role) {
		if(keyword!=null) {
			return empRepo.searchFillAll(keyword, role);
		}
		return empRepo.findByRoleOrderByNameAsc(role);
	}

	@Override
	public List<DetailEmployeeDTO> detaiEmployee(Integer id) {
		
		
		Employee emp = empRepo.findById(id).get();
		
		List<TaskToEmployee> lists = taskToEmpRepo.findByEmployee(emp);
		List<DetailEmployeeDTO> rs = new ArrayList<DetailEmployeeDTO>();
		for(TaskToEmployee tte: lists) {
			ProjectDTO project = new ProjectDTO(tte.getTask().getProject().getId(), tte.getTask().getProject().getName());
			TaskDTO task = new TaskDTO(tte.getTask().getId(), tte.getTask().getDecription());
			Integer progress = tte.getTask().getProgressTask();
			rs.add(new DetailEmployeeDTO(project, task, progress));
		}
		
		
		return rs;
	}

	
	

}
