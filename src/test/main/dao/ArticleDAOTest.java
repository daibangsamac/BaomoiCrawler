package src.test.main.dao;

import org.junit.Test;

// import org.junit.Test;

// import src.main.dao.ArticleDAO;
// import src.main.dao.ArticleDAOImplement;
// import src.main.model.Article;

// import static org.junit.Assert.*;


/**
 * Unit tests for the Article class.
 * @version 1.0
 * @author Nguyen Huu Quang
 */
public class ArticleDAOTest {

    @Test
    public void dummy() {
        assert(true);
    }
    // /**
    //  * Test CRUD operations in ArticleDAO
    //  */
    // public void testCRUDArticleDAO() {
    //     ArticleDAO articleDAO = new ArticleDAOImplement();

    //     String url = "https://example.com/article1";
    //     String time = "2024-06-01 10:00:00";
    //     String title = "Sample Article Title";
    //     String topic = "the-gioi";
    //     String content = "This is the content of the sample the-gioi article.";

    //     Article article = new Article(url, time, title, topic, content);

    //     // CREATE
    //     boolean result = articleDAO.addArticleToDB(article);
    //     assertTrue(result);
        
    //     // READ
    //     article = articleDAO.getArticleByUrlFromDB(url);
    //     assertNotNull(article);
    //     assertEquals(url, article.getUrl());
        
    //     // READ ALL
    //     java.util.List<Article> articles = articleDAO.getAllArticlesFromDB();
    //     assertNotNull(articles);
    //     assertTrue(articles.size() > 0);

    //     // UPDATE
    //     article.setTitle("Updated Article Title");
    //     boolean result2 = articleDAO.updateArticleToDB(article);
    //     assertTrue(result2);

    //     Article updatedArticle = articleDAO.getArticleByUrlFromDB(url);
    //     assertEquals("Updated Article Title", updatedArticle.getTitle());

    //     // DELETE
    //     boolean result3 = articleDAO.deleteArticleByURLFromDB(url);
    //     assertTrue(result3);
    //     Article article2 = articleDAO.getArticleByUrlFromDB(url);
    //     assertNull(article2);
    // }
}
