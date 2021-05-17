var btnTaskToEmp;//day la the <a>
var chooseProjectDropDown;
var chooseTaskDropDown;
var chooseEmpDropDown;
var emailEmployee;
var btnTaskToEmployee;
var projectID = 0;
var taskID = 0;
var emailEmp;
var textEmailEmployee;

$(document).ready(function() {
	btnTaskToEmp = $('#btnTaskToEmp');
	chooseProjectDropDown = $('#chooseProjectDropDown');
	chooseTaskDropDown = $('#chooseTaskDropDown');
	chooseEmpDropDown = $('#chooseEmpDropDown');
	emailEmployee = $('#emailEmployee');
	btnTaskToEmployee = $('#btnTaskToEmployee');
	textEmailEmployee = $('#textEmailEmployee');

	$("#btnTaskToEmp").click(function() {

		$('.formTaskToEmp #projectModal').modal();
		taskToEmp();
		projectID = $("#chooseProjectDropDown option:selected").val();

		loadEmployee();
		clickChooseTask();
	});

	chooseProjectDropDown.on("change", function() {
		projectID = $("#chooseProjectDropDown option:selected").val();
		console.log(projectID);
		clickChooseTask();
	});



	chooseEmpDropDown.on("change", function() {
		
		emailEmp = $("#chooseEmpDropDown option:selected").val();
		textEmailEmployee.text(emailEmp);
	});
	btnTaskToEmployee.click(function() {
		giaoNhiemVu();



	});

});

async function giaoNhiemVu() {
	taskID = $("#chooseTaskDropDown option:selected").val();
	emailEmp = $("#chooseEmpDropDown option:selected").val();
	console.log("Ban chon task ID: " + taskID);
	console.log("Ban chon EMP : " + emailEmp);
	if (typeof taskID === 'undefined' || typeof emailEmp === 'undefined')
		alert("Kiểm tra lại thông tin")
	else {

		var url = "/rest/adminUpdateTaskToEmployee";
		var jsonData = { task_id: taskID, empEmail: emailEmp };

		await $.ajax({
			type: "POST",
			contentType: "application/json",
			url: url,
			data: JSON.stringify(jsonData),

			success: function(data, textStatus, jqXHR) {


				console.log("Status : ", textStatus);
				console.log("data: ", data);
				if (textStatus == "success") {
					alert("Giao việc thành công")
				}
				else
					alert("Giao việc thất bại nhân viên đã có trong công việc");
			},
			error: function(jqXHR, textStatus, errorThrown) {

				alert("Giao việc thất bại nhân viên đã có trong công việc");



			}
		});
	}
}

function loadEmployee() {
	url = "/listEmployee";

	$.get(url, function(responseJson) {
		chooseEmpDropDown.empty();
		
		$.each(responseJson, function(index, employee) {
			$("<option>").val(employee.email).text(employee.name).appendTo(chooseEmpDropDown);
		});
	}).done(function() {

	}).fail(function() {
		alert('Failed');
	});

}

function taskToEmp() {
	url = "/rest/listProject";

	$.get(url, function(responseJson) {
		chooseProjectDropDown.empty();
		$("<option>").val(98765).text("").appendTo(chooseProjectDropDown);
		$.each(responseJson, function(index, projectDTO) {

			$("<option>").val(projectDTO.id).text(projectDTO.name).appendTo(chooseProjectDropDown);

		});


	}).done(function() {

	}).fail(function() {
		
	});

}

function clickChooseTask() {

	url = "/rest/listTask/" + projectID;
	console.log("da nhan vao");
	$.get(url, function(responseJson) {
		chooseTaskDropDown.empty();
		$.each(responseJson, function(index, taskDTO) {
			$("<option>").val(taskDTO.id).text(taskDTO.name).appendTo(chooseTaskDropDown);
		});
	}).done(function() {

	}).fail(function() {
		
	});
}