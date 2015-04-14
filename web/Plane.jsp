<%-- 
    Document   : Plane
    Created on : 24.02.2015, 14:45:13
    Author     : Ксю
--%>
<%@page import="model.Plane"%>
<%@page  import="java.util.ArrayList" %>
<%@page import="db.Sql" %>
<%@page import="db.DataAccessObject" %>
<%@ page import="java.io.*,java.util.*" %>
<%@ page import="model.aircraft.Boeing747SP" %>
<%@ page import="controller.Controller" %>
<%@ page import="model.Model" %>
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
      Controller controller=new Controller(new Model(true));
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
        }else{
            alert("Произошла ошибка");
            this.location.reload();
        }

    }
    function setPlane(id){
        var number=document.getElementById("number_"+id).value;
        var name=document.getElementById("name_"+id).value;
        var passangers=parseInt(document.getElementById("passengers_"+id).value);
        var fuel=parseFloat(document.getElementById("fuel_"+id).value);
        var xmlhttp = getXmlHttp();
        var url="?cmd=setplane&0="+id+"&1="+number+"&2="+name+"&3="+passangers+"&4="+fuel;
        xmlhttp.open('GET', "View.jsp"+url, false);
        xmlhttp.send(null);
        if(xmlhttp.status == 200) {
           // alert(xmlhttp.responseText);
        }else{
            alert("Произошла ошибка");
            this.location.reload();
        }
    }
    function delPlane(id){
        var xmlhttp = getXmlHttp();
        xmlhttp.open('GET', "View.jsp?cmd=delplane&0="+id, false);
        xmlhttp.send(null);
        if(xmlhttp.status == 200) {
            //alert(xmlhttp.responseText);
            this.location.reload();
        }else {
            alert("Произошла ошибка");
            this.location.reload();
        }

    }
</script>
<body>
<br>
<%@ include file="Header.jspf" %>
<br>
<div class="content">
  <h1 align="center">Plane</h1>
    <table align="center">
        <th>Id</th><th>Title</th><th>Name</th><th>FuelConsumption</th><th>PassengerSeatsCount</th>
        <%
            for(Plane p:list1)
            {
        %>
        <tr>
            <td>
                <input type="text" value="<%=p.getId()%>" readonly >
            </td>

            <td>
                <input id="name_<%=p.getId()%>" type="text" value="<%=p.getName()%>" onchange="setPlane(<%=p.getId()%>)">
            </td>
            <td>
                 <input id="number_<%=p.getId()%>" type="text" value="<%=p.getNumber()%>" onchange="setPlane(<%=p.getId()%>)">
            </td>
            <td>
                <input id="fuel_<%=p.getId()%>" type="text" value="<%=p.getFuelConsumption()%>" onchange="setPlane(<%=p.getId()%>)">
            </td>
            <td>
                <input id="passengers_<%=p.getId()%>" type="text" value="<%=p.getPassengerSeatsCount()%>" onchange="setPlane(<%=p.getId()%>)">
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
</body>
</html>
