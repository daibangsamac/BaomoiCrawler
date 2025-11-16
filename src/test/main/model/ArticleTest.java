package src.test.main.model;

import org.junit.Test;

import src.main.model.Article;
import static org.junit.Assert.*;


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

    
}
