/*
 * Copyright 20010 RAYNET s.r.o., All rights reserved.
 * RAYNET s.r.o. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * www.raynet.cz
 */
package cz.raynet.core;

import java.sql.Connection;
import java.sql.SQLException;


public abstract class AbstractDataManager implements IDataManager {

    private IDAO fDAO;

    protected AbstractDataManager() {
        connect(AppConnectionConfig.getConnectionConfig());
    }

    protected AbstractDataManager(ConnectionConfig connectionConfig) {
        if (connectionConfig == null) {
            throw new NullPointerException("connectionConfig cannot be NULL");
        }
        connect(connectionConfig);
    }

    public IDAO getDAO() {
        return fDAO;
    }

    public void connect(ConnectionConfig config) {
        fDAO = new DAO(config);
    }

    public void disconnect() {
        fDAO.close();
    }

    protected void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e1) {
                System.err.println(e1.toString());
            }
        }
    }

    protected void rollbackConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException e1) {
                System.err.println(e1.toString());
            }
        }
    }

}
