package src.test.main.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import src.main.model.Browser;
import src.main.service.BrowserService;

/**
 * Unit tests for the BrowserService class.
 * @version 1.0
 * @author Nguyen Huu Quang
 */
public class BrowserServiceTest {
    
    /**
     * Test the createBrowser method
     */
    @Test
    public void testCreateBrowser() {
        BrowserService browserService = new BrowserService();
        String url = "https://www.example.com";
        String type = "sample";
        String topic = "test";
        try {
            Browser browser = browserService.createBrowser(url, type, topic);
            assertNotNull(browser);
            assertTrue(url.equals(browser.getUrl().toString()));
            assertTrue(type.equals(browser.getType()));
            assertTrue(topic.equals(browser.getTopic()));
        } catch (Exception e) {
            fail("Exception occurred while creating Browser: " + e.getMessage());
        }
    }
}
