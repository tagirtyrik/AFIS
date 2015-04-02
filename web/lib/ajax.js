
var Ajax = {
	getXmlHttp : function (){
	 var xmlhttp;
	  try  { xmlhttp = new ActiveXObject("Msxml2.XMLHTTP"); }
	  catch (e) {
	    try   { xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); }
	    catch (E)  { xmlhttp = false; }
	  }
	  if (!xmlhttp && typeof XMLHttpRequest!='undefined')xmlhttp = new XMLHttpRequest();
	  return xmlhttp;
	}, 

   run : function(url,e)  {
   var xmlhttp=Ajax.getXmlHttp();
   //xmlhttp.overrideMimeType('text/html;charset=cp1251');
   if(typeof(e)=='undefined') {
     xmlhttp.open('GET',url,false);
     xmlhttp.send(null);
   }
   else {
     xmlhttp.open('POST',url,false);
     xmlhttp.send(e);
   }
   if(xmlhttp.status!= 200)return(NaN);
   else return(xmlhttp.responseText);
  },


  runEval : function(url,e) {
	var s=String(Ajax.run(url,e));
	if(s.length==0)return eval(s);
	return JSON.parse(s);
  }

}

function getXmlHttp(){
    var xmlhttp;
    try {
        xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
    } catch (e) {
        try {
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        } catch (E) {
            xmlhttp = false;
        }
    }
    if (!xmlhttp && typeof XMLHttpRequest!='undefined') {
        xmlhttp = new XMLHttpRequest();
    }
    return xmlhttp;
}
