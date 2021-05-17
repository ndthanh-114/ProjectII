package project.web.restcontroller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import project.entity.Project;
import project.entity.Task;
import project.entity.TaskToEmployee;
import project.repository.ProjectRepository;
import project.repository.TaskRepository;
import project.service.ProjectService;
import project.web.dto.CongViecDaGiao;
import project.web.dto.ProjectDTO;
import project.web.dto.TaskDTO;

@RestController
public class ProjectRestController {
	
	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private TaskRepository taskRepo;
	
	@GetMapping("/rest/listProject")
	public List<ProjectDTO> listProject() {
		return projectService.findProjects();
	}
	
	@GetMapping("/rest/listTask/{projectID}")
	public List<TaskDTO> searchTask(@PathVariable("projectID")Integer projectID){
		Project project = projectRepo.findById(projectID).get();
		List<Task> listTask = taskRepo.findByProject(project);
		List<TaskDTO> rs = new ArrayList<TaskDTO>();
		if(listTask != null) {
			for(Task t: listTask) {
				rs.add(new TaskDTO(t.getId(), t.getDecription()));
			}
		}
		return rs;
	}
}
