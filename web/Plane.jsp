<%-- 
    Document   : Plane
    Created on : 24.02.2015, 14:45:13
    Author     : Ксю
--%>
<%@page import="Tools.CreateDb"%>
<%@page import="model.Plane"%>
<%@page  import="java.util.ArrayList" %>
<%@page import="Tools.CreateModel "%>
<%@page import="db.Sql" %>
<%@page import="db.DataAccessObject" %>
<%@ page import="java.io.*,java.util.*" %>
<%@ page import="model.aircraft.Boeing747SP" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>JSP Page</title>
  <%
    ArrayList<Plane> list1 = new ArrayList<>();
      list1.add(new Boeing747SP(0,"plane1"));
      list1.add(new Boeing747SP(1,"plane2"));
      list1.add(new Boeing747SP(2,"plane3"));
      list1.add(new Boeing747SP(3,"plane4"));
      list1.add(new Boeing747SP(4,"plane5"));
    int count = list1.size();
  %>
</head>
<body>
  <h1 align="center">Plane</h1>
  <form name="plane" method="post">
    <table border = "1" width="250" height="170" align="center">
        <th>Id</th><th>Name</th><th>Number</th><th>FuelConsumption</th><th>PassengerSeatsCount</th>
        <%
            for(Plane p:list1)
            {
        %>
        <tr>
            <td>
                <input type="text" value="<%=p.getId()%>">
            </td>
            <td>
                <input type="text" value="<%=p.getName()%>">
            </td>
            <td>
                <input type="text" value="<%=p.getNumber()%>">
            </td>
            <td>
                <input type="text" value="<%=p.getFuelConsumption()%>">
            </td>
            <td>
                <input type="text" value="<%=p.getPassengerSeatsCount()%>">
            </td>
        </tr>
        <%
            }
        %>
    </table>
</form>
</body>
</html>
