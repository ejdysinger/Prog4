function Search() {
  window.location = "searchprocess.jsp";
}
function insertBranch(branch_id, street, postal, city, manager_id){
	console.log("here")
}
function testResults (form) {
    var branch_id = form.branch_id.value;
	var street = form.street.value;
    var postal = form.postal.value;
    var city = form.city.value;
    var manager_id = form.manager_id.value;

    alert ("You typed: " + branch_id );
}

function insertEmployee(emp_id, emp_class, lname, fname, branch_id, sex, phone_num, age){
	
}

function insertClient(client_id, lname, fname, sex, miles_driven, branch_id, valid_license){
	
}

function insertVehicle(vehicle_id, milage, emp_id){
	
}

function insertLesson(lesson_id, date, client_id, emp_id, vehicle_id, mileage){
	
}

function insertVI(vehicle_id, date, fault){
	
}

function insertExam(exam_id, type, client_id, result){
	
}

function ionsertInterview(client_id, emp_id, description){
	
}