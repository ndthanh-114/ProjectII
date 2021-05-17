package project.web.dto;

public class UserUpdateDTO {
	private Integer taskID;
	private Integer progress;
	private String completeDate;
	public UserUpdateDTO() {
		super();
	}
	
	
	
	public UserUpdateDTO(Integer taskID, Integer progress) {
		super();
		this.taskID = taskID;
		this.progress = progress;
	}



	public UserUpdateDTO(Integer taskID, Integer progress, String completeDate) {
		super();
		this.taskID = taskID;
		this.progress = progress;
		this.completeDate = completeDate;
	}
	public Integer getTaskID() {
		return taskID;
	}
	public void setTaskID(Integer taskID) {
		this.taskID = taskID;
	}
	public Integer getProgress() {
		return progress;
	}
	public void setProgress(Integer progress) {
		this.progress = progress;
	}
	public String getCompleteDate() {
		return completeDate;
	}
	public void setCompleteDate(String completeDate) {
		this.completeDate = completeDate;
	}
	
	
	
	
}
