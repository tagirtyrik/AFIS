<%--
  Created by IntelliJ IDEA.
  User: Ксю
  Date: 07.02.2016
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
  <script type="text/javascript" src="/lib/ajax.js" charset="utf-8"></script>
  <link rel="stylesheet" type="text/css" href="style.css">
  <%
    String id=request.getParameter("id");
    String takeOffPort =request.getParameter("takeOffPort");
    String landingPort=request.getParameter("landingPort");
    String distance=request.getParameter("distance");
    boolean useFSearch=false;

    if(id==null)id="null";
    else useFSearch=true;
    if(takeOffPort==null)takeOffPort="null";
    else useFSearch=true;
    if(landingPort==null)landingPort="null";
    else useFSearch=true;
    if(distance==null)distance="null";
    else useFSearch=true;

    ArrayList<Route> routes;
    ArrayList<Airport> airports;
    Controller controller=new Controller(new Model());

    if(!useFSearch)routes = controller.routeList();
    else routes=controller.getFRoute(id, takeOffPort, landingPort, distance);
    airports = controller.portList();
  %>
</head>
<script>

   function addRoute(id){
    var url="?cmd=addroute&0="+id;
    var xmlhttp = getXmlHttp();
    xmlhttp.open('GET', "View.jsp"+url, false);
    xmlhttp.send(null);
     if(xmlhttp.status == 200) {
       this.location.reload();
     }else {
       alert("Ошибка. Возможно проблемы с доступом к базе данных \n");
       alert(xmlhttp.responseText);
       this.location.reload();
     }

  }
  function setRoute(id){
    var takeOfPortId = parseInt(document.getElementById("selectTakeOfPort_"+id).value);
    var landingPortId = parseInt(document.getElementById("selectLandingPort_"+id).value);
    var distance =parseFloat(document.getElementById("distance_"+id).value);
    var url="?cmd=setroute&0="+id+"&1="+takeOfPortId+"&2="+landingPortId+"&3="+distance;
    var xmlhttp = getXmlHttp();
    xmlhttp.open('GET', "View.jsp"+url, false);
    xmlhttp.send(null);
    if(xmlhttp.status == 200) {
      this.location.reload();
    }else {
      alert("Ошибка. Возможно проблемы с доступом к базе данных \n");
      alert(xmlhttp.responseText);
      this.location.reload();
    }
  }
  function delRoute(id){
    var xmlhttp = getXmlHttp();
    xmlhttp.open('GET', "View.jsp?cmd=delroute&0="+id, false);
    xmlhttp.send(null);
    if(xmlhttp.status == 200) {
      this.location.reload();
    }else {
      alert("Удаление не возможно, запись используется в другой таблице.");
      this.location.reload();
    }
  }
</script>
<br>
<%@ include file="Header.jspf" %>
<br>
<%@ include file="search/Route.jspf" %>
<br>
<div class="content">
<h1 align="center">Route</h1>
  <table align="center">
    <th>Id</th><th>Take-Off Port</th><th>Landing Port</th><th>Distance</th>
    <%
      for(Route route:routes)
      {
    %>
    <tr>
      <td>
        <input type="text" value="<%=route.getId()%>" readonly >
      </td>
      <td>
        <select id="selectTakeOfPort_<%=route.getId()%>" onchange="setRoute(<%=route.getId()%>)"  style="max-width: 150px;">
          <%for(Airport a: airports){%>
          <option value="<%=a.getId()%>" <%=route.getTakeOffPort()==a.getId()?"selected":""%> ><%=new String(a.getId()+":"+a.getName())%></option>
          <%}%>
        </select>
      </td>
      <td>
        <select id="selectLandingPort_<%=route.getId()%>" onchange="setRoute(<%=route.getId()%>)"  style="max-width: 150px;">
          <%for(Airport a: airports){%>
          <option value="<%=a.getId()%>" <%=route.getLandingPort()==a.getId()?"selected":""%> ><%=new String(a.getId()+":"+a.getName())%></option>
          <%}%>
        </select>
      </td>
      <td>
        <input id="distance_<%=route.getId()%>" type="text" value="<%=route.getDistance()%>" onchange="setRoute(<%=route.getId()%>)">
      </td>
      <td>
        <input type="button" value="X" onclick="delRoute(<%=route.getId()%>)">
      </td>
    </tr>
    <%
      }
    %>
  </table>
  <p align="center"><input type="button" value="Добавить маршрут" onclick="addRoute(<%=routes.get(routes.size()-1).getId()+1%>)"></p>
</div>
</body>
<%controller.exit();%>
</html>
