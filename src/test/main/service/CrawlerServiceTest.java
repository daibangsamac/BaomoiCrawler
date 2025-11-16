package src.test.main.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import src.main.model.Crawler;
import src.main.service.CrawlerService;

/**
 * Test class for CrawlerService
 * <p>This class contains unit tests for the CrawlerService class.</p>
 * @version 1.0
 * @author Nguyen Huu Quang
 */
public class CrawlerServiceTest {
    
    /**
     * Test method for createCrawler
     */
    @Test
    public void testCreateCrawler() {
        CrawlerService crawlerService = new CrawlerService();
        String url = "https://baomoi.com";
        String topic = "test";
        Crawler crawler = crawlerService.createCrawler(url, topic);
        assertNotNull(crawler);
        assertTrue(crawler.getUrl().equals(url));
        assertTrue(crawler.getTopic().equals(topic));
    }
}
