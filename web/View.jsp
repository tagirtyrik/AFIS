<%@page
        import="java.util.ArrayList"
        import="controller.Controller"
        import="model.Model"
        import="java.sql.SQLException"
        import="java.text.ParseException"
        import="view.LocalView"
        contentType="text/xml;charset=UTF-8" language="java"
        import="view.XmlWebView"
%>
<%@ page import="model.airport.InternationalAirport" %>
<response>
  <%XmlWebView view=new XmlWebView();
    String cmd=request.getParameter("cmd");
    Controller controller=new Controller(new Model(),view);
    int argNum=0;
    ArrayList<String> args=new ArrayList<>();

    while(request.getParameter(Integer.toString(argNum))!=null){
      args.add(request.getParameter(Integer.toString(argNum)));
      argNum++;
    }
    String[] arg=new String[args.size()];
    for(int i=0;i<args.size();i++){
      arg[i]=args.get(i);
    }

    controller.parse(request.getParameter("cmd"),arg);%>
  <cmd><%=cmd%></cmd>
  <args>
  <%  for(int i=0;i<args.size();i++){%>
      <arg>
        <key><%=i%></key>
        <value><%=args.get(i)%></value>
      </arg>
  <%}%>
  </args>
    <%=view.getResponse()%>
</response>

