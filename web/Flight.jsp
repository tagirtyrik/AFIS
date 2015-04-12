<%--
  Created by IntelliJ IDEA.
  User: GeneraL
  Date: 09.04.2015
  Time: 11:15
  To change this template use File | Settings | File Templates.
--%>
<%@page import="model.Plane"%>
<%@page  import="java.util.ArrayList" %>
<%@page import="db.Sql" %>
<%@page import="db.DataAccessObject" %>
<%@ page import="java.io.*,java.util.*" %>
<%@ page import="model.aircraft.Boeing747SP" %>
<%@ page import="controller.Controller" %>
<%@ page import="model.Model" %>
<%@ page import="model.Flight" %>
<%@ page import="model.Route" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Flight Page</title>
  <script type="text/javascript" src="/lib/ajax.js"></script>
  <link rel="stylesheet" type="text/css" href="style.css">
  <%
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    /*timeFormat.setTimeZone(TimeZone.getDefault());
    dateFormat.setTimeZone(TimeZone.getDefault());*/
    ArrayList<Flight> flights;
    ArrayList<Plane> planes;
    ArrayList<Route> routes;
    Controller controller=new Controller(new Model());
    flights = controller.flightList();
    planes=controller.planeList();
    routes=controller.routeList();
  %>
</head>
<script>
  function addFlight(){
    var planeId=parseInt(document.getElementById("selectPlane").value);
    var routeId=parseInt(document.getElementById("selectRoute").value);
    var takeOffDate=document.getElementById("takeOffDate").value;
    var takeOffTime=document.getElementById("takeOffTime").value;
    var landDate=document.getElementById("landingDate").value;
    var landTime=document.getElementById("landingTime").value;
    var dateArr=takeOffDate.split("-");
    var timeArr=takeOffTime.split(":");
    var  takeOff=dateArr[1]+"."+dateArr[2]+"."+dateArr[0]+"-"+timeArr[0]+":"+timeArr[1];
    var dateArr=landDate.split("-");
    var timeArr=landTime.split(":");
    var Landing=dateArr[1]+"."+dateArr[2]+"."+dateArr[0]+"-"+timeArr[0]+":"+timeArr[1];
    var url="?cmd=addflight&0="+planeId+"&1="+routeId+"&2="+takeOff+"&3="+Landing;
    var xmlhttp = getXmlHttp();
    xmlhttp.open('GET', "View.jsp"+url, false);
    xmlhttp.send(null);
    if(xmlhttp.status == 200) {
      //alert(xmlhttp.responseText);
      this.location.reload();
    }else{
      alert("Произошла ошибка");
      this.location.reload();
    }

  }
  function setFlight(id){
    var planeId=parseInt(document.getElementById("selectPlane_"+id).value);
    var routeId=parseInt(document.getElementById("selectRoute_"+id).value);
    var takeOffDate=document.getElementById("takeOffDate_"+id).value;
    var takeOffTime=document.getElementById("takeOffTime_"+id).value;
    var landDate=document.getElementById("landingDate_"+id).value;
    var landTime=document.getElementById("landingTime_"+id).value;
    var dateArr=takeOffDate.split("-");
    var timeArr=takeOffTime.split(":");
    var  takeOff=dateArr[1]+"."+dateArr[2]+"."+dateArr[0]+"-"+timeArr[0]+":"+timeArr[1];
    var dateArr=landDate.split("-");
    var timeArr=landTime.split(":");
    var Landing=dateArr[1]+"."+dateArr[2]+"."+dateArr[0]+"-"+timeArr[0]+":"+timeArr[1];
    //dd.MM.yy-kk:mm
    var xmlhttp = getXmlHttp();
    var url="?cmd=setflight&0="+id+"&1="+planeId+"&2="+routeId+"&3="+takeOff+"&4="+Landing;
    xmlhttp.open('GET', "View.jsp"+url, false);
    xmlhttp.send(null);
    if(xmlhttp.status == 200) {
      // alert(xmlhttp.responseText);
    }else{
      alert("Произошла ошибка");
      this.location.reload();
    }
  }
  function delFlight(id){
    var xmlhttp = getXmlHttp();
    xmlhttp.open('GET', "View.jsp?cmd=delflight&0="+id, false);
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
<h1 align="center">Flight</h1>
  <table align="center">
    <th>Id</th><th>Plane</th><th>Route</th><th>Take-Off</th><th >Landing</th><th>Ticket price</th>
    <%
      for(Flight flight: flights)
      {
    %>
    <tr>
      <td>
        <input type="text" value="<%=flight.getId()%>" readonly size="3">
      </td>

      <td>
        <select id="selectPlane_<%=flight.getId()%>" onchange="setFlight(<%=flight.getId()%>)"  style="max-width: 200px;">
          <%for(Plane plane: planes){%>
          <option value="<%=plane.getId()%>" <%=flight.getPlane()==plane.getId()?"selected":""%> ><%=new String(plane.getId()+":"+plane.getNumber())%></option>
          <%}%>
        </select>
      </td>
      <td>
        <select id="selectRoute_<%=flight.getId()%>" onchange="setFlight(<%=flight.getId()%>)" style="max-width: 200px;">
          <%for(Route route: routes){%>
          <option value="<%=route.getId()%>" <%=flight.getRoute()==route.getId()?"selected":""%> ><%=new String(controller.getAirport(route.getTakeOffPort()).getLocation() +"->"+controller.getAirport(route.getLandingPort()).getLocation())%></option>
          <%}%>
        </select>
      </td>
      <td style="min-width: 240px">
        <input id="takeOffDate_<%=flight.getId()%>" type="date" value="<%=dateFormat.format(flight.getTakeOffTimeShedule())%>" onchange="setFlight(<%=flight.getId()%>)">
        <input id="takeOffTime_<%=flight.getId()%>" type="time" value="<%=timeFormat.format(flight.getTakeOffTimeShedule())%>" onchange="setFlight(<%=flight.getId()%>)">
      </td>
      <td style="min-width: 240px">
        <input id="landingDate_<%=flight.getId()%>" type="date" value="<%=dateFormat.format(flight.getLandingTimeShedule())%>" onchange="setFlight(<%=flight.getId()%>)">
        <input id="landingTime_<%=flight.getId()%>" type="time" value="<%=timeFormat.format(flight.getLandingTimeShedule())%>" onchange="setFlight(<%=flight.getId()%>)">
      </td>
      <td>
        <input id="cost_<%=flight.getId()%>" type="text" value="<%=String.format("%.2f", flight.ticketPrice(controller.getRoute(flight.getRoute()),controller.getPlane(flight.getPlane())))%>" size="7" readonly>
      </td>
      <td>
        <input type="button" value="X" onclick="delFlight(<%=flight.getId()%>)">
      </td>
    </tr>
    <%
      }
    %>
  </table>
  <br>
  <table>
    <tr>
      <td>
        <input type="text" value="новый" readonly size="3">
      </td>

      <td>
        <select id="selectPlane" style="max-width: 200px;">
          <%for(Plane plane: planes){%>
          <option value="<%=plane.getId()%>"><%=new String(plane.getId()+":"+plane.getNumber())%></option>
          <%}%>
        </select>
      </td>
      <td>
        <select id="selectRoute" style="max-width: 200px;">
          <%for(Route route: routes){%>
          <option value="<%=route.getId()%>"><%=new String(controller.getAirport(route.getTakeOffPort()).getLocation() +"->"+controller.getAirport(route.getLandingPort()).getLocation())%></option>
          <%}%>
        </select>
      </td>
      <td style="min-width: 240px">
        <input id="takeOffDate" type="date" value="" >
        <input id="takeOffTime" type="time" value="" >
      </td>
      <td style="min-width: 240px">
        <input id="landingDate" type="date" value="" >
        <input id="landingTime" type="time" value="" >
      </td>
      <td>
        <input type="button" value="Добавить полет" onclick="addFlight()">
      </td>
    </tr>
  </table>
</div>
</body>
<%controller.exit();%>
</html>
