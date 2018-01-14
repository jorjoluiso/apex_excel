/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reportes.superjasper;

import com.db.DataBaseConnection;
import com.propiedades.General;
import com.propiedades.MotorConfiguracion;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author jorgequiguango
 */
public class SuperTotales {

    private String usuario;

    public SuperTotales(String usuario) {
        this.usuario = usuario;
    }

    public String reporteTotal() {
        String txtFileNameVariable = this.usuario + "_jasper.pdf";

        MotorConfiguracion configMotor = new MotorConfiguracion();
        DataBaseConnection oc = new DataBaseConnection();
        Connection conn;
        conn = oc.getConnection(configMotor.getHost(), configMotor.getPuerto(), configMotor.getServicio(), configMotor.getUsuario(), configMotor.getClave());
        
        General general = new General();
        
        String archivo = general.ReportesJasper + File.separatorChar + "RptMorosidadClientes.jasper";
        System.out.println("Archivo cargado desde :" + archivo);
        JasperReport masterReport = null;

        try {
            masterReport = (JasperReport) JRLoader.loadObjectFromFile(archivo);
        } catch (JRException ex) {
            System.out.println("Error al cargar el archivo de reporte" + ex.getMessage());
        }

        Map parametro = new HashMap();
        parametro.put("P_USUARIO", this.usuario.toUpperCase());       

        try {
            JasperPrint jp = JasperFillManager.fillReport(masterReport, parametro, conn);
            String pathGenerado
                    = System.getProperty("java.io.tmpdir")
                    + File.separatorChar
                    + txtFileNameVariable;
            System.out.println("Path: " + pathGenerado);
            
            OutputStream output = new FileOutputStream(new File(pathGenerado));

            JasperExportManager.exportReportToPdfStream(jp, output);

            output.close();
            return txtFileNameVariable;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(SuperTotales.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | JRException ex) {
            Logger.getLogger(SuperTotales.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public static void main(String[] args) {
        SuperTotales superTotales = new SuperTotales("dismemayor");
        superTotales.reporteTotal();

    }

}
