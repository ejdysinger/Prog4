<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
 
<html>
<head>
<title>Delete employee Operation</title>
</head>
<body>
 
<sql:setDataSource var="snapshot" driver="oracle.jdbc.OracleDriver"
     url="jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle"
     user="tkoch"  password="a6743"/>

<sql:update dataSource="${snapshot}" var="result">
delete from mattseall.employee 
where emp_id = ?
  <sql:param value="${param.emp_id}" />
</sql:update>
<sql:query dataSource="${snapshot}" var="result">
SELECT * from mattseall.employee
</sql:query>
 
<table border="1" width="100%">
<tr>
   <th>Employee Id</th>
   <th>Class</th>
    <th>Last Name</th>
   <th>First name</th>
   <th>Sex</th>   
   <th>Phone Number</th>
   <th>Age</th>
   <th>Branch</th>

</tr>
<c:forEach var="row" items="${result.rows}">
<tr>
   <td><c:out value="${row.emp_id}"/></td>
   <td><c:out value="${row.class}"/></td>
    <td><c:out value="${row.lastname}"/></td>
   <td><c:out value="${row.firstname}"/></td>
   <td><c:out value="${row.sex}"/></td>
   <td><c:out value="${row.phoneNum}"/></td>
   <td><c:out value="${row.age}"/></td>
   <td><c:out value="${row.branch_id}"/></td>

</tr>
</c:forEach>
</table>
 
</body>
</html>