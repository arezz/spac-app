package cz.raynet.core;

public class AppConnectionConfig {

    private static ConnectionConfig sConnectionConfig;

    private AppConnectionConfig() {
    }
    
    public static void initConnectionConfig() {
        sConnectionConfig = new ConnectionConfig();
    }

    public static ConnectionConfig getConnectionConfig() {
        if (sConnectionConfig == null) {
            throw new IllegalStateException("sConnectionConfig has not been initialized");
        }
        return sConnectionConfig;
    }

}
