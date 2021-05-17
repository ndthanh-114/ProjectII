package project.web.dto;

public class ProjectDTO {
	private Integer id;
	private String name;
	public ProjectDTO() {
		super();
	}
	public ProjectDTO(Integer id, String name) {
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
	@Override
	public String toString() {
		return "ProjectDTO [id=" + id + ", name=" + name + "]";
	}
	
	
}
