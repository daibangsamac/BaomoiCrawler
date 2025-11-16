package src.main.dao; 

import src.main.model.Article;
import java.util.List;

/**
 * Data Access Object (DAO) for managing Article entities in the database.
 * Provides CRUD operations: Create, Read, Update, Delete.
 * @version 1.0
 * @author Nguyen Huu Quang
 */
public interface ArticleDAO {

    /**
     * CREATE
     * Add a new article to the database
     * @param article The Article object to be added
     * @return true if the article was added successfully, false otherwise
     */
    public boolean addArticleToDB(Article article);

    /**
     * READ
     * Get an article by its URL
     * @param url The URL of the article
     * @return The Article object if found, null otherwise
     */
    public Article getArticleByUrlFromDB(String url);

    /**
     * Get all articles from the database
     * @return List of Article objects
     */
    public List<Article> getAllArticlesFromDB();
    
    /**
     * UPDATE
     * Update an existing article in the database
     * @param article article The Article object with updated information
     * @return true if the article was updated successfully, false otherwise
     */
    public boolean updateArticleToDB(Article article);

    /**
     * DELETE
     * Delete an article from the database by its url
     * @param url The url of the article to be deleted
     * @return true if the article was deleted successfully, false otherwise
     */
    public boolean deleteArticleByURLFromDB(String url);
}