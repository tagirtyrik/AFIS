
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
<%@ page import="java.text.DateFormat" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Flight Page</title>
  <script type="text/javascript" src="/lib/ajax.js"></script>
  <link rel="stylesheet" type="text/css" href="simple-style.css">
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
    boolean useOr=(request.getParameter("useor")==null)?false:request.getParameter("useor").equalsIgnoreCase("true");
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
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM");
    /*timeFormat.setTimeZone(TimeZone.getDefault());
    dateFormat.setTimeZone(TimeZone.getDefault());*/
    ArrayList<Flight> flights;
    ArrayList<Plane> planes;
    ArrayList<Route> routes;

    Controller controller=new Controller(new Model(true));
    if(!useFSearch)flights = controller.flightList();
    else flights = controller.getFFlight(id,selectRoute,selectPlane,takeOff,landing,useOr);
    planes=controller.planeList();
    routes=controller.routeList();
  %>
</head>
<script>
  function search(){
    var id=document.getElementById("id_F").value;
    var selectPlane=document.getElementById("selectPlane_F").value;
    var selectRoute=document.getElementById("selectRoute_F").value;
    var takeOffDate=document.getElementById("takeOffDate_F").value;
    var takeOffTime=document.getElementById("takeOffTime_F").value;
    var landDate=document.getElementById("landingDate_F").value;
    var landTime=document.getElementById("landingTime_F").value;
    var dateArr=takeOffDate.split("-");
    var timeArr=takeOffTime.split(":");
    var  takeOff=dateArr[1]+"."+dateArr[2]+"."+dateArr[0]+"-"+timeArr[0]+":"+timeArr[1];
    var dateArr=landDate.split("-");
    var timeArr=landTime.split(":");
    var landing=dateArr[1]+"."+dateArr[2]+"."+dateArr[0]+"-"+timeArr[0]+":"+timeArr[1];
    //dd.MM.yy-kk:mm
    var useor=!document.getElementById("useOr").checked;
    if(id=="")id="null";
    if(takeOff=="" || takeOffDate=="" || takeOffTime=="")takeOff="null";
    if(selectRoute=="")selectRoute="null";
    if(selectPlane=="")selectPlane="null";
    if(landing=="" || landDate=="" || landTime=="")landing="null";
    window.location=window.location.pathname+"?id="+id+"&selectPlane="+selectPlane+"&selectRoute="+selectRoute+"&takeOff="+takeOff+"&landing="+landing+"&useor="+useor+"#id_F";

  }
</script>
<body>
<br>
<%@ include file="Header.jspf" %>
<br>
<div class="content">
  <table align="center">
    <th>Id</th><th>Plane</th><th>Route</th><th>Take-Off</th><th >Landing</th><th></th>
    <tr>
      <td>
        <input type="text" id="id_F" value="<%=id.equalsIgnoreCase("null")?"":id%>" size="3">
      </td>

      <td>
        <select id="selectPlane_F"  style="max-width: 150px;">
          <option value="null" <%=selectPlane.equalsIgnoreCase("null")? "selected":""%> ></option>
          <%for(Plane plane: planes){%>
          <option value="<%=plane.getId()%>" <%=(selectPlane.equalsIgnoreCase("null")?-1:Integer.parseInt(selectPlane)) == plane.getId() ? "selected":""%> ><%=new String(plane.getId()+":"+plane.getNumber())%></option>
          <%}%>
        </select>
      </td>
      <td>
        <select id="selectRoute_F" style="max-width: 150px;">
          <option value="null" <%=selectRoute.equalsIgnoreCase("null")? "selected":""%> ></option>
          <%for(Route route: routes){%>
          <option value="<%=route.getId()%>" <%=(selectRoute.equalsIgnoreCase("null")?-1:Integer.parseInt(selectRoute)) == route.getId() ? "selected":""%> ><%=new String(controller.getAirport(route.getTakeOffPort()).getLocation() +"->"+controller.getAirport(route.getLandingPort()).getLocation())%></option>
          <%}%>
        </select>
      </td>
      <td style="min-width: 240px">
        <input id="takeOffDate_F" type="date" value="<%=takeOffDate!=null?dateFormat.format(takeOffDate):""%>" >
        <input id="takeOffTime_F" type="time" value="<%=takeOffDate!=null?timeFormat.format(takeOffDate):""%>" >
      </td>
      <td style="min-width: 240px">
        <input id="landingDate_F" type="date" value="<%=landingDate!=null?dateFormat.format(landingDate):""%>" >
        <input id="landingTime_F" type="time" value="<%=landingDate!=null?timeFormat.format(landingDate):""%>" >
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
<h1 align="center">Flight</h1>
  <table align="center">
    <th>Id</th><th>Plane</th><th>Route</th><th>Take-Off</th><th >Landing</th><th>Ticket price</th>
    <%
      for(Flight flight: flights)
      {
    %>
    <tr>
      <td>
        <%=flight.getId()%>
      </td>

      <td>
        <% Plane plane=controller.getPlane(flight.getPlane());%>
        <%=plane.getId()+":"+plane.getNumber()%>
        </select>
      </td>
      <td>
        <% Route route=controller.getRoute(flight.getRoute());%>
        <%=controller.getAirport(route.getTakeOffPort()).getLocation()+"→"+
                controller.getAirport(route.getLandingPort()).getLocation()%>
        </select>
      </td>
      <td style="min-width: 240px">
       <%=dateFormat.format(flight.getTakeOffTimeShedule())%>
        <%=timeFormat.format(flight.getTakeOffTimeShedule())%>
      </td>
      <td style="min-width: 240px">
       <%=dateFormat.format(flight.getLandingTimeShedule())%>
        <%=timeFormat.format(flight.getLandingTimeShedule())%>
      </td>
      <td>
        <%=String.format("%.2f", flight.ticketPrice(controller.getRoute(flight.getRoute()),controller.getPlane(flight.getPlane())))%>
      </td>
    </tr>
    <%
      }
    %>
  </table>
  <br>
  <p align="Right"><a href="Flight.jsp">Полная версия</a></p>
</div>
</body>
<%controller.exit();%>
</html>
