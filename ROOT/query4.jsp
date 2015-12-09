<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
 
<html>
<head>
<title>List employees that are Senior Instructors</title>
</head>
<body>
 
<sql:setDataSource var="snapshot" driver="oracle.jdbc.OracleDriver"
     url="jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle"
     user="tkoch"  password="a6743"/>

<sql:query dataSource="${snapshot}" var="result">
select emp_id, lastname, firstname, class 
from mattseall.employee 
where class='Senior Instr.'
</sql:query>
 
<table border="1" width="100%">
<tr>
   <th>Employee ID</th>
   <th>Last Name</th>
   <th>First Name</th>
   <th>Class</th>


</tr>
<c:forEach var="row" items="${result.rows}">
<tr>
   <td><c:out value="${row.emp_id}"/></td>
   <td><c:out value="${row.lastname}"/></td>
   <td><c:out value="${row.firstname}"/></td>
   <td><c:out value="${row.class}"/></td>


</tr>
</c:forEach>
</table>
 
</body>
</html>