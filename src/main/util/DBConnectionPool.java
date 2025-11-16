package src.main.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Database Connection Pool class for managing database connections
 */
public class DBConnectionPool {
    private static final String URL = "jdbc:mysql://localhost:3306/data";
    private static final String USER = "root";
    private static final String PASS = "11012004";

    private static final int INITIAL_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 20;

    private final List<Connection> availableConnections = new ArrayList<>();
    private final List<Connection> usedConnections = new ArrayList<>();

    private static DBConnectionPool instance;

    /**
     * Private constructor to initialize the connection pool
     * @throws Exception if a database access error occurs
     */
    private DBConnectionPool() {
        try {
            for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
                availableConnections.add(createConnection());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the singleton instance of DBConnectionPool
     * @return The DBConnectionPool instance
     */
    public static synchronized DBConnectionPool getInstance() {
        if (instance == null) {
            instance = new DBConnectionPool();
        }
        return instance;
    }

    /**
     * Create a new database connection
     * @return A new Connection object
     * @throws Exception if a database access error occurs
     */
    private Connection createConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    /**
     * Get a connection from the pool
     * @return A Connection object
     * @throws Exception if a database access error occurs
     */
    public synchronized Connection getConnection() throws Exception {
        if (availableConnections.isEmpty()) {
            if (usedConnections.size() < MAX_POOL_SIZE) {
                availableConnections.add(createConnection());
            } else {
                throw new RuntimeException("Maximum pool size reached!");
            }
        }

        Connection conn = availableConnections.remove(availableConnections.size() - 1);
        usedConnections.add(conn);
        return conn;
    }

    /**
     * Release a connection back to the pool
     * @param conn The Connection object to be released
     */
    public synchronized void releaseConnection(Connection conn) {
        usedConnections.remove(conn);
        availableConnections.add(conn);
    }

    /**
     * Shutdown the connection pool and close all connections
     */
    public synchronized void shutdown() {
        usedConnections.forEach(this::closeQuietly);
        availableConnections.forEach(this::closeQuietly);
    }

    /**
     * Close a connection quietly
     * @param conn The Connection object to be closed
     */
    private void closeQuietly(Connection conn) {
        try { conn.close(); } catch (Exception ignored) {}
    }
}
