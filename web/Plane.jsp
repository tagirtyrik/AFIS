<%-- 
    Document   : Plane
    Created on : 24.02.2015, 14:45:13
    Author     : Ксю
--%>
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
    <script type="text/javascript" src="/lib/ajax.js" charset="utf-8"></script>
    <script type="text/javascript" src="/lib/fileSaver.js" charset="utf-8"></script><!--библиотека для созранения файлов на клиенте-->
    <script type="text/javascript" src="/lib/XmlReader.js" charset="utf-8"></script><!--библиотека для парсинга XML-->
    <script type="text/javascript" src="/lib/showModalDialog.js" charset="UTF-8"></script><!--фикс модального диалога-->
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
        saveAs(blob, "Planes.xml");
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
            var boeings = data.getElementsByTagName("model.aircraft.Boeing747SP");
            var boeing = null;
            if(boeings)
                for(var i=0; i< boeings.length; i++)
                {
                    boeing = boeings[i];
                    var id = boeing.getElementsByTagName("id")[0].innerHTML;
                    var name = boeing.getElementsByTagName("planeName")[0].innerHTML;
                    var number = boeing.getElementsByTagName("planeNumber")[0].innerHTML;
                    var fuel = boeing.getElementsByTagName("fuelConsumption")[0].innerHTML;
                    var passangers = boeing.getElementsByTagName("passengerSeatsCount")[0].innerHTML;
                    var xmlhttp = getXmlHttp();
                    xmlhttp.open('GET', "View.jsp?cmd=recoveryplane&0="+id+"&1="+number+"&2="+name+"&3="+passangers+"&4="+fuel, false);
                    xmlhttp.send(null);
                }
            setTimeout("window.location.reload()",5)
        };
        reader.readAsText(file);
    }


    function addPlane(number){
        var xmlhttp = getXmlHttp();
        xmlhttp.open('GET', "View.jsp?cmd=addplane&0="+number, false);
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
            if (document.getElementById("checkResponse")!=undefined && document.getElementById("checkResponse").checked)
                checkResponce(xmlhttp.responseText);
        }else{
            var xml=getDomXml(xmlhttp.responseText);
            if (confirm("Произошла ошибка:\n"+xml.getElementsByTagName("info")[0].innerHTML+"\n\nПосмотреть полный отклик сервера?")) {
                var ret =  window.showModalDialog("ModalDialog.html", xmlhttp.responseText, "dialogWidth:90%");
            }
            this.location.reload();
        }
    }
    function delPlane(id){
        var xmlhttp = getXmlHttp();
        xmlhttp.open('GET', "View.jsp?cmd=delplane&0="+id, false);
        xmlhttp.send(null);
        if(xmlhttp.status == 200) {
            //alert(xmlhttp.responseText);
            if (document.getElementById("checkResponse")!=undefined && document.getElementById("checkResponse").checked)
                checkResponce(xmlhttp.responseText);
            this.location.reload();
        }else {
            var xml=getDomXml(xmlhttp.responseText);
            if (confirm("Произошла ошибка:\n"+xml.getElementsByTagName("info")[0].innerHTML+"\n\nПосмотреть полный отклик сервера?")) {
                var ret =  window.showModalDialog("ModalDialog.html", xmlhttp.responseText, "dialogWidth:90%");
            }
            this.location.reload();
        }

    }
    function onLoad(){
        document.getElementById('get-File').addEventListener('change', loadData, false);//в этот момент где-то в HTML: <input type='file' id='get-File'/>
    }
</script>
<body onload="onLoad()">
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
    <p align="right"><input type="button" value="Сохранить как Xml" onclick="saveData()">
        <input type="file" id = "get-File"></p>
    <p align="Right"><a href="simplePlane.jsp">Версия для печати</a></p>
</div>
</body>
</html>
