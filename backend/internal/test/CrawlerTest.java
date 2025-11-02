package backend.internal.test;

import backend.internal.source.Crawler;
import backend.internal.source.Article;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.util.LinkedList;

/**
 * Unit tests for the Crawler class.
 * @version 1.0
 * @author Nguyen Huu Quang
 */
public class CrawlerTest {
  
    /**
     * Test Crawler getters and setters
     */
    @Test
    public void testCrawlerGettersAndSetters() {
        String url = "https://www.baomoi.com/the-gioi";
        String topic = "the-gioi";
        Crawler crawler = new Crawler(url, topic);

        assertEquals("URL getter should return the correct URL", url, crawler.getUrl());
        assertEquals("Topic getter should return the correct topic", topic, crawler.getTopic());

        String newUrl = "https://www.baomoi.com/kinh-te";
        String newTopic = "kinh-te";
        crawler.setUrl(newUrl);
        crawler.setTopic(newTopic);

        assertEquals("URL setter should update the URL correctly", newUrl, crawler.getUrl());
        assertEquals("Topic setter should update the topic correctly", newTopic, crawler.getTopic());
    }

    /**
     * Test creating JSON file from articles
     */
    @Test
    public void testCreateJsonFile() {
        String url = "https://example.com/article1";
        String time = "2024-06-01 10:00:00";
        String title = "Sample Article Title";
        String topic = "the-gioi";
        String content = "This is the content of the sample the-gioi article.";

        Article article = new Article(url, time, title, topic, content);
        LinkedList<Article> articles = new LinkedList<>();
        
        articles.add(article);
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
        Crawler crawler = new Crawler("https://www.baomoi.com", "sample");
        crawler.setArticles(articles);
        crawler.createJson();
        
        String expectedFileName = "JSON Article Title.json";
        File outputFile = new File(tempDir, expectedFileName);
        assertTrue("JSON file should be created", outputFile.exists());
    
    }
}
