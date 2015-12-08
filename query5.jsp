<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
 
<html>
<head>
<title>Count of Clients</title>
</head>
<body>
 
<sql:setDataSource var="snapshot" driver="oracle.jdbc.OracleDriver"
     url="jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle"
     user="tkoch"  password="a6743"/>

<sql:query dataSource="${snapshot}" var="result">
SELECT count(client_id)
from employee.client
</sql:query>
 
<table border="1" width="100%">
<tr>
   <th>Number of Clients</th>



</tr>
<c:forEach var="row" items="${result.rows}">
<tr>
   <td><c:out value="${row.count(client_id}"/></td>


</tr>
</c:forEach>
</table>
 
</body>
</html>