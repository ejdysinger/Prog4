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
insert into mattseall.exam
(exam_id, type, client_id, pass)
values
(?, ?, ?, ?)
  <sql:param value="${param.exam_id}" />
  <sql:param value="${param.type}" />
  <sql:param value="${param.client_id}" />
  <sql:param value="${param.pass}" />


</sql:update>
<sql:query dataSource="${snapshot}" var="result">
SELECT * from mattseall.exam
</sql:query>
 
<table border="1" width="100%">
<tr>
   <th>exam ID</th>
   <th>type</th>
   <th>client id</th>
   <th>pass</th>


</tr>
<c:forEach var="row" items="${result.rows}">
<tr>
   <td><c:out value="${row.exam_id}"/></td>
   <td><c:out value="${row.type}"/></td>
   <td><c:out value="${row.client_id}"/></td>
   <td><c:out value="${row.pass}"/></td>


</tr>
</c:forEach>
</table>
 
</body>
</html>