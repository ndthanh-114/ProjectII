package project.web.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import project.convert.ProcessDate;
import project.entity.Employee;
import project.entity.Project;
import project.entity.Task;
import project.repository.ProjectRepository;
import project.repository.TaskRepository;
import project.service.CustomService;
import project.service.ProjectService;
import project.web.dto.DisplayProject;
import project.web.dto.TaskDTO;

@Controller
public class ProjectController {

	@Autowired
	private ProjectService pjService;

	@Autowired
	private ProjectRepository pjRepo;

	@Autowired
	private TaskRepository taskRepo;

	@Autowired
	private CustomService customService;

	@RequestMapping("/admin/project")
	public String viewProjectHome(Model model, @Param("keyword") String keyword) {
		return homePageProject(model, keyword, 1);
	}

	@GetMapping("/admin/project/{pageNo}")
	public String homePageProject(Model model, @Param("keyword") String keyword,
			@PathVariable(value = "pageNo") int pageNo) {


		
		Integer pageSize = 4;
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		
		System.out.println("Keyword " + keyword);
		List<DisplayProject> listPage = customService.displayProject(keyword);
		
		ProcessDate.convertDate(listPage);
		int start = (int) pageable.getOffset();
		int end = (int) ((start + pageable.getPageSize()) > listPage.size() ? listPage.size()
				  : (start + pageable.getPageSize()));
		Page<DisplayProject> pages = new PageImpl<DisplayProject>(listPage.subList(start, end), pageable, listPage.size());
		
		
		List<DisplayProject> listProjects = pages.toList();
		System.out.println("Get: "+pages.getPageable().toString());
		System.out.println("Size of Pages: "+ pages.getSize());
		System.out.println("Size: "+ pages.toList().size());
		System.out.println("TotalPages: " + pages.getTotalPages());
		System.out.println("TotalItems: " + pages.getTotalElements());
		System.out.println("CurrentPage: "+ pageNo);
		model.addAttribute("projects", pages.toList());
		model.addAttribute("keyword", keyword);
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", pages.getTotalPages());
		model.addAttribute("totalItems", pages.getTotalElements());
		return "home_project";
	}

	@GetMapping("admin/project/create")
	public String createProject(Model model) {
		Project project = new Project();
		model.addAttribute("project", project);

		return "project_new";
	}

	@PostMapping("/admin/project/save")
	public String saveProject(Project project, HttpServletRequest request, Model model) {

		if (pjService.saveProject(project).equals("trung_ten"))
			return "redirect:/admin/project/create?trung_ten";
		if (pjService.saveProject(project).equals("date"))
			return "redirect:/admin/project/create?sai_ngay";

		
		
		String[] decriptions = request.getParameterValues("decription");
		String[] createDers = request.getParameterValues("createDer");
		String[] endDers = request.getParameterValues("endDer");

		for (int i = 0; i < endDers.length; i++) {
			if (ProcessDate.checkDate(project, createDers[i], endDers[i]) == false) {
				model.addAttribute("nameProject", project.getName());
				model.addAttribute("createProject", project.getCreateDate());
				model.addAttribute("endProject", project.getEndDate());
				return "redirect:/admin/project/create?sai_ngay_cong_viec";
			}
		}

		for (int i = 0; i < decriptions.length; i++) {
			project.addTask(decriptions[i], createDers[i], endDers[i]);
		}

		pjRepo.save(project);
		return "redirect:/admin/project";
	}

	@GetMapping("/admin/project/update/{id}")
	public String showFormatForUpdate(@PathVariable(value = "id") Integer id, Model model) {
		Project project = pjRepo.findById(id).get();
//		TaskDTO listTasks = new TaskDTO(project.getTasks());
//		model.addAttribute("listTasks", listTasks);

		model.addAttribute("project", project);
		return "project_update";
	}

	@PostMapping("/admin/project/update")
	public String showFormatForUpdate(Project project, HttpServletRequest request, Model model) {
		int id = project.getId();
		String ten = project.getName();
		Project checkPr = pjRepo.findByName(project.getName());
		if (checkPr != null && !ten.equals(checkPr.getName()))
			return "redirect:/admin/project/update/" + id + "?trung_ten";
		if (project.getCreateDate().compareTo(project.getEndDate()) > 0)
			return "redirect:/admin/project/update/" + id + "?sai_ngay";

		String[] decriptions = request.getParameterValues("decription");
		String[] createDers = request.getParameterValues("createDer");
		String[] endDers = request.getParameterValues("endDer");
		String[] taskID = request.getParameterValues("taskID");
		int idMax = taskID.length;
		for (int i = 0; i < idMax; i++) {
			if (ProcessDate.checkDate(project, createDers[i], endDers[i]) == false) {
				model.addAttribute("stt", String.valueOf(i + 1));
				return "redirect:/admin/project/update/" + id + "?chi_tiet_sai";
			}
			taskRepo.updateTask(Integer.valueOf(taskID[i]), decriptions[i], createDers[i], endDers[i]);
			System.out.println(decriptions[i] + " " + createDers[i] + " " + endDers[i]);
		}

		for (int i = idMax; i < endDers.length; i++) {
			if (ProcessDate.checkDate(project, createDers[i], endDers[i]) == false) {
				model.addAttribute("stt", String.valueOf(i + 1));
				return "redirect:/admin/project/update/" + id + "?chi_tiet_sai";
			}
			project.addTask(decriptions[i], createDers[i], endDers[i]);
		}

		pjRepo.save(project);
		return "redirect:/admin/project";
	}

	@GetMapping("/admin/project/delete/{id}")
	public String deleteEmployee(@PathVariable(value = "id") Integer id) {
		this.pjRepo.deleteById(id);

		return "redirect:/admin/project";
	}
}
