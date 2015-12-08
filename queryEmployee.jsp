// query: list all senior instructors in the company and the branches where they work

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

<sql:query dataSource="${snapshot}" var="result">
select emp_id, lastname, firstname, class from mattseall.employee where class="senior_instructor";
</sql:update>

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

