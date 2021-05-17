package project.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import project.entity.Employee;
import project.repository.EmployeeRepository;

@Controller
public class MainController {
	@Autowired
	private EmployeeRepository empRepo;
	

	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/")
	public String homePage() {
		return "index";
	}
	
	@GetMapping("/403")
	public String denyRequest() {
		return "403";
	}
	
	@GetMapping("/userInfo")
	public String userInfo(Model model) {
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String emailEmp = userDetails.getUsername();
	
		Employee emp = empRepo.findByEmail(emailEmp);
		
		model.addAttribute("employee", emp);
		return "userInfo";
	}
}
