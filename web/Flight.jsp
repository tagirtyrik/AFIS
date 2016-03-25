<%--
  Created by IntelliJ IDEA.
  User: Ксю
  Date: 09.02.2016
  Time: 11:15
  To change this template use File | Settings | File Templates.
--%>
<%@page import="model.Plane"%>
<%@page  import="java.util.ArrayList" %>
<%@ page import="java.util.*" %>
<%@ page import="controller.Controller" %>
<%@ page import="model.Model" %>
<%@ page import="model.Flight" %>
<%@ page import="model.Route" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DateFormat" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Flight Page</title>
  <script type="text/javascript" src="/lib/ajax.js" charset="utf-8"></script>
  <link rel="stylesheet" type="text/css" href="style.css">
  <%
    DateFormat formatter =new SimpleDateFormat("dd.MM.yy-HH:mm");
    String id=request.getParameter("id");
    String selectPlane=request.getParameter("selectPlane");
    String selectRoute=request.getParameter("selectRoute");
    String takeOff=request.getParameter("takeOff");
    Date takeOffDate=null;
    try{
      if(takeOff!=null)takeOffDate=formatter.parse(takeOff);
    }catch(java.text.ParseException e){
      takeOff=null;
    }
    String landing=request.getParameter("landing");
    Date landingDate=null;
    try{
      if(landing!=null)landingDate=formatter.parse(landing);
    }catch(java.text.ParseException e){
      landing=null;
    }

    boolean useFSearch=false;

    if(id==null)id="null";
    else useFSearch=true;
    if(selectPlane==null)selectPlane="null";
    else useFSearch=true;
    if(selectRoute==null)selectRoute="null";
    else useFSearch=true;
    if(takeOff==null)takeOff="null";
    else useFSearch=true;
    if(landing==null)landing="null";
    else useFSearch=true;

    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    ArrayList<Flight> flights;
    ArrayList<Plane> planes;
    ArrayList<Route> routes;

    Controller controller=new Controller(new Model(true));
    if(!useFSearch)flights = controller.flightList();
    else flights = controller.getFFlight(id,selectRoute,selectPlane,takeOff,landing);
    planes=controller.planeList();
    routes=controller.routeList();
  %>
</head>
<script>
 function addFlight(id){
    var url="?cmd=addflight&0="+id+"&1="+"0"+"&2="+"0"+"&3="+"01.01.2016-00:00"+"&4="+"01.01.2016-00:00";
    var xmlhttp = getXmlHttp();
    xmlhttp.open('GET', "View.jsp"+url, false);
    xmlhttp.send(null);
   if(xmlhttp.status == 200) {
     this.location.reload();
   }else {
     alert("Ошибка. Возможно неверный ввод данных. Проверьте правильность ввода. \n");
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
    var  takeOff=dateArr[1]+"."+dateArr[0]+"."+dateArr[2]+"-"+timeArr[0]+":"+timeArr[1];
    var dateArr=landDate.split("-");
    var timeArr=landTime.split(":");
    var landing=dateArr[1]+"."+dateArr[0]+"."+dateArr[2]+"-"+timeArr[0]+":"+timeArr[1];
    var xmlhttp = getXmlHttp();
    var url="?cmd=setflight&0="+id+"&1="+planeId+"&2="+routeId+"&3="+takeOff+"&4="+landing;
    xmlhttp.open('GET', "View.jsp"+url, false);
    xmlhttp.send(null);
    if(xmlhttp.status == 200) {
      this.location.reload();
    }else {
      alert(xmlhttp.responseText);
      this.location.reload();
    }
  }

  function delFlight(id){
    var xmlhttp = getXmlHttp();
    xmlhttp.open('GET', "View.jsp?cmd=delflight&0="+id, false);
    xmlhttp.send(null);
    if(xmlhttp.status == 200) {
      this.location.reload();
    }else {
      alert("Ошибка. Возможно проблемы с доступом к базе данных \n");
      alert(xmlhttp.responseText);
      this.location.reload();
    }
  }
</script>
<br>
<%@ include file="Header.jspf" %>
<br>
<%@ include file="search/Flight.jspf" %>
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
        <select id="selectPlane_<%=flight.getId()%>" onchange="setFlight(<%=flight.getId()%>)"  style="max-width: 150px;">
          <%for(Plane plane: planes){%>
          <option value="<%=plane.getId()%>" <%=flight.getPlane()==plane.getId()?"selected":""%> ><%=new String(plane.getId()+":"+plane.getNumber())%></option>
          <%}%>
        </select>
      </td>
      <td>
        <select id="selectRoute_<%=flight.getId()%>" onchange="setFlight(<%=flight.getId()%>)" style="max-width: 150px;">
          <%for(Route route: routes){%>
          <option value="<%=route.getId()%>" <%=flight.getRoute()==route.getId()?"selected":""%> ><%=new String(controller.getAirport(route.getTakeOffPort()).getLocation() +"->"+controller.getAirport(route.getLandingPort()).getLocation())%></option>
          <%}%>
        </select>
      </td>
      <td style="min-width: 240px">
        <input style="max-width: 70px"  id="takeOffDate_<%=flight.getId()%>" value="<%=dateFormat.format(flight.getTakeOffTimeShedule())%>" onchange="setFlight(<%=flight.getId()%>)">
        <input style="max-width: 35px" id="takeOffTime_<%=flight.getId()%>"  value="<%=timeFormat.format(flight.getTakeOffTimeShedule())%>" onchange="setFlight(<%=flight.getId()%>)">
      </td>
      <td style="min-width: 240px">
        <input style="max-width: 70px" id="landingDate_<%=flight.getId()%>"  value="<%=dateFormat.format(flight.getLandingTimeShedule())%>" onchange="setFlight(<%=flight.getId()%>)">
        <input style="max-width: 35px" id="landingTime_<%=flight.getId()%>"  value="<%=timeFormat.format(flight.getLandingTimeShedule())%>" onchange="setFlight(<%=flight.getId()%>)">
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
  <p align="center"><input type="button" value="Добавить полет" onclick="addFlight(<%= flights.get(flights.size()-1).getId()+1%>)"></p>
  </div>
</body>
<%controller.exit();%>
</html>
