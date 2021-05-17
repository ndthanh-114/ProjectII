package project.service;

import project.entity.Task;
import project.web.dto.TaskToEmployeeDTO;

public interface TaskToEmployeeSerive {
	Task saveTaskToEmployee(TaskToEmployeeDTO taskToEmployeeDTO);
}
