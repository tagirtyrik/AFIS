<%--
  Created by IntelliJ IDEA.
  User: GeneraL
  Date: 14.05.2015
  Time: 10:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"  isErrorPage="true" import="java.io.*" language="java" %>
<link rel="stylesheet" type="text/css" href="style.css">
<html>
<head>
    <title>HTTP STATUS 500</title>
</head>
<body>
  <div class="content">
    <p align="center" ><h1>HTTP STATUS 500</h1></p>
    <div class="plain">
      <info><%=exception.getCause().getMessage()%></info>
    </div>
    <details>
      <summary>Exception:</summary>
      <div class="plain">
        <%=exception.toString()%>
      </div>
    </details>
    <details>
      <summary>Message:</summary>
      <div class="plain">
        <%=exception.getMessage()%>
      </div>
    </details>
    <details>
      <summary>Cause:</summary>
      <div class="plain">
        <%=exception.getCause().toString()%>
        <br>Message:
        <br><%=exception.getCause().getMessage()%>
      </div>
    </details>
    <details>
      <summary>StackTrace:</summary>
      <div class="plain">
        <%
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);
        out.println(stringWriter);
        printWriter.close();
        stringWriter.close();
        %>
      </div>
    </details>
  </div>
</body>
</html>
