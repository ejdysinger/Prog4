<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
 
<html>
<head>
<title>Names of the clients who passed the driving exam.</title>
</head>
<body>
 
<sql:setDataSource var="snapshot" driver="oracle.jdbc.OracleDriver"
     url="jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle"
     user="tkoch"  password="a6743"/>

<sql:query dataSource="${snapshot}" var="result">
SELECT unique b.firstname as fname, b.lastname as lname, b.client_id as cid
from mattseall.exam a, mattseall.client b
where a.client_id=b.client_id and a.type='driving' and a.pass='y'
</sql:query>
 
<table border="1" width="100%">
<tr>
   <th>First Name</th>
   <th>Last Name</th>
   <th>Client ID</th>

</tr>
<c:forEach var="row" items="${result.rows}">
<tr>
   <td><c:out value="${row.fname}"/></td>
   <td><c:out value="${row.lname}"/></td>
   <td><c:out value="${row.cid}"/></td>

</tr>
</c:forEach>
</table>
 
</body>
</html>