<%@page import="java.io.IOException"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.FileNotFoundException"%>
<%@page import="java.io.File"%>
<%@page import="java.io.BufferedInputStream"%>
<%@page import="com.reportes.superjasper.SuperTotales"%>
<%
    
    /*
    Acceso:
    javascript:var x=window.open('localhost:9001/apex_excel_disme/super_morosidad.jsp?usuario=&APP_USER.','_blank');
    http://localhost:9001/apex_excel_disme/super_morosidad.jsp?usuario=dismemayor
    */
    if (request.getParameter("usuario") == null || request.getParameter("usuario") == "") {
        out.println("Es necesario el parámetro usuario");
        return;
    }

    String usuario = request.getParameter("usuario");
    String txtFileNameVariable;

    SuperTotales supertotales = new SuperTotales(usuario);
    txtFileNameVariable = supertotales.reporteTotal();

    try {
        String locationVariable = System.getProperty("java.io.tmpdir") + File.separatorChar;
        String PathVariable = "";

        PathVariable = locationVariable + txtFileNameVariable;
        BufferedInputStream bufferedInputStream = null;
        try {
            bufferedInputStream = new BufferedInputStream(new java.io.FileInputStream(PathVariable));
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
        File f = new File(locationVariable, txtFileNameVariable);
        String fileType = txtFileNameVariable.substring(txtFileNameVariable.indexOf(".") + 1, txtFileNameVariable.length());
        if (fileType.trim().equalsIgnoreCase("txt")) {
            response.setContentType("text/plain");
        } else if (fileType.trim().equalsIgnoreCase("doc")) {
            response.setContentType("application/msword");
        } else if (fileType.trim().equalsIgnoreCase("xls")) {
            response.setContentType("application/vnd.ms-excel");
        } else if (fileType.trim().equalsIgnoreCase("pdf")) {
            response.setContentType("application/pdf");
        } else {
            response.setContentType("application/octet-stream");
        }
        String original_filename = txtFileNameVariable;
        response.setHeader("Content-Disposition", "attachment; filename=\"" + original_filename + "\"");
        try {
            int anInt = 0;
            OutputStream output = response.getOutputStream();
            while ((anInt = bufferedInputStream.read()) != -1) {
                output.write(anInt);
            }
            output.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    } catch (Exception e) {
        out.println("This is the Error " + e.getMessage());
    }

%>