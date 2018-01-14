<%@page import="java.io.FileOutputStream"%>
<%@page import="net.sf.jasperreports.engine.JasperExportManager"%>
<%@page import="net.sf.jasperreports.view.JasperViewer"%>
<%@page import="net.sf.jasperreports.engine.JasperFillManager"%>
<%@page import="net.sf.jasperreports.engine.JasperPrint"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="net.sf.jasperreports.engine.util.JRLoader"%>
<%@page import="net.sf.jasperreports.engine.JasperReport"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.util.logging.Level"%>
<%@page import="java.util.logging.Logger"%>
<%@page import="net.sf.jasperreports.engine.JRException"%>
<%@page import="com.reportes.morosidad.Reportes"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.FileNotFoundException"%>
<%@page import="java.io.BufferedInputStream"%>
<%@page import="java.io.File"%>
<%@page import="com.db.DataBaseConnection"%>
<%@page import="java.sql.Connection"%>


<%

    try {
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
    } catch (SQLException ex) {
        Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        System.out.println("Driver Oracle no encontrado");
    }
    Connection conn = null;
    try {
        conn = DriverManager.getConnection("jdbc:oracle:thin:@//127.0.0.1:1521/xe", "dismemayor", "d");
        System.out.println("Conectado a la base de datos Oracle");
    } catch (SQLException ex) {
        Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        System.out.println("La conexión a la base Oracle de datos fallo");
    }

    String archivo = "/data/git/apex_excel_disme/src/java/com/reportes/recursos/RptMorosidadClientes.jasper";
    System.out.println("Archivo cargado desde :" + archivo);
    JasperReport masterReport = null;

    try {
        masterReport = (JasperReport) JRLoader.loadObjectFromFile(archivo);
    } catch (JRException ex) {
        System.out.println("Error al cargar el archivo de reporte" + ex);
    }

    Map parametro = new HashMap();
    parametro.put("UNANIO", "2017");
    parametro.put("UNMESINICIO", "1");

    try {
        JasperPrint jp = JasperFillManager.fillReport(masterReport, parametro, conn);
        OutputStream output = new FileOutputStream(new File("/tmp/jasper.pdf"));
        JasperExportManager.exportReportToPdfStream(jp, output);
        output.close();

    } catch (JRException ex) {
        System.out.println("Error en el reporte" + ex.getMessage());
    }
    System.out.println("Fin de aplicación");

%>