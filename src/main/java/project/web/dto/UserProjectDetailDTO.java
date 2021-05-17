package project.web.dto;

import java.util.List;

import project.entity.TaskToEmployee;

public class UserProjectDetailDTO {
	private String projectName;
	private String createDate;
	private String createEnd;
	private List<TaskDTO> tasks;
	private List<String> mofidyBy;
	public UserProjectDetailDTO() {
		super();
	}
	public UserProjectDetailDTO(String projectName, String createDate, String createEnd, List<TaskDTO> tasks,
			List<String> mofidyBy) {
		super();
		this.projectName = projectName;
		this.createDate = createDate;
		this.createEnd = createEnd;
		this.tasks = tasks;
		this.mofidyBy = mofidyBy;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCreateEnd() {
		return createEnd;
	}
	public void setCreateEnd(String createEnd) {
		this.createEnd = createEnd;
	}
	public List<TaskDTO> getTasks() {
		return tasks;
	}
	public void setTasks(List<TaskDTO> tasks) {
		this.tasks = tasks;
	}
	public List<String> getMofidyBy() {
		return mofidyBy;
	}
	public void setMofidyBy(List<String> mofidyBy) {
		this.mofidyBy = mofidyBy;
	}
		
}
