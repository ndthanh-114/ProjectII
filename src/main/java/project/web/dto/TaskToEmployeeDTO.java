package project.web.dto;

public class TaskToEmployeeDTO {
	Integer task_id;
	String empEmail;
	public TaskToEmployeeDTO() {
		super();
	}
	public TaskToEmployeeDTO(Integer task_id, String empEmail) {
		super();
		this.task_id = task_id;
		this.empEmail = empEmail;
	}
	public Integer getTask_id() {
		return task_id;
	}
	public void setTask_id(Integer task_id) {
		this.task_id = task_id;
	}
	public String getEmpEmail() {
		return empEmail;
	}
	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}
	@Override
	public String toString() {
		return "TaskToEmployeeDTO [task_id=" + task_id + ", empEmail=" + empEmail + "]";
	}
	
	
}
