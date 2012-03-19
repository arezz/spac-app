/*
 * Copyright 2005 RAYNET s.r.o., All rights reserved.
 * RAYNET s.r.o. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * www.raynet.cz
 */
package cz.raynet.core;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ConnectionConfig {

    private static final String DB_DRIVER_INIT_PARAM = "db_driver";
    private static final String DB_URL_INIT_PARAM = "db_url";
    private static final String DB_USER_INIT_PARAM = "db_user";
    private static final String DB_PSSWD_INIT_PARAM = "db_psswd";
    private static final String DB_MAXCOUNT_INIT_PARAM = "db_maxcount";
    private static final String DB_MINCOUNT_INIT_PARAM = "db_mincount";

    private final String fDriverClass;
    private final String fUrl;
    private final String fUser;
    private final String fPassword;
    private final int fMaxCount;
    private final int fMinCount;


    public ConnectionConfig() {
        String resourceClassName = "cz.raynet.settings";
        ResourceBundle resourceBundle;
        try {
            resourceBundle = ResourceBundle.getBundle(resourceClassName);
        } catch (MissingResourceException e) {
            throw new IllegalArgumentException("resourceClassName is invalid (" + resourceClassName + ")", e);
        }
        fDriverClass = resourceBundle.getString(DB_DRIVER_INIT_PARAM);
        fUrl = resourceBundle.getString(DB_URL_INIT_PARAM);
        fUser = resourceBundle.getString(DB_USER_INIT_PARAM);
        fPassword = resourceBundle.getString(DB_PSSWD_INIT_PARAM);
        fMaxCount = Integer.parseInt(resourceBundle.getString(DB_MAXCOUNT_INIT_PARAM));
        fMinCount = Integer.parseInt(resourceBundle.getString(DB_MINCOUNT_INIT_PARAM));
    }

    public String getDriverClass() {
        return fDriverClass;
    }

    public String getUrl() {
        return fUrl;
    }

    public int getMaxCount() {
        return fMaxCount;
    }

    public int getMinCount() {
        return fMinCount;
    }

    public String getUser() {
        return fUser;
    }

    public String getPassword() {
        return fPassword;
    }


    @Override
    public String toString() {
        return new StringBuffer(getClass().getName())
                .append("[")
                .append("driverClass=").append(fDriverClass)
                .append(", url=").append(fUrl)
                .append(", user=").append(fUser)
                .append(", psswd=").append(fPassword)
                .append(", maxCount=").append(fMaxCount)
                .append(", minCount=").append(fMinCount)
                .append("]")
                .toString();
    }
}
