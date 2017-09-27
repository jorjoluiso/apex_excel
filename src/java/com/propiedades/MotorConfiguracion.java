/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.propiedades;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 *
 * @author jorjoluiso
 */
public class MotorConfiguracion {

    private String Host;
    private String Puerto;
    private String Servicio;
    private String Usuario;
    private String Clave;

    public MotorConfiguracion() {
        try {
            General general = new General();

            if (general.BaseDatos.equals("oracle")) {

                PropertiesConfiguration configOracle = new PropertiesConfiguration("./quijotelu/Oracle.properties");
                if (configOracle.getProperty("oracle.host") == null) {
                    configOracle.setProperty("oracle.host", "127.0.0.1");
                    configOracle.save();
                }

                if (configOracle.getProperty("oracle.puerto") == null) {
                    configOracle.setProperty("oracle.puerto", "1521");
                    configOracle.save();
                }
                if (configOracle.getProperty("oracle.servicio") == null) {
                    configOracle.setProperty("oracle.servicio", "xe");
                    configOracle.save();
                }
                if (configOracle.getProperty("oracle.usuario") == null) {
                    configOracle.setProperty("oracle.usuario", "anita");
                    configOracle.save();
                }
                if (configOracle.getProperty("oracle.clave") == null) {
                    configOracle.setProperty("oracle.clave", "a");
                    configOracle.save();
                }
                Host = (String) configOracle.getProperty("oracle.host");
                Puerto = (String) configOracle.getProperty("oracle.puerto");
                Servicio = (String) configOracle.getProperty("oracle.servicio");
                Usuario = (String) configOracle.getProperty("oracle.usuario");
                Clave = (String) configOracle.getProperty("oracle.clave");
            } else if (general.BaseDatos.equals("sqlserver")) {
                PropertiesConfiguration configSqlServer = new PropertiesConfiguration("./quijotelu/SqlServer.properties");
                if (configSqlServer.getProperty("sqlserver.host") == null) {
                    configSqlServer.setProperty("sqlserver.host", "127.0.0.1");
                    configSqlServer.save();
                }                
                if (configSqlServer.getProperty("sqlserver.puerto") == null) {
                    configSqlServer.setProperty("oracle.puerto", "1521");
                    configSqlServer.save();
                }
                if (configSqlServer.getProperty("sqlserver.servicio") == null) {
                    configSqlServer.setProperty("sqlserver.servicio", "xe");
                    configSqlServer.save();
                }
                if (configSqlServer.getProperty("sqlserver.usuario") == null) {
                    configSqlServer.setProperty("sqlserver.usuario", "anita");
                    configSqlServer.save();
                }
                if (configSqlServer.getProperty("sqlserver.clave") == null) {
                    configSqlServer.setProperty("sqlserver.clave", "a");
                    configSqlServer.save();
                }
                Host = (String) configSqlServer.getProperty("sqlserver.host");
                Puerto = (String) configSqlServer.getProperty("sqlserver.puerto");
                Servicio = (String) configSqlServer.getProperty("sqlserver.servicio");
                Usuario = (String) configSqlServer.getProperty("sqlserver.usuario");
                Clave = (String) configSqlServer.getProperty("sqlserver.clave");
            }

        } catch (ConfigurationException ex) {
            Logger.getLogger(MotorConfiguracion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getHost() {
        return Host;
    }

    public String getPuerto() {
        return Puerto;
    }

    public String getServicio() {
        return Servicio;
    }

    public String getUsuario() {
        return Usuario;
    }

    public String getClave() {
        return Clave;
    }

}
