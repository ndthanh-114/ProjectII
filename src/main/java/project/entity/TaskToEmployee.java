package project.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "task_to_employee")
public class TaskToEmployee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "task_id")
	private Task task;

	@Column(name = "progress")
	private Integer progress;

	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;

	@ManyToOne
	@JoinColumn(name = "modify_by")
	private Employee modifyBy;
	
	@Column(name = "modify_date")
	private String modifyDate;

	public TaskToEmployee() {
		super();
	}

	public TaskToEmployee(Task task, Integer progress, Employee employee, Employee modifyBy, String modifyDate) {
		super();
		this.task = task;
		this.progress = progress;
		this.employee = employee;
		this.modifyBy = modifyBy;
		this.modifyDate = modifyDate;
	}

	public TaskToEmployee(Integer id, Task task, Integer progress, Employee employee, Employee modifyBy) {
		super();
		this.id = id;
		this.task = task;
		this.progress = progress;
		this.employee = employee;
		this.modifyBy = modifyBy;
	}
	
	

	public TaskToEmployee(Task task, Employee employee, Employee modifyBy, String modifyDate) {
		super();
		this.task = task;
		this.employee = employee;
		this.modifyBy = modifyBy;
		this.modifyDate = modifyDate;
	}

	public TaskToEmployee(Integer id, Task task, Integer progress, Employee employee, Employee modifyBy,
			String modifyDate) {
		super();
		this.id = id;
		this.task = task;
		this.progress = progress;
		this.employee = employee;
		this.modifyBy = modifyBy;
		this.modifyDate = modifyDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Employee getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(Employee modifyBy) {
		this.modifyBy = modifyBy;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	@Override
	public String toString() {
		return "hello";
	}

	



	
	
}
