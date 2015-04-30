
<%@page import="model.Plane"%>
<%@page  import="java.util.ArrayList" %>
<%@ page import="controller.Controller" %>
<%@ page import="model.Model" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Plane Page</title>
  <script type="text/javascript" src="/lib/ajax.js"></script>
  <link rel="stylesheet" type="text/css" href="simple-style.css">
  <%
    String id=request.getParameter("id");
    String name=request.getParameter("name");
    String number=request.getParameter("number");
    String fuel=request.getParameter("fuel");
    String passengers=request.getParameter("passengers");
    boolean useOr=(request.getParameter("useor")==null)?false:request.getParameter("useor").equalsIgnoreCase("true");
    boolean useFSearch=false;

    if(id==null)id="null";
    else useFSearch=true;
    if(name==null)name="null";
    else useFSearch=true;
    if(number==null)number="null";
    else useFSearch=true;
    if(fuel==null)fuel="null";
    else useFSearch=true;
    if(passengers==null)passengers="null";
    else useFSearch=true;

    ArrayList<Plane> list1;
    Controller controller=new Controller(new Model(true));

    if(!useFSearch)list1 = controller.planeList();
    else list1=controller.getFPlane(id,name,number,passengers,fuel,useOr);
    controller.exit();


  %>
</head>
<body>
<br>
<%@ include file="Header.jspf" %>
<br>
<%@ include file="search/Plane.jspf" %>
<br>
<div class="content">
  <h1 align="center">Plane</h1>
  <table align="center">
    <th>Id</th><th>Title</th><th>Name</th><th>FuelConsumption</th><th>PassengerSeatsCount</th>
    <%
      for(Plane p:list1)
      {
    %>
    <tr>
      <td>
        <%=p.getId()%>
      </td>

      <td>
        <%=p.getName()%>
      </td>
      <td>
        <%=p.getNumber()%>
      </td>
      <td>
        <%=p.getFuelConsumption()%>
      </td>
      <td>
        <%=p.getPassengerSeatsCount()%>
      </td>
    </tr>
    <%
      }
    %>
  </table>
  <p align="Right"><a href="Plane.jsp">Полная версия</a></p>
</div>
</body>
</html>
