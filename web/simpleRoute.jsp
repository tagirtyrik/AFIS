
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
<body>
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
