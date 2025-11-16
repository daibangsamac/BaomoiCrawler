package src.test.main.model;

import org.junit.Test;

import src.main.model.Crawler;

import static org.junit.Assert.*;

/**
 * Unit tests for the Crawler class.
 * @version 1.0
 * @author Nguyen Huu Quang
 */
public class CrawlerTest {
  
    /**
     * Test Crawler Constructor, getters and setters
     */
    @Test
    public void testCrawlerConstructorGettersAndSetters() {
        String url = "https://www.baomoi.com/the-gioi";
        String topic = "the-gioi";
        Crawler crawler = new Crawler(url, topic);

        assertNotNull("Crawler instance should not be null", crawler);

        assertEquals("URL getter should return the correct URL", url, crawler.getUrl());
        assertEquals("Topic getter should return the correct topic", topic, crawler.getTopic());

        String newUrl = "https://www.baomoi.com/kinh-te";
        String newTopic = "kinh-te";
        crawler.setUrl(newUrl);
        crawler.setTopic(newTopic);

        assertEquals("URL setter should update the URL correctly", newUrl, crawler.getUrl());
        assertEquals("Topic setter should update the topic correctly", newTopic, crawler.getTopic());
    }

}
