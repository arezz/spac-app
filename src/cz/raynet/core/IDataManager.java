/*
 * Copyright 2005 RAYNET s.r.o., All rights reserved.
 * RAYNET s.r.o. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * www.raynet.cz
 */
package cz.raynet.core;

public interface IDataManager {
    public void connect(ConnectionConfig config);
    public void disconnect();
}
