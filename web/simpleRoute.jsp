
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
  <link rel="stylesheet" type="text/css" href="simple-style.css">
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
        <%=route.getId()%>
      </td>
      <td>
        <%Airport takeOff= controller.getAirport(route.getTakeOffPort());%>
        <%=new String(takeOff.getId()+":"+takeOff.getName())%>
      </td>
      <td>
        <%Airport landing= controller.getAirport(route.getLandingPort());%>
        <%=new String(landing.getId()+":"+landing.getName())%>
      </td>
      <td>
        <%=route.getDistance()%>
      </td>
    </tr>
    <%
      }
    %>
  </table>
  <br>

  <p align="Right"><a href="Route.jsp">Полная версия</a></p>
</div>
</body>
<%controller.exit();%>
</html>
