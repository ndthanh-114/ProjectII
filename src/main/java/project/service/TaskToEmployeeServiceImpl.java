package project.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import project.entity.Employee;
import project.entity.Task;
import project.entity.TaskToEmployee;
import project.repository.EmployeeRepository;
import project.repository.TaskRepository;
import project.repository.TaskToEmployeeRepository;
import project.web.dto.TaskToEmployeeDTO;

@Service
public class TaskToEmployeeServiceImpl implements TaskToEmployeeSerive{

	@Autowired
	private TaskToEmployeeRepository taskToEmpRepo;
	
	@Autowired
	private EmployeeRepository empRepo;
	
	@Autowired
	private TaskRepository taskRepo;
	
	
	
	@Override
	public Task saveTaskToEmployee(TaskToEmployeeDTO taskToEmployeeDTO) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String emailAdmin = userDetails.getUsername();
		Employee modifyBy = empRepo.findByEmail(emailAdmin);
		Employee emp = empRepo.findByEmail(taskToEmployeeDTO.getEmpEmail());
		Task task = taskRepo.findById(taskToEmployeeDTO.getTask_id()).get();
		Employee checkEmp = taskToEmpRepo.findByEmployeeAndTask(emp, task);
		if(checkEmp != null) {
			return null;
		}
		
		
		
		Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        
        taskToEmpRepo.save(new TaskToEmployee(task, emp, modifyBy, strDate));
        
		
		return task;
	}
	
}
