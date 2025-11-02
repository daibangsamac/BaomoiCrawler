package backend.internal.test;

import backend.internal.source.Browser;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.LinkedList;

/**
 * Unit tests for the Browser class.
 * @version 1.0
 * @author Nguyen Huu Quang
 */
public class BrowserTest {
    
    /**
     * Test fetching a valid URL
     */
    @Test
    public void testFetchURLValid() {
        String validUrl = "https://www.baomoi.com/";
        try {
            Browser browser = new Browser(validUrl, "summary", "the-gioi");
            // If no exception is thrown, the test passes
        } catch (Exception e) {
            fail("Exception should not be thrown for a valid URL: " + e.getMessage());
        }
    }

    /**
     * Test fetching an invalid URL
     */
    @Test
    public void testFetchURLInvalid() {
        String invalidUrl = "abcdxyazmx";
        try {
            Browser browser = new Browser(invalidUrl, "summary", "the-gioi");
            fail("Exception should be thrown for an invalid URL");
        } catch (Exception e) {
            // Expected exception, test passes
        }
    }

    /**
     * Test getting links of articles from a summary page
     */
    @Test
    public void testGetLinksOfArticles() {
        String summaryPageUrl = "https://www.baomoi.com/the-gioi.epi";
        try {
            Browser browser = new Browser(summaryPageUrl, "summary", "the-gioi");
            LinkedList<String> articleLinks = browser.getLinksOfArticles();
            assertNotNull("Article links should not be null", articleLinks);
            assertTrue("Article links should contain at least one link", articleLinks.size() > 0);
        } catch (Exception e) {
            fail("Exception should not be thrown while fetching article links: " + e.getMessage());
        }
    }

    /**
     * Test parsing an article page
     */
    @Test
    public void testParseArticlePage() {
        String summaryPageUrl = "https://www.baomoi.com/the-gioi.epi";
        Browser browser;
        LinkedList<String> articleLinks = null;
        try {
            browser = new Browser(summaryPageUrl, "summary", "the-gioi");
            articleLinks = browser.getLinksOfArticles();
        } catch (Exception e) {
            fail("Exception should not be thrown while parsing article page: " + e.getMessage());
        }
        
        try {
            Browser browser2 = new Browser(articleLinks.getFirst(), "article", "the-gioi");
            backend.internal.source.Article article = browser2.parseArticlePage();
            assertNotNull("Parsed article should not be null", article);
            assertNotNull("Article title should not be null", article.getTitle());
            assertNotNull("Article content should not be null", article.getContent());
        } catch (Exception e) {
            fail("Exception should not be thrown while parsing article page: " + e.getMessage());
        }
    }
}
