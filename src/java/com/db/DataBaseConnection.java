/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.db;

/**
 *
 * @author jorgequiguango
 */
import com.propiedades.General;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jorjoluiso
 */
public class DataBaseConnection {

    public Connection getConnection(String host, String puerto, String servicio, String usuario, String clave) {
        General general = new General();

        if (general.BaseDatos.equals("oracle")) {
            return oracleConnection(host, puerto, servicio, usuario, clave);
        } else if (general.BaseDatos.equals("sqlserver")) {
            return sqlserverConnection(host, puerto, servicio, usuario, clave);
        }
        return null;
    }

    private Connection oracleConnection(String host, String puerto, String servicio, String usuario, String clave) {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Driver Oracle no encontrado");
        }
        Connection conn = null;
        try {
            //conn = DriverManager.getConnection("jdbc:oracle:thin:@//127.0.0.1:1521/xe", usuario, clave);
            conn = DriverManager.getConnection("jdbc:oracle:thin:@//" + host + ":" + puerto + "/" + servicio, usuario, clave);
            System.out.println("Conectado a la base de datos Oracle");
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("La conexión a la base Oracle de datos fallo");
        }
        return conn;

    }

    private Connection sqlserverConnection(String host, String puerto, String servicio, String usuario, String clave) {
        try {
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Driver SqlServer no encontrado");
        }
        Connection conn = null;
        try {            
            conn = DriverManager.getConnection("jdbc:sqlserver://" + host + ":" + puerto + ";databaseName=" + servicio, usuario, clave);
            System.out.println("Conectado a la base de datos SqlServer");
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("La conexión a la base SqlServer de datos fallo");
        }
        return conn;

    }

    public static void main(String[] args) throws SQLException{
        DataBaseConnection dbc = new DataBaseConnection();
        //Connection conn = dbc.sqlserverConnection("192.168.1.121", "1433", "bdquality", "quality", "Horiz0ns__");
        Connection conn = dbc.getConnection("127.0.0.1", "1521", "xe", "dismemayor", "d");
        conn.close();
    }
}
