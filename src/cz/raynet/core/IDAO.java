/*
 * Copyright 2005 RAYNET s.r.o., All rights reserved.
 * RAYNET s.r.o. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * www.raynet.cz
 */
package cz.raynet.core;

import java.sql.Connection;

public interface IDAO {
    Connection getConnection();

    void close();

    void start();

    void commit();

    void rollback();

    IDAO SHARED_APP_DAO = new DAO(AppConnectionConfig.getConnectionConfig());
}
