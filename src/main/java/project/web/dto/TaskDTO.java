package project.web.dto;

import java.util.List;

import project.entity.Task;

public class TaskDTO {
	private Integer id; 
	private String name;
	public TaskDTO() {
		super();
	}
	public TaskDTO(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
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
	
}
