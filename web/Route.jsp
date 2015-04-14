<%--
  Created by IntelliJ IDEA.
  User: Ксю
  Date: 07.04.2015
  Time: 19:20
  To change this template use File | Settings | File Templates.
--%>
<%@page  import="java.util.ArrayList" %>
<%@ page import="controller.Controller" %>
<%@ page import="model.Model" %>
<%@ page import="model.Route" %>
<%@ page import="model.Airport" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Route Page</title>
  <script type="text/javascript" src="/lib/ajax.js"></script>
  <link rel="stylesheet" type="text/css" href="style.css">
  <%
    ArrayList<Route> list1;
    ArrayList<Airport> list2;
    Controller controller=new Controller(new Model(true));
    list1 = controller.routeList();
    list2 = controller.portList();
    controller.exit();

  %>
</head>
<script>
  function addRoute(idPort1, idPort2, distance){
    var xmlhttp = getXmlHttp();
    xmlhttp.open('GET', "View.jsp?cmd=addroute&0="+idPort1+"&1="+idPort2+"&2="+distance, false);
    xmlhttp.send(null);
    if(xmlhttp.status == 200) {
      this.location.reload();
    }else alert("Произошла ошибка")

  }
  function setRoute(id, idPort1, idPort2, distance){
    var xmlhttp = getXmlHttp();
    xmlhttp.open('GET', "View.jsp?cmd=setroute&0="+id+"&1="+idPort1+"&2="+idPort2+"&3="+distance, false);
    xmlhttp.send(null);
    if(xmlhttp.status == 200) {
    }else alert("Произошла ошибка")
  }
  function delRoute(id){
    var xmlhttp = getXmlHttp();
    xmlhttp.open('GET', "View.jsp?cmd=delroute&0="+id, false);
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
<h1 align="center">Route</h1>
  <table align="center">
    <th>Id</th><th>Name</th><th>Location</th>
    <%
      for(Route r:list1)
      {
    %>
    <tr>
      <td>
        <input type="text" value="<%=r.getId()%>" readonly >
      </td>
      <td>
        <input type="text" value="<%=r.getLandingPort()%>" onchange="setRoute(<%=r.getId()%>,this.value, this.value, this.value)">
      </td>
      <td>
        <input type="text" value="<%=r.getTakeOffPort()%>" onchange="setRoute(<%=r.getId()%>,this.value, this.value, this.value)">
      </td>
      <td>
        <input type="text" value="<%=r.getDistance()%>" onchange="setRoute(<%=r.getId()%>,this.value, this.value, this.value)">
      </td>
      <td>
        <input type="button" value="X" onclick="delRoute(<%=r.getId()%>)">
      </td>
    </tr>
    <%
      }
    %>
  </table>
  <p align="center">
    <input type="button" value="Добавить аэропорт" onclick="addRoute(<%=list2.get(0).getId()%>, <%=list2.get(1).getId()%>, 0)">
  </p>
</div>
</body>
</html>
