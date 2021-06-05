package project.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import net.bytebuddy.asm.Advice.OffsetMapping.Sort;
import project.convert.ProcessDate;
import project.entity.Project;
import project.entity.Task;
import project.repository.ProjectRepository;
import project.repository.TaskRepository;
import project.web.dto.DisplayProject;

@Controller
public class TaskToEmployeeController {
	
	@Autowired
	private ProjectRepository repoProject;
	
	@Autowired
	private TaskRepository repoTask;
	
	@RequestMapping("/admin/project/detail/{id}")
	public String viewdetailProject(@PathVariable("id")Integer id, Model model) {
		return detailProject(id, model, 1);
	}

	
	@GetMapping("/admin/project/detail/{id}/{pageNo}")
	public String detailProject(@PathVariable("id")Integer id, Model model, @PathVariable(value = "pageNo") int pageNo) {
		Project project = repoProject.findById(id).get();
		
		
		
		ProcessDate.convertDateProject(project);
		
		Integer pageSize = 3;
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		
		
		List<Task> listTasks = repoTask.findByProject(project);
		ProcessDate.convertDateTask(listTasks);
		
		int start = (int) pageable.getOffset();
		int end = (int) ((start + pageable.getPageSize()) > listTasks.size() ? listTasks.size()
				  : (start + pageable.getPageSize()));
		Page<Task> pages = new PageImpl<Task>(listTasks.subList(start, end), pageable, listTasks.size());
		
		
		listTasks = pages.toList();
		if(listTasks.size() >= 1) {
		System.out.println("Get: "+pages.getPageable().toString());
		System.out.println("Size of Pages: "+ pages.getSize());
		System.out.println("Size: "+ pages.toList().size());
		System.out.println("TotalPages: " + pages.getTotalPages());
		System.out.println("TotalItems: " + pages.getTotalElements());
		System.out.println("CurrentPage: "+ pageNo);

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", pages.getTotalPages());
		model.addAttribute("totalItems", pages.getTotalElements());

		model.addAttribute("listTasks", listTasks);
		model.addAttribute("project", project);
		return "project_detail";
		}else {
			return "redirect:/admin/project/detail/"+ id +"/1";
		}
	}


}
