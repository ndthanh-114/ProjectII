var projectID;
var btnUpdateProgress;
var chooseProjectDropDown;
var chooseTaskDropDown;
var chooseProgressDropDown;
var btnClose;
var btnUpdateTask;
var detailNameProject;
var managerBy;
var detailCreateProject;
var detailEndProject;
var listTask;
$(document).ready(function() {
	btnUpdateProgress = $('#btnUpdateProgress');
	chooseProjectDropDown = $('#chooseProjectDropDown');
	chooseTaskDropDown = $('#chooseTaskDropDown');
	chooseProgressDropDown = $('#chooseProgressDropDown');

	btnUpdateTask = $('#btnUpdateTask');
	btnClose = $('.btnClose');
	detailNameProject = $('#detailNameProject');
	managerBy = $('#managerBy');
	listTask = $('#listTask');
	detailCreateProject = $('#detailCreateProject');
	detailEndProject = $('#detailEndProject');
	$('.table .aDetailProject').on('click', function(event) {
		event.preventDefault();
		var href = $(this).attr('href');
		$('.myFormDetail #detailProject').modal();
			
		$.get(href, function(responseJson) {
			detailNameProject.empty();
			managerBy.empty();
			listTask.empty();
			detailCreateProject.empty();
			detailEndProject.empty();
			detailNameProject.text(responseJson.projectName);
			detailCreateProject.append(responseJson.createDate);
			detailEndProject.append(responseJson.createEnd);
			
			$.each(responseJson.mofidyBy, function(index, cc) {
				managerBy.append("<div>" +cc + "</div>");
				

			});
			
			$.each(responseJson.tasks, function(index, task) {
				listTask.append("<div>" +task.name + "</div>");
				

			});
			
			
		}).done(function() {

		}).fail(function() {
			alert('Thất bại');
		});



	});

	$(function() {
		$('.table tr').click(function(e) {
			taskID = $(this).closest('tr').find('td:nth-child(2)').html();
			console.log(taskID);
		});
	});
	btnUpdateProgress.click(async function() {
		await loadProject();
		console.log(projectID);
	});

	chooseProjectDropDown.on("change", function() {
		loadTask();
	});

	btnClose.click(function() {
		projectID = 0;
		chooseTaskDropDown.empty();
		console.log("click close");
	});


	btnUpdateTask.click(function() {
		projectID = chooseProjectDropDown.val();
		taskID = chooseTaskDropDown.val();
		progressOfTask = chooseProgressDropDown.val();
		if (projectID == null || taskID == null || (typeof projectID === 'undefined') || (typeof taskID === 'undefined')) {
			projectID = 0;
			chooseTaskDropDown.empty();
			$("#chooseProjectDropDown option[value='" + 0 + "']").prop("selected");
			console.log("click close");
			alert("Kiểm tra lại đầy đủ thông tin");

		} else {

			console.log(projectID);
			console.log(taskID);
			console.log(progressOfTask);
			$("#chooseProjectDropDown option[value='" + 0 + "']").prop("selected");
			saveProgress();
			projectID = 0;
			chooseTaskDropDown.empty();
			console.log("click close");
		}
	});
});

async function saveProgress() {
	url = "/rest/employee/updateProgress";

	taskID = chooseTaskDropDown.val();
	progressOfTask = chooseProgressDropDown.val();

	var jsonData = { taskID: taskID, progress: progressOfTask };

	await $.ajax({
		type: "POST",
		contentType: "application/json",
		url: url,
		data: JSON.stringify(jsonData),

		success: function(data, textStatus, jqXHR) {
			var rowID = '#' + taskID;
			$('table ' + rowID + ' .cot5').empty();
			if (data.completeDate != null) {
				$('table ' + rowID + ' .cot5').append("<p style = 'font-size: 1vw; color: red;'><strong><i>Hoàn thành lúc: " + data.completeDate + "</i></strong></p>");
			} else {
				$('table ' + rowID + ' .cot5').append("<p>" + data.progress + "</p>");
			}
			alert("Cập nhật thành công");

		},
		error: function(jqXHR, textStatus, errorThrown) {

			alert("Cập nhật thất bại " + textStatus);



		}
	});
}

function loadTask() {
	projectID = chooseProjectDropDown.val();
	console.log("CC: " + projectID);
	if (!(typeof projectID === 'undefined' || projectID == null || projectID == "")) {
		url = "/rest/employee/project/" + projectID;

		$.get(url, function(responseJson) {
			chooseTaskDropDown.empty();

			$.each(responseJson, function(index, userTaskDTO) {

				$("<option>").val(userTaskDTO.id).text(userTaskDTO.name).appendTo(chooseTaskDropDown);

			});
			$("#chooseProjectDropDown option[value='" + projectID + "']").prop("selected");

		}).done(function() {

		}).fail(function() {
			alert('Failed');
		});
	} else {
		chooseTaskDropDown.empty();

	}
}

function loadProject() {
	$('.myForm #exampleModal').modal();
	url = "/rest/employee/project";

	$.get(url, function(responseJson) {
		chooseProjectDropDown.empty();
		$("<option>").val("").text("").appendTo(chooseProjectDropDown);
		$.each(responseJson, function(index, projectDTO) {

			$("<option>").val(projectDTO.id).text(projectDTO.name).appendTo(chooseProjectDropDown);


		});
		$("#chooseProjectDropDown option[value='" + projectID + "']").prop("selected");

	}).done(function() {

	}).fail(function() {
		alert('Failed');
	});


}