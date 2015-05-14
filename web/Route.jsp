<%--
  Created by IntelliJ IDEA.
  User: Ксю
  Date: 07.04.2015
  Time: 19:20
  To change this template use File | Settings | File Templates.
--%>
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
  <script type="text/javascript" src="/lib/ajax.js" charset="utf-8"></script>
  <script type="text/javascript" src="/lib/fileSaver.js" charset="utf-8"></script><!--библиотека для созранения файлов на клиенте-->
  <script type="text/javascript" src="/lib/XmlReader.js" charset="utf-8"></script><!--библиотека для парсинга XML-->
  <script type="text/javascript" src="/lib/showModalDialog.js" charset="UTF-8"></script><!--фикс модального диалога-->
  <link rel="stylesheet" type="text/css" href="style.css">
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

  function saveData()
  {
    var xmlhttp = getXmlHttp();
    xmlhttp.open('GET', "View.jsp?cmd=saveXml&0="+"routes", false);
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
    saveAs(blob, "Routes.xml");
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
      var routes = data.getElementsByTagName("model.route.RegularRoute");
      var route = null;
      if(routes)
        for(var i=0; i< routes.length; i++)
        {
          route = routes[i];
          var id = route.getElementsByTagName("id")[0].innerHTML;
          var takeOffPort = route.getElementsByTagName("takeOffPortId")[0].innerHTML;
          var landingPort = route.getElementsByTagName("landingPortId")[0].innerHTML;
          var distance = route.getElementsByTagName("distance")[0].innerHTML;
          var xmlhttp = getXmlHttp();
          xmlhttp.open('GET', "View.jsp?cmd=recoveryroute&0="+id+"&1="+takeOffPort+"&2="+landingPort+"&3="+distance, false);
          xmlhttp.send(null);
        }
      setTimeout("window.location.reload()",5)
    };
    reader.readAsText(file);
  }
  function onLoad(){
    document.getElementById('get-File').addEventListener('change', loadData, false);//в этот момент где-то в HTML: <input type='file' id='get-File'/>
  }


  function addRoute(){
    var takeOfPortId = parseInt(document.getElementById("selectTakeOfPort").value);
    var landingPortId = parseInt(document.getElementById("selectLandingPort").value);
    var distance = parseFloat(document.getElementById("distance").value);
    var url="?cmd=addroute&0="+takeOfPortId+"&1="+landingPortId+"&2="+distance;
    var xmlhttp = getXmlHttp();
    xmlhttp.open('GET', "View.jsp"+url, false);
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
  function setRoute(id){
    var takeOfPortId = parseInt(document.getElementById("selectTakeOfPort_"+id).value);
    var landingPortId = parseInt(document.getElementById("selectLandingPort_"+id).value);
    var distance =parseFloat(document.getElementById("distance_"+id).value);
    var xmlhttp = getXmlHttp();
    var url="?cmd=setroute&0="+id+"&1="+takeOfPortId+"&2="+landingPortId+"&3="+distance;
    var xmlhttp = getXmlHttp();
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
  function delRoute(id){
    var xmlhttp = getXmlHttp();
    xmlhttp.open('GET', "View.jsp?cmd=delroute&0="+id, false);
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
        <input type="text" value="<%=route.getId()%>" readonly >
      </td>
      <td>
        <select id="selectTakeOfPort_<%=route.getId()%>" onchange="setRoute(<%=route.getId()%>)">
          <%for(Airport port: airports){%>
          <option value="<%=port.getId()%>" <%=route.getTakeOffPort()==port.getId()?"selected":""%> ><%=new String(port.getId()+":"+port.getName())%></option>
          <%}%>
        </select>
      </td>
      <td>
        <select id="selectLandingPort_<%=route.getId()%>" onchange="setRoute(<%=route.getId()%>)">
          <%for(Airport port: airports){%>
          <option value="<%=port.getId()%>" <%=route.getLandingPort()==port.getId()?"selected":""%> ><%=new String(port.getId()+":"+port.getName())%></option>
          <%}%>
        </select>
      </td>
      <td>
        <input id="distance_<%=route.getId()%>" type="text" value="<%=route.getDistance()%>" onchange="setRoute(<%=route.getId()%>)">
      </td>
      <td>
        <input type="button" value="X" onclick="delRoute(<%=route.getId()%>)">
      </td>
    </tr>
    <%
      }
    %>
  </table>
  <br>
  <table  align="center">
    <tr>
      <td>
        <input type="text" value="новый" readonly size="3">
      </td>
      <td>
        <select id="selectTakeOfPort">
          <%for(Airport a: airports){%>
          <option value="<%=a.getId()%>"><%=new String(a.getId()+":"+a.getName())%></option>
          <%}%>
        </select>
      </td>
      <td>
        <select id="selectLandingPort">
            <%for(Airport a: airports){%>
          <option value="<%=a.getId()%>"><%=new String(a.getId()+":"+a.getName())%></option>
            <%}%>
         </select>
      </td>
      <td>
        <input id="distance" type="text" value="0.0" >
      </td>
      <td>
        <input type="button" value="Добавить маршрут" onclick="addRoute()">
      </td>
  </tr>
  </table>
  <p align="right"><input type="button" value="Сохранить как Xml" onclick="saveData()">
    <input type="file" id = "get-File"></p>
  <p align="Right"><a href="simpleRoute.jsp">Версия для печати</a></p>
</div>
</body>
<%controller.exit();%>
</html>
