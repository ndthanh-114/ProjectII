package project.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import project.entity.Employee;
import project.entity.Project;
import project.entity.Task;
import project.entity.TaskToEmployee;
import project.repository.ProjectRepository;
import project.repository.TaskRepository;
import project.repository.TaskToEmployeeRepository;
import project.web.dto.DisplayProject;

@Service
public class CustomService {
	@Autowired
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private TaskToEmployeeRepository taskToEmpRepo;
	
	@Autowired
	private TaskRepository taskRepo;
	
	@Autowired
	private ProjectRepository projectRepo;

	private static Integer taskToEmployeeId;

	private static Integer listEmp;

	public List<DisplayProject> displayProject(String keyword) {

		List<DisplayProject> listDp = new ArrayList<DisplayProject>();
		//id, name, createDate, endDate, tongCongViec, hoanThanh
		if (keyword == null) {

			
			List<Project> lists = projectRepo.findAll();
		
			for(Project p: lists) {
				int tmp = 0;
				for(Task t: p.getTasks()) {
					if(t.getProgressTask() == 101 || t.getProgressTask() == 102)
						tmp++;
				}
				listDp.add(new DisplayProject(p.getId(), p.getName(), p.getCreateDate(), p.getEndDate(), p.getTasks().size(), tmp));
			}

		} else {
			
			List<Project> lists = projectRepo.searchProject(keyword);
			
			for(Project p: lists) {
				int tmp = 0;
				for(Task t: p.getTasks()) {
					if(t.getProgressTask() == 101 || t.getProgressTask() == 102)
						tmp++;
				}
				listDp.add(new DisplayProject(p.getId(), p.getName(), p.getCreateDate(), p.getEndDate(), p.getTasks().size(), tmp));
			}
		}
		return listDp;
	}

	@Transactional
	public void deleteTaskToEmployee(Task task, Employee employee) {
		TaskToEmployee taskToEmp = taskToEmpRepo.findByTaskAndEmployee(task, employee);
		entityManager.persist(taskToEmp);
		taskToEmployeeId = taskToEmp.getId();
		TaskToEmployee taskToEmpQuery = entityManager.find(TaskToEmployee.class, taskToEmployeeId);

		if (taskToEmpQuery != null) {
			entityManager.remove(taskToEmpQuery);
		}
		return;
	}

	@Transactional
	public void changeRoleEmployee(Employee employee) {
		List<TaskToEmployee> taskToEmp = taskToEmpRepo.findByEmployee(employee);

		for (TaskToEmployee t : taskToEmp)
			if (t != null) {
				System.out.println("Lay duoc du lieu " + t.getEmployee().toString());
				listEmp = t.getId();
				TaskToEmployee queryEmp = entityManager.find(TaskToEmployee.class, listEmp);
				entityManager.persist(queryEmp);
				if (queryEmp != null) {
					entityManager.remove(queryEmp);
					System.out.println("Da xoa nhan vien cua cong viec " + queryEmp.getEmployee().toString());
				}
			}
		return;
	}
}
