/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reportes.morosidad;

import com.db.DataBaseConnection;
import com.propiedades.MotorConfiguracion;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author jorgequiguango
 */
public class Reportes {
    
    String usuario;
    
    public Reportes(String usuario) {
        this.usuario = usuario;
    }
    
    public void getTotales() {
        try {
            MotorConfiguracion configMotor = new MotorConfiguracion();
            DataBaseConnection oc = new DataBaseConnection();
            Connection conn;
            conn = oc.getConnection(configMotor.getHost(), configMotor.getPuerto(), configMotor.getServicio(), configMotor.getUsuario(), configMotor.getClave());
            
            Statement stmt = null;
            ResultSet rs = null;
            stmt = conn.createStatement();
            
            rs = stmt.executeQuery("SELECT "
                    + "orden,"
                    + "descripcion,"
                    + "expresado,"
                    + "mora_hasta_30,"
                    + "mora_31_60,"
                    + "mora_mas_61,"
                    + "no_vencido,"
                    + "round(nvl(valor,0),2) as valor,"
                    + "abono,saldo "
                    + "FROM v_super_totales "
                    + "order by orden asc");
            
            excel(rs);
            
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void excel(ResultSet rs) {
        FileOutputStream fileOut = null;
        try {
            HSSFWorkbook wb = new HSSFWorkbook();
            int r = 0;
            HSSFSheet sheet = wb.createSheet("Totales");
            
            HSSFRow rowUsuario = sheet.createRow(r);
            HSSFCell cellUsuario = rowUsuario.createCell(0, HSSFCell.CELL_TYPE_STRING);
            cellUsuario.setCellValue(usuario);
            r++;
            
            while (rs.next()) {
                HSSFRow row = sheet.createRow(r);

                //iterating c number of columns
                for (int c = 0; c < rs.getMetaData().getColumnCount(); c++) {
                    HSSFCell cell = null;
                    if (c + 1 == 4 || c + 1 == 5 || c + 1 == 6 || c + 1 == 7 || c + 1 == 8 || c + 1 == 9 || c + 1 == 10) {
                        cell = row.createCell(c, HSSFCell.CELL_TYPE_NUMERIC);
                    } else if (c + 1 == 2 || c + 1 == 3) {
                        cell = row.createCell(c, HSSFCell.CELL_TYPE_STRING);
                    }
                    if (c + 1 == 4 || c + 1 == 5 || c + 1 == 6 || c + 1 == 7 || c + 1 == 8 || c + 1 == 9 || c + 1 == 10) {
                        cell.setCellValue(rs.getFloat(c + 1));
                    } else if (c + 1 == 2 || c + 1 == 3) {
                        cell.setCellValue(rs.getString(c + 1));
                    }
                }
                r++;
            }

            // Write the output to a file
            System.out.println(System.getProperty("java.io.tmpdir"));
            fileOut = new FileOutputStream(System.getProperty("java.io.tmpdir") + File.separatorChar + "Totales.xls");
            wb.write(fileOut);
            fileOut.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | SQLException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fileOut.close();
            } catch (IOException ex) {
                Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void main(String[] args) throws SQLException {
        Reportes r = new Reportes("dismemayor");
        r.getTotales();
    }
}
