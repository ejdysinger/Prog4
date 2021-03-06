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
insert into mattseall.lesson
(lesson_id, lesson_date, mileage, client_id, emp_id)
values
(?, TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS'), ?, ?, ?)
  <sql:param value="${param.lesson_id}" />
  <sql:param value="${param.lesson_date}" />
  <sql:param value="${param.mileage}" />
  <sql:param value="${param.client_id}" />
  <sql:param value="${param.emp_id}" />
</sql:update>
<sql:query dataSource="${snapshot}" var="result">
SELECT * from mattseall.lesson
</sql:query>
 
<table border="1" width="100%">
<tr>
   <th>lesson id</th>
   <th>lesson date</th>
   <th>mileage</th>
   <th>client_id</th>   
   <th>employee ID</th>

</tr>
<c:forEach var="row" items="${result.rows}">
<tr>
   <td><c:out value="${row.lesson_id}"/></td>
   <td><c:out value="${row.lesson_date}"/></td>
   <td><c:out value="${row.mileage}"/></td>
   <td><c:out value="${row.client_id}"/></td>
   <td><c:out value="${row.emp_id}"/></td>
</tr>
</c:forEach>
</table>
 
</body>
</html>