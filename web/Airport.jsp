<%--
  Created by IntelliJ IDEA.
  User: Ксю
  Date: 07.04.2015
  Time: 17:22
  To change this template use File | Settings | File Templates.
--%>
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
  <script type="text/javascript" src="/lib/ajax.js" charset="utf-8"></script>
  <script type="text/javascript" src="/lib/fileSaver.js" charset="utf-8"></script><!--библиотека для созранения файлов на клиенте-->
  <script type="text/javascript" src="/lib/XmlReader.js" charset="utf-8"></script><!--библиотека для парсинга XML-->
  <script type="text/javascript" src="/lib/showModalDialog.js" charset="UTF-8"></script><!--фикс модального диалога-->
  <link rel="stylesheet" type="text/css" href="style.css">
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
  function saveData()
  {
    var xmlhttp = getXmlHttp();
    xmlhttp.open('GET', "View.jsp?cmd=saveXml&0="+"ports", false);
    xmlhttp.send(null);
    if(xmlhttp.status == 200) {
      //alert(xmlhttp.responseText);
      if (document.getElementById("checkResponse")!=undefined && document.getElementById("checkResponse").checked)
        checkResponce(xmlhttp.responseText);
      this.location.reload();
    }else{
      var xml=getDomXml(xmlhttp.responseText);
      if (confirm("Произошла ошибка:\n"+xml.getElementsByTagName("info")[0].innerHTML+"\n\nПосмотреть полный отклик сервера?")) {
        var ret =  window.showModalDialog("ModalDialog.html", xmlhttp.responseText, "dialogWidth:90%");
      }
      this.location.reload();
    }
    var data=xmlhttp.response;
    var xmlDoc=getDomXml(data);
    var blob = new Blob([xmlDoc.getElementsByTagName("info")[0].innerHTML], {type: "text/xml;charset=utf-8"});
    saveAs(blob, "Airports.xml");
  }

  function loadData(e)//функция для загрузки файла с диска клиента
  {
    var file = e.target.files[0];
    if (!file) {
      return;
    }
    var reader = new FileReader();
    reader.onload = function(e) {
      var contents = e.target.result;
      var xml=getDomXml(contents);
      var data =  xml.getElementsByTagName("Data")[0];
      var ports = data.getElementsByTagName("model.airport.InternationalAirport");
      var port = null;
      if(ports)
        for(var i=0; i< ports.length; i++)
        {
          port = ports[i];
          var id = port.getElementsByTagName("id")[0].innerHTML;
          var name = port.getElementsByTagName("airportName")[0].innerHTML;
          var location = port.getElementsByTagName("airportLocation")[0].innerHTML;
          var xmlhttp = getXmlHttp();
          xmlhttp.open('GET', "View.jsp?cmd=recoveryport&0="+id+"&1="+name+"&2="+location, false);
          xmlhttp.send(null);
        }
      setTimeout("window.location.reload()",5)
    };
    reader.readAsText(file);
  }

  function onLoad(){
    document.getElementById('get-File').addEventListener('change', loadData, false);//в этот момент где-то в HTML: <input type='file' id='get-File'/>
  }

  function addPort(name, location){
    var xmlhttp = getXmlHttp();
    xmlhttp.open('GET', "View.jsp?cmd=addport&0="+name+"&1="+location, false);
    xmlhttp.send(null);
    if(xmlhttp.status == 200) {
      if (document.getElementById("checkResponse")!=undefined && document.getElementById("checkResponse").checked)
        checkResponce(xmlhttp.responseText);
      this.location.reload();
    }else {
      var xml=getDomXml(xmlhttp.responseText);
      if (confirm("Произошла ошибка:\n"+xml.getElementsByTagName("info")[0].innerHTML+"\n\nПосмотреть полный отклик сервера?")) {
        var ret =  window.showModalDialog("ModalDialog.html", xmlhttp.responseText, "dialogWidth:90%");
      }
    }

  }
  function setPort(id){
    var name=document.getElementById("name_"+id).value;
    var location=(document.getElementById("location_"+id).value);
    var xmlhttp = getXmlHttp();
    var url="?cmd=setport&0="+id+"&1="+name+"&2="+location;
    xmlhttp.open('GET', "View.jsp"+url, false);
    xmlhttp.send(null);
    if(xmlhttp.status == 200) {
      if (document.getElementById("checkResponse")!=undefined && document.getElementById("checkResponse").checked)
        checkResponce(xmlhttp.responseText);
    }else {
      var xml=getDomXml(xmlhttp.responseText);
      if (confirm("Произошла ошибка:\n"+xml.getElementsByTagName("info")[0].innerHTML+"\n\nПосмотреть полный отклик сервера?")) {
        var ret =  window.showModalDialog("ModalDialog.html", xmlhttp.responseText, "dialogWidth:90%");
      }
    }
  }
  function delPort(id){
    var xmlhttp = getXmlHttp();
    xmlhttp.open('GET', "View.jsp?cmd=delport&0="+id, false);
    xmlhttp.send(null);
    if(xmlhttp.status == 200) {
      if (document.getElementById("checkResponse")!=undefined && document.getElementById("checkResponse").checked)
        checkResponce(xmlhttp.responseText);
      this.location.reload();
    }else {
      var xml=getDomXml(xmlhttp.responseText);
      if (confirm("Произошла ошибка:\n"+xml.getElementsByTagName("info")[0].innerHTML+"\n\nПосмотреть полный отклик сервера?")) {
        var ret =  window.showModalDialog("ModalDialog.html", xmlhttp.responseText, "dialogWidth:90%");
      }
    }

  }
</script>
<body onload="onLoad()">
<br>
<%@ include file="Header.jspf" %>
<br>
<%@ include file="search/Airport.jspf" %>
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
        <input type="text" value="<%=airport.getId()%>" readonly >
      </td>
      <td>
        <input id="name_<%=airport.getId()%>" type="text" value="<%=airport.getName()%>" onchange="setPort(<%=airport.getId()%>)">
      </td>
      <td>
        <input id="location_<%=airport.getId()%>" type="text" value="<%=airport.getLocation()%>" onchange="setPort(<%=airport.getId()%>)">
      </td>
      <td>
        <input type="button" value="X" onclick="delPort(<%=airport.getId()%>)">
      </td>
    </tr>
    <%
      }
    %>
  </table>
  <p align="center"><input type="button" value="Добавить аэропорт" onclick="addPort('', '')"></p>
  <p align="right"><input type="button" value="Сохранить как Xml" onclick="saveData()">
    <input type="file" id = "get-File"></p>
  <p align="Right"><a href="simplePort.jsp">Версия для печати</a></p>
</div>
</body>
</html>

