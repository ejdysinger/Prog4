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