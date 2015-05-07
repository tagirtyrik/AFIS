<%-- 
    Document   : Plane
    Created on : 24.02.2015, 14:45:13
    Author     : Ксю
--%>
<%@page import="model.Plane"%>
<%@page  import="java.util.ArrayList" %>
<%@page import="db.Sql" %>
<%@page import="db.DataAccessObject" %>
<%@ page import="java.io.*,java.util.*" %>
<%@ page import="model.aircraft.Boeing747SP" %>
<%@ page import="controller.Controller" %>
<%@ page import="model.Model" %>
<%@ page import="xml.OperationWithXml" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Plane Page</title>
    <script type="text/javascript" src="/lib/ajax.js"></script>
    <script type="text/javascript" src="/lib/fileSaver.js"></script><!--библиотека для созранения файлов на клиенте-->
    <link rel="stylesheet" type="text/css" href="style.css">
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


    
    function saveData()
    {
        var xmlhttp = getXmlHttp();
        xmlhttp.open('GET', "View.jsp?cmd=saveXml&0="+"planes", false);
        xmlhttp.send(null);
        if(xmlhttp.status == 200) {
            //alert(xmlhttp.responseText);
            this.location.reload();
        }else{
            alert("Произошла ошибка");
            this.location.reload();
        }
        var data=xmlhttp.response;
        var blob = new Blob([data], {type: "text/xml;charset=utf-8"});
        saveAs(blob, "Planes.xml");
    }

    document.getElementById('get-File').addEventListener('change', loadData, false);//в этот момент где-то в HTML: <input type='file' id='get-File'/>
    function loadData(e)//функция для загрузки файла с диска клиента
    {
    }


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
</script>
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
                <input type="text" value="<%=p.getId()%>" readonly >
            </td>

            <td>
                <input id="name_<%=p.getId()%>" type="text" value="<%=p.getName()%>" onchange="setPlane(<%=p.getId()%>)">
            </td>
            <td>
                 <input id="number_<%=p.getId()%>" type="text" value="<%=p.getNumber()%>" onchange="setPlane(<%=p.getId()%>)">
            </td>
            <td>
                <input id="fuel_<%=p.getId()%>" type="text" value="<%=p.getFuelConsumption()%>" onchange="setPlane(<%=p.getId()%>)">
            </td>
            <td>
                <input id="passengers_<%=p.getId()%>" type="text" value="<%=p.getPassengerSeatsCount()%>" onchange="setPlane(<%=p.getId()%>)">
            </td>
            <td>
                <input type="button" value="X" onclick="delPlane(<%=p.getId()%>)">
            </td>
        </tr>
        <%
            }
        %>
    </table>
      <p align="center"><input type="button" value="Добавить самолет" onclick="addPlane('')"></p>
    <p align="right"><input type="button" value="Сохранить как Xml" onclick="saveData()"></p>
    <p align="Right"><a href="simplePlane.jsp">Версия для печати</a></p>
    <input type="file" id = "get-File">
</div>
</body>
</html>
