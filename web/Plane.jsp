<%-- 
    Document   : Plane
    Created on : 24.02.2015, 14:45:13
    Author     : Ксю
--%>
<%@page import="src.java.Tools.CreateDb"%>
<%@page import="src.java.model.Plane"%>
<%@page  import="java.util.ArrayList" %>
<%@page import="src.java.Tools.CreateModel "%>
<%@page import="src.java.db.Sql" %>
<%@page import="src.java.db.DataAccessObject" %>
<%@ page import="java.io.*,java.util.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>JSP Page</title>
  <%
    CreateModel model =new CreateModel();
    //CreateDb db = new CreateDb();
    ArrayList<Plane> list1 = model.getPlane();
    int count = list1.size();
  //  ArrayList<Plane> list2 = DataAccessObject.getPlanes();
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
