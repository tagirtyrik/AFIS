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
<%@ page import="controller.Controller" %>
<%@ page import="model.Model" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="view.LocalView" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Plane Page</title>
    <script type="text/javascript" src="/lib/ajax.js"></script>
    <link rel="stylesheet" type="text/css" href="style.css">
  <%
      ArrayList<Plane> list1;
      Controller controller=new Controller(new Model());
      list1 = controller.planeList();
      controller.exit();

  %>
</head>
<script>
    function addPlane(number){
        var xmlhttp = getXmlHttp();
        xmlhttp.open('GET', "View.jsp?cmd=addplane&0="+number, false);
        xmlhttp.send(null);
        if(xmlhttp.status == 200) {
            //alert(xmlhttp.responseText);
            this.location.reload();
        }else alert("Произошла ошибка")

    }
    function setPlane(id,number){
        var xmlhttp = getXmlHttp();
        xmlhttp.open('GET', "View.jsp?cmd=setplane&0="+id+"&1="+number, false);
        xmlhttp.send(null);
        if(xmlhttp.status == 200) {
           // alert(xmlhttp.responseText);
        }else alert("Произошла ошибка")
    }
    function delPlane(id){
        var xmlhttp = getXmlHttp();
        xmlhttp.open('GET', "View.jsp?cmd=delplane&0="+id, false);
        xmlhttp.send(null);
        if(xmlhttp.status == 200) {
            //alert(xmlhttp.responseText);
            this.location.reload();
        }else alert("Произошла ошибка")

    }
</script>
<body>
  <h1 align="center">Plane</h1>
<p align="center"><div style="width:70%; margin: auto;">
    <table align="center">
        <th>Id</th><th>Name</th><th>Number</th><th>FuelConsumption</th><th>PassengerSeatsCount</th>
        <%
            for(Plane p:list1)
            {
        %>
        <tr>
            <td>
                <input type="text" value="<%=p.getId()%>" readonly >
            </td>

            <td>
                <input type="text" value="<%=p.getName()%>" readonly>
            </td>
            <td>
                <input type="text" value="<%=p.getNumber()%>" onchange="setPlane(<%=p.getId()%>,this.value)">
            </td>
            <td>
                <input type="text" value="<%=p.getFuelConsumption()%>"readonly>
            </td>
            <td>
                <input type="text" value="<%=p.getPassengerSeatsCount()%>"readonly>
            </td>
            <td>
                <input type="button" value="X" onclick="delPlane(<%=p.getId()%>)">
            </td>
        </tr>
        <%
            }
        %>
    </table>
      <p align="center"><input type="button" value="Добавить самолет" onclick="addPlane('')"></p>
  </div>
  </p>
</body>
</html>
