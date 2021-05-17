package project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.entity.Project;
import project.repository.ProjectRepository;
import project.web.dto.DisplayProject;
import project.web.dto.ProjectDTO;

@Service
public class ProjectServiceImpl implements ProjectService{
	@Autowired
	private ProjectRepository pjRepo;

	@Override
	public List<Project> findAllProject() {
		return pjRepo.findAll();
	}

	@Override
	public String saveProject(Project project) {
		Project pj = pjRepo.findByName(project.getName());
		if(pj!=null) {
			return "trung_ten";
		}
		if(project.getCreateDate().compareTo(project.getEndDate()) > 0)
			return "date";
		return "success";
	}

	@Override
	public List<ProjectDTO> findProjects() {
		return pjRepo.findProject();
		
		
	}	
}
