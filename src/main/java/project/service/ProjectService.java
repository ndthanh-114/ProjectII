package project.service;

import java.util.List;

import project.entity.Project;
import project.web.dto.DisplayProject;
import project.web.dto.ProjectDTO;


public interface ProjectService {
	List<Project> findAllProject();
	
	String saveProject(Project project);
	
	List<ProjectDTO> findProjects();

	String completeDateProject(Project project);
}
