package project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import project.convert.ProcessDate;
import project.entity.Employee;
import project.entity.Project;
import project.entity.Task;
import project.entity.TaskToEmployee;
import project.repository.EmployeeRepository;
import project.repository.ProjectRepository;
import project.repository.TaskToEmployeeRepository;
import project.web.dto.TaskDTO;
import project.web.dto.UserProjectDetailDTO;

@Service
public class UserTaskService {

	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private EmployeeRepository empRepo;
	
	@Autowired
	private TaskToEmployeeRepository taskToEmpRepo;
	
	public List<TaskToEmployee> taskNow() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String emailUser = userDetails.getUsername();
		Employee emp = empRepo.findByEmail(emailUser);
		return taskToEmpRepo.findByEmployee(emp);
		
	}
	public List<TaskToEmployee> taskUserSearch(String keyword) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String emailUser = userDetails.getUsername();
		Employee emp = empRepo.findByEmail(emailUser);
		if(keyword == null)
			return taskToEmpRepo.findByEmployee(emp);
		else if(keyword.equals("yes")) {
			return taskToEmpRepo.findByEmployeeYes(emp);
		}else {
			return taskToEmpRepo.findByEmployeeNo(emp);
		}
		
	}
	public UserProjectDetailDTO detailProject(Integer id){
		Project project = projectRepo.findById(id).get();
		ProcessDate.convertDateProject(project);
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String emailEmp = userDetails.getUsername();
		Employee emp = empRepo.findByEmail(emailEmp);
		List<TaskDTO> tasks = new ArrayList<TaskDTO>();
		List<String> modifyBy = new ArrayList<String>();
		for(Task t: project.getTasks()) {
			TaskDTO taskDTO = new TaskDTO(t.getId(), t.getDecription());
			tasks.add(taskDTO);
			for(TaskToEmployee tte: t.getTask_to_employee()) {
				
				if(modifyBy.size() == 0 || modifyBy == null) {
					modifyBy.add(tte.getModifyBy().getEmail());
					
				}else {
					Boolean check = true;
					for(int j = 0 ;j< modifyBy.size();j++) {
						if((modifyBy.get(j).equals(tte.getModifyBy().getEmail()))) {
							check = false;
							
						}
					}
					if(check)
						modifyBy.add(tte.getModifyBy().getEmail());
				}
			}
		}
		
		
		return new UserProjectDetailDTO(project.getName(), project.getCreateDate(), project.getEndDate(), tasks, modifyBy);
	}
}
