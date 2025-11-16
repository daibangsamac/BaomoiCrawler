package src.main.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import src.main.model.Article;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import src.main.util.DBConnectionPool;

/**
 * Class ArticleDAOImplement
 * <p>This class implements the ArticleDAO interface to provide database operations for Article objects.</p>
 * @version 1.0
 * @author Nguyen Huu Quang	
 */
public class ArticleDAOImplement implements ArticleDAO {
    
    DBConnectionPool pool;
    /**
     * Constructor of ArticleDAOImplement class
     * Get the instance of DBConnectionPool
     */
    public ArticleDAOImplement() {
        this.pool = DBConnectionPool.getInstance();
    }

    // TODO: Implement the fetched htmlSource and lastFetchedTime fields in the database operations
    /**
     * CREATE
     * Add a new article to the database
     * @param article The Article object to be added
     * @return true if the article was added successfully, false otherwise
     */
    @Override
    public boolean addArticleToDB(Article article) {
        String sql = "INSERT INTO articles(url, htmlSource, title, publishDate, topic, content, lastFetchedTime)" + 
        " VALUES (?, ?, ?, ?, ?, ?, ?)"; 
        Connection conn = null;
        try {
            conn = pool.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, article.getUrl());
            stmt.setString(2, article.getHtmlSource());
            stmt.setString(3, article.getTitle());
            stmt.setString(4, article.getTime());
            stmt.setString(5, article.getTopic());
            stmt.setString(6, article.getContent());
            stmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) 
                pool.releaseConnection(conn);
        }
        
    }

    /**
     * READ
     * Get an article by its URL
     * @param url The URL of the article
     * @return The Article object if found, null otherwise
     */
    @Override
    public Article getArticleByUrlFromDB(String url) {
        String sql = "SELECT * FROM articles WHERE url = ?";
        Connection conn = null;
        try {
            conn = pool.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, url);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Article(
                        rs.getString("url"),
                        rs.getString("publishDate"),
                        rs.getString("title"),
                        rs.getString("topic"),
                        rs.getString("content")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) pool.releaseConnection(conn);
        }
        return null;
    }

    /**
     * Get all articles from the database
     * @return List of Article objects
     */
    @Override
    public List<Article> getAllArticlesFromDB() {
        List<Article> list = new ArrayList<>();
        String sql = "SELECT * FROM articles";
        Connection conn = null;
        try {
            conn = pool.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql); 

            while (rs.next()) {
                list.add(new Article (
                        rs.getString("url"),
                        rs.getString("publishDate"),
                        rs.getString("title"),
                        rs.getString("topic"),
                        rs.getString("content")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) pool.releaseConnection(conn);
        }
        return list;
    }

    /**
     * UPDATE
     * Update an existing article in the database
     * @param article article The Article object with updated information
     * @return true if the article was updated successfully, false otherwise
     */
    @Override
    public boolean updateArticleToDB(Article article) {
        String sql = "UPDATE articles" + 
            " SET htmlSource=?, title=?, publishDate=?, topic=?, content=?, lastFetchedTime=?" +
            " WHERE url=?";
        Connection conn = null;
        try {
            conn = pool.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, article.getHtmlSource());
            stmt.setString(2, article.getTitle());
            stmt.setString(3, article.getTime());
            stmt.setString(4, article.getTopic());
            stmt.setString(5, article.getContent());
            stmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            stmt.setString(7, article.getUrl());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) pool.releaseConnection(conn);
        }
    }

    /**
     * DELETE
     * Delete an article from the database by its url
     * @param url The url of the article to be deleted
     * @return true if the article was deleted successfully, false otherwise
     */
    @Override
    public boolean deleteArticleByURLFromDB(String url) {
        String sql = "DELETE FROM articles WHERE url=?";
        Connection conn = null;
        try {
            conn = pool.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, url);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) pool.releaseConnection(conn);
        }
    }
}
