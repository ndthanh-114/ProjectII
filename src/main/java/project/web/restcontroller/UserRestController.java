package project.web.restcontroller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import project.entity.Employee;
import project.entity.Project;
import project.entity.Task;
import project.entity.TaskToEmployee;
import project.repository.EmployeeRepository;
import project.repository.ProjectRepository;
import project.repository.TaskRepository;
import project.repository.TaskToEmployeeRepository;
import project.service.UserTaskService;
import project.web.dto.ProjectDTO;
import project.web.dto.TaskDTO;
import project.web.dto.UserProjectDetailDTO;
import project.web.dto.UserTaskDTO;
import project.web.dto.UserUpdateDTO;

@RestController
public class UserRestController {

	@Autowired
	private TaskToEmployeeRepository taskToEmpRepo;

	@Autowired
	private EmployeeRepository empRepo;

	@Autowired
	private TaskRepository taskRepo;
	
	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private UserTaskService userTaskService;

	
	@GetMapping("/rest/employee/project")
	public List<ProjectDTO> listUpdateTask() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String emailUser = userDetails.getUsername();
		Employee emp = empRepo.findByEmail(emailUser);
		List<TaskToEmployee> listTask = taskToEmpRepo.findByEmployee(emp);
		List<ProjectDTO> rs = new ArrayList<ProjectDTO>();
		Set<Integer> check = new HashSet<Integer>();
		for (TaskToEmployee tte : listTask) {
			Task t = tte.getTask();
			if (!(t.getProgressTask() == 101 || t.getProgressTask() == 102)) {
				if (!check.contains(t.getProject().getId())) {
					check.add(t.getProject().getId());
					ProjectDTO pjDTO = new ProjectDTO(t.getProject().getId(), t.getProject().getName());
					
					rs.add(pjDTO);
				}
			}
		}

		return rs;
	}

	@GetMapping("/rest/employee/project/{id}")
	public List<UserTaskDTO> listUpdateTask2(@PathVariable("id") Integer projectID) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String emailUser = userDetails.getUsername();
		Employee emp = empRepo.findByEmail(emailUser);
		List<TaskToEmployee> listTask = taskToEmpRepo.findByEmployee(emp);
		List<UserTaskDTO> rs = new ArrayList<UserTaskDTO>();

		for (TaskToEmployee tte : listTask) {
			Task t = tte.getTask();
			if (t.getProject().getId() == projectID && !(t.getProgressTask() == 101 || t.getProgressTask() == 102)) {
				rs.add(new UserTaskDTO(t.getId(), t.getDecription(), t.getProgressTask()));
			}

		}
		return rs;
	}

	@PostMapping("rest/employee/updateProgress")
	public UserUpdateDTO updateProgress(@RequestBody UserUpdateDTO userUpdateDTO) {
		System.out.println("TaskID: " + userUpdateDTO.getTaskID());
		System.out.println("progress: " + userUpdateDTO.getProgress());
		System.out.println("date: " + userUpdateDTO.getCompleteDate());

		Task t = taskRepo.findById(userUpdateDTO.getTaskID()).get();
		String completeDate = null;

		if (userUpdateDTO.getProgress() != 100) {
			t.setProgressTask(userUpdateDTO.getProgress());
			taskRepo.save(t);

		} else if (userUpdateDTO.getProgress() == 100) {
			LocalDateTime myDateObj = LocalDateTime.now();
			System.out.println("Before formatting: " + myDateObj);
			DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

			completeDate = myDateObj.format(myFormatObj);

			t.setProgressTask(userUpdateDTO.getProgress());
			t.setCompleteDate(completeDate);
			taskRepo.save(t);

		}
		UserUpdateDTO userUpDTO = new UserUpdateDTO(userUpdateDTO.getTaskID(), userUpdateDTO.getProgress(),
				completeDate);
		return userUpDTO;
	}
	
	@GetMapping("/rest/detail")
	public UserProjectDetailDTO detailProject(Integer id) {
		
		
		return userTaskService.detailProject(id);
		
	}

}
