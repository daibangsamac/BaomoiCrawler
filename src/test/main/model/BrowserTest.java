package src.test.main.model;

import org.junit.Test;

import src.main.model.Browser;

import static org.junit.Assert.*;

import java.io.IOException;

/**
 * Unit tests for the Browser class.
 * @version 1.0
 * @author Nguyen Huu Quang
 */
public class BrowserTest {
    
    /**
     * Test Browser constructor with valid URL
     */
    @Test
    public void testBrowserConstructorWithValidUrl() {
        String validUrl = "https://www.baomoi.com/the-gioi.epi";
        try {
            Browser browser = new Browser(validUrl, "summary", "the-gioi");
            assertEquals("URL should be set correctly", validUrl, browser.getUrl().toString());
            assertEquals("Type should be set correctly", "summary", browser.getType());
            assertEquals("Topic should be set correctly", "the-gioi", browser.getTopic());
        } catch (IOException e) {
            fail("IOException should not be thrown for a valid URL");
        }
    }

    /**
     * Test Browser constructor with invalid URL
     */
    @Test
    public void testBrowserConstructorWithInvalidUrl() {
        String invalidUrl = "htp://invalid-url";
        try {
            Browser browser = new Browser(invalidUrl, "summary", "the-gioi");
            assertTrue("Topic should be null", browser.getTopic() == null);
            assertTrue("Type should be null", browser.getType() == null);
            fail("MalformedURLException should be thrown for an invalid URL");
        } catch (IOException e) {
            // Expected exception
        }
    }

    /**
     * Test Browser getters
     */
    @Test
    public void testBrowserGetters() {
        String url = "https://www.baomoi.com/the-gioi.epi";
        String type = "summary";
        String topic = "the-gioi";
        try {
            Browser browser = new Browser(url, type, topic);
            assertEquals("getUrl should return the correct URL", url, browser.getUrl().toString());
            assertEquals("getType should return the correct type", type, browser.getType());
            assertEquals("getTopic should return the correct topic", topic, browser.getTopic());
        } catch (IOException e) {
            fail("IOException should not be thrown for a valid URL");
        }
    }

    /**
     * Test Browser setters
     */
    @Test
    public void testBrowserSetters() {
        String url = "https://www.baomoi.com/the-gioi.epi";
        String type = "summary";
        String topic = "the-gioi";
        try {
            Browser browser = new Browser(url, type, topic);
            String newUrl = "https://www.baomoi.com/kinh-te.epi";
            String newType = "article";
            String newTopic = "kinh-te";

            browser.setUrl(newUrl);
            browser.setType(newType);
            browser.setTopic(newTopic);

            assertEquals("setUrl should update the URL correctly", newUrl, browser.getUrl().toString());
            assertEquals("setType should update the type correctly", newType, browser.getType());
            assertEquals("setTopic should update the topic correctly", newTopic, browser.getTopic());
        } catch (IOException e) {
            fail("IOException should not be thrown for a valid URL");
        }
    }
}
