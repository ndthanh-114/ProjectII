package project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import project.entity.Project;
import project.web.dto.DisplayProject;
import project.web.dto.ProjectDTO;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer>{
	
	
	@Query("SELECT p FROM Project p ORDER BY p.name")
	List<Project> findAll();
	
	Optional<Project> findById(Integer id);
	
	Project findByName(String name);
	
	
	@Query("SELECT new project.web.dto.ProjectDTO(p.id, p.name) FROM Project p")
	List<ProjectDTO> findProject();
	
	@Query("SELECT p FROM Project p WHERE p.name LIKE %?1% ORDER BY p.name")
	List<Project> searchProject(String keyword);
}
