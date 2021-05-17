package project.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import project.convert.ProcessDate;
import project.entity.TaskToEmployee;
import project.repository.EmployeeRepository;
import project.repository.TaskToEmployeeRepository;
import project.service.UserTaskService;

@Controller
public class UserController {
	@Autowired
	private UserTaskService userTaskService;
	
	@Autowired
	private EmployeeRepository empRepo;
	
	@GetMapping("/employee/updateProject")
	public String homeUser(Model model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String empEmail = userDetails.getUsername();
		String nameEmp = empRepo.findByEmail(empEmail).getName();
		List<TaskToEmployee> listTaskToEmp = userTaskService.taskNow();
		for(TaskToEmployee tte: listTaskToEmp) {
			tte.getTask().setCreateDate(ProcessDate.atomDate(tte.getTask().getCreateDate()));
			tte.getTask().setEndDate(ProcessDate.atomDate(tte.getTask().getEndDate()));
		}
		model.addAttribute("totalTask", listTaskToEmp.size());
		model.addAttribute("nameEmp", nameEmp);
		model.addAttribute("listTaskToEmp", listTaskToEmp);
		return "user_home";
	}
}
