package project.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import project.convert.ProcessDate;
import project.entity.TaskToEmployee;
import project.repository.EmployeeRepository;
import project.repository.TaskToEmployeeRepository;
import project.service.UserTaskService;
import project.web.dto.DisplayProject;

@Controller
public class UserController {
	@Autowired
	private UserTaskService userTaskService;
	
	@Autowired
	private EmployeeRepository empRepo;


	@GetMapping("/employee/updateProject")
	public String homeUser(Model model) {
		return homePageUser(model, 1);
		
	}
	
	@GetMapping("/employee/updateProject/{pageNo}")
	public String homePageUser(Model model,@PathVariable(value = "pageNo") int pageNo) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String empEmail = userDetails.getUsername();
		String nameEmp = empRepo.findByEmail(empEmail).getName();
		List<TaskToEmployee> listTaskToEmp = userTaskService.taskNow();
		for(TaskToEmployee tte: listTaskToEmp) {
			tte.getTask().setCreateDate(ProcessDate.atomDate(tte.getTask().getCreateDate()));
			tte.getTask().setEndDate(ProcessDate.atomDate(tte.getTask().getEndDate()));
		}
		

		
		Integer pageSize = 3;
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		int start = (int) pageable.getOffset();
		int end = (int) ((start + pageable.getPageSize()) > listTaskToEmp.size() ? listTaskToEmp.size()
				  : (start + pageable.getPageSize()));
		Page<TaskToEmployee> pages = new PageImpl<TaskToEmployee>(listTaskToEmp.subList(start, end), pageable, listTaskToEmp.size());
		
		
		listTaskToEmp = pages.toList();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", pages.getTotalPages());
		model.addAttribute("totalItems", pages.getTotalElements());
		
		model.addAttribute("totalTask", listTaskToEmp.size());
		model.addAttribute("nameEmp", nameEmp);
		model.addAttribute("listTaskToEmp", listTaskToEmp);
		return "user_home";
	}
}
