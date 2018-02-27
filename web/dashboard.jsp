<%-- 
    Document   : dashboard
    Created on : Apr 18, 2014, 12:27:10 PM
    Author     : Dalal
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
 <%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
 

 <core:if test="${empty sessionScope.name}">  <core:redirect url="index.html"/> </core:if>
 <core:if test="${not empty sessionScope.name}"><p> 

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Welcome
   ${sessionScope.name}</h1>
   <a href="logout" >logout</a>
    </body>
</html>
</p></core:if>