package project.repository;

import java.util.List;
import java.util.Optional;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import project.entity.Employee;
import project.entity.Task;
import project.entity.TaskToEmployee;

public interface TaskToEmployeeRepository extends JpaRepository<TaskToEmployee, Integer>{
	Optional<TaskToEmployee> findById(Integer id);
	
	Employee findByEmployeeAndTask(Employee employee, Task task);
	
	List<TaskToEmployee> findByTask(Task task);
	
	TaskToEmployee findByTaskAndEmployee(Task task, Employee employee);
	
	@Query("SELECT tte FROM TaskToEmployee tte WHERE tte.employee = ?1 ORDER BY tte.task.project.name , tte.task.decription ")
	List<TaskToEmployee> findByEmployee(Employee employee);
	
	@Query("SELECT tte FROM TaskToEmployee tte WHERE tte.employee = ?1 AND tte.task.progressTask > 100 ORDER BY tte.task.project.name , tte.task.decription")
	List<TaskToEmployee> findByEmployeeYes(Employee employee);
	
	@Query("SELECT tte FROM TaskToEmployee tte WHERE tte.employee = ?1 AND tte.task.progressTask <101 ORDER BY tte.task.project.name , tte.task.decription")
	List<TaskToEmployee> findByEmployeeNo(Employee employee);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM TaskToEmployee tte WHERE tte.employee = ?1 ")
	void deleteByEmployee(Employee emp);
}
