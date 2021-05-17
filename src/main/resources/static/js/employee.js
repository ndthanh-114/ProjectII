var oneTaskEmp;
var danhMuc;
var totalTask;
var dem=0;
$(document).ready(function() {
	oneTaskEmp = $('#oneTaskEmp');
	danhMuc = $('#danhMuc');
	totalTask = $('#totalTask');
	$('.table .aDetailEmployee').on('click', function(event) {
		event.preventDefault();
		var href = $(this).attr('href');
		$('.myFormDetail #detailEmployee').modal();
			
		$.get(href, function(responseJson) {
			dem = 0;
			danhMuc.empty();
			oneTaskEmp.empty();
			$.each(responseJson, function(index, detailEmployee) {
				
				danhMuc.append("<div class= 'oneTaskEmp'><div class='form-group'><label for='detailNameProject' class='col-form-label'>Tên dự án:</label> <label id='detailNameProject' class='col-form-label'><a href='/admin/project/detail/" +detailEmployee.project.id + "'>" +detailEmployee.project.name + "</a></label></div><div class='form-group'><label for='Danh sách công việc' class='col-form-label'>Công việc:</label> <label id='listTask' class='col-form-label'>"+detailEmployee.task.name + "</label></div>");
				if(detailEmployee.progress <= 100){
					danhMuc.append("<div class='form-group classProgress'><label for='progressProject' class='col-form-label'>Tiến độ:</label> <label id='progressProject' class='col-form-label'>" +detailEmployee.progress + "</label></div>");
				}else if(detailEmployee.progress == 101){
					danhMuc.append("<div class='form-group classProgress'><label for='progressProject' class='col-form-label'></label> <label id='progressProject' class='col-form-label'><strong>Đánh giá: Kém</strong></label></div>");
				}else if(detailEmployee.progress == 102){
					danhMuc.append("<div class='form-group classProgress'><label for='progressProject' class='col-form-label'></label> <label id='progressProject' class='col-form-label'><strong style='color:red;'>Đánh giá: Tốt</strong></label></div>");
				}
				dem++;
			});
			 $('#totalTask').empty();
			  $('#totalTask').append("Tổng số công việc: " + dem);
			
			
		}).done(function() {

		}).fail(function() {
			alert('Thất bại');
		});



	});


});