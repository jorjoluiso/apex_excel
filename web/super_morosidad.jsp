<%@page import="java.sql.Connection"%>
<%@page import="com.propiedades.MotorConfiguracion"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="net.sf.jasperreports.engine.JasperExportManager"%>
<%@page import="net.sf.jasperreports.view.JasperViewer"%>
<%@page import="net.sf.jasperreports.engine.JasperFillManager"%>
<%@page import="net.sf.jasperreports.engine.JasperPrint"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="net.sf.jasperreports.engine.util.JRLoader"%>
<%@page import="net.sf.jasperreports.engine.JasperReport"%>
<%@page import="net.sf.jasperreports.engine.JRException"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.FileNotFoundException"%>
<%@page import="java.io.BufferedInputStream"%>
<%@page import="java.io.File"%>
<%@page import="com.db.DataBaseConnection"%>


<%
    if (request.getParameter("usuario") == null || request.getParameter("usuario") == "") {
        out.println("Es necesario el parámetro usuario");
        return;
    }

    MotorConfiguracion configMotor = new MotorConfiguracion();
    DataBaseConnection oc = new DataBaseConnection();
    Connection conn;
    conn = oc.getConnection(configMotor.getHost(), configMotor.getPuerto(), configMotor.getServicio(), configMotor.getUsuario(), configMotor.getClave());

    String archivo = "/data/git/apex_excel/src/java/com/reportes/recursos/RptMorosidadClientes.jasper";
    System.out.println("Archivo cargado desde :" + archivo);
    JasperReport masterReport = null;

    try {
        masterReport = (JasperReport) JRLoader.loadObjectFromFile(archivo);
    } catch (JRException ex) {
        out.println("Error al cargar el archivo de reporte" + ex.getMessage());
    }

    Map parametro = new HashMap();
    parametro.put("UNANIO", "2017");
    parametro.put("UNMESINICIO", "1");

    try {
        JasperPrint jp = JasperFillManager.fillReport(masterReport, parametro, conn);
        OutputStream output = new FileOutputStream(
                new File(
                        System.getProperty("java.io.tmpdir")
                        + File.separatorChar
                        + "jasper.pdf"));
        JasperExportManager.exportReportToPdfStream(jp, output);
        output.close();

    } catch (JRException ex) {
        out.println("Error en el reporte" + ex.getMessage());
    }

    try {
        String txtFileNameVariable = "jasper.pdf";
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