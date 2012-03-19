/*
 * Copyright 2005 RAYNET s.r.o., All rights reserved.
 * RAYNET s.r.o. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * www.raynet.cz
 */
package cz.raynet.core;

import java.sql.*;
import java.util.Properties;

class DBConnection {


    public static Connection createConnection(String url, String user, String psswd, boolean shutdown) {
        Connection connection;
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            if (classLoader == null) {
                classLoader = DBConnection.class.getClassLoader();
            }

            Properties prop = new Properties();
            prop.setProperty("user", user);
            prop.setProperty("password", psswd);

            Class driverClass = (classLoader.loadClass("com.mysql.jdbc.Driver"));
            Driver driver = (Driver) driverClass.newInstance();

            connection = driver.connect(url, prop);

        } catch (Exception e) {
            if (shutdown) {
                throw new Error("Připojení k databázi selhalo", e);
            } else {
                throw new IllegalStateException("Připojení k databázi selhalo", e);
            }
        }
        return connection;
    }

    public static Connection createConnection(String url, String user, String psswd) {
        return createConnection(url, user, psswd, true);
    }

}
