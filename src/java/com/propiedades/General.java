/*
 * Copyright (C) 2015 jorjoluiso
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
public class General {

    public String BaseDatos;
    public String Publicidad;
    public String Nombre;

    public General() {
        try {
            PropertiesConfiguration config = new PropertiesConfiguration("./quijotelu/General.properties");
            if (config.getProperty("general.BaseDatos") == null) {
                /*
                 Conexión con base de datos:
                 oracle, sqlserver
                 */
                config.setProperty("general.BaseDatos", "oracle");
                config.save();
            }         
            if (config.getProperty("general.Publicidad") == null) {
                config.setProperty("general.Publicidad", "si");
                config.save();
            }
            if (config.getProperty("general.Nombre") == null) {
                config.setProperty("general.Nombre", "QuijoteLu");
                config.save();
            }
            BaseDatos = (String) config.getProperty("general.BaseDatos");
            Publicidad = (String) config.getProperty("general.Publicidad");
            Nombre = (String) config.getProperty("general.Nombre");
            
        } catch (ConfigurationException ex) {
            Logger.getLogger(General.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
