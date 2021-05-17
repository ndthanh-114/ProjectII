package project.entity;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "project")
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "name", unique = true)
	private String name;
	

	@Column(name ="create_date")
	private String createDate;
	
	@Column(name ="complete_date")
	private String completeDate;
	
	@Column(name ="end_date")
	private String endDate;

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	private List<Task> tasks=new ArrayList<Task>();
	
	public Project() {
		super();
	}

	public Project(String name, String createDate, String completeDate, String endDate) {
		super();
		this.name = name;
		this.createDate = createDate;
		this.completeDate = completeDate;
		this.endDate = endDate;
	}

	public Project(String name, String createDate, String endDate) {
		super();
		this.name = name;
		this.createDate = createDate;
		this.endDate = endDate;
	}

	
	
	public Project(String name, String createDate, String completeDate, String endDate, List<Task> tasks) {
		super();
		this.name = name;
		this.createDate = createDate;
		this.completeDate = completeDate;
		this.endDate = endDate;
		this.tasks = tasks;
	}

	public Project(String name, String createDate, String endDate, List<Task> tasks) {
		super();
		this.name = name;
		this.createDate = createDate;

		this.endDate = endDate;
		this.tasks = tasks;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(String completeDate) {
		this.completeDate = completeDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", name=" + name + ", createDate=" + createDate + ", completeDate=" + completeDate
				+ ", endDate=" + endDate + "]";
	}
	
	public void addTask(String decription, String createDate, String endDate) {
		this.tasks.add(new Task(decription, createDate, endDate, this));
	}
	
	public void updateTask(int id, String decription, String createDate, String endDate, List<Task> tasks) {
		this.tasks=tasks;
		for(Task task: tasks) {
			if(task.getId()==id) {
			
				task.setCreateDate(createDate);
				task.setEndDate(endDate);
				task.setDecription(decription);
				
				break;
			}
		}
	}
}
