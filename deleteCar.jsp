<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
 
<html>
<head>
<title>Delete Vehicle Operation</title>
</head>
<body>
 
<sql:setDataSource var="snapshot" driver="oracle.jdbc.OracleDriver"
     url="jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle"
     user="tkoch"  password="a6743"/>

<sql:update dataSource="${snapshot}" var="result">
delte from mattseall.vehicle
where mattseall.vehicle.vehicle_id = ?
  <sql:param value="${param.vehicle_id}" />
</sql:update>
<sql:query dataSource="${snapshot}" var="result">
SELECT * from mattseall.vehicle
</sql:query>
 
<table border="1" width="100%">
<tr>
   <th>Vehicle Id</th>
   <th>Employee Id</th>
    <th>Description</th>   

</tr>
<c:forEach var="row" items="${result.rows}">
<tr>
   <td><c:out value="${row.vehicle_id}"/></td>
   <td><c:out value="${row.emp_id}"/></td>
    <td><c:out value="${row.description}"/></td>
</tr>
</c:forEach>
</table>
 
</body>
</html>