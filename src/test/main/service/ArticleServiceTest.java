package src.test.main.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.nio.file.Files;

import org.junit.Test;

import src.main.model.Article;
import src.main.service.ArticleService;

/**
 * Unit tests for the ArticleService class.
 * @version 1.0
 * @author Nguyen Huu Quang
 */
public class ArticleServiceTest {
    /**
     * Test the createJson method
     */
    @Test
    public void testJsonCreation() {
        String url = "https://example.com/article3";
        String time = "2024-06-03 12:00:00";
        String title = "JSON Article Title";
        String topic = "sample";
        String content = "This article is for testing JSON creation.";

        File tempDir = new File("output/sample");
        if (!tempDir.exists()) {
            // Create the directory if it does not exist
            boolean created = tempDir.mkdirs(); 
            if (created) {
                System.out.println("Created directory: " + tempDir.getAbsolutePath());
            } else {
                System.out.println("Can't create temporary directory for test at: " + tempDir.getAbsolutePath());
            }
        }
        
        tempDir.deleteOnExit();
        
        Article article = new Article(url, time, title, topic, content);
        ArticleService articleService = new ArticleService();
        // Assuming createJson method creates a JSON file named after the article title
        try {
            articleService.createJson(article);
        } catch (Exception e) {
            fail("Exception occurred while creating JSON: " + e.getMessage());
        }
        String expectedFileName = "JSON Article Title.json";
        File outputFile = new File(tempDir, expectedFileName);

        assertTrue(outputFile.getAbsolutePath() + "JSON file should be created",outputFile.exists());

        try {
            String fileContent = new String(Files.readAllBytes(outputFile.toPath()));

            assertTrue(fileContent.contains("\"title\": \"" + title + "\""));
            assertTrue(fileContent.contains("\"url\": \""+ url + "\""));
            assertTrue(fileContent.contains("\"time\": \""+ time + "\""));
            assertTrue(fileContent.contains("\"topic\": \""+ topic + "\""));
            assertTrue(fileContent.contains("\"content\": \""+ content + "\""));
        }
        catch  (Exception e) {
            fail("Exception occurred while reading JSON file: " + e.getMessage());}
        outputFile.deleteOnExit();
    }

    /**
     * Test CRUD operations via ArticleService
     */
    @Test
    public void testArticleCRUDService() {
        ArticleService articleService = new ArticleService();
        
        String url = "https://example.com/article1";
        String time = "2024-06-01 10:00:00";
        String title = "Sample Article Title";
        String topic = "the-gioi";
        String content = "This is the content of the sample the-gioi article.";

        Article article = new Article(url, time, title, topic, content);

        // CREATE
        articleService.addArticleToDB(article);
        
        Article article2 = articleService.getArticleByUrlFromDB(url);
        assertNotNull(article2);
        assertEquals(url, article2.getUrl());
        
        // READ ALL
        java.util.List<Article> articles = articleService.getAllArticlesFromDB();
        assertNotNull(articles);
        assertTrue(articles.size() > 0);

        // UPDATE
        article.setTitle("Updated Article Title");
        articleService.updateArticleInDB(article);
        Article updatedArticle = articleService.getArticleByUrlFromDB(url);
        assertEquals("Updated Article Title", updatedArticle.getTitle());

        // DELETE
        articleService.deleteArticleByUrlInDB(url);
        Article article3 = articleService.getArticleByUrlFromDB(url);
        assertNull(article3);
    }
}
