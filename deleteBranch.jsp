<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
 
<html>
<head>
<title>Delete Operation</title>
</head>
<body>
 
<sql:setDataSource var="snapshot" driver="oracle.jdbc.OracleDriver"
     url="jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle"
     user="tkoch"  password="a6743"/>

<sql:update dataSource="${snapshot}" var="result">
delete from mattseall.branch 
where branch_id = ?
  <sql:param value="${param.branch_id}" />
</sql:update>
<sql:query dataSource="${snapshot}" var="result">
SELECT * from mattseall.branch
</sql:query>
 
<table border="1" width="100%">
<tr>
   <th>Branch Id</th>
   <th>Street</th>
    <th>Postal</th>
   <th>City</th>
   <th>Mananger ID</th>   

</tr>
<c:forEach var="row" items="${result.rows}">
<tr>
   <td><c:out value="${row.branch_id}"/></td>
   <td><c:out value="${row.street}"/></td>
    <td><c:out value="${row.postal}"/></td>
   <td><c:out value="${row.city}"/></td>
   <td><c:out value="${row.manager_id}"/></td>

</tr>
</c:forEach>
</table>
 
</body>
</html>