package project.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "task")
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 400)
	private String decription;
	
	@Column(name ="create_date")
	private String createDate;
	
	@Column(name ="end_date")
	private String endDate;
	
	@Column(name ="complete_date")
	private String completeDate;
	
	@Column(name ="progress_task")
	private Integer progressTask;
	
	
	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;

	@OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
	private List<TaskToEmployee> task_to_employee=new ArrayList<TaskToEmployee>();
	
	public Task() {
		super();
	}
	

	
	
	public Task(String decription, String createDate, String endDate, String completeDate, Integer progress,
			Project project, List<TaskToEmployee> task_to_employee) {
		super();
		this.decription = decription;
		this.createDate = createDate;
		this.endDate = endDate;
		this.completeDate = completeDate;
		this.progressTask = progress;
		this.project = project;
		this.task_to_employee = task_to_employee;
	}




	public Task(String decription, String createDate, String endDate) {
		super();
		this.decription = decription;
		this.createDate = createDate;
		this.endDate = endDate;this.progressTask = 0;
	
	}




	public Task(String decription, String createDate, String endDate, String completeDate, Project project,
			List<TaskToEmployee> task_to_employee) {
		super();
		this.decription = decription;
		this.createDate = createDate;
		this.endDate = endDate;
		this.completeDate = completeDate;
		this.project = project;
		this.task_to_employee = task_to_employee;this.progressTask = 0;
	}




	public Task(String decription, String createDate, String endDate, Project project) {
		super();
		this.decription = decription;
		this.createDate = createDate;
		this.endDate = endDate;
		this.project = project;progressTask = 0;
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getDecription() {
		return decription;
	}



	public void setDecription(String decription) {
		this.decription = decription;
	}



	public String getCreateDate() {
		return createDate;
	}



	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}



	public String getEndDate() {
		return endDate;
	}



	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}



	public String getCompleteDate() {
		return completeDate;
	}



	public void setCompleteDate(String completeDate) {
		this.completeDate = completeDate;
	}



	public Project getProject() {
		return project;
	}



	public void setProject(Project project) {
		this.project = project;
	}




	public List<TaskToEmployee> getTask_to_employee() {
		return task_to_employee;
	}




	public void setTask_to_employee(List<TaskToEmployee> task_to_employee) {
		this.task_to_employee = task_to_employee;
	}

	public Integer getProgressTask() {
		return progressTask;
	}




	public void setProgressTask(Integer progressTask) {
		this.progressTask = progressTask;
	}




	@Override
	public String toString() {
		return "Task [id=" + id + ", decription=" + decription + ", createDate=" + createDate + ", endDate=" + endDate
				+ ", completeDate=" + completeDate + ", progressTask=" + progressTask + ", project=" + project
				+ ", task_to_employee=" + task_to_employee + "]";
	}

	
}
