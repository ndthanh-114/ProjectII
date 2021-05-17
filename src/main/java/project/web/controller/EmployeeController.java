package project.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import project.entity.Employee;

import project.repository.EmployeeRepository;
import project.repository.TaskToEmployeeRepository;
import project.service.CustomService;
import project.service.EmployeeService;
import project.web.dto.DetailEmployeeDTO;
import project.web.dto.UserProjectDetailDTO;

@Controller
public class EmployeeController {
	@Autowired
	private EmployeeService empService;

	@Autowired
	private BCryptPasswordEncoder passwordEncode;

	@Autowired
	private TaskToEmployeeRepository taskToEmpRepo;

	@Autowired
	private CustomService customService;

	@Autowired
	private EmployeeRepository empRepo;

	@RequestMapping("/admin/managerEmployee")
	public String viewListEmployees(Model model, @Param("keyword") String keyword) {
		List<Employee> users = empService.searchEmployee(keyword, "ROLE_USER");
		System.out.println(users.size());

		model.addAttribute("users", users);
		model.addAttribute("keyword", keyword);
//		return findPaginated(1, "name", "asc", model);
		return "/admin_managerEmployee";
	}

	@GetMapping("admin/managerEmployee/showNewEmployeeForm")
	public String showNewEmployeeForm(Model model) {
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "new_employee";
	}

	@PostMapping("/admin/managerEmployee/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") Employee employee) {

		System.out.println("Password: " + employee.getPassword());
		if (employee.getRole() == null) {
			Employee check = empRepo.findByEmail(employee.getEmail());
			if (check != null) {
				return "redirect:/admin/managerEmployee/showNewEmployeeForm?errorMs";

			}
			check = empRepo.findByPhone(employee.getPhone());
			if (check != null) {
				return "redirect:/admin/managerEmployee/showNewEmployeeForm?errorMs";
			}
			employee.setRole("ROLE_USER");
			if (employee.getPassword().equals(null)) {
				System.out.println("Set password 123456");
				employee.setPassword(passwordEncode.encode("123456"));
			} else {
				employee.setPassword(passwordEncode.encode(employee.getPassword()));
			}

			empRepo.save(employee);
		} else {
			if (employee.getPassword() == null) {
				System.out.println("Set password 123456");
				employee.setPassword(passwordEncode.encode("123456"));
			}

			empRepo.save(employee);
		}

		if (employee.getRole().equals("ROLE_ADMIN")) {
			System.out.println("Quyen: " + employee.getRole());
			customService.changeRoleEmployee(employee);
		}
		return "redirect:/admin/managerEmployee";
	}

	@GetMapping("/admin/managerEmployee/showFormatForUpdate/{id}")
	public String showFormatForUpdate(@PathVariable(value = "id") Integer id, Model model) {
		Employee employee = empRepo.findAllById(id);
		System.out.println(employee);
		model.addAttribute("employee", employee);
		return "update_employee";
	}

	@GetMapping("/admin/managerEmployee/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable(value = "id") Integer id) {

		this.empRepo.deleteById(id);

		return "redirect:/admin/managerEmployee";
	}

	@GetMapping("/rest/admin/employee")
	@ResponseBody
	public List<DetailEmployeeDTO> detailEmployee(Integer id) {
		
		return empService.detaiEmployee(id); 

	}
}
