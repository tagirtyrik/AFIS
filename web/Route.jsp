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
    String id=request.getParameter("id");
    String takeOffPort =request.getParameter("takeOffPort");
    String landingPort=request.getParameter("landingPort");
    String distance=request.getParameter("distance");
    boolean useOr=(request.getParameter("useor")==null)?false:request.getParameter("useor").equalsIgnoreCase("true");
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
    Controller controller=new Controller(new Model(true));

    if(!useFSearch)routes = controller.routeList();
    else routes=controller.getFRoute(id, takeOffPort, landingPort, distance, useOr);
    airports = controller.portList();
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
  function search(){
    var id=document.getElementById("id_F").value;
    var takeOffPort=document.getElementById("takeOffPort_F").value;
    var landingPort=document.getElementById("landingPort_F").value;
    var distance=document.getElementById("distance_F").value;
    var useor=!document.getElementById("useOr").checked;
    if(id=="")id="null";
    if(takeOffPort=="")takeOffPort="null";
    if(landingPort=="")landingPort="null";
    if(distance=="")distance="null";
    window.location=window.location.pathname+"?id="+id+"&takeOffPort="+takeOffPort+"&landingPort="+landingPort+"&distance="+distance+"&useor="+useor+"#id_F";
  }


</script>
<body>
<br>
<%@ include file="Header.jspf" %>
<br>
<div class="content">
  <table align="center">
    <th>Id</th><th>Take-Off Port</th><th>Landing Port</th><th>Distance</th>
    <tr>
      <td>
        <input id="id_F" type="text" value="<%=id.equalsIgnoreCase("null")?"":id%>">
      </td>
      <td>
        <input id="takeOffPort_F" type="text" value="<%=takeOffPort.equalsIgnoreCase("null")?"":takeOffPort%>">
      </td>
      <td>
        <input id="landingPort_F" type="text" value="<%=landingPort.equalsIgnoreCase("null")?"":landingPort%>">
      </td>
      <td>
        <input id="distance_F" type="text" value="<%=distance.equalsIgnoreCase("null")?"":distance%>">
      </td>
      <td>
        <input type="button" value="Поиск" onclick="search()">
        <br>
        <input type="checkbox" id="useOr" <%=useOr?"":"checked"%>>Точное совпадение
      </td>
    </tr>
  </table>
</div>
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
        <select id="selectTakeOfPort_<%=route.getId()%>" onchange="setRoute(<%=route.getId()%>)">
          <%for(Airport port: airports){%>
          <option value="<%=port.getId()%>" <%=route.getTakeOffPort()==port.getId()?"selected":""%> ><%=new String(port.getId()+":"+port.getName())%></option>
          <%}%>
        </select>
      </td>
      <td>
        <select id="selectLandingPort_<%=route.getId()%>" onchange="setRoute(<%=route.getId()%>)">
          <%for(Airport port: airports){%>
          <option value="<%=port.getId()%>" <%=route.getLandingPort()==port.getId()?"selected":""%> ><%=new String(port.getId()+":"+port.getName())%></option>
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
  <br>
  <table  align="center">
    <tr>
      <td>
        <input type="text" value="новый" readonly size="3">
      </td>
      <td>
        <select id="selectTakeOfPort">
          <%for(Airport a: airports){%>
          <option value="<%=a.getId()%>"><%=new String(a.getId()+":"+a.getName())%></option>
          <%}%>
        </select>
      </td>
      <td>
        <select id="selectLandingPort">
            <%for(Airport a: airports){%>
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
  <p align="Right"><a href="simpleRoute.jsp">Версия для печати</a></p>
</div>
</body>
<%controller.exit();%>
</html>
