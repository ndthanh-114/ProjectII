package project.web.dto;

import java.util.List;

import project.entity.TaskToEmployee;

public class DetailEmployeeDTO {
	private ProjectDTO project;
	private TaskDTO task;
	private Integer progress;
	public DetailEmployeeDTO() {
		super();
	}
	public DetailEmployeeDTO(ProjectDTO project, TaskDTO task, Integer progress) {
		super();
		this.project = project;
		this.task = task;
		this.progress = progress;
	}
	public ProjectDTO getProject() {
		return project;
	}
	public void setProject(ProjectDTO project) {
		this.project = project;
	}
	public TaskDTO getTask() {
		return task;
	}
	public void setTask(TaskDTO task) {
		this.task = task;
	}
	public Integer getProgress() {
		return progress;
	}
	public void setProgress(Integer progress) {
		this.progress = progress;
	}
	
		
		
}
