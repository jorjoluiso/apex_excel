<%-- 
    Document   : index
    Created on : Sep 26, 2017, 12:00:15 PM
    Author     : jorgequiguango
--%>
<%
    String usuario = "";
    String clave = "";
    if (request.getParameter("usuario") == null || request.getParameter("usuario") == "") {
        usuario = "usuario";
    } else {
        usuario = request.getParameter("usuario");
    }
    if (request.getParameter("clave") == null || request.getParameter("clave") == "") {
        clave = "clave";
    } else {
        clave = request.getParameter("clave");
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reportes Apex</title>
    </head>
    <body>
        <h2>Reportes Apex</h2>
        <form method="get" action="super_morosidad.jsp">
            <p>
                <input type="text" name="usuario" value="<%=usuario%>"/>
            <p>
                <input type="text" name="clave" value="<%=clave%>"/>
            <p>
                <input type="submit" value="Generar reporte morosidad"/>
            </p>
        </form>
    </body>
</html>
