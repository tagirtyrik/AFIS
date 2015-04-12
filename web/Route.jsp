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
    Controller controller=new Controller(new Model());
    list1 = controller.routeList();
    list2 = controller.portList();
  %>
</head>
<script>
  function addRoute(){
    var takeOfPortId = parseInt(document.getElementById("selectTakeOfPort").value);
    var landingPortId = parseInt(document.getElementById("selectLandingPort").value);
    var distance = parseFloat(document.getElementById("distance").value);
    var url="?cmd=addroute&0="+takeOfPortId+"&1="+landingPortId+"&2="+distance;
    var xmlhttp = getXmlHttp();
    xmlhttp.open('GET', "View.jsp"+url, false);
    xmlhttp.send(null);
    if(xmlhttp.status == 200) {
      this.location.reload();
    }else alert("Произошла ошибка")

  }
  function setRoute(id){
    var takeOfPortId = parseInt(document.getElementById("selectTakeOfPort_"+id).value);
    var landingPortId = parseInt(document.getElementById("selectLandingPort_"+id).value);
    var distance =parseFloat(document.getElementById("distance_"+id).value);
    var xmlhttp = getXmlHttp();
    var url="?cmd=setroute&0="+id+"&1="+takeOfPortId+"&2="+landingPortId+"&3="+distance;
    var xmlhttp = getXmlHttp();
    xmlhttp.open('GET', "View.jsp"+url, false);
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
    <th>Id</th><th>Take-Off Port</th><th>Landing Port</th><th>Distance</th>
    <%
      for(Route r:list1)
      {
    %>
    <tr>
      <td>
        <input type="text" value="<%=r.getId()%>" readonly >
      </td>
      <td>
        <select id="selectTakeOfPort_<%=r.getId()%>" onchange="setRoute(<%=r.getId()%>)">
          <%for(Airport port: list2){%>
          <option value="<%=port.getId()%>" <%=r.getTakeOffPort()==port.getId()?"selected":""%> ><%=new String(port.getId()+":"+port.getName())%></option>
          <%}%>
        </select>
      </td>
      <td>
        <select id="selectLandingPort_<%=r.getId()%>" onchange="setRoute(<%=r.getId()%>)">
          <%for(Airport port: list2){%>
          <option value="<%=port.getId()%>" <%=r.getLandingPort()==port.getId()?"selected":""%> ><%=new String(port.getId()+":"+port.getName())%></option>
          <%}%>
        </select>
      </td>
      <td>
        <input id="distance_<%=r.getId()%>" type="text" value="<%=r.getDistance()%>" onchange="setRoute(<%=r.getId()%>)">
      </td>
      <td>
        <input type="button" value="X" onclick="delRoute(<%=r.getId()%>)">
      </td>
    </tr>
    <%
      }
    %>
  </table>
  <br>
  <table  align="center">
    <tr>
      <td>
        <input type="text" value="новый" readonly size="3">
      </td>
      <td>
        <select id="selectTakeOfPort">
          <%for(Airport a: list2){%>
          <option value="<%=a.getId()%>"><%=new String(a.getId()+":"+a.getName())%></option>
          <%}%>
        </select>
      </td>
      <td>
        <select id="selectLandingPort">
            <%for(Airport a: list2){%>
          <option value="<%=a.getId()%>"><%=new String(a.getId()+":"+a.getName())%></option>
            <%}%>
         </select>
      </td>
      <td>
        <input id="distance" type="text" value="0.0" >
      </td>
      <td>
        <input type="button" value="Добавить маршрут" onclick="addRoute()">
      </td>
  </tr>
  </table>
</div>
</body>
<%controller.exit();%>
</html>
