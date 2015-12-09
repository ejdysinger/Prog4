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
insert into mattseall.vehicle_inspection
(vehicle_id, inspection_date, fault)
values
(?, TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS'), ?)
  <sql:param value="${param.vehicle_id}" />
  <sql:param value="${param.inspection_date}" />
  <sql:param value="${param.fault}" />
</sql:update>
<sql:query dataSource="${snapshot}" var="result">
SELECT * from mattseall.vehicle_inspection
</sql:query>
 
<table border="1" width="100%">
<tr>
   <th>vehicle ID</th>
   <th>inspection date</th>
   <th>fault</th>

</tr>
<c:forEach var="row" items="${result.rows}">
<tr>
   <td><c:out value="${row.vehicle_id}"/></td>
   <td><c:out value="${row.inspection_date}"/></td>
   <td><c:out value="${row.fault}"/></td>

</tr>
</c:forEach>
</table>
 
</body>
</html>