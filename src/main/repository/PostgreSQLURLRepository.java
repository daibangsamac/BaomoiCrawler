package src.main.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import src.main.infrastructure.database.PostgreConnectionPool;

/**
 * Warehouse postgre entry point
 * @version 1.0
 * @author Nguyen Huu Quang 
 */
public class PostgreSQLURLRepository implements URLsReadRepositoryInterface {
    private PostgreConnectionPool pool; 

    /**
     * Default constructor for Postgre ref
     */
    public PostgreSQLURLRepository() {
        pool = PostgreConnectionPool.getInstance();
    }

    /**
     * Check if url exist and HTMLSource not null
     * @param url
     * @return boolean 
     */
    @Override
    public boolean exists(String url) {
        String sql = "SELECT * FROM urls WHERE url = ?";
        Connection conn = null;
        try {
            synchronized (pool) {
                while (conn == null)
                    try {
                        conn = pool.getConnection();
                    } catch (RuntimeException e) {
                        // do sth
                    }
            }
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, url);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String htmlSource = rs.getString("htmlSource");
                if (htmlSource == null || htmlSource.equals(""))
                    return false;
                return true;
            }
            else 
                return false;
        } catch (Exception e) {
            // Log something here
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                pool.releaseConnection(conn);
            }
        }
    }

    /**
     * Get HTMLSource of url
     * @return String of HTMLSource
     */
    @Override
    public String getHTMLSource(String url) {
        String sql = "SELECT * FROM urls WHERE url = ?";
        Connection conn = null;
        try {
            synchronized (pool) {
                while (conn == null)
                    try {
                        conn = pool.getConnection();
                    } catch (RuntimeException e) {
                        // do sth
                    }
            }
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, url);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String htmlSource = rs.getString("htmlSource");
                if (htmlSource == null || htmlSource.equals(""))
                    return null;
                return htmlSource;
            }
            else 
                return null;
        } catch (Exception e) {
            // Log something here
            e.printStackTrace();
            return null;
        } finally {
            if (conn != null) {
                pool.releaseConnection(conn);
            }
        }
    }

    /**
     * Insert url and HTMLSource into database
     * @param url String
     * @param HTMLSource String
     */
    public void insert(String url, String HTMLSource) {
        String sql = "INSERT INTO urls(url,htmlSource) VALUES(?,?) "
            + "ON CONFLICT (url) " 
            + "DO UPDATE SET "
            + "htmlSource = ?";
        Connection conn = null;
        try {
            synchronized (pool) {
                while (conn == null)
                    try {
                        conn = pool.getConnection();
                    } catch (RuntimeException e) {
                        // do sth
                    }
            }
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, url);
            ps.setString(2,HTMLSource);
            ps.setString(3,HTMLSource);
            ps.executeUpdate();
        } catch (Exception e) {
            // Log something here
            e.printStackTrace();
        } finally {
            if (conn != null) {
                pool.releaseConnection(conn);
            }
        }
    }
}
