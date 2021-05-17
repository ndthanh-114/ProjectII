package project.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import project.convert.ProcessDate;
import project.entity.Project;
import project.entity.Task;
import project.repository.ProjectRepository;
import project.repository.TaskRepository;

@Controller
public class TaskToEmployeeController {
	
	@Autowired
	private ProjectRepository repoProject;
	
	@Autowired
	private TaskRepository repoTask;
	
	@GetMapping("/admin/project/detail/{id}")
	public String detailProject(@PathVariable("id")Integer id, Model model) {
		Project project = repoProject.findById(id).get();
		List<Task> listTasks = repoTask.findByProject(project);
		
		for(Task t: listTasks) {
			System.out.println(t.toString());
		}
		ProcessDate.convertDateTask(listTasks);
		ProcessDate.convertDateProject(project);
		System.out.println(project.toString());
		model.addAttribute("listTasks", listTasks);
		model.addAttribute("project", project);
		return "project_detail";
	}
}
