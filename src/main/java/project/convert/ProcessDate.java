package project.convert;

import java.util.List;

import project.entity.Project;
import project.entity.Task;
import project.web.dto.DisplayProject;

public class ProcessDate {

	public static void convertDate(List<DisplayProject> projects) {
		if (projects == null) {
			System.out.println("NULL kho hieu");
			return;
		}
		if (projects.size() == 0) {
			System.out.println("Size =0");
		} else {
			for (DisplayProject pj : projects) {
				String[] split = pj.getCreateDate().split("-");
				pj.setCreateDate(split[2] + "-" + split[1] + "-" + split[0]);
				String[] splitEnd = pj.getEndDate().split("-");
				pj.setEndDate(splitEnd[2] + "-" + splitEnd[1] + "-" + splitEnd[0]);
			}
		}

	}

	public static void convertDateProject(Project pj) {
		if (pj == null) {
			System.out.println("NULL kho hieu");
			return;
		} else {
			String[] split = pj.getCreateDate().split("-");
			pj.setCreateDate(split[2] + "-" + split[1] + "-" + split[0]);
			String[] splitEnd = pj.getEndDate().split("-");
			pj.setEndDate(splitEnd[2] + "-" + splitEnd[1] + "-" + splitEnd[0]);
		}
	}
	
	public static void convertDateTask(List<Task> tasks) {
		if (tasks == null) {
			System.out.println("NULL kho hieu");
			return;
		}
		if (tasks.size() == 0) {
			System.out.println("Size =0");
		} else {
			for (Task task : tasks) {
				String[] split = task.getCreateDate().split("-");
				task.setCreateDate(split[2] + "-" + split[1] + "-" + split[0]);
				String[] splitEnd = task.getEndDate().split("-");
				task.setEndDate(splitEnd[2] + "-" + splitEnd[1] + "-" + splitEnd[0]);
				
			}
		}

	}

	public static boolean checkDate(Project project, String createDate, String endDate) {
		if (project.getCreateDate().compareTo(createDate) > 0 || endDate.compareTo(project.getEndDate()) > 0)
			return false;
		if (createDate.compareTo(endDate) > 0)
			return false;
		return true;
	}
	
	public static String atomDate(String date) {
		String rs="";
		String[] split = date.split("-");
		rs = split[2] + "-" + split[1] + "-" + split[0];
		return rs;
	}
}
