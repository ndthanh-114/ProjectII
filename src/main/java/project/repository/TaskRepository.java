package project.repository;


import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import project.entity.Project;
import project.entity.Task;
import project.web.dto.TaskDTO;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>{
	@Transactional
	@Modifying
	@Query("update Task t set t.decription = :decription, t.createDate = :creatDate, t.endDate = :endDate where t.id = :id")
	void updateTask(@Param(value = "id") int id, @Param(value = "decription") String decription, @Param(value = "creatDate") String creatDate, @Param(value = "endDate") String endDate);
	
	
	Optional<Task> findById(Integer id);
	
	List<Task> findByProject(Project project);
	
	
	
	
}	
