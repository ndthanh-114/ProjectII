var dropDownEmployees;
var emailEmployee;
var btnTaskToEmployee;
var taskID;
var empSelected;
var btnDelTaskDetail;
var delEmailEmployee;
var delDropDownEmps;
var labelDelModifyBy;
var delEmailEmp;
var btnDelAllOfTask;
var btnDelTaskToEmployee;
const mapEmail = new Map();
var cellChoose = new Array();
var danhGiaCuaToiBad;
var danhGiaCuaToiGood;
var btnBad;
var btnGood;

var saveUpdateBad;
var saveUpdateGood;
$(document).ready(function() {
	dropDownEmployees = $('#dropDownEmployees');
	emailEmployee = $('#emailEmployee');
	btnTaskToEmployee = $('#btnTaskToEmployee');
	btnDelTaskDetail = $('#btnDelTaskDetail');
	delDropDownEmps = $('#delDropDownEmps');
	delEmailEmployee = $('#delEmailEmployee');
	labelDelModifyBy = $('#labelDelModifyBy');
	btnDelTaskToEmployee = $('#btnDelTaskToEmployee');

	btnDelAllOfTask = $('#btnDelAllOfTask');
	btnBad = $('#btnBad');
	btnGood = $('#btnGood');
	danhGiaCuaToiBad = $('#danhGiaCuaToiBad');
	danhGiaCuaToiGood = $('#danhGiaCuaToiGood');
	saveUpdateBad = $('#saveUpdateBad');
	saveUpdateGood = $('#saveUpdateGood');
	$('.table .editBtn').on('click', function(event) {
		loadEmployee();
	});

	dropDownEmployees.on("change", function() {
		clickChooseEmployee();
	});

	$(function() {
		$('.table #editTaskDetail').click(function(e) {
			taskID = $(this).closest('tr').find('td:nth-child(1)').text();
			console.log(taskID);
		});
	});

	$(function() {
		$('.table #btnDelTaskDetail').click(function(e) {
			taskID = $(this).closest('tr').find('td:nth-child(1)').text();

			cellChoose = $(this).closest('tr').find('td:nth-child(6)').text();
			console.log(taskID);

			$('.myFormDel #delModal').modal();
			var totalNow = $("#totalTaskNav").val();
			//console.log("task now: "+ totalNow);
			searchTask();

			//phan them moi
			

		});
	});

	btnTaskToEmployee.click(async function() {
		await taskToEmployee();
	});

	delDropDownEmps.on("change", function() {
		clickDel();
	});

	btnDelTaskToEmployee.click(function() {

		delEmployeeOutTask();
	});



	btnDelAllOfTask.click(function() {

		deleteTask();



	});


	$(function() {
		$('.table #btnBad').click(function(e) {
			taskID = $(this).closest('tr').find('td:nth-child(1)').text();
			console.log("CC " + taskID);
			$('.myDanhGiaBad').modal();
			danhGiaBad();
		});
	});



	$(function() {
		$('.table #btnGood').click(function(e) {
			taskID = $(this).closest('tr').find('td:nth-child(1)').text();
			console.log("vao");
			$('.myDanhGiaGood').modal();
			danhGiaGood();
		});
	});


	saveUpdateBad.click(async function() {
		console.log("Bad nhan");
		await saveBad();
		loadCompleteleDate();
	});

	saveUpdateGood.click(async function() {
		console.log("Good nhan");
		await saveGood();
		loadCompleteleDate();

	});
});
function loadCompleteleDate() {
	var projectID = $('#projectID').val();
	console.log("ProjectID " + projectID);
	url = "/rest/completeDate/" + projectID;
	console.log("Nhan load ngay hoan thanh");
	$.get(url, function(responseJson) {
		console.log("response complete date " + responseJson.name);
		if(responseJson.name != null){
			console.log("response complete date" + responseJson.name);
			$('#projectCompleted').empty();
			$('#projectCompleted').append("<h4>Ng??y ho??n th??nh " + responseJson.name +"</h4>");
		}
		

	}).done(function() {

	}).fail(function() {
		alert('Failed');
	});
}

