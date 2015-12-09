<html>
<body>

<%

// Grab the variables from the form.
  String branch_id = request.getParameter("branch_id");
  String street = request.getParameter("street");
  String postal = request.getParameter("postal");
  String city = request.getParameter("city");
    String manager_id = request.getParameter("manager_id");

%>
<%-- Print out the variables. --%>
<br><%=branch_id%></br>
<br><%=street%></br>
<br><%=postal%></br>
<br><%=city%></br>
<br><%=manager_id%></br>

</body>
</html>