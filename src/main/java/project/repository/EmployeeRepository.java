package project.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import project.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
	Employee findByEmail(String email);

	Employee findAllById(Integer id);
	
	Employee findByPhone(String phone);
	
	List<Employee> findByRoleOrderByNameAsc(String userRole);
	
	@Query("SELECT e FROM Employee e WHERE (e.name LIKE %?1%"
			+" OR e.email LIKE %?1%"
			+" OR e.phone LIKE %?1%)"
			+" AND e.role LIKE %?2%"
			+" ORDER BY e.name ASC")
	List<Employee> searchFillAll(String keyword, String role );
	
	

	@Query("SELECT e FROM Employee e WHERE (e.name LIKE %?1%"
			+" OR e.email LIKE %?1%"
			+" OR e.phone LIKE %?1%)"
			+" AND e.role LIKE %?2%"
			+" ORDER BY e.name ASC")
	Page<Employee> pageEmployee(String keyword, String role, Pageable pageable);
	
	Page<Employee> findByRole(String userRole, Pageable pageable);
	
}
