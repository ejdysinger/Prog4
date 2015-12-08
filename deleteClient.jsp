<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
 
<html>
<head>
<title>SELECT Operation</title>
</head>
<body>
 
<sql:setDataSource var="snapshot" driver="oracle.jdbc.OracleDriver"
     url="jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle"
     user="tkoch"  password="a6743"/>

<sql:update dataSource="${snapshot}" var="result">
delte from mattseall.client 
where mattseall.client.client_id = ?
  <sql:param value="${param.client_id}" />
</sql:update>
<sql:query dataSource="${snapshot}" var="result">
SELECT * from mattseall.client
</sql:query>
 
<table border="1" width="100%">
<tr>
   <th>Client Id</th>
   <th>last Name</th>
   <th>first name</th>
   <th>sex</th>   
   <th>miles driven</th>
   <th>license</th>
   <th>branch id</th>

</tr>
<c:forEach var="row" items="${result.rows}">
<tr>
   <td><c:out value="${row.client_id}"/></td>
   <td><c:out value="${row.lastname}"/></td>
   <td><c:out value="${row.firstname}"/></td>
   <td><c:out value="${row.sex}"/></td>
   <td><c:out value="${row.milesDriven}"/></td>
   <td><c:out value="${row.license}"/></td>
   <td><c:out value="${row.branch_id}"/></td>

</tr>
</c:forEach>
</table>
 
</body>
</html>