async function saveBad() {
	url = "/rest/admin/task/rate";

	console.log("TaskID " + taskID);
	var bad = 101;
	console.log("Bad " + bad);


	var jsonData = { taskID: taskID, progress: bad };

	await $.ajax({
		type: "POST",
		contentType: "application/json",
		url: url,
		data: JSON.stringify(jsonData),

		success: function(data, textStatus, jqXHR) {
			var tmp = Number(1000) + Number(taskID);
			var trID = '#' + tmp;

			$('.table ' + trID + ' .cot5').empty();// x??a c???t 5 (c???t ti???n ?????)
			console.log("TrID" + trID);
			if (data.progress == 101) {
				$('table ' + trID + ' .cot5').append("<p style = 'font-size: 0.85rem; color: red;'><strong><i>Ho??n th??nh l??c: " + data.completeDate + "</i></strong></p>" + "<p style='font-size: 0.85rem; color: red;'><strong><i>????nh gi??: K??m</i></strong></p>");
			}
			alert("C???p nh???t th??nh c??ng");

		},
		error: function(jqXHR, textStatus, errorThrown) {

			alert("C???p nh???t th???t b???i " + textStatus);



		}
	});
}
async function saveGood() {
	url = "/rest/admin/task/rate";

	console.log("TaskID " + taskID);
	var good = 102;
	console.log("Good " + good);

	var jsonData = { taskID: taskID, progress: good };
	await $.ajax({
		type: "POST",
		contentType: "application/json",
		url: url,
		data: JSON.stringify(jsonData),

		success: function(data, textStatus, jqXHR) {
			var tmp = Number(1000) + Number(taskID);
			var trID = '#' + tmp;
			console.log("vao lan 2");
			$('.table ' + trID + ' .cot5').empty();// x??a c???t 5 (c???t ti???n ?????)

			if (data.progress == 102) {
				$('.table ' + trID + ' .cot5').append("<p style = 'font-size: 0.85rem; color: red;'><strong><i>Ho??n th??nh l??c: " + data.completeDate + "</i></strong></p>" + "<p style='font-size: 0.85rem; color: red;'><strong><i>????nh gi??: T???t</i></strong></p>");
			}
			alert("C???p nh???t th??nh c??ng");

		},
		error: function(jqXHR, textStatus, errorThrown) {

			alert("C???p nh???t th???t b???i " + textStatus);



		}
	});
}
function danhGiaBad() {
	$('#danhGiaCuaToiBad').empty();
	$('#danhGiaCuaToiBad').append("B???n ????nh gi?? c??ng vi???c n??y: K??m");
}

function danhGiaGood() {
	$('#danhGiaCuaToiGood').empty();
	$('#danhGiaCuaToiGood').append("B???n ????nh gi?? c??ng vi???c n??y: T???t");
}

async function deleteTask() {
	var url = "/rest/deleteTask";
	var jsonData = { task_id: taskID, empEmail: null };

	await $.ajax({
		type: "POST",
		contentType: "application/json",
		url: url,
		data: JSON.stringify(jsonData),

		success: function(responseJson, textStatus, jqXHR) {
			var tmp = Number(1000) + Number(taskID);
			var trID = '#' + tmp;
			console.log(trID);
			$('.table ' + trID).remove();


			var totalRow = $('#totalNowTaskNav').text();
			console.log("task row: " + totalRow);
			var deletedSucess = Number(totalRow) - 1;
			console.log("task new total row: " + deletedSucess);
			$('#totalNowTaskNav').empty();
			$('#totalNowTaskNav').append(deletedSucess);

			alert("X??a to??n b??? c??ng vi???c th??nh c??ng");
		},
		error: function(jqXHR, textStatus, errorThrown) {

			alert("X??a nh??n vi??n th???t b???i!");



		}
	});

}

