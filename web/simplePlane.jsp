
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
<script>
  function addPlane(number){
    var xmlhttp = getXmlHttp();
    xmlhttp.open('GET', "View.jsp?cmd=addplane&0="+number, false);
    xmlhttp.send(null);
    if(xmlhttp.status == 200) {
      //alert(xmlhttp.responseText);
      this.location.reload();
    }else{
      alert("Произошла ошибка");
      this.location.reload();
    }

  }
  function setPlane(id){
    var number=document.getElementById("number_"+id).value;
    var name=document.getElementById("name_"+id).value;
    var passangers=parseInt(document.getElementById("passengers_"+id).value);
    var fuel=parseFloat(document.getElementById("fuel_"+id).value);
    var xmlhttp = getXmlHttp();
    var url="?cmd=setplane&0="+id+"&1="+number+"&2="+name+"&3="+passangers+"&4="+fuel;
    xmlhttp.open('GET', "View.jsp"+url, false);
    xmlhttp.send(null);
    if(xmlhttp.status == 200) {
      // alert(xmlhttp.responseText);
    }else{
      alert("Произошла ошибка");
      this.location.reload();
    }
  }
  function delPlane(id){
    var xmlhttp = getXmlHttp();
    xmlhttp.open('GET', "View.jsp?cmd=delplane&0="+id, false);
    xmlhttp.send(null);
    if(xmlhttp.status == 200) {
      //alert(xmlhttp.responseText);
      this.location.reload();
    }else {
      alert("Произошла ошибка");
      this.location.reload();
    }

  }
  function search(){
    var id=document.getElementById("id_F").value;
    var name=document.getElementById("name_F").value;
    var number=document.getElementById("number_F").value;
    var passengers=document.getElementById("passengers_F").value;
    var fuel=document.getElementById("fuel_F").value;
    var useor=!document.getElementById("useOr").checked;
    if(id=="")id="null";
    if(name=="")name="null";
    if(number=="")number="null";
    if(passengers=="")passengers="null";
    if(fuel=="")fuel="null";
    window.location=window.location.pathname+"?id="+id+"&name="+name+"&number="+number+"&passengers="+passengers+"&fuel="+fuel+"&useor="+useor+"#id_F";

  }
</script>
<body>
<br>
<%@ include file="Header.jspf" %>
<br>
<div class="content">
  <table align="center">
    <th>Id</th><th>Title</th><th>Name</th><th>FuelConsumption</th><th>PassengerSeatsCount</th>
    <tr>
      <td>
        <input id="id_F" type="text" value="<%=id.equalsIgnoreCase("null")?"":id%>">
      </td>
      <td>
        <input id="name_F" type="text" value="<%=name.equalsIgnoreCase("null")?"":name%>">
      </td>
      <td>
        <input id="number_F" type="text" value="<%=number.equalsIgnoreCase("null")?"":number%>">
      </td>
      <td>
        <input id="fuel_F" type="text" value="<%=fuel.equalsIgnoreCase("null")?"":fuel%>">
      </td>
      <td>
        <input id="passengers_F" type="text" value="<%=passengers.equalsIgnoreCase("null")?"":passengers%>">
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
        <%=p.getNumber()%>"
      </td>
      <td>
        <%=p.getFuelConsumption()%>"
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
