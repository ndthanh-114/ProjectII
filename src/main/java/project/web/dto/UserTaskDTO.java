package project.web.dto;

import java.util.List;

import project.entity.Task;

public class UserTaskDTO {
	private Integer id; 
	private String name;
	private Integer progress;
	public UserTaskDTO() {
		super();
	}
	public UserTaskDTO(Integer id, String name, Integer progress) {
		super();
		this.id = id;
		this.name = name;
		this.progress = progress;
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
	public Integer getProgress() {
		return progress;
	}
	public void setProgress(Integer progress) {
		this.progress = progress;
	}
		
}
