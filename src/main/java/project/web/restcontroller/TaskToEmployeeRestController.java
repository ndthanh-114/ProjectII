package project.web.restcontroller;

import java.awt.SystemTray;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import project.entity.Employee;
import project.entity.Task;
import project.entity.TaskToEmployee;
import project.repository.EmployeeRepository;
import project.repository.TaskRepository;
import project.repository.TaskToEmployeeRepository;
import project.service.CustomService;
import project.service.EmployeeService;
import project.service.TaskToEmployeeSerive;
import project.web.dto.CongViecDaGiao;
import project.web.dto.TaskToEmployeeDTO;
import project.web.dto.UserUpdateDTO;

@RestController
public class TaskToEmployeeRestController {
	@Autowired
	private EmployeeRepository empRepo;
	
	@Autowired
	private EmployeeService empService;
	
	@Autowired
	private TaskToEmployeeSerive taskToEmpService;
	
	@Autowired
	private TaskRepository taskRepo;

	@Autowired
	private TaskToEmployeeRepository taskToEmpRepo;
	
	@Autowired
	private CustomService customService;
	
	@GetMapping("/listEmployee")
	public List<Employee> listAllEmployee(){
		return empService.searchEmployee(null, "ROLE_USER");
	}
	
	
	@PostMapping("/rest/adminUpdateTaskToEmployee")
	public ResponseEntity<?> adminUpdateTaskToEmployee(@RequestBody TaskToEmployeeDTO taskToEmployeeDTO) {
		
		Task task = taskToEmpService.saveTaskToEmployee(taskToEmployeeDTO);
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String emailAdmin = userDetails.getUsername();
		Employee modifyBy = empRepo.findByEmail(emailAdmin);
		String nameModifyBy = modifyBy.getName();
		if(task == null) {
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}
		System.out.println(task.toString());
		return new ResponseEntity<String>(nameModifyBy, HttpStatus.OK);
	}

	@GetMapping("/rest/searchTask/{taskID}")
	public CongViecDaGiao searchTask(@PathVariable("taskID")Integer taskID){
		Task task =taskRepo.findById(taskID).get();
		List<TaskToEmployee> lists = task.getTask_to_employee();
		CongViecDaGiao congViecDaGiao =new CongViecDaGiao();
		for(TaskToEmployee tasked : lists) {
			congViecDaGiao.getEmployee().add(tasked.getEmployee());
			congViecDaGiao.getModifyBy().add(tasked.getModifyBy());
		}
		return congViecDaGiao;
	
		
	}
	
	@PostMapping("/rest/delEmployeeOutTask")
	public CongViecDaGiao delEmployeeOutTask(@RequestBody TaskToEmployeeDTO taskToEmployeeDTO){
		Employee emp = empRepo.findByEmail(taskToEmployeeDTO.getEmpEmail());//nhân viên dudojc xóa
		Task task =taskRepo.findById(taskToEmployeeDTO.getTask_id()).get();
		
		customService.deleteTaskToEmployee(task, emp);

		Task taskUpdate = taskRepo.findById(taskToEmployeeDTO.getTask_id()).get();
		List<TaskToEmployee> lists = taskUpdate.getTask_to_employee();
		System.out.println("Size: "+lists.size());
		CongViecDaGiao congViecDaGiao =new CongViecDaGiao();
		for(TaskToEmployee tasked : lists) {
			congViecDaGiao.getEmployee().add(tasked.getEmployee());
			congViecDaGiao.getModifyBy().add(tasked.getModifyBy());
			
		}
		
		return congViecDaGiao;
	
		
	}
	
	
	@PostMapping("/rest/deleteTask")
	public String deleteTask(@RequestBody TaskToEmployeeDTO taskToEmployeeDTO){
		
		taskRepo.deleteById(taskToEmployeeDTO.getTask_id());
		return "OK";
			
		
	}
	
	
	@PostMapping("/rest/admin/task/rate")
	public UserUpdateDTO rateTask(@RequestBody UserUpdateDTO userUpdateDTO) {
		System.out.println("Task ID: "+ userUpdateDTO.getTaskID());
		System.out.println("Progreess: "+ userUpdateDTO.getProgress());
		Task t = taskRepo.findById(userUpdateDTO.getTaskID()).get();
		
		if(userUpdateDTO.getProgress() == 101) {
			t.setProgressTask(101);
			taskRepo.save(t);
		}else if(userUpdateDTO.getProgress() == 102) {
			t.setProgressTask(102);
			taskRepo.save(t);
		}
		return new UserUpdateDTO(t.getId(), t.getProgressTask(), t.getCompleteDate());
	}
}
