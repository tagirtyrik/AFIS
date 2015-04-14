<%--
  Created by IntelliJ IDEA.
  User: Ксю
  Date: 07.04.2015
  Time: 17:22
  To change this template use File | Settings | File Templates.
--%>
<%@page  import="java.util.ArrayList" %>
<%@ page import="controller.Controller" %>
<%@ page import="model.Model" %>
<%@ page import="model.Airport" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Airport Page</title>
  <script type="text/javascript" src="/lib/ajax.js"></script>
  <link rel="stylesheet" type="text/css" href="style.css">
  <%
    ArrayList<Airport> list1;
    Controller controller=new Controller(new Model(true));
    list1 = controller.portList();
    controller.exit();

  %>
</head>
<script>
  function addPort(name, location){
    var xmlhttp = getXmlHttp();
    xmlhttp.open('GET', "View.jsp?cmd=addport&0="+name+"&1="+location, false);
    xmlhttp.send(null);
    if(xmlhttp.status == 200) {
      this.location.reload();
    }else alert("Произошла ошибка")

  }
  function setPort(id){
    var name=document.getElementById("name_"+id).value;
    var location=(document.getElementById("location_"+id).value);
    var xmlhttp = getXmlHttp();
    var url="?cmd=setport&0="+id+"&1="+name+"&2="+location;
    xmlhttp.open('GET', "View.jsp"+url, false);
    xmlhttp.send(null);
    if(xmlhttp.status == 200) {
    }else alert("Произошла ошибка")
  }
  function delPort(id){
    var xmlhttp = getXmlHttp();
    xmlhttp.open('GET', "View.jsp?cmd=delport&0="+id, false);
    xmlhttp.send(null);
    if(xmlhttp.status == 200) {
      this.location.reload();
    }else alert("Произошла ошибка")

  }
</script>
<body>
<br>
<%@ include file="Header.jspf" %>
<br>
<div class="content">
<h1 align="center">Airport</h1>
<p align="center">
  <table align="center">
    <th>Id</th><th>Name</th><th>Location</th>
    <%
      for(Airport a:list1)
      {
    %>
    <tr>
      <td>
        <input type="text" value="<%=a.getId()%>" readonly >
      </td>
      <td>
        <input id="name_<%=a.getId()%>" type="text" value="<%=a.getName()%>" onchange="setPort(<%=a.getId()%>)">
      </td>
      <td>
        <input id="location_<%=a.getId()%>" type="text" value="<%=a.getLocation()%>" onchange="setPort(<%=a.getId()%>)">
      </td>
      <td>
        <input type="button" value="X" onclick="delPort(<%=a.getId()%>)">
      </td>
    </tr>
    <%
      }
    %>
  </table>
  <p align="center"><input type="button" value="Добавить аэропорт" onclick="addPort('', '')"></p>
</p>
</div>
</body>
</html>

