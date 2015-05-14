/**
 * Created by GeneraL on 14.05.2015.
 */

function getDomXml(XmlString){
    var xml;
    if (window.DOMParser)
    {
        parser=new DOMParser();
        xml=parser.parseFromString(XmlString,"text/xml");
    }
    else // Internet Explorer
    {
        xml=new ActiveXObject("Microsoft.XMLDOM");
        xml.async=false;
        xml.loadXML(XmlString);
    }
    return xml;
}
function checkResponce(XmlResponse){//выводит отклик сервера, если есть
    var xml=getDomXml(XmlResponse);
    if(xml.getElementsByTagName("info")!=undefined && xml.getElementsByTagName("info")[0]!=undefined &&
        xml.getElementsByTagName("info")[0].innerHTML!=undefined && xml.getElementsByTagName("info")[0].innerHTML!=""){
            alert (xml.getElementsByTagName("info")[0].innerHTML);
    }

}