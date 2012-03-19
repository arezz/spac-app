/*
 * Copyright 2005 RAYNET s.r.o., All rights reserved.
 * RAYNET s.r.o. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * www.raynet.cz
 */
package cz.raynet.core;

import java.sql.Connection;
import java.sql.SQLException;

public class DAO implements IDAO {

    private final ConnectionConfig fConnectionConfig;

    private Connection fConnection;

    public DAO() {
        this(AppConnectionConfig.getConnectionConfig());
    }

    public DAO(ConnectionConfig config) {
        fConnectionConfig = config;
    }

    public Connection getConnection() {
        
            if (fConnection != null) {
                try {
                    fConnection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            fConnection = DBConnection.createConnection(
                    fConnectionConfig.getUrl(),
                    fConnectionConfig.getUser(),
                    fConnectionConfig.getPassword(),
                    false
            );

        return fConnection;
    }

    public void close() {
        try {
            if (fConnection != null && !fConnection.isClosed()) {
                fConnection.close();
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public void start() {
        try {
            getConnection().setAutoCommit(false);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public void commit() {
        try {
            getConnection().commit();
            getConnection().setAutoCommit(true);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public void rollback() {
        try {
            getConnection().rollback();
            getConnection().setAutoCommit(true);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

}
