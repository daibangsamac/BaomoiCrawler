package src.test.main.controller;

import org.junit.Test;

import src.main.controller.CrawlerController;
import src.main.model.Crawler;

import static org.junit.Assert.*;

/**
 * Unit tests for the CrawlerController class.
 * @version 1.0
 * @author Nguyen Huu Quang
 */
public class CrawlerControllerTest {
    /**
     * Test handleCreateCrawler method
     */
    @Test
    public void testHandleCreateCrawler() {
    CrawlerController crawlerController = new CrawlerController();
    String url = "https://example.com";
    String topic = "sample";
    Crawler crawler = crawlerController.handleCreateCrawler(url, topic);
    assertNotNull(crawler);
    assertEquals(url, crawler.getUrl());
    assertEquals(topic, crawler.getTopic());
    }
}
