package src.main.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;

import src.main.infrastructure.database.MySQLConnectionPool;

import src.main.model.Article;

public class MySQLRepository implements URLsWriteRepositoryInterface{
    private MySQLConnectionPool pool;

    public MySQLRepository() {
        pool = MySQLConnectionPool.getInstance();
    }
    
    /**
     * Insert if not exist else update
     * @param article 
     */
    @Override
    public void upsert(Article article) {
        String url = article.getUrl();
		int id = article.getId();
		String og_url = article.getOg_url();
		String author = article.getAuthor();
		String title = article.getTitle();
		Instant publish_date = article.getPublish_date();
		String topic = article.getTopic();
		String body = article.getBody();
		String keywords = article.getKeywords();

        Instant lastFetched = Instant.now();

        String sql = "INSERT INTO metadata (url, article_id, original_url, author, title, publishdate, topic, body, keyword, lastfetched) "
           + "VALUES (?,?,?,?,?,?,?,?,?,?) "
           + "ON DUPLICATE KEY UPDATE "
           + "url=VALUES(url), "
           + "original_url=VALUES(original_url), "
           + "author=VALUES(author), "
           + "title=VALUES(title), "
           + "publishdate=VALUES(publishdate), "
           + "topic=VALUES(topic), "
           + "body=VALUES(body), "
           + "keyword=VALUES(keyword), "
           + "lastfetched=VALUES(lastfetched)";
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
            ps.setString(1, url);                 // url
            ps.setInt(2, id);                     // article_id
            ps.setString(3, og_url);         // original_url
            ps.setString(4, author);              // author
            ps.setString(5, title);               // title
            ps.setTimestamp(6, Timestamp.from(publish_date));      // publishdate (Timestamp)
            ps.setString(7, topic);               // topic
            ps.setString(8, body);                // body
            ps.setString(9, keywords);             // keyword           // trend
            ps.setTimestamp(10, Timestamp.from(lastFetched));     // lastfetched (Timestamp)
            
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

    /**
     * Delete by url
     * @param url
     */
    @Override
    public void delete(String url) {
        String sql = "DELETE FROM metadata WHERE url = ?";
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

            ps.executeUpdate(sql);
        } catch (Exception e) {
            // Log something here
            e.printStackTrace();
        } finally {
            if (conn != null) {
                pool.releaseConnection(conn);
            }
        }
    }
    
    public Instant getLastFetch(String url) {
        String sql = "SELECT lastfetched FROM metadata WHERE url = ?";
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
                Timestamp ts = rs.getTimestamp("lastfetched");
                Instant lastFetched = ts.toInstant();
                return lastFetched;
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
}
