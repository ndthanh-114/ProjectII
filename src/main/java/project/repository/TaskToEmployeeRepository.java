package project.repository;

import java.util.List;
import java.util.Optional;



import org.springframework.data.jpa.repository.JpaRepository;
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
	
	List<TaskToEmployee> findByEmployee(Employee employee);
}
