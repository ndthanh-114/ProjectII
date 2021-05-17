package project.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import project.entity.Employee;
import project.repository.EmployeeRepository;
import project.service.EmployeeService;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
	@Autowired
	private EmployeeService empService;

	@Autowired
	private BCryptPasswordEncoder passwordEncode;
	
	@Autowired
	private EmployeeRepository empRepo;
	
	public RegistrationController(EmployeeService empService) {
		super();
		this.empService = empService;
	}
	
	@GetMapping
	public String registrationForm(Model model) {
		model.addAttribute("employee", new Employee());

		return "registration";
	}
	@PostMapping
	public String saveRegistration(@ModelAttribute("employee")Employee emp) {
		Employee checkEmp = empRepo.findByEmail(emp.getEmail());
		
		System.out.println(emp);
		if(checkEmp !=null) {
			return "redirect:/registration?errorMessage";
		}else {
			checkEmp = empRepo.findByPhone(emp.getPhone());
			if(checkEmp !=null) {
				return "redirect:/registration?errorMessage";
			}
			else
			empRepo.save(new Employee(emp.getName(), emp.getEmail(), emp.getPhone(), passwordEncode.encode(emp.getPassword()),	emp.getRole()));
		}
		return "redirect:/registration?success";
	}
}
