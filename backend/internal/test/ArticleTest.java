package backend.internal.test;

import backend.internal.source.Article;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.Files;

/**
 * Unit tests for the Article class.
 * @version 1.0
 * @author Nguyen Huu Quang
 */
public class ArticleTest {
    
    /**
     * Test the Article constructor and getters
     */
    @Test
    public void testArticleConstructorAndGetters() {
        String url = "https://example.com/article1";
        String time = "2024-06-01 10:00:00";
        String title = "Sample Article Title";
        String topic = "the-gioi";
        String content = "This is the content of the sample the-gioi article.";

        Article article = new Article(url, time, title, topic, content);

        assertEquals(url, article.getUrl());
        assertEquals(time, article.getTime());
        assertEquals(title, article.getTitle());
        assertEquals(topic, article.getTopic());
        assertEquals(content, article.getContent());
    }

    /**
     * Test the Article setters
     */
    @Test
    public void testArticleSetters() {
        Article article = new Article();

        String url = "https://example.com/article2";
        String time = "2024-06-02 11:00:00";
        String title = "Another Article Title";
        String topic = "the-gioi";
        String content = "This is another article content.";

        article.setUrl(url);
        article.setTime(time);
        article.setTitle(title);
        article.setTopic(topic);
        article.setContent(content);

        assertEquals(url, article.getUrl());
        assertEquals(time, article.getTime());
        assertEquals(title, article.getTitle());
        assertEquals(topic, article.getTopic());
        assertEquals(content, article.getContent());
    }

    /**
     * Test Article empty constructor
     */
    @Test
    public void testArticleEmptyConstructor() {
        Article article = new Article();

        assertNull(article.getUrl());
        assertNull(article.getTime());
        assertNull(article.getTitle());
        assertNull(article.getTopic());
        assertNull(article.getContent());
    }

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
        
        // Assuming createJson method creates a JSON file named after the article title
        article.createJson();

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
        
    }
}
