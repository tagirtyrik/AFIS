
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
  <link rel="stylesheet" type="text/css" href="simple-style.css">
  <%
    String id=request.getParameter("id");
    String name=request.getParameter("name");
    String location=request.getParameter("location");
    boolean useOr=(request.getParameter("useor")==null)?false:request.getParameter("useor").equalsIgnoreCase("true");
    boolean useFSearch=false;

    if(id==null)id="null";
    else useFSearch=true;
    if(name==null)name="null";
    else useFSearch=true;
    if(location==null)location="null";
    else useFSearch=true;

    ArrayList<Airport> airports;
    Controller controller=new Controller(new Model(true));

    if(!useFSearch)airports = controller.portList();
    else airports = controller.getFPort(id, name, location, useOr);
    controller.exit();


  %>
</head>
<script>
  function search(){
    var id=document.getElementById("id_F").value;
    var name=document.getElementById("name_F").value;
    var location=document.getElementById("location_F").value;
    var useor=!document.getElementById("useOr").checked;
    if(id=="")id="null";
    if(name=="")name="null";
    if(location=="")location="null";
    window.location=window.location.pathname+"?id="+id+"&name="+name+"&location="+location+"&useor="+useor+"#id_F";
  }
</script>
<body>
<br>
<%@ include file="Header.jspf" %>
<br>
<div class="content">
  <table align="center">
    <th>Id</th><th>Name</th><th>Location</th>
    <tr>
      <td>
        <input id="id_F" type="text" value="<%=id.equalsIgnoreCase("null")?"":id%>">
      </td>
      <td>
        <input id="name_F" type="text" value="<%=name.equalsIgnoreCase("null")?"":name%>">
      </td>
      <td>
        <input id="location_F" type="text" value="<%=location.equalsIgnoreCase("null")?"":location%>">
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
  <h1 align="center">Airport</h1>
  <p align="center">
  <table align="center">
    <th>Id</th><th>Name</th><th>Location</th>
    <%
      for(Airport airport:airports)
      {
    %>
    <tr>
      <td>
        <%=airport.getId()%>
      </td>
      <td>
        <%=airport.getName()%>
      </td>
      <td>
        <%=airport.getLocation()%>
      </td>
    </tr>
    <%
      }
    %>
  </table>
</p>
  <p align="Right"><a href="Airport.jsp">Полная версия</a></p>
</div>
</body>
</html>

