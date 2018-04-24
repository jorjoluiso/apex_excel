/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import com.db.DataBaseConnection;
import com.propiedades.MotorConfiguracion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jorgequiguango
 */
public class DecryptString {

    public String decrypt(String cadena) {
        try {
            MotorConfiguracion configMotor = new MotorConfiguracion();
            DataBaseConnection oc = new DataBaseConnection();
            Connection conn;
            String clave = "";

            conn = oc.getConnection(configMotor.getHost(), configMotor.getPuerto(), configMotor.getServicio(), configMotor.getUsuario(), configMotor.getClave());
            PreparedStatement preparedStatement = conn.
                    prepareStatement("select toolkit.decrypt(?) as clave from dual");
            preparedStatement.setString(1, cadena);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                clave = resultSet.getString("clave");
                break;
            }
            conn.close();
            return clave;
        } catch (SQLException ex) {
            Logger.getLogger(DecryptString.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public static void main(String[] args) {
        DecryptString d = new DecryptString();
        System.out.println("Decrypt Clave");
        System.out.println(d.decrypt("97F876A877A0DDC721A4922D30462714"));
    }
}