async function delEmployeeOutTask() {
	var url = "/rest/delEmployeeOutTask";
	var jsonData = { task_id: taskID, empEmail: delEmailEmp };
	console.log(taskID);
	console.log(delEmailEmp);
	await $.ajax({
		type: "POST",
		contentType: "application/json",
		url: url,
		data: JSON.stringify(jsonData),

		success: function(responseJson, textStatus, jqXHR) {
			//data l?? d??? li???u 

			var rowID = '#' + taskID;//cot nhan vien
			$('tbody ' + rowID).empty();
			var rowClass = '.' + taskID;//cot nguoi sua
			$('tbody ' + rowClass).empty();


			$.each(responseJson.employee, function(index, employee) {
				$('tbody ' + rowID).append("<div>" + employee.name + "</div>");
				console.log(employee.name);
			});

			$.each(responseJson.modifyBy, function(index2, modifyBy) {

				$('tbody ' + rowClass).append('<div>' + modifyBy.name + '</div>');
				console.log(modifyBy.name);
			});


			alert("X??a nh??n vi??n th??nh c??ng");
		},
		error: function(jqXHR, textStatus, errorThrown) {

			alert("X??a nh??n vi??n th???t b???i!");



		}
	});

}

function clickDel() {
	delEmailEmp = $("#delDropDownEmps option:selected").val();
	console.log(delEmailEmp);
	delEmailEmployee.text(delEmailEmp);
	labelDelModifyBy.text(mapEmail.get(delEmailEmp));
}


function searchTask() {

	url = "/rest/searchTask/" + taskID;

	$.get(url, function(responseJson) {
		delDropDownEmps.empty();
		$.each(responseJson.employee, function(index, employee) {

			$("<option>").val(employee.email).text(employee.name).appendTo(delDropDownEmps);
			$.each(responseJson.modifyBy, function(index2, modifyBy) {
				if (index == index2) {
					mapEmail.set(employee.email, modifyBy.email);
				}

			});
		});


	}).done(function() {

	}).fail(function() {
		alert('Failed');
	});


}



function loadEmployee() {
	$('.myForm #exampleModal').modal();

	url = "/listEmployee";

	$.get(url, function(responseJson) {
		dropDownEmployees.empty();
		$.each(responseJson, function(index, employee) {
			$("<option>").val(employee.email).text(employee.name).appendTo(dropDownEmployees);
		});
	}).done(function() {

	}).fail(function() {
		alert('Failed');
	});

}



function clickChooseEmployee() {
	empSelected = $("#dropDownEmployees option:selected").val();
	emailEmployee.text(empSelected);
	console.log("Thay doi");
}

async function taskToEmployee() {
	var url = "/rest/adminUpdateTaskToEmployee";
	var jsonData = { task_id: taskID, empEmail: empSelected };

	await $.ajax({
		type: "POST",
		contentType: "application/json",
		url: url,
		data: JSON.stringify(jsonData),

		success: function(data, textStatus, jqXHR) {


			console.log("Status : ", textStatus);
			console.log("data: ", data);
			if (textStatus == "success") {
				var rowID = '#' + taskID;
				console.log(rowID);
				var nameEmp = $("#dropDownEmployees option:selected").text();
				//				update lai bang
				$('tbody ' + rowID).append("<div>" + nameEmp + "</div>");
				//				$('tbody ' + rowID + ' div').append("<input name = 'emailNhanVien"+  "' type = 'hidden' value = '"+ empSelected + "' />");
				var rowClass = '.' + taskID;
				$('tbody ' + rowClass).append('<div>' + data + '</div>');
				alert("Th??nh c??ng");
			}
			else
				alert("Giao vi???c th???t b???i nh??n vi??n ???? c?? trong c??ng vi???c");
		},
		error: function(jqXHR, textStatus, errorThrown) {

			alert("Giao vi???c th???t b???i nh??n vi??n ???? c?? trong c??ng vi???c");



		}
	});
	/*	
	??
		$.post(url, {taskID,emailEmployee}, function(response){
			alert("Thanh cong");
		}).fail(function(){
			alert("That bai");
		}).done(function(){
			alert("Done");
		});*/

}